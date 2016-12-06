<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%  
    String path = request.getContextPath();  
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";  
    long t = System.currentTimeMillis();  
    String code = request.getParameter("code");
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, user-scalable=no">
<title>Insert title here</title>

<link rel="stylesheet" href="<%=basePath %>css/weui.min.css">
<link rel="stylesheet" href="<%=basePath %>css/jquery-weui.min.css">


</head>
<body>

	<div class="weui_cells weui_cells_form">
		<div class="weui_cell">
			<div class="weui_cell_hd">
				<label class="weui_label">金额
			</div>
			<div class="weui_cell_bd weui_cell_primary">
				<input id = "fee" class="weui_input" type="number" placeholder="请输入金额">
			</div>
		</div>
		<div class="weui_btn_area">
			<a href="javascript:;" class="weui_btn weui_btn_primary"
				id="btnPrepay" onclick="savePayInfo(getPayInfo())">确定</a>
		</div>
	</div>


	<script src="<%=basePath %>js/jquery.min.js"></script>
	<script src="<%=basePath %>js/jquery-weui.min.js"></script>
	<script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>

	<script type="text/javascript">
	
// 		$(function(){
// 	        //按钮单击时执行
// 	        $("#btnPrepay").click(function(){
	              
// 	              //Ajax调用处理
// 	            $.ajax({
// 	               type: "POST",
<%-- 	               url: "<%=basePath%>/payment/getJSSDKPayInfo", --%>
// 	               data: JSON.stringify(getJsonData()),
// 	               contentType : 'application/json',
// 	               success: function(data){
// 	            	   if(data != null) {
// 	            		   onBridgeReady(data);
// 	            	   }
//                    }
// 	            });
	            
// 	         });
// 	    });	
	
		function getJsonData() {
			var fee = $("#fee").val();
			var code = "<%=code%>";
			var json = {
					"appId" : "",
					"mchId" : "",
					"deveiceInfo" : "WEB",
					"nonceStr" : "",
					"sign" : "",
					"body" : "充值中心-会员充值",
					"totalFee" : fee,
					"outTradeNo" : "34123452345",
					"spbillCreateIp" : "127.0.0.1",
					"notifyURL" : "https://123.56.233.132/wechatserver/payment",
					"detail" : "vip",
					"tradeType" : "JSAPI",
					"attach" : code
			}
			
			return json;
		}
		
		function getPayInfo() {
			
			var json = {
					"appId" : "appIdfasda1",
					"openId" : "sregsfgfdgs1",
					"createTimestamp" : 253452342,
					"outTradeNo" : "fdasfad1",
					"totalFee" : 2300
			}
			
			return JSON.stringify(json);
			
		}
		
		function onBridgeReady(data){
			   WeixinJSBridge.invoke(
			       'getBrandWCPayRequest', {
			           "appId" : data.appId,     //公众号名称，由商户传入     
			           "timeStamp" : data.timeStamp,         //时间戳，自1970年以来的秒数     
			           "nonceStr" : data.nonceStr, //随机串     
			           "package" : data.package,     
			           "signType" : data.signType,         //微信签名方式：     
			           "paySign" : data.paySign //微信签名 
			       },
			       function(res){
			           if(res.err_msg == "get_brand_wcpay_request：ok" ) {
			        	   savePayInfo(data);
			           }     // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。 
			       }
			   ); 
			}
			if (typeof WeixinJSBridge == "undefined"){
			   if( document.addEventListener ){
			       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
			   }else if (document.attachEvent){
			       document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
			       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
			   }
			}else{
			   onBridgeReady();
			}
			
			
		function savePayInfo(data) {
			 $.ajax({
	               type: "POST",
	               url: "<%=basePath%>/payment/savePayInfo",
	               data: data,
	               contentType : 'application/json',
	               success: function(res){
                 }
	            });
		}
		
		
	</script>
</body>
</html>