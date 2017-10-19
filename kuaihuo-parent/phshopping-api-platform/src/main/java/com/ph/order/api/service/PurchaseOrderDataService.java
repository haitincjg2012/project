package com.ph.order.api.service;

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
 * 供应链导出数据封装
 *
 * @author 郑朋
 * @create 2017/5/25
 **/
@Service
public class PurchaseOrderDataService {

    /**
     * @methodname getPurchaseOrder 的描述：封装进货订单列表导出数据
     * @param list
     * @param name
     * @param type 1 订单 2 退款订单
     * @param tableHeads
     * @return com.ph.shopping.common.util.excel.ExcelBean
     * @author 郑朋
     * @create 2017/5/25
     */
    public ExcelBean getPurchaseOrder(List<PurchasePageVO> list, String name, Integer type,String[] tableHeads) {
        List<Object[]> sheetData = ContainerUtil.aList();
        ExcelBean excelBean = ExcelUtil.getExcelHeadBean(tableHeads, name, sheetData);
        HashMap<Integer, ExcelBean> sheetsMap = new HashMap<Integer, ExcelBean>();
        Object[] content;
        PurchasePageVO purchasePageVO;
        Object obj;
        int length = list.size();
        int olength = tableHeads.length;
        for (int j = 0; j < length; j++) {
            obj = list.get(j);
            purchasePageVO = (PurchasePageVO) obj;
            int k = 0;
            content = new Object[olength];
            content[k++] = purchasePageVO.getOrderNo();
            content[k++] = SyncDateUtil.dateToString("yyyy-MM-dd HH:mm:ss", purchasePageVO.getCreateTime());
            content[k++] = purchasePageVO.getSupplierName();
            content[k++] = purchasePageVO.getPurchaseName();
            content[k++] = purchasePageVO.getMoney();
            content[k++] = purchasePageVO.getFreight();
            content[k++] = purchasePageVO.getTotalCost();
            Byte payment = purchasePageVO.getPayment();
            String str = "";
            if (null != payment) {
                switch (payment) {
                    case 0: str = "余额支付"; break;
                    case 1: str = "银行卡支付"; break;
                    default: str = "-";
                }
            }
            content[k++] = str;
            if (type == 1) {
                payment = purchasePageVO.getStatus();
                switch (payment) {
                    case 0: str = "待付款"; break;
                    case 1: str = "待发货"; break;
                    case 2: str = "待收货"; break;
                    case 3: str = "交易完成"; break;
                    case 4: str = "交易取消"; break;
                    default: str = "-";
                }
            } else if (type == 2) {
                payment = purchasePageVO.getRefundStatus();
                switch (payment) {
                    case 0: str = "审核中"; break;
                    case 1: str = "退款中"; break;
                    case 2: str = "退款完成"; break;
                    case 3: str = "拒绝退款"; break;
                    default: str = "-";
                }
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
                littleExcelBean.setColWidth(ExcelUtil.getColWidth(olength));
                littleExcelBean.setSheetData(sheetData);
                sheetsMap.put(sheetNum, littleExcelBean);
                sheetData = ContainerUtil.aList();
            }
        }
        excelBean.setSheets(sheetsMap);
        return excelBean;
    }

}
