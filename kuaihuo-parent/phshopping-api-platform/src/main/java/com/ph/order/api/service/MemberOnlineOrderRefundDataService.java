package com.ph.order.api.service;

import cm.ph.shopping.facade.order.constant.OrderOnlineAppliStatusEnum;
import cm.ph.shopping.facade.order.vo.PhMemberSubOrderRefundPageVO;
import com.ph.shopping.common.core.customenum.PayTypeEnum;
import com.ph.shopping.common.util.container.ContainerUtil;
import com.ph.shopping.common.util.excel.ExcelBean;
import com.ph.shopping.common.util.excel.ExcelExportConfig;
import com.ph.shopping.common.util.excel.ExcelUtil;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @项目：phshopping-facade-order
 * @描述：线上订单申请退款订单导出数据封装
 * @作者： 张霞
 * @创建时间： 15:30 2017/6/20
 * @Copyright @2017 by zhangxia
 */
@Service
public class MemberOnlineOrderRefundDataService {
    public ExcelBean getOnlineOrderRefund(List<PhMemberSubOrderRefundPageVO> list, String name) {
        List<Object[]> sheetData = ContainerUtil.aList();
        String[] tableHeads = { "订单号","终端来源","下单时间","收货人","商品金额（元）","物流费用（元）", "订单总额（元）","付款方式", "订单状态"};
        ExcelBean excelBean = ExcelUtil.getExcelHeadBean(tableHeads, name, sheetData);
        HashMap<Integer, ExcelBean> sheetsMap = new HashMap<Integer, ExcelBean>();
        Object[] content = null;
        PhMemberSubOrderRefundPageVO orderOnlinePageVO;
        Object obj;
        int length = list.size();
        int olength = tableHeads.length;
        for (int j = 0; j < length; j++) {
            obj = list.get(j);
            orderOnlinePageVO = (PhMemberSubOrderRefundPageVO) obj;
            int k = 0;
            String str = "";
            content = new Object[olength];
            content[k++] = orderOnlinePageVO.getOrderNo();
            int unit = orderOnlinePageVO.getTerminalUnit();
            switch (unit){
                case 0:str="平台";break;
                case 1:str="APP商城";break;
            }
            content[k++] = str;
            content[k++] = orderOnlinePageVO.getCreateTime();
            content[k++] = orderOnlinePageVO.getShippingName();
            content[k++] = orderOnlinePageVO.getProductMoney();
            content[k++] = orderOnlinePageVO.getLogisticsMoney();
            content[k++] = orderOnlinePageVO.getOrderMoney();
            Integer payType = orderOnlinePageVO.getPayType();
            str = "";
            if (null != payType) {
                switch (payType){
                    case 0:str= PayTypeEnum.PAY_TYPE_CASH.getDesc();break;
                    case 1:str=PayTypeEnum.PAY_TYPE_SCORE.getDesc();break;
                    case 2:str=PayTypeEnum.PAY_TYPE_ALIPAY.getDesc();break;
                    case 3:str=PayTypeEnum.PAY_TYPE_WEIXINPAY.getDesc();break;
                    case 4:str=PayTypeEnum.PAY_TYPE_YILIANPAY.getDesc();break;
                }
            }
            content[k++] = str;
            int status = orderOnlinePageVO.getAppliStatus();
            str = "";
            switch (status) {
                case 0: str = OrderOnlineAppliStatusEnum.APPLISTATUS_AUDITING.getDesc(); break;
                case 1: str = OrderOnlineAppliStatusEnum.APPLISTATUS_REFUND_REFUSE.getDesc(); break;
                case 2: str = OrderOnlineAppliStatusEnum.APPLISTATUS_REFUNDING.getDesc(); break;
                case 3: str = OrderOnlineAppliStatusEnum.APPLISTATUS_REFUNDED.getDesc(); break;
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
