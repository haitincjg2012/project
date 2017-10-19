function addDepot() {
	clearData();
	layer.open({
		type : 1,
		title : '新增仓库地址',
		closeBtn : 0,
		area : [ '442px', '392px' ],
		btn : [ '确定', '取消' ],
		yes : function(index, layero) {
			var bool = validateParam();
			if (bool) {
				var params = $("#addWareForm").serializeJson();
				$.ajax({
					url : '/web/warehouseaddress/add',
					type : 'post',
					data : params,
					dataType : 'json',
					success : function(json) {
						if (json.code == 200) {
							Dialog.alertInfo(json.message);
							layer.close(index);
							$("#wareList").DataTable().draw();
						} else {
							Dialog.alertWarn(json.message);
						}
					}
				})
			}
		},
		btnAlign : 'c',
		content : $('#addLinkmanBox')
	});
}

function getCityies(provinceId, cityId, countyId) {
	Area.getCityies(provinceId, cityId, countyId);
}

function getCounties(cityId, countyId) {
	Area.getCounties(cityId, countyId);
}

function validateParam() {
	var contacts = $("#contacts").val();
	if (!contacts) {
		Dialog.alertWarn('联系人不能为空!');
		return false;
	}

	var telPhone = $("#telPhone").val();
	if (!telPhone) {
		Dialog.alertWarn('联系电话不能为空!');
		return false;
	}

	if (telPhone.length != 11 || !(/^1(3|4|5|7|8)\d{9}$/.test(telPhone))) {
		Dialog.alertWarn('输入手机号码有误!');
		return false;
	}
	//设置区域id
	//	var positionId = $("#countyId").find("option:checked").attr("id");
	//	$("#positionId").val(positionId);
	Area.setPositionId("countyId", "positionId");

	var provinceId = $("#provinceId").val();
	var provinceName = $("#provinceId").find("option:selected").text();
	$("#provinceName").val(provinceName);
	var cityId = $("#cityId").val();
	var cityName = $("#cityId").find("option:selected").text();
	$("#cityName").val(cityName);
	var countyId = $("#countyId").val();
	var countyName = $("#countyId").find("option:selected").text();
	$("#countyName").val(countyName);
	//	alert(provinceName + cityName + countyName);
	if (!provinceId) {
		Dialog.alertWarn('请选择省');
		return false;
	}

	if (!cityId) {
		Dialog.alertWarn('请选择市');
		return false;
	}

	if (!countyId) {
		Dialog.alertWarn('请选择区');
		return false;
	}

	var detailAddress = $("#detailAddress").val();
	if (!detailAddress) {
		Dialog.alertWarn('详细地址不能为空!');
		return false;
	}
	return true;
}

function clearData() {
	$("#addWareForm")[0].reset();
	
	$("#provinceId").find("option:selected").text("请选择");
	$("#cityId").find("option:selected").text("请选择");
	$("#countyId").find("option:selected").text("请选择");
//	$("#provinceId").prepend("<option value=''>请选择</option>");
//	$("#cityId").prepend("<option value=''>请选择</option>");
//	$("#countyId").prepend("<option value=''>请选择</option>");
//	
//	$("#provinceId option:first").prop("selected", 'selected'); 
//	$("#cityId option:first").prop("selected", 'selected'); 
//	$("#countyId option:first").prop("selected", 'selected'); 

}

//编辑仓库地址
function editAddress(id) {
	var jsonData = {
		wareHouseId : id
	};
	$.ajax({
		url : '/web/warehouseaddress/pagelist',
		type : 'get',
		data : jsonData,
		dataType : 'json',
		success : function(json) {
			if (json.code == 200) {
				var da = json.data[0];
				$("#wareHouseId").val(da.id);
				$("#contacts").val(da.contacts);
				$("#telPhone").val(da.telPhone);
				$("#phoneNo").val(da.phoneNo);
				$("#provinceId").find("option:selected").text(da.provinceName);
				$("#provinceId").find("option:selected").val(da.provinceId);
				$("#cityId").find("option:selected").text(da.cityName);
				$("#cityId").find("option:selected").val(da.cityId);
				$("#countyId").find("option:selected").text(da.countyName);
				$("#countyId").find("option:selected").val(da.countyId);
				$("#detailAddress").val(da.detailAddress);
				layer.open({
					type : 1,
					title : '编辑仓库地址',
					closeBtn : 0,
					area : [ '442px', '392px' ],
					btn : [ '确定', '取消' ],
					yes : function(index, layero) {
						var bool = validateParam();
						if (bool) {
							var params = $("#addWareForm").serializeJson();
							$.ajax({
								url : '/web/warehouseaddress/update',
								type : 'post',
								data : params,
								dataType : 'json',
								success : function(json) {
									if (json.code == 200) {
										Dialog.alertInfo(json.message);
										layer.close(index);
										$("#wareList").DataTable().draw();
									} else {
										Dialog.alertWarn(json.message);
									}
								}
							})
						}
					},
					btnAlign : 'c',
					content : $('#addLinkmanBox')
				});
			} else {
				alert("错误无!");
			}
		}
	});
}

function setIsDefault(id) {
	$.post("/web/warehouseaddress/updatewarehousedefault", {wareHouseId :id,isDefault:1}, function(data, textStatus, req) {
		if(data.code == 200) {
			Dialog.alertInfo(data.message);
			$("#wareList").DataTable().draw();
		} else {
			Dialog.alertWarn(data.message);
		}
	}, 'json');
}

function setIsDeliveryPoint(id) {
	$.post("/web/warehouseaddress/updatewarehousedeliverypoint", {wareHouseId :id,isDeliveryPoint:1}, function(data, textStatus, req) {
		if(data.code == 200) {
			Dialog.alertInfo(data.message);
			$("#wareList").DataTable().draw();
		} else {
			Dialog.alertWarn(data.message);
		}
	}, 'json');
}

function delAddress(id) {
	layer.confirm('是否删除？', {
        icon: 3,
        btn: ['是', '否'], //按钮
        btn1: function (index) {
        	$.post("/web/warehouseaddress/delwarehouseaddress", {wareHouseId :id}, function(data, textStatus, req) {
        		if(data.code == 200) {
        			Dialog.alertInfo(data.message);
        			$("#wareList").DataTable().draw();
        		} else {
        			Dialog.alertWarn(data.message);
        		}
        	}, 'json');
            layer.close(index);
        }
    });
}
