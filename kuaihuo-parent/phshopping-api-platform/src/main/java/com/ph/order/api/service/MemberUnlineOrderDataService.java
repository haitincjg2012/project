package com.ph.order.api.service;

import cm.ph.shopping.facade.order.vo.PhMemberOrderUnlineVO;
import com.ph.order.api.controller.vo.PurchasePageVO;
import com.ph.shopping.common.util.container.ContainerUtil;
import com.ph.shopping.common.util.date.SyncDateUtil;
import com.ph.shopping.common.util.excel.ExcelBean;
import com.ph.shopping.common.util.excel.ExcelExportConfig;
import com.ph.shopping.common.util.excel.ExcelUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @项目：phshopping-facade-order
 * @描述：线下订单导出数据封装
 * @作者： 张霞
 * @创建时间： 15:27 2017/6/13
 * @Copyright @2017 by zhangxia
 */
@Service
public class MemberUnlineOrderDataService {
    /**
     * @author: 张霞
     * @description：封装导出数据
     * @createDate: 15:29 2017/6/13
     * @param list 导出数据
     * @param name 表名
     * @return: com.ph.shopping.common.util.excel.ExcelBean
     * @version: 2.1
     */
    public ExcelBean getPurchaseOrder(List<PhMemberOrderUnlineVO> list, String name) {
        List<Object[]> sheetData = ContainerUtil.aList();
        String[] tableHeads = { "订单号","下单时间","付款方式", "订单总额（元）", "会员账号", "订单状态"};
        ExcelBean excelBean = ExcelUtil.getExcelHeadBean(tableHeads, name, sheetData);
        HashMap<Integer, ExcelBean> sheetsMap = new HashMap<Integer, ExcelBean>();
        Object[] content = null;
        PhMemberOrderUnlineVO phMemberOrderUnlineVO;
        Object obj;
        int length = list.size();
        int olength = tableHeads.length;
        for (int j = 0; j < length; j++) {
            obj = list.get(j);
            phMemberOrderUnlineVO = (PhMemberOrderUnlineVO) obj;
            int k = 0;
            content = new Object[olength];
            content[k++] = phMemberOrderUnlineVO.getOrderNo();
            content[k++] = phMemberOrderUnlineVO.getCreateTime();
            Integer payType = phMemberOrderUnlineVO.getPayType();
            String str = "";
            if (null != payType) {
                switch (payType){
                    case 0:str="现金支付";break;
                    case 1:str="积分支付";break;
                    case 2:str="支付宝支付";break;
                    case 3:str="微信支付";break;
                }
            }
            content[k++] =str;
            content[k++] = phMemberOrderUnlineVO.getOrderMoney();
            content[k++] = phMemberOrderUnlineVO.getMemberTelPhone();
            int status = phMemberOrderUnlineVO.getStatus();
            str = "";
            switch (status) {
                case 0: str = "待付款"; break;
                case 1: str = "付款中"; break;
                case 2: str = "交易完成"; break;
                case 3: str = "交易取消"; break;
            }
            content[k++] = str;
            sheetData.add(content);
            if ((j + 1) % ExcelExportConfig.MAX_SIZE == 0 || (j + 1) == length) {
                int sheetNum = j / ExcelExportConfig.MAX_SIZE;
                ExcelBean littleExcelBean = new ExcelBean();
                littleExcelBean.setNum(sheetNum);
                littleExcelBean.setName(name);
                littleExcelBean
                        .setSheetName(name + (sheetNum * ExcelExportConfig.MAX_SIZE + 1) + "-" + (j + 1) + "条");
                littleExcelBean.setHeaderCenter(name);
                littleExcelBean.setTableHeader(tableHeads);
                littleExcelBean.setColWidth(ExcelUtil.getColWidth(tableHeads.length));
                littleExcelBean.setSheetData(sheetData);
                sheetsMap.put(sheetNum, littleExcelBean);
                sheetData = ContainerUtil.aList();
            }
        }
        excelBean.setSheets(sheetsMap);
        return excelBean;
    }
}
