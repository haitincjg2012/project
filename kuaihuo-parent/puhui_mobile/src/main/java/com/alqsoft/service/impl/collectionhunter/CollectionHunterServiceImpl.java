package com.alqsoft.service.impl.collectionhunter;

import com.alqsoft.dao.collectionhunter.CollectionHunterDao;
import com.alqsoft.entity.collectionhunter.CollectionHunter;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.member.Member;
import com.alqsoft.rpc.mobile.RpcCollectionHunterService;
import com.alqsoft.service.collectionhunter.CollectionHunterService;
import com.alqsoft.service.member.MemberService;
import com.alqsoft.utils.HunterLevelEnums;
import com.alqsoft.utils.NumberFormat;
import com.alqsoft.utils.StatusCodeEnums;
import com.alqsoft.vo.CollectionHunterVO;
import com.google.common.collect.Maps;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Xuejizheng
 * @date 2017-03-11 10:26
 */
@Service
@Transactional
public class CollectionHunterServiceImpl implements CollectionHunterService {
    private static Logger log = LoggerFactory.getLogger(CollectionHunterServiceImpl.class);

    @Autowired
    private CollectionHunterDao collectionHunterDao;

    @Autowired
    private RpcCollectionHunterService rpcCollectionHunterService;
    @Autowired
    private MemberService memberService;

    /**
     * 收藏、取消收藏
     * @param id
     * @param hid
     * @param type
     * @return
     */
    @Override
    public Result collect(Long id, Long hid, Integer type) {
    		
    	Long hunterid=memberService.getHunteridByMemberid(id);
    	if(hunterid!=null){
    		return ResultUtils.returnError("只有商户能进行此操作，请登录商户");
    	}
        if (Objects.isNull(id)|| Objects.isNull(hid) || Objects.isNull(type)){
            return ResultUtils.returnError(StatusCodeEnums.ERROR_PARAM.getMsg());
        }
        try {
            CollectionHunterVO  collectionHunterVO = collectionHunterDao.getCollectionHunter(id,hid);
            //收藏
            if (collectionHunterVO==null && 0==type){
            	Member memberdb = memberService.getMemberByHunterId(hid);
            	if(memberdb==null){
            		 return ResultUtils.returnError("该用户会员信息不存在，无法收藏");
            	}
            	if(id.longValue()==memberdb.getId().longValue()){
            		 return ResultUtils.returnError("不能关注自己");
            	}
                CollectionHunter collectionHunter = new CollectionHunter();
                Member member = new Member();
                member.setId(id);
                Hunter hunter= new Hunter();
                hunter.setId(hid);
                collectionHunter.setMember(member);
                collectionHunter.setType(type);
                collectionHunter.setHunter(hunter);
                rpcCollectionHunterService.saveAndModify(collectionHunter);
                return ResultUtils.returnSuccess("关注成功");
            }
            //收藏 取消收藏
            if (collectionHunterVO != null){
                Integer collectionType =  collectionHunterVO.getType();
                if (String.valueOf(type).equals(String.valueOf(collectionType))){
                    if ("1".equals(String.valueOf(collectionType))){
                        return ResultUtils.returnError("不能重复取消关注");
                    }
                    if ("0".equals(String.valueOf(collectionType))){
                        return ResultUtils.returnError("不能重复关注");
                    }
                }
                if("0".equals(String.valueOf(type))){
                	Member memberdb = memberService.getMemberByHunterId(hid);
                	if(memberdb==null){
                		 return ResultUtils.returnError("该用户会员信息不存在，无法收藏");
                	}
                	if(id.longValue()==memberdb.getId().longValue()){
                		 return ResultUtils.returnError("不能关注自己");
                	}
                }
                CollectionHunter collectionHunter = new CollectionHunter();
                collectionHunter.setId(collectionHunterVO.getId());
                Member member = new Member();
                member.setId(id);
                Hunter hunter= new Hunter();
                hunter.setId(hid);
                collectionHunter.setMember(member);
                collectionHunter.setType(type);
                collectionHunter.setHunter(hunter);
                rpcCollectionHunterService.saveAndModify(collectionHunter);
                if ("1".equals(String.valueOf(type))){
                    return ResultUtils.returnSuccess("取消关注");
                }
                if ("0".equals(String.valueOf(type))){
                    return ResultUtils.returnSuccess("关注成功");
                }
            }
            return ResultUtils.returnError(StatusCodeEnums.ERROR_PARAM.getMsg());
        } catch (Exception e) {
            log.error(e.getMessage(),e);
           // TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultUtils.returnError(StatusCodeEnums.ERROR.getMsg());
        }
    }

    @Override
    public Result list(Long id, int page, int rows) {
        if (Objects.isNull(id)){
            return ResultUtils.returnError(StatusCodeEnums.ERROR_PARAM.getMsg());
        }
        Map<String,Object> map = Maps.newHashMap();
        map.put("uid",id);
        map.put("page",(page-1)*rows);
        map.put("rows",rows);
        try {
            List<Map<String,Object>> lists=collectionHunterDao.getCollectionHunterList(map);
            lists.forEach((m)->{
                        Integer valueOf = Integer.valueOf(m.get("level").toString());
                        if(valueOf==0){
                            m.put("level", HunterLevelEnums.LT_LEVEL_COMMON.getData());
                        }
                        if(valueOf==1){
                            m.put("level", HunterLevelEnums.LT_LEVEL_ADVANCED.getData());
                        }
                        if(valueOf==2){
                            m.put("level",HunterLevelEnums.LT_LEVEL_PROFESSOR.getData());
                        }
                        if(valueOf==3){
                            m.put("level",HunterLevelEnums.LT_LEVEL_TOP.getData());
                        }
                m.put("goodCommentNum", NumberFormat.getFormatNumber(String.valueOf(m.get("goodCommentNum"))));
                m.put("num", NumberFormat.getFormatNumber(String.valueOf(m.get("num"))));
                String pro=m.get("pname")==null?"":m.get("pname").toString();
                String city=m.get("cname")==null?"":m.get("cname").toString();
                String county=m.get("detail")==null?"":m.get("detail").toString();
                if("北京市".equals(pro)||"天津市".equals(pro)||"上海市".equals(pro)||"重庆市".equals(pro)){
                    m.put("cname",county);//服务的区域
                    m.put("hunterStation",pro+county);
                }else{
                    m.put("cname",city);
                    //獵頭駐地
                    m.put("hunterStation",pro+city);
                }

            });
            return ResultUtils.returnSuccess(StatusCodeEnums.SUCCESS.getMsg(),lists);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
            return ResultUtils.returnError(StatusCodeEnums.ERROR.getMsg());
        }
    }
}
