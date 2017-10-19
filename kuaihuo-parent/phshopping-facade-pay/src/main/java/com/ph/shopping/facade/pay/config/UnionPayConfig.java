package com.ph.shopping.facade.pay.config;

/**
 * @项目：phshopping-parent
 * @描述：贵州银联代付参数
 * @作者： Mr.chang
 * @创建时间：2017/5/31
 * @Copyright @2017 by Mr.chang
 */
public class UnionPayConfig {
    /**
     * 贵州银联代付正式参数
     */
    public static final String CUST_ID = "CB0000030105"; //客户号 云平台提供，云平台唯一编号，在云平台注册成功后，登陆云平台即可查询到。
    public static final String PARTNER_ID = "00000117"; // 合作渠道ID
    public static final String NOTIFY_URL = "https://pay.phds315.com/union/cash/asyncCallBack";
    //public static final String NOTIFY_URL = "http://123.206.8.92:8080/union/cash/asyncCallBack";
//    public static final String JSB_UNION_URL = "https://yun.unionpay.com:18080/openplatform-webapp/payOrderJSB.do"; //请求地址
    public static final String JSB_UNION_URL = "https://yun.unionpay.com/gateway/api/payOrderJSB.do"; //请求地址
    public static final String PRIVATE_KEY= "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAL+TwAR9lV3YNtrWpP4L5RY/BuSGlpKC7Yh1U9ELFg55h1i3ZW8OV0mqiIHN07eey7j1pAp2xiAVQZtQZto2KIhvMiSymLa4XJkYWLVcxDboiwlozZ832nML8al3vS/JiZaeL3g7lVA9JhhEFOPS0pkIA1G7zpquYuhPQquXKDHFAgMBAAECgYAm1aeWAXMW2+56vAokKXsd4FbgWlwJhRrrj4UXGh01f/Msy7h3I7wUBcq4LWYekcUQUFMZf+w3srdi6ZB/6XybmbsuCd3NWVDJZh16FH3WPL6eESIp5JMJ4X3/yDAQI/Slu4ZgT603E8GkCKcd1aTYUWNzTcX4fFvDoV96IbbTnQJBAOaU/19Ub1rJ6eNlFEcH/dNpCZTVYoP6k4pggnBTbXA0wNj99EUm2SZac3OtQMSHR/aIZLA5bvo1yrE4pfqQficCQQDUsgnsMgG9w/aB1z2xDUb3BE7gs93/ERlJQ0cXI+s9PXKL5uzPEoOiX1ns/5bwkSa/R4oITTFc4QTuFYQsInAzAkAdmZDyzucAk5z3uPkSaT83TWuSdJYR9S/Nergj7UHGPq9m66rY2bTUjJX3io0e7XLafgXW7XiGnDMp7ui3sqbPAkEAhp7JOP3NrFZDx5p8KXvF1sKbSg2ODMq9vmkerb7GPVSPE41thQKq3jKLsD11Q34bkCWvb+GrxKWHgUmNBoIlkQJBAIVolzTCwGlYNx5vgEdS2OCWKUqBmo1B03HuV1aThMpFXMQ3DWGD3ifUDfr9iZaR1NFF1JQ0x8OdujVDSvmmHak=" ; // 私钥
    public static final String PUBLIC_KEY= "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2/H/PnFlbJRxExmmVxIbIU+xvnwRNoN/GRlfMRlesDAqQU2BPcj7iXMfX4NCK632B+Spe+oI6xAo0rJr2qyjCiUgvLl6hvd/okR+Ee9SuDMRXFhqxR0kulDhE0Dfj0dVd6SokUEOBcM16bXH0oCu0DgbiUcRDMfS5p1VxjhrxLAOJ0N4y3dek3t2Lh1fL9ok3aVXpwRzvYjS4SxBt6A03mDoHpksmSYjEA+o1NNR0h0SD0H7f7C7J1reoRqsfjcjjy3pbeCauTkCNB3TJvBVYN4LLRUHkvWeA7GwANvmpDdPYpSWCa9G3akEA7Za+y39rMqU8zCT6FtfsHH9q2OHvQIDAQAB"; // 公钥

    public static final String PAYEE_ACCT_TYPE = "PERSONAL"; //PERSONAL 对私 ， BUSINESS 对公

    /**
     * 贵州银联代付测试参数
     */
//    public static final String CUST_ID = "CP0000000098"; //客户号 云平台提供，云平台唯一编号，在云平台注册成功后，登陆云平台即可查询到。
//    public static final String PARTNER_ID = "00000078"; // 合作渠道ID
//    public static final String NOTIFY_URL = "http://106.37.208.102:8097/JSB"; // 异步通知地址
//    public static final String JSB_UNION_URL = "http://58.42.236.252:9920/openplatform-webapp/payOrderJSB.do"; //请求地址
//    public static final String PRIVATE_KEY = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAOg+PNOota7ppeHwDBB7zttL/OpUmaOCbei/J2t/FLNjumsMjJVGLfdxKlrqCQXVAzAZDIakQcbfRvvlhZJCEBAnL2tru8Qp6hs9dokI2zOXjf9wyk4hPQT5/noXEnlnXO1lr4MZeXhgLKCwZOQfTeE9WSWuTmdA5Dzgu0d/TvuJAgMBAAECgYBBEqdCeyQlFWyYaQVIXRhx09HS6s99xB79twnZker/9LKYKhT+AoMAsSG4BZlvm+bfxDUBSObxTUB7di099OrAw0J1F0QpCXL5Jrxc2NdW8/j1hXb77UbdgsUZg4hM5JkJ2QRxiwT0JyWUAIikSx0W+jUzTFkz1UFaMiZOwEX7rQJBAPcd1+dvclPiR77McvxEpje04dddIiGIQxCw7oZmARMOK33Jrd3+6nTv8xlhFSWM9/xgJzQq8n+aZ4X9+ZXEp9sCQQDwl4RolGsvtp/8jyFBRNGOTQ6CWM/77lK47swzeu50GCFNyf+tLNu8kOhyk+8LIftKFm44m8PZrsZDYZLNDGlrAkA6n0bHrWWGxshUV/XzKGnyDyQATiS5pbSbMg3zriEVHyhsF7r6Te3avc2CuMgmd1Gg+kJymrmaUcu7OqvJvrQ/AkBFQylwPgIZi1bFi6MEOj6l29MofU7q9TJFYSHSVDqfm27DCTsc7MQZphH1Ild3+gFw08JJc7ZPTbxwG3/6ne8fAkBDiUWE/r5GAVrLDFXIqglkG+25B0LPGT2ttTNL2Id/4QAbUXLxGuwmBY52B8m2Y2U8agJDL6YdsE+gvykcH9oB" ; // 私钥
//    public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC+IMBbuSZVmiilWuGhGm4cgTmw7YBXykebkQkIDJEifj+SZxjMJBsjZ5JqjAFSlPNW+gv9T3UXe5gBQPM8YqB+kwAWtHjzRDlU/kaAq2A+MVCqR44KDNaVK+raiBme1wJ3w0bxDPwxjMPkg2psc0jGuP+lovS3fJwNbkEHRne68wIDAQAB"; // 公钥

}
