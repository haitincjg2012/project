var Dialog = function() {};
//查询来源
$(function() {
	$.ajax({
		url : '/web/useraccountmanager/gettranscode',
		type : 'get',
		success : function(json) {
			for (var i = 0; i < json.length; i++) {
				$("#userSource").append("<option value='" + json[i].transCode + "'>" + json[i].source + "</option>");
			}
			if ($('select').hasClass('selectpicker')) {
				$('.selectpicker').selectpicker('refresh');
			}
		}
	});
});

//将提交的表单数据转换成json数据格式
(function($) {
	$.fn.serializeJson = function() {
		var serializeObj = {};
		var array = this.serializeArray();
		var str = this.serialize();
		$(array).each(function() {
			if (serializeObj[this.name]) {
				if ($.isArray(serializeObj[this.name])) {
					serializeObj[this.name].push(this.value);
				} else {
					serializeObj[this.name] = [ serializeObj[this.name], this.value ];
				}
			} else {
				serializeObj[this.name] = this.value;
			}
		});
		return serializeObj;
	};
})(jQuery);

//操作成功提示框
Dialog.alertInfo = function(msg) {
	layer.alert(msg, {
		icon : 1
	});
}

Dialog.toLogin = function(msg){
	layer.alert(msg, 
			{
		icon : 1
			},
	function(index){
		location.href="/login";
	});
}

//操作成功提示框
Dialog.alertRefreshInfo = function(msg) {
	layer.alert(msg, 
			{
		icon : 1
			},
	function(index){
		location.href=location.href;
	});
}

//错误提示框
Dialog.alertError = function(msg) {
	layer.alert(msg, {
		icon : 2
	});
}

//询问提示框
Dialog.alertAsk = function(msg) {
	layer.alert(msg, {
		icon : 3
	});
}

//锁定提示框
Dialog.alertLock = function(msg) {
	layer.alert(msg, {
		icon : 4
	});
}
//警告提示框
Dialog.alertWarn = function(msg) {
	layer.alert(msg, {
		icon : 7
	});
}

Dialog.clearData = function(formId) {
	$("#" + formId)[0].reset();
}

//selectIds中用每个id用,分隔
Dialog.clearData = function(formId, selectIds) {
	$("#" + formId)[0].reset();
	var strs = new Array();
	strs = selectIds.split(",");

	for (var i = 0; i < strs.length; i++) {
		$("#" + strs[i] + " option:first").prop("selected", 'selected');
	}
	$('.selectpicker').selectpicker('refresh');
}

//查询
Dialog.doSearch = function(tableId) {
	$('#' + tableId).DataTable().draw();
}

//导出
Dialog.exportExcel = function(formId) {
	$("#" + formId).submit();
}

Dialog.isInteger = function(score) {
	var r = /^\+?[1-9][0-9]*$/; //正整数
	return r.test(score);
}