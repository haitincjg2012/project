package com.ph.shopping.common.core.other.deliver;

import com.alibaba.fastjson.JSONObject;
import com.ph.shopping.common.core.cache.redis.ICacheService;
import com.ph.shopping.common.core.other.deliver.config.AliDeliverConfig;
import com.ph.shopping.common.util.result.Result;
import com.show.api.ShowapiRequest;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

/**
 * 阿里快递查询类
 *
 * @author 郑朋
 * @create 2017/6/21
 **/
@Service
public class AliDeliver {

    private static final Logger log = LoggerFactory.getLogger(AliDeliver.class);

    @Autowired
    ICacheService<String,String> cacheService;


    /**
     * @methodname getAliDeliver 的描述：查询包裹物流信息
     * @param com
     *              快递公司字母简称,可以从"快递公司查询 中查到该信息 如不知道快递公司名,可以使用"auto"代替,此时将自动识别快递单号所属公司.
     *              但不推荐大面积使用auto,因为有部分快递公司的单号相互之间有重叠,识别后查询易导致查询失败
     * @param nu
     *              快递单号
     * @return com.ph.shopping.common.util.result.Result
     * @author 郑朋
     * @create 2017/6/21
     */
    public Result getAliDeliver(String com, String nu) {
        log.info("查询物流信息接口入参，com={}，nu={}", com, nu);
        Result result = new Result(false,"300","系统繁忙，请重试！");
        try {
            if (StringUtils.isEmpty(nu)) {
                result.setMessage("物流号不能为空");
                return result;
            }
            JSONObject object = null;
            //从redis中查询物流信息，存在直接返回给页面，不存在则调用ali接口查询
            Object cacheDeliverInfo = cacheService.get(nu);
            if (null == cacheDeliverInfo) {
                // 如不知道快递公司名,可以使用"auto"代替
                com = StringUtils.isEmpty(com) ? AliDeliverConfig.AUTO : com;
                ShowapiRequest req = new ShowapiRequest(AliDeliverConfig.DELIVER_URL,AliDeliverConfig.APP_CODE)
                        .addTextPara("com",com)
                        .addTextPara("nu",nu);
                //查询物流信息
                byte b[] = req.getAsByte();
                String content = new String(b,"utf-8");
                log.info("调用ali物流详细接口,返回值为content={}",content);
                if (StringUtils.isNotBlank(content)) {
                    JSONObject json=JSONObject.parseObject(content);
                    object = json.getJSONObject("showapi_res_body");
                    if (null != object) {
                        //查询信息缓存在redis中，有效时间为2小时
                        cacheService.set(nu,object.toJSONString());
                        cacheService.expire(nu,AliDeliverConfig.TIMEOUT, TimeUnit.HOURS);
                    }
                }
            } else {
                object = JSONObject.parseObject(cacheDeliverInfo.toString());
            }
            if (null != object) {
                result.setMessage("查询物流信息成功");
                result.setSuccess(true);
                result.setCode("200");
                result.setData(object);
            }
        } catch (Exception e) {
            log.error("快递查询错误,e={}",e);
        }
        log.info("查询物流号为：nu={},result={}",nu,JSONObject.toJSONString(result));
        return result;
    }


    public static void main(String[] args) {
        AliDeliver aliDeliver = new AliDeliver();
        aliDeliver.getAliDeliver("shunfeng","613191171759");
    }
}
