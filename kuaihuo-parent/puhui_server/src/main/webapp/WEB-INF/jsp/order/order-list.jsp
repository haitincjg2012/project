<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@include file="/common/taglibs.jsp"%>
<script type="text/javascript">

    function orderQuery() {
        $('#orderTable').datagrid("reload", {
            startTime:$('#start').datebox('getValue'),
            endTime:$('#end').datebox('getValue'),
            orderNo: $('#orderNo').val(),
            payType: $('#payType').val(),
            phone: $('#phone').val(),
            hphone: $('#nickname').val(),
            status: $('#status').val()
        });
    }

    function deleteOrders() {
        var rows = $('#orderTable').datagrid('getChecked');
        if (rows == null || rows == '') {
            $.messager.alert('温馨提示', '请选择要删除的订单信息！', 'warning');
        } else {
            var ids = '';
            for ( var i = 0; i < rows.length; i++) {
                if (ids != '')
                    ids += ',';
                ids += rows[i].id;
            }
            $.messager.confirm(
                '温馨提示',
                '你确定要删除选中的订单信息吗？',
                function(r) {
                    if (r) {
                        $.ajax({type : "POST",
                            url : "${ctx}/order/deleteBatch?ids="+ ids,
                            success : function(data) {
                                data = $.parseJSON(data);
                                $.messager.show({
                                    title : '操作提示',
                                    msg : data.msg,
                                    showType : 'slide',
                                    style : {
                                        right : '',
                                        top : document.body.scrollTop
                                        + document.documentElement.scrollTop,
                                        bottom : ''
                                    }
                                });
                                $('#orderTable').datagrid("reload");
                                $('#orderTable').datagrid('clearSelections');
                            }
                        });
                    }
                    $('#orderTable').datagrid('clearSelections');
                });
        }
    }
	function getOrderDetail(rowId){
		   $.post('${ctx}/order/detail',{id:rowId},function(data){
		       var jsonData = $.parseJSON(data);
		       $("#orderForm").form('load',jsonData);

		       $('#orderWindow').window('open');
		   });
	}
    function operateByFeed(val, row) {
        var rowId = row.id;
        return " <a onclick=\'getOrderDetail("
            + rowId
            + ")\' style=\'cursor:pointer\''><img src=\'${ctx}/themes/operate/icon_detail.png\' alt=\'订单详情查看\' title=\'订单详情查看\' /></a>";
    }


    function closeOrderWindow(){
	    $('#orderWindow').window('close');
	}

	function  formatterPayType(value,row,index){

       return  value==0 ? "预付定金" : "全款";
	}

	function formatterStatus(value,row,index){
	    var status;
	    switch (value){
            case 0:
                status="待付款";
                break;
            case 1:
                status="已付定金";
                break;
            case 2:
                status="待发货";
                break;
            case 3:
                status="待收货";
                break;
            case 4:
                status="已评价";
               break;
            case 5:
                status="申请退款";
                break;
            case 6:
                status="已退款";
                break;
            case 7:
                status="已收货";
                break;
			default :
                status="";
					break;
        }
        return status;
	}
</script>
<table id="orderTable" class="easyui-datagrid" border="0"
	   fit="true"
	   data-options="idField:'id',method:'post',pageList:[20,30,50],rownumbers:true,singleSelect:false,toolbar:'#orderTool',url:'${ctx}/order/order-list',pagination:true">
	<thead>
	<tr>
		<th data-options="field:'id',checkbox:true">id</th>
		<th data-options="field:'order_no'" width="110" align="center">订单号</th>
		<th data-options="field:'hphone'" width="110" align="center">批发商账号</th>
		<th data-options="field:'phone'" width="110" align="center" >用户账号</th>
		<th data-options="field:'name'" width="110" align="center" >商品名称</th>
		<th data-options="field:'content'" width="110" align="center" >商品规格</th>
		<th data-options="field:'num'" width="100" align="center" >数量</th>
		<!-- <th data-options="field:'pay_type'" width="80" align="center" formatter="formatterPayType" >支付类型</th> -->
		<!-- <th data-options="field:'price'" width="110" align="center" >商品成本价格</th> -->
		<th data-options="field:'sale_price'" width="110" align="center" >商品销售价格</th>
		<th data-options="field:'subscription_money'" width="110" align="center" >定金价格</th>
		<th data-options="field:'negotiate_price'" width="110" align="center" >尾款价格</th>
		<%--<th data-options="field:'service_money'" width="110" align="center" >服务费</th>--%>
		<th data-options="field:'status'" width="80" align="center" formatter="formatterStatus" >订单状态</th>
		<th data-options="field:'created_time',sortable:true,sortOrder:'desc'" width="160" align="center" >订单创建时间</th>
		<th data-options="field:'deleteOne'" width="50" align='center'
			formatter="operateByFeed">操作</th>
	</tr>
	</thead>
</table>
<div id="orderTool" style="padding: 5px; height: auto">

	订单号：<input id="orderNo"
			   class="easyui-validatebox"
			   style="width: 150px" name="orderNo" />

	批发商账号	<input id="nickname" name="nickname"
				   class="easyui-validatebox"
				   style="width: 150px" />
	用户账号	<input id="phone" name="phone"
				   class="easyui-validatebox"
				   style="width: 100px" />
	创建时间	<input id="start"
				   class="easyui-datebox"
				   style="width: 100px" />-
	<input id="end"
		   class="easyui-datebox"
		   style="width: 100px" />
	订单状态：  <select id="status" name="status">
	<option value="">全部</option>
	<option value="0">待付款</option>
	<option value="1">已付定金</option>
	<!-- <option value="2">待发货</option> -->
	<option value="3">待收货</option>
	<option value="4">已评价</option>
	<option value="5">申请退款</option>
	<option value="6">已退款</option>
	<option value="7">已收货</option>
</select>
	<!-- 支付类型：  <select id="payType" name="payType">
	<option value="">全部</option>
	<option value="0">预付定金</option>
	<option value="1">全额</option>
</select> -->

	<a href="javascript:void(0)" class="easyui-linkbutton"
	   iconCls="icon-search" plain="true" onclick="orderQuery();">查询</a>

</div>

<div id="orderWindow" class="easyui-window" title="订单详情" style="width: 440px; height: 500px"
	 data-options="collapsible:false,minimizable:false,closed:true,maximizable:false,modal:true">
	<div class="easyui-form" id="orderForm" data-options="region:'center',split:true">
		<div style="padding: 10px;">
			<table style="width: 420px; ">
				<tr>
					<td>
						<fieldset name="fieldset26">
							<legend>
								<span class="legend">订单信息详情</span>
							</legend>
							<table>

								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">订单号：</span>
									</td>
									<td><input class="easyui-validatebox" readonly name="order_no"></td>
								</tr>

								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">批发商账号：</span>
									</td>
									<td><input class="easyui-validatebox" readonly name="hphone"></td>
								</tr>

								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">用户账号：</span>
									</td>
									<td><input class="easyui-validatebox" readonly name="phone"></td>
								</tr>

								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">商品名称：</span>
									</td>
									<td><input class="easyui-validatebox" readonly name="name"></td>
								</tr>

								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">商品规格：</span>
									</td>
									<td><input class="easyui-validatebox" readonly name="content"></td>
								</tr>

								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">数量：</span>
									</td>
									<td><input class="easyui-validatebox" readonly name="num"></td>
								</tr>

								<!-- <tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">支付类型：</span>
									</td>
									<td><input class="easyui-validatebox" readonly name="pay_type"></td>
								</tr>

								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">商品成本价：</span>
									</td>
									<td><input class="easyui-validatebox" readonly name="price"></td>
								</tr> -->

								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">商品销售价：</span>
									</td>
									<td><input class="easyui-validatebox" readonly name="sale_price"></td>
								</tr>

								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">定金价格：</span>
									</td>
									<td><input class="easyui-validatebox" readonly name="subscription_money"></td>
								</tr>
								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">尾款价格：</span>
									</td>
									<td><input class="easyui-validatebox" readonly name="negotiate_price"></td>
								</tr>

								<%--<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">服务费：</span>
									</td>
									<td><input class="easyui-validatebox" readonly name="service_money"></td>
								</tr>--%>

								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">收货人姓名：</span>
									</td>
									<td><input class="easyui-validatebox" readonly name="receive_name"></td>
								</tr>
								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">收货人电话：</span>
									</td>
									<td><input class="easyui-validatebox" readonly name="receive_phone"></td>
								</tr>
								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">收货人地址：</span>
									</td>
									<td><input class="easyui-validatebox" readonly name="receive_address"></td>
								</tr>
								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">订单创建时间：</span>
									</td>
									<td><input class="easyui-validatebox" readonly name="created_time"></td>
								</tr>
								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">定金付款时间：</span>
									</td>
									<td><input class="easyui-validatebox" readonly name="subscription_time"></td>
								</tr>
								
								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">订单发货时间：</span>
									</td>
									<td><input class="easyui-validatebox" readonly name="send_time"></td>
								</tr>
								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">尾款付款时间：</span>
									</td>
									<td><input class="easyui-validatebox" readonly name="pay_time"></td>
								</tr>

								<!-- <tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">订单收货时间：</span>
									</td>
									<td><input class="easyui-validatebox" readonly name="receive_time"></td>
								</tr>

								 <tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">订单发货时间：</span>
									</td>
									<td><input class="easyui-validatebox" readonly name="send_time"></td>
								</tr> 
								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">订单收货时间：</span>
									</td>
									<td><input class="easyui-validatebox" readonly name="receive_time"></td>
								</tr> -->

								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">订单评价时间：</span>
									</td>
									<td><input class="easyui-validatebox" readonly name="comment_time"></td>
								</tr>
								<tr>
									<td width="25%" height="28" align="right" bgcolor="#ececec">
										<span style="font-size: 15px; color: rgb(2, 48, 97);">订单退款时间：</span>
									</td>
									<td><input class="easyui-validatebox" readonly name="refund_time"></td>
								</tr>



							</table>
						</fieldset>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div data-options="region:'south',border:false"
		 style="text-align: center; padding: 5px 0 5px;">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"
		   href="javascript:void(0)" onclick="closeOrderWindow();">关闭窗口</a>
	</div>
</div>