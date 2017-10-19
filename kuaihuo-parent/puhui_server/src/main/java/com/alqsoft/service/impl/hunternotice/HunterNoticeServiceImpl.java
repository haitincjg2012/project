package com.alqsoft.service.impl.hunternotice;

import com.alqsoft.dao.hunternotice.HunterNoticeDao;
import com.alqsoft.entity.hunter.Hunter;
import com.alqsoft.entity.hunternotice.HunterNotice;
import com.alqsoft.entity.member.Member;
import com.alqsoft.service.hunternotice.HunterNoticeService;
import org.alqframework.result.Result;
import org.alqframework.result.ResultUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Date;

/**
 * 批发商公告业务实现层
 * @author xuxin
 */
@Service
@Transactional
public class HunterNoticeServiceImpl implements HunterNoticeService {
    private static Logger logger = LoggerFactory.getLogger(HunterNoticeServiceImpl.class);
    @Autowired
    private HunterNoticeDao hunterNoticeDao;

    @Override
    public Result addHunterNotice(Long hunterId, String content, Date releaseTime,Member member) {
        Result result = new Result();
        Hunter hunter = member.getHunter();//获取登录批发商信息
        if(hunter == null){
            return ResultUtils.returnError("对不起，您不是批发商，请先申请！");
        }
        try{
            hunterNoticeDao.addHunterNotice(hunterId,content,releaseTime);
            result.setCode(1);
            result.setMsg("批发商公告添加成功！");
        }catch(Exception e){
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
            result.setCode(0);
            result.setMsg("批发商公告添加失败！");
        }

        return result;
    }

    @Override
    public Result deleteHunterNoticeById(Long id,Member member) {
        Result result = new Result();
        Hunter hunter = member.getHunter();//获取登录批发商信息
        if(hunter == null){
            return ResultUtils.returnError("对不起,您不是批发商,请先申请");
        }
        try {
            hunterNoticeDao.deleteHunterNoticeById(id);
            result.setCode(1);
            result.setMsg("批发商公告删除成功");
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
            result.setCode(0);
            result.setMsg("批发商公告删除失败");
        }
        return result;
    }

    @Override
    public Result updateHunterNoticeStatusById(Long id,Member member) {
        Result result = new Result();
        Hunter hunter = member.getHunter();//获取登录批发商信息
        if(hunter == null){
            return ResultUtils.returnError("对不起,您不是批发商,请先申请");
        }
        try {
            hunterNoticeDao.updateHunterNoticeStatusById(id);
            result.setCode(1);
            result.setMsg("批发商公告发布成功！");
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
            result.setCode(0);
            result.setMsg("批发商公告发布失败！");
        }
        return result;
    }

    @Override
    public Result updateHunterNoticeById(Long id, String content,Member member) {
        Result result = new Result();
        Hunter hunter = member.getHunter();//获取登录批发商信息
        if(hunter == null){
            return ResultUtils.returnError("对不起,您不是批发商,请先申请");
        }else if (content.length() > 200){
            return ResultUtils.returnError("请输入在200个字以内");
        }
        try {
            hunterNoticeDao.updateHunterNoticeById(id,content);
            result.setCode(1);
            result.setMsg("批发商公告编辑成功！");
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//回滚数据
            result.setCode(0);
            result.setMsg("批发商公告编辑失败！");
        }
        return result;
    }




    @Override
    public HunterNotice saveAndModify(HunterNotice hunterNotice) {
        return null;
    }

    @Override
    public boolean delete(Long aLong) {
        return false;
    }

    @Override
    public HunterNotice get(Long aLong) {
        return null;
    }
}
