<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%  
    String path = request.getContextPath();  
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";  
    long t = System.currentTimeMillis();  
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no">
<title>Insert title here</title>

<link rel="stylesheet" href="css/weui.min.css">
<link rel="stylesheet" href="css/jquery-weui.min.css">


</head>
<body>

	<div class="weui_cells weui_cells_form">
		<div class="weui_cell">
			<div class="weui_cell_hd">
				<label class="weui_label">金额</label>
			</div>
			<div class="weui_cell_bd weui_cell_primary">
				<input class="weui_input" type="number" placeholder="请输入金额">
			</div>
		</div>
		<div class="weui_btn_area">
			<a href="javascript:;" class="weui_btn weui_btn_primary"
				id="btnPrepay">确定</a>
		</div>
	</div>


	<script src="js/jquery.min.js"></script>
	<script src="js/jquery-weui.min.js"></script>
	<script type="text/javascript"
		src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>

	<script type="text/javascript">
	
		$(function(){
	        //按钮单击时执行
	        $("#btnPrepay").click(function(){
	              
	        	var jsonObject = {
	        			name : "json"
	        	}
	        	
	              //Ajax调用处理
	            $.ajax({
	               type: "POST",
	               url: "<%=basePath%>/payment/prepay",
	               data: JSON.stringify(getJsonData()),
	               contentType : 'application/json',
	               success: function(data){
	                        
	                  }
	            });
	            
	         });
	    });	
	
		function getJsonData() {
			var json = {
					"appId" : "",
					"mchId" : "",
					"deveiceInfo" : "WEB",
					"nonceStr" : "",
					"sign" : "",
					"body" : "充值中心-会员充值",
					"totalFee" : 100,
					"outTradeNo" : "34123452345",
					"spbillCreateIp" : "123.56.233.132",
					"notifyURL" : "https://123.56.233.132/wechatserver/payment",
					"detail" : "vip",
					"tradeType" : "JSAPI"
			}
			
			return json;
		}
		
		function pay() {
			
			
			window.location.href= url + "&showwxpaytitle=1";   
		}
	</script>
</body>
</html>