var Area = function() {
	
};

//获取省市区公共js
//查询市
$(function() {
	var provinceId = 0;
	$.ajax({
		url : '/web/position/getPositionNoAuthorityList',
		type : 'post',
		data : {
			provinceId : provinceId
		},
		dataType : 'json',
		success : function(json) {
			for (var i = 0; i < json.length; i++) {
				$("#provinceId").append("<option value='" + json[i].provinceId + "'>" + json[i].provinceName + "</option>");
			}
		}
	});
});
//获取市
Area.getCityies = function(provinceId, cityId,countyId) {
	var provinceId = $("#" + provinceId).val();
	$("#" + cityId).empty();
	jQuery("<option value=''>请选择</option>").appendTo("#" + cityId);
	$("#" + countyId).empty();
	jQuery("<option value=''>请选择</option>").appendTo("#" + countyId); 
	$.ajax({
		url : '/web/position/getPositionNoAuthorityList',
		type : 'post',
		data : {
			provinceId : provinceId
		},
		dataType : 'json',
		success : function(json) {
			for (var i = 0; i < json.length; i++) {
				$("#" + cityId).append("<option value='" + json[i].cityId + "'>" + json[i].cityName + "</option>");
			}
		}
	});
}

//获取区
Area.getCounties = function(cityId, countyId) {
	var cityId = $("#" + cityId).val();
	$("#" + countyId).empty();
	jQuery("<option value=''>请选择</option>").appendTo("#" + countyId); 
	$.ajax({
		url : '/web/position/getPositionNoAuthorityList',
		type : 'post',
		data : {
			cityId : cityId
		},
		dataType : 'json',
		success : function(json) {
			for (var i = 0; i < json.length; i++) {
				$("#" + countyId).append("<option id='" + json[i].id + "'" + " " + "value='" + json[i].countyId + "'>" + json[i].countyName + "</option>");
			}
		}
	});
}
//设置positionId
Area.setPositionId = function(countyId, posId) {
	var positionId = $("#" + countyId).find("option:checked").attr("id");
	$("#" + posId).val(positionId);
}