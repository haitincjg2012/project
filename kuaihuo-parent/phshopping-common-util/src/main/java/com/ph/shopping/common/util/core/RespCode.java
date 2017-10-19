package com.ph.shopping.common.util.core;

/**
 * 返回抽象对象
 *
 * @author 郑朋
 * @create 2017/4/25
 **/
public interface RespCode {
    String getMsg();
    String getCode();

    enum Code implements RespCode {
        SUCCESS("200","操作成功"),
        PWD_ERROR("201","密码格式不正确,请填写6-16字母或数字"),
        NICKNAME_ERROR("202","昵称长度不正确,请填写2-10个字符"),
        FAIL("300","操作失败"),
		ERROER_PHONE("11001","手机号不符合格式"),
        INTERNAL_SERVER_ERROR("0405", "服务器内部错误"),
        PERMISSION_DENIED("10101","没有权限访问！"),
    	REPETITION("10102","当前操作未处理完，请稍后再试！"),
        REQUEST_DATA_ERROR("10100","请求参数不全"),
        SEND_MESSAGE_ERROR("301","验证码发送失败"),
        CODE_ERROR("302","验证码错误"),
    	LIUYAN_ERROR("10103","留言不能超过50字"),
    	DISH_ERROR("10104","请选择餐位"),
    	LEAVE_TIME("10105","当前餐位已经被预定"),
    	NOW_TIME("10106","预计到店时间必须大于当前时间"),
    	NO_ORDER("10107","未查询到数据"),
    	CHOOSE_ROOM("10108","请先选择包间或打包"),
    	ERROR_PARAM("-1","参数异常"),
    	NUM_ERROR("-2","库存不足"),
    	DELETE_OK("1","删除成功"),
		EXIST_NIKENAME("10011","该昵称已存在，请重新输入"),
		NOTEMPTY_NIKENAME("10012","昵称不能为空"),
		MAXTWENTY_NIKENAME("10013","昵称4-16位字符"),
		FIRST_ENGLISHANDCHINESE("10014","以英文字母或汉字开头"),
    	NO_MERCHANTMESSAGE("10103","未查询到商户信息"),
    	DISH_CHOOSE_ERROR("8899","请选择商品"),
    	DISH_ADD_ERROR("8877","商品参数不能为空"),
    	DISH_ADD_WORDS_ERROR("8866","不能超过30字符"),
    	DISH_ADD_IMAGES_ERROR("8855","请上传图片"),
    	DISH_ADD_IMAGESCOUNT_ERROR("8844","图片数量不能超过4张"),
    	DISH_ADDDISHTYPE_ERROR("8833","请选择商品分类"),
    	DISH_DISHTYPE_ERROR("8822","商品分类不存在"),
    	MERCHANT_NO_DISH("10109","没有查询到菜品信息"),
    	MERCHANT_NO_DISHTYPE("10110","该商户下没有分类"),
    	UPLOADFILE_ERROR("8811","上传文件失败"),
    	NO_CDKEY("10111","激活码不匹配"),
    	NO_MERMBER("10112","无此会员"), 
    	OVER_CDKEY("10113","已激活"),
    	MERCHANT_NO_OPEN("10114","非营业时间，请您更换时间再试"),
    	NO_HOPETIME("10115","请选择预定时间"),
		IS_SALESMAN("10116","此账号已经是业务员"),
		IS_MERCHANT("10117","此账号已经是商户了"),
		IS_BINGINDG_MERCHANT("10118","此账号已经绑定其他商户了"),
		IS_NOTMEMBER("101190","此账号还不是快火会员，请先注册"),
		GOODS_UNDER_CLASSIFICATION("9910","当前分类下已经存在商品，不可删除"),
    	CLASSIFICATION_EXISTS("9911","当前分类已经存在，无需添加"),
    	CLOSE_TIME_ERROR("10116","结束时间不能小于开始时间"),
    	PLEASE_MERCHANTSETMONEY("10117","请商户设置金额"),
    	NO_DABAO_MERCHANT("10118","选择打包不能选择其它餐位"),
    	CHOOSE_CONSUMPTION_TIME("10228","请选择消费时间段"),
    	CURRENT_TIME_ALREADY_EXISTS("10338","当前消费时间已经存在,请重新选择");
        private String code;
        private String msg;

        Code(String code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        @Override
        public String getMsg() {
            return msg;
        }

        @Override
        public String getCode() {
            return code;
        } 
    }

}
