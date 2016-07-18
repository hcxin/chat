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
<title>消息推送</title>
<link rel="stylesheet" href="<%=contextPath%>/scripts/static/bootstrap/bootstrap.min.css">
<link rel="stylesheet" href="<%=contextPath%>/style/subStyle.css">
</head>
<body>
	<div class="container">

		<!-- Static navbar -->
		<nav class="navbar navbar-default">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target="#navbar" aria-expanded="false"
						aria-controls="navbar">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
				</div>
				<div id="navbar" class="navbar-collapse collapse">
					<ul class="nav navbar-nav" class="nav nav-tabs">
						<li class="active"><a href="#home" data-toggle="tab">推送首页</a></li>
						<li><a id="userListBtn" href="#userList" data-toggle="tab">用户列表</a></li>
						<li><a href="#history" data-toggle="tab">推送历史</a></li>
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown" role="button" aria-haspopup="true"
							aria-expanded="false">其他<span class="caret"></span></a>
							<ul class="dropdown-menu">
								<li><a href="#others" data-toggle="tab">其他</a></li>
								<!--                   <li><a href="#">Another action</a></li>
                  <li><a href="#">Something else here</a></li>
                  <li role="separator" class="divider"></li>
                  <li class="dropdown-header">Nav header</li>
                  <li><a href="#">Separated link</a></li>
                  <li><a href="#">One more separated link</a></li> -->
							</ul></li>
					</ul>
					<ul class="nav navbar-nav navbar-right">
						<li class="active"><a href="">当前用户<span class="sr-only">(current)</span></a></li>
						<li><a href="./">注销</a></li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
			<!--/.container-fluid -->
		</nav>

		<div class="tab-content">
			<div class="tab-pane active" id="home">
				<div class="jumbotron">
					<p>
						<input type="text" class="form-control" id="pushContent" placeholder="推送内容" />
					</p>
					<p>
						<a data-toggle="modal"  data-target="#warnModal" class="btn btn-lg btn-primary" role="button">推送</a>
					</p>
				</div>
			</div>
			<div class="tab-pane" id="userList">
				<div>
					<table class="table table-hover">
						<!-- <caption>用户列表</caption> -->
						<thead>
							<tr>
								<th>用户ID</th>
								<th>连接时间</th>
								<th>状态</th>
							</tr>
						</thead>
						<tbody id="userListTable" data-bind="foreach: userList">
							<tr class="success mouse-pointer" data-bind ="click: $root.singlePushWindow">
								<td data-bind = "text: userName"></td>
								<td data-bind = "text: connectTime"></td>
								<td data-bind = "text: connectStatus"></td>
							</tr>
<!-- 							<tr class="active">
								<td>haichen</td>
								<td>23/11/2013</td>
								<td>已连接</td>
							</tr>
							<tr class="success">
								<td>zyk</td>
								<td>10/11/2013</td>
								<td>已断开</td>
							</tr>
							<tr class="warning">
								<td>yixu</td>
								<td>20/10/2013</td>
								<td>已断开</td>
							</tr>
							<tr class="danger">
								<td>sun</td>
								<td>20/10/2013</td>
								<td>已断开</td>
							</tr> -->
						</tbody>
					</table>
				</div>
			</div>
			<div class="tab-pane" id="history">待开发...</div>
			<div class="tab-pane" id="others">待开发...</div>
		</div>
		
<div class="modal fade" id="warnModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
	  <div class="modal-header">
	  		<a class="close" data-dismiss="modal">×</a>
	  </div>
      <div class="modal-body">
        <h5>该消息将会推送给所有在线用户,确认推送该消息吗？</h5>
      </div>
      <div class="modal-footer">
				<a href="#" class="btn" data-dismiss="modal">关闭</a>
				<a id ="pushBtn" href="#" class="btn btn-success">确认推送</a> 
			</div>
    </div>
  </div>
</div>

<div class="modal fade" id="singlePushModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content">
	  <div class="modal-header">
	  		<a class="close" data-dismiss="modal">×</a>
	  </div>
      <div class="modal-body">
     	<input type="text" class="form-control" id="singlePushContent" placeholder="推送内容" />
      </div>
      <div class="modal-footer">
				<a href="#" class="btn" data-dismiss="modal">关闭</a>
				<a id ="singlePushBtn" href="#" class="btn btn-success">确认推送</a> 
			</div>
    </div>
  </div>
</div>		
	</div>
	<!-- /container -->
	<script type="text/javascript"
		src="<%=contextPath%>/scripts/static/jquery-1.12.1.min.js"></script>
	<script type="text/javascript"
		src="<%=contextPath%>/scripts/static/bootstrap/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="<%=contextPath%>/scripts/static/jquery.base64.js"></script>
	<script type="text/javascript"
		src="<%=contextPath%>/scripts/static/knockout-3.4.0.js"></script>
	<script type="text/javascript"
		src="<%=contextPath%>/scripts/main/push.js"></script>
</body>
</html>