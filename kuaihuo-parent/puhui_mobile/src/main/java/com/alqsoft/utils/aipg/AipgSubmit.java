package com.alqsoft.utils.aipg;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* *
 *类名：AipgSubmit
 *功能：通联各接口请求提交类
 *详细：构造支付宝各接口表单HTML文本，获取远程HTTP数据
 *版本：3.3
 *日期：2012-08-13
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AipgSubmit {
    
    /**
     * 建立请求，以表单HTML形式构造（默认）
     *  @param serverHost 请求通联服务器地址
     * @param sParaTemp 请求参数数组
     * @param strMethod 提交方式。两个值可选：post、get
     * @param strButtonName 确认按钮显示文字
     * @return 提交表单HTML文本
     */
    public static String buildRequest(String serverHost,Map<String, String> sParaTemp, String strMethod, String strButtonName) {

    	
    	  List<String> keys = new ArrayList<String>(sParaTemp.keySet());
        StringBuffer sbHtml = new StringBuffer();

        sbHtml.append("<form id=\"aipgsubmit\" name=\"aipgsubmit\" target=\"_self\" action=\"" +serverHost+ "\" method=\"" + strMethod + "\">");

        for (int i = 0; i < keys.size(); i++) {
            String name = (String) keys.get(i);
            String value = (String) sParaTemp.get(name);

            sbHtml.append("<input type=\"hidden\" name=\"" + name + "\" value=\"" + value + "\"/>");
        }

        //submit按钮控件请不要含有name属性
        sbHtml.append("<input type=\"submit\" value=\"" + strButtonName + "\" style=\"display:none;\"></form>");
        sbHtml.append("<script>document.forms['aipgsubmit'].submit();</script>");

        return sbHtml.toString();
    }
    

}
