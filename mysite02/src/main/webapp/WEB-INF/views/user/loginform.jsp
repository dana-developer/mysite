<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.Optional"%>
<!doctype html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="<%=request.getContextPath()%>/assets/css/user.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/includes/header.jsp"></jsp:include>
		<div id="content">
			<div id="user">
				<form id="login-form" name="loginform" method="post" action="<%=request.getContextPath()%>/user">
					<input type="hidden" name = "a" value="login">
					<label class="block-label" for="email">이메일</label>
					<input id="email" name="email" type="text" value="<%=Optional.ofNullable(request.getAttribute("email")).orElse("")%>">
					<label class="block-label" >패스워드</label>
					<input name="password" type="password" value="">
					<%
						if("fail".equals(request.getAttribute("result"))) {
					%>
					<p>
						로그인이 실패했습니다.
					</p>
					<%
						}
					%>
					<input type="submit" value="로그인">
				</form>
			</div>
		</div>
			<jsp:include page="/WEB-INF/views/includes/navigation.jsp"></jsp:include>
			<jsp:include page="/WEB-INF/views/includes/footer.jsp"></jsp:include>
	</div>
</body>
</html>