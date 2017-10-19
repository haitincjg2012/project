/**
 * @描述 针对单个下拉设计
 * @param url -- 地址
 * @param id -- html
 * @param selectId -- 下拉框id
 * @param type -- 灵活处理
 * @param onclickFunction -- 点击方法 
 * @author 何文浪
 * @时间 2017-5-27
 */
var select = function(url,id,selectId,type,onclickFunction){
	$.ajax({
		async:false,
		type : "GET",
        url: url,
		success: function(data) {
			if(data){
				var selectVal ="";
				if(onclickFunction){
                    selectVal = " <select  data-live-search='true' class='selectpicker' name='4' id='" + selectId + "' onchange='javascript:" + onclickFunction + "'> <option value='' >请选择</option>";
				}else{
					selectVal =" <select  data-live-search='true' class='selectpicker' name='4' id='"+selectId+"' > <option value='' >请选择</option>";
				}
					$.each(data, function(i, item){ 
						if(type == "level"){
							selectVal += " <option  value='"+item.id+"'>"+item.levelName+"</option> ";
						}else{
							selectVal += " <option  value='"+item.id+"'>"+item.name+"</option> ";
						}
					});
					selectVal +="</select>";
					$("#"+id).html(selectVal);
					$(".selectpicker").selectpicker('refresh');
			}
		}
	});
};

/**
 * @描述 跳转
 * @param val -- 地址
 * @param url -- 跳转链接
 * @author 何文浪
 * @时间 2017-5-27
 */
var localHref = function(url){
	window.location.href=url;
};

/**
 * @描述 ajax跳转
 * @param val -- 地址
 * @param url -- 跳转链接
 * @author 何文浪
 * @时间 2017-5-27
 */
var auditOrFrozenOrDelete = function(url,data,text,type,hrefURL,successText,filedText){
	 layer.confirm('确定'+text+'吗？',{
	        title: text,
	        icon: 3,
	        btnAlign: 'c',
	        closeBtn : 0
	    },function(indexs){
	    	$.ajax({
	    		async:false,
	    		type : "POST",
	            url: url,
	            data:data,
	    		success: function(data) {
	    			if(data.success){
	    				if(type){
	    					if(type=="object"){
	    						if(successText){
	    							 layer.alert(successText, {icon: 1,btnAlign: 'c', closeBtn : 0},function(index){
	    								 hrefURL.draw(false);
	    								 layer.close(index);
	    							 });
		    					}else{
		    						 layer.alert(data.message, {icon: 1,btnAlign: 'c', closeBtn : 0},function(index){
		    							 hrefURL.draw(false);
		    							 layer.close(index);
		    						 });
		    					}
	    					}else{
	    						if(successText){
		    						 layer.alert(successText, {icon: 1,btnAlign: 'c', closeBtn : 0},function(index){
		    							 localHref(hrefURL);
		    							 layer.close(index);
		    						 });
		    					}else{
		    						 layer.alert(data.message, {icon: 1,btnAlign: 'c', closeBtn : 0},function(index){
		    							 localHref(hrefURL);
		    							 layer.close(index);
		    						 });
		    					}
	    					}
	    				}else{
	    					if(successText){
	    						 layer.alert(successText, {icon: 1,btnAlign: 'c', closeBtn : 0});
	    					}else{
	    						 layer.alert(data.message, {icon: 1,btnAlign: 'c', closeBtn : 0});
	    					}
	    				}
	    			}else{
                        layer.alert(data.message || "操作失败", {icon: 2, btnAlign: 'c', closeBtn: 0});
	    			}
	    		}
	    });
	});
};
//导出
var exportExcel = function(formId,url,data){
//	data = JSON.stringify(data);
//	alert(JSON.parse(data));
	var str = "";
	$.each(data,function(i,n){
		str+=i+"="+n+"&";
	});
	window.open(url+"?"+str,"_blank");
	//或者可以提交表单
//	$('#'+formId).attr('action',url);
//	$('#'+formId).attr('target','_blank');
//	$('#'+formId).attr('data',data);
//	$('#'+formId).submit();
};

function getPositionVal() {
    var $townSelect = $("select[name='townId']");
    var $countySelect = $("select[name='countyId']");
    var $citySelect = $("select[name='cityId']");
    var $provinceSelect = $("select[name='provinceId']");
    return $townSelect.attr("data-area-code-selected") ||
        $countySelect.attr("data-area-code-selected") ||
        $citySelect.attr("data-area-code-selected") ||
        $provinceSelect.attr("data-area-code-selected") || ""
}