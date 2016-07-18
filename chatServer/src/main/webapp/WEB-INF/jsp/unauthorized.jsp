<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%
    String contextPath = request.getContextPath();
    request.setCharacterEncoding("utf-8");
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>无操作权限</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="<%=contextPath%>/scripts/static/jqueryMobile/jquery.mobile-1.4.5.min.css">
</head>
<body>
<div style='margin:0 auto;width:0px;height:0px;overflow:hidden;'>
<img src="<%=contextPath%>/resources/imges/share_cat.jpeg" />
</div>
<div id="pageOne" data-role="page" data-title="无操作权限" style="text-align: center;margin-top: 100px;">
<p>无操作权限</p>
</div>
<script type="text/javascript" src="<%=contextPath%>/scripts/static/jquery-1.12.1.min.js"></script>
<script type="text/javascript" src="<%=contextPath%>/scripts/static/jqueryMobile/jquery.mobile-1.4.5.min.js"></script>
</body>
</html>