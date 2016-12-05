<!DOCTYPE html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%  
    String path = request.getContextPath();  
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";  
    long t = System.currentTimeMillis();  
    String fee = request.getParameter("fee");
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
<div class="weui_msg">
  <div class="weui_icon_area"><i class="weui_icon_success weui_icon_msg"></i></div>
  <div class="weui_text_area">
    <h2 class="weui_msg_title">支付成功</h2>
    <p class="weui_msg_desc">您已经成功支付<%=fee %>元</p>
  </div>
  <div class="weui_opr_area">
    <p class="weui_btn_area">
      <a href="javascript:;" onclick = "close()" class="weui_btn weui_btn_primary">确定</a>
    </p>
  </div>
</div>
<script type="text/javascript">
	function close() {
		$.close();
	}
</script>
</body>
</html>