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
<title>我的红包</title>

<link rel="stylesheet" href="<%=basePath %>css/weui.min.css">
<link rel="stylesheet" href="<%=basePath %>css/jquery-weui.min.css">


</head>
<body style="background-color:#CAE1FF">
	<div class="weui-pull-to-refresh-layer">
	  <div class="pull-to-refresh-arrow"></div> <!-- 上下拉动的时候显示的箭头 -->
	  <div class="pull-to-refresh-preloader"></div> <!-- 正在刷新的菊花 -->
	  <div class="down">下拉刷新</div><!-- 下拉过程显示的文案 -->
	  <div class="up">释放刷新</div><!-- 下拉超过50px显示的文案 -->
	  <div class="refresh">正在刷新...</div><!-- 正在刷新时显示的文案 -->
	</div>
	<div class="weui-row" style="background-color:#32CD32">
		<div class="weui-col-30" style="text-align: right;margin-top:10px;margin-left:10px;margin-bottom:10px">红包总数：</div>
		<div id="packCount" class="weui-col-20" style="text-align: left;margin-top:10px"></div>
		<div class="weui-col-30" style="text-align: center;margin-top:10px">红包总额：</div>
		<div id="allaMount" class="weui-col-20" style="text-align: left;margin-top:10px"></div>
		
	</div>
	<div id="redPackList" style="margin-top:5px;background-color:#CAE1FF">
   </div>
   
<!--    <div class="weui-infinite-scroll"> -->
<!-- 	  <div class="infinite-preloader"></div>菊花 -->
<!-- 	  正在加载... 文案，可以自行修改 -->
<!-- 	</div> -->
  
  	<script src="<%=basePath %>js/jquery.min.js"></script>
	<script src="<%=basePath %>js/jquery-weui.min.js"></script>
  <script type="text/javascript">
  $(document.body).pullToRefresh();
  queryRedpack();
  $(document.body).on("pull-to-refresh", function() {
	  
	  // 清空数据
	  $("#redPackList").empty();
	  queryRedpack();
	  $(document.body).pullToRefreshDone();
	});
  $(document.body).pullToRefreshDone();
  
  
//   var loading = false;  //状态标记
//   $(document.body).infinite(100).on("infinite", function() {
//     if(loading) return;
//     loading = true;
//     setTimeout(function() {
//       $("#redPackList").append("<p> 我是新加载的内容 </p>");
//       loading = false;
//     }, 1000);   //模拟延迟
//   });
  
  
  
  function queryRedpack() {
	  var html_div_col = '<div class="weui-row">';
	  var html_col_header = '<div class="weui-col-20" style="text-align: center;">';
	  var html_col_moddle = '<div class="weui-col-30" style="text-align: center;">';
	  var html_col_left = '<div class="weui-col-50" style="text-align: left;">';
	  var append_rt_div = '</div>';
	  var icon_redpack = html_col_header + '<img src=' + '<%=basePath %>' +'img/redpack.jpg' + ' alt="icon"' + 'style="width:40px">' + append_rt_div;
      $.ajax({
	      type: "GET",
          url: "<%=basePath%>/payment/queryRedpack",
	      data: {"code": "<%=code %>"},
	      contentType : 'application/json',
	      success: function(data){
	   	   if(data != null) {
	   		   var allAmount = 0;
	   		   for(var i = 0; i < data.length; i++) {
	   			
	   			var innerHtml = html_div_col + icon_redpack + html_col_moddle + data[i].totalFee 
	   				+ append_rt_div +html_col_left + new Date(data[i].createTime).format("yyyy-MM-dd hh:mm:ss") + append_rt_div;
	   			
	   			$("#redPackList").append(innerHtml);
	   			allAmount+= data[i].totalFee/100;
	   		   }
	   		   
	   		$("#packCount").html(data.length+"个");
	   		$("#allaMount").html(allAmount+"元");
	   		   
	   	   }
	      }
 	  });
  }
  
  
  
  
  /** 
   * 时间对象的格式化; 
   */  
  Date.prototype.format = function(format) {  
      /* 
       * eg:format="yyyy-MM-dd hh:mm:ss"; 
       */  
      var o = {  
          "M+" : this.getMonth() + 1, // month  
          "d+" : this.getDate(), // day  
          "h+" : this.getHours(), // hour  
          "m+" : this.getMinutes(), // minute  
          "s+" : this.getSeconds(), // second  
          "q+" : Math.floor((this.getMonth() + 3) / 3), // quarter  
          "S" : this.getMilliseconds()  
          // millisecond  
      }  
    
      if (/(y+)/.test(format)) {  
          format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4  
                          - RegExp.$1.length));  
      }  
    
      for (var k in o) {  
          if (new RegExp("(" + k + ")").test(format)) {  
              format = format.replace(RegExp.$1, RegExp.$1.length == 1  
                              ? o[k]  
                              : ("00" + o[k]).substr(("" + o[k]).length));  
          }  
      }  
      return format;  
  } 
  </script>
  
</body>
</html>