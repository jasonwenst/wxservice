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
	<div class="weui-pull-to-refresh-layer">
	  <div class="pull-to-refresh-arrow"></div> <!-- 上下拉动的时候显示的箭头 -->
	  <div class="pull-to-refresh-preloader"></div> <!-- 正在刷新的菊花 -->
	  <div class="down">下拉刷新</div><!-- 下拉过程显示的文案 -->
	  <div class="up">释放刷新</div><!-- 下拉超过50px显示的文案 -->
	  <div class="refresh">正在刷新...</div><!-- 正在刷新时显示的文案 -->
	</div>
	<div class="weui-row">
	  <div class="weui-col-20" style="text-align: center;"><img src="<%=basePath %>img/redpack.jpg" alt="icon" style="width:40px"></div>
	  <div class="weui-col-30" style="text-align: center;">33元</div>
	  <div class="weui-col-50" style="text-align: left;">2016-12-08 16:32:17</div>
   </div> 
  <div class="weui-infinite-scroll">
	  <div class="infinite-preloader"></div><!-- 菊花 -->
	  正在加载... <!-- 文案，可以自行修改 -->
  </div>
  
  	<script src="<%=basePath %>js/jquery.min.js"></script>
	<script src="<%=basePath %>js/jquery-weui.min.js"></script>
  <script type="text/javascript">
  $(document.body).pullToRefresh();
  $(document.body).on("pull-to-refresh", function() {
	  $(document.body).pullToRefreshDone();
	});
  $(document.body).pullToRefreshDone();
  $(document.body).infinite();
  </script>
  
</body>
</html>