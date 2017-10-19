package com.ph.shopping.facade.pay.utils.payeco;

import com.ph.shopping.common.util.rsa.MD5;
import com.ph.shopping.facade.pay.config.PayecoConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.ph.shopping.common.util.http.IPUtil.getIpAddress;

/**
 * @项目：phshopping-api-pay
 * @描述：易联支付工具类
 * @作者： Mr.Chang
 * @创建时间： 14:06 2017/5/31
 * @Copyright @2017 by Mr.Chang
 */
public class PayecoUtils {

    protected static Logger log = LoggerFactory.getLogger(PayecoUtils.class);
    protected static final String CharSet = "UTF-8";


    public static String getString(String src) {
        return (isNullOrEmpty(src) ? "" : (" " + src.trim()));
    }

    public static boolean isNullOrEmpty(String src){
    	return src == null || "".equals(src.trim());
    }

    /**
     * 获取md5加密后的mac字符串
     * 第一步：计算MAC的源字符串，用商户密钥签名放到MAC
     * 第二步：将mac进行md5加密
     * @param amount 充值金额
     * @param orderNo 订单编号
     * @return
     */
    public static String getMac(String amount,String orderNo){
        String card = "";
        String AcqSsn = new SimpleDateFormat("HHmmss").format(new Date());
    	String TransDatetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String TransData = "";
        String RespCode = "";
        String OrderState = "";
        String terminalNo = "";
        String OrderNo="";
        String src = PayecoConfig.PROC_CODE
				+ getString(card)
				+ getString(PayecoConfig.PROCESS_CODE)
				+ getString(amount)
				+ getString(TransDatetime)
				+ getString(AcqSsn)
				+ getString(OrderNo)
				+ getString(TransData)
				+ getString(PayecoConfig.REFERENCE)
				+ getString(RespCode)
				+ getString(terminalNo)
				+ getString(PayecoConfig.MERCHANT_NO)
				+ getString(orderNo)
				+ getString(OrderState);
         	String macSrc = (src + " " + PayecoConfig.MERCHANT_PWD).toUpperCase();
			String MAC = MD5.getMD5Str(macSrc);
        log.info("md5加密MAC后的字符串:"+MAC);
        return MAC;
    }

    /**
     * 组装报文
     * @param orderNum
     * @param amount
     * @param remark
     * @return
     * @throws Exception
     */
    public static String encryptMD5(String orderNum,String amount,String remark)throws Exception{
    	String MAC=PayecoUtils.getMac(amount, orderNum);
    	return encryptMiWen(MAC, amount, getIpAddress(), remark, MAC);
    }
    /**
     * 第三步：加上加密后的mac，组装完成的xml报文，
     * //第四步：用Base64对xml报文进行编码得到Base64字符串，并对Base64字符串进行UrlEncode，编码使用UTF-8，得到最终编码字符串request_text；
     * @param MAC 加密后的mac
     * @param amount 金额
     * @param ipAddress 请求ip地址
     * @param remark //订单类型  充值方式:1,充值，2,购买商品支付
     * @param MerchantOrderNo 订单号
     * @return
     */
    public static String encryptMiWen(String MAC,String amount,String ipAddress,String remark,String MerchantOrderNo) throws Exception{
        String srcXml = "", request_text = "";

        String card = "";//银行卡号
        String phone = "";//手机号
        String terminalNo = "";//终端号
        String OrderNo = "";
        String AcqSsn = new SimpleDateFormat("HHmmss").format(new Date());
        String TransDatetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String TransData = "";//其他业务数据
        String name = "";//姓名
        String idcard = "";//身份证
        String bankAddress = "";//银行开户省市
        String idcardtype = "01";//证件类型
        String beneficiary = "";//受益人
        String beneficiaryMobileNo = null;//受益人电话号码
        String deliveryAddress = null;
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>").append("\n")
                .append("<x:NetworkRequest xmlns:x=\"http://www.payeco.com\" xmlns:xsi=\"http://www.w3.org\">").append("\n\t")
                .append("<Version>").append(PayecoConfig.VERSION).append("</Version>").append("\n\t")
                .append("<ProcCode>").append(PayecoConfig.PROC_CODE).append("</ProcCode>").append("\n\t")
                .append("<ProcessCode>").append(PayecoConfig.PROCESS_CODE).append("</ProcessCode>").append("\n\t")
                .append("<AccountNo>").append(card).append("</AccountNo>").append("\n\t")
                .append("<AccountType>").append("").append("</AccountType>").append("\n\t")
                .append("<MobileNo>").append(phone).append("</MobileNo>").append("\n\t")
                .append("<Amount>").append(amount).append("</Amount>").append("\n\t")
                .append("<Currency>").append(PayecoConfig.CUR_CODE).append("</Currency>").append("\n\t")
                .append("<SynAddress>").append(PayecoConfig.SYNC_ADDRESS).append("</SynAddress>").append("\n\t")
                .append("<AsynAddress>").append(PayecoConfig.ASYNC_ADDRESS).append("</AsynAddress>").append("\n\t")
                .append("<Remark>").append(remark).append("</Remark>").append("\n\t")
                .append("<TerminalNo>").append(terminalNo).append("</TerminalNo>").append("\n\t")
                .append("<MerchantNo>").append(PayecoConfig.MERCHANT_NO).append("</MerchantNo>").append("\n\t")
                .append("<MerchantOrderNo>").append(MerchantOrderNo).append("</MerchantOrderNo>").append("\n\t")
                .append("<OrderNo>").append(OrderNo).append("</OrderNo>").append("\n\t")
                .append("<OrderFrom>").append("16").append("</OrderFrom>").append("\n\t")
                .append("<Language>").append("00").append("</Language>").append("\n\t")
                .append("<Description>").append(remark).append("</Description>").append("\n\t")
                .append("<OrderType>").append("00").append("</OrderType>").append("\n\t")
                .append("<AcqSsn>").append(AcqSsn).append("</AcqSsn>").append("\n\t")
                .append("<Reference>").append(PayecoConfig.REFERENCE).append("</Reference>").append("\n\t")
                .append("<TransDatetime>").append(TransDatetime).append("</TransDatetime>").append("\n\t")
                .append("<MerchantName>").append(PayecoConfig.MERCHANT_NAME).append("</MerchantName>").append("\n\t")
                .append("<TransData>").append(TransData).append("</TransData>").append("\n\t")
                .append("<IDCardName>").append(name).append("</IDCardName>").append("\n\t")
                .append("<IDCardNo>").append(idcard).append("</IDCardNo>").append("\n\t")
                .append("<BankAddress>").append(bankAddress).append("</BankAddress>").append("\n\t")
                .append("<IDCardType>").append(idcardtype).append("</IDCardType>").append("\n\t")
                .append("<BeneficiaryName>").append(beneficiary).append("</BeneficiaryName>").append("\n\t")
                .append("<BeneficiaryMobileNo>").append(beneficiaryMobileNo).append("</BeneficiaryMobileNo>").append("\n\t")
                .append("<DeliveryAddress>").append(deliveryAddress).append("</DeliveryAddress>").append("\n\t")
                .append("<IpAddress>").append(ipAddress).append("</IpAddress>").append("\n\t")
                .append("<Location>").append("").append("</Location>").append("\n\t")
                .append("<UserFlag>").append("").append("</UserFlag>").append("\n\t")
                .append("<MAC>").append(MAC).append("</MAC>").append("\n")
                .append("</x:NetworkRequest>").append("\n");
        srcXml = sb.toString();
        log.info("根据收集到的订单信息组装XML报文:" + srcXml);
        request_text = URLEncoder.encode(new BASE64Encoder().encode(srcXml.getBytes(CharSet)), CharSet);//第四步：用Base64对xml报文进行编码得到Base64字符串，并对Base64字符串进行UrlEncode，编码使用UTF-8，得到最终编码字符串request_text；
        log.info("requst_text加密:" + request_text);
        return request_text;
    }

    /**
     * 项目回调函数，异步方法中，并解密接收的文件
     * @param response_text 接收的密文信息
     * @return
     * @throws IOException
     */
    public static String decryptMiWen(String response_text) throws IOException {
        String xml="";
        StringBuffer buffer = new StringBuffer();
        buffer.append("<br/>");
        buffer.append("response_text UrlDecode解码后<br/>");
        //如果解出来的xml是乱码的，请将这个步骤给注释掉，有的服务器程序会自动做UrlDecode
//        String ut = URLDecoder.decode(response_text, CharSet);
        String urlText = response_text;
        buffer.append("<textarea rows=\"20\" cols=\"100\">" + urlText + "</textarea>");
        buffer.append("<br/>");
        xml = new String(new sun.misc.BASE64Decoder().decodeBuffer(urlText),CharSet);
        buffer.append("response_text Base64解码后<br/>");
        buffer.append("<textarea rows=\"40\" cols=\"100\">" + xml + "</textarea>");
        buffer.append("");

        String src = getValue(xml,"ProcCode")
                + getString(getValue(xml,"AccountNo"))
                + getString(getValue(xml,"ProcessCode"))
                + getString(getValue(xml,"Amount"))
                + getString(getValue(xml,"TransDatetime"))
                + getString(getValue(xml,"AcqSsn"))
                + getString(getValue(xml,"OrderNo"))
                + getString(getValue(xml,"TransData"))
                + getString(getValue(xml,"Reference"))
                + getString(getValue(xml,"RespCode"))
                + getString(getValue(xml,"TerminalNo"))
                + getString(getValue(xml,"MerchantNo"))
                + getString(getValue(xml,"MerchantOrderNo"))
                + getString(getValue(xml,"OrderState")) + " " + "商户密钥";

        buffer.append("<br/>response_text MAC源字符串：<br/>");
        buffer.append("<textarea rows=\"2\" cols=\"100\">" + src + "</textarea>");
        buffer.append("");
        String MAC = MD5.getMD5Str(src);
        buffer.append("<br/>response_text MAC：<br/>");
        buffer.append("<textarea rows=\"1\" cols=\"100\">" + MAC + "</textarea>");
        buffer.append("");
        return buffer.toString();
    }
    public static String getValue(String xml, String name){
        if(xml==null || "".equals(xml.trim())
                || name == null || "".equals(name.trim())){
            return "";
        }
        String tag = "<" + name + ">";
        String endTag = "</" + name + ">";
        if(!xml.contains(tag) || !xml.contains(endTag)){
            return "";
        }
        String value = xml.substring(xml.indexOf(tag) + tag.length(), xml.indexOf(endTag));
        if(value != null && !"".equals(value)){
            return value;
        }
        return "";
    }
}
