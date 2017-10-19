package com.alqsoft.utils;
/**
 * 支付工程 重要参数配置文件
 * @Description: TODO
 * @author Shen.joe
 * @e-mail sudiluo_java@163.com
 * @version v1.0
 * @copyright 2010-2015
 * @create-time 2017年1月22日 下午4:00:50
 * 
 */
public class PayConfig {
	
	public static final String JDpay="京东";
	public static final String YLpay="易联";
	public static final String GZYLpay="贵州银联";
	/**
	 * 京东支付 参数
	 */
	public static final String key="pBqdzOqCob1GqcrfRliK1FuT9ngKTuC7";//密钥
	public static final String v_mid="110201467001";//商户号
	public static final String v_moneytype="CNY";//货币种类
	public static final String v_url="http://";//支付返回地址 pay工程
	public static final String v_url_member="http://";
	public static final String remark2="[url:=http://123.207.167.176:6555/pay/pc/view/jdpayDirect/returnMsg.do]";//异步回调
	
	/**
	 * 贵州银联代付 参数
	 */
	public static final String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2/H/PnFlbJRxExmmVxIbIU+xvnwRNoN/GRlfMRlesDAqQU2BPcj7iXMfX4NCK632B+Spe+oI6xAo0rJr2qyjCiUgvLl6hvd/okR+Ee9SuDMRXFhqxR0kulDhE0Dfj0dVd6SokUEOBcM16bXH0oCu0DgbiUcRDMfS5p1VxjhrxLAOJ0N4y3dek3t2Lh1fL9ok3aVXpwRzvYjS4SxBt6A03mDoHpksmSYjEA+o1NNR0h0SD0H7f7C7J1reoRqsfjcjjy3pbeCauTkCNB3TJvBVYN4LLRUHkvWeA7GwANvmpDdPYpSWCa9G3akEA7Za+y39rMqU8zCT6FtfsHH9q2OHvQIDAQAB"; // 公钥
}
