<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<%@ page import = "mysite.vo.UserVo" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	UserVo authUser = (UserVo) session.getAttribute("authUser");
%>
<c:set var="path" value = "${pageContext.request.contextPath}"/>

<div id="header">
	<h1>MySite</h1>
	<ul>
		<c:if test="${empty authUser }">
			<li><a href="${path}/user?a=loginform">로그인</a><li>
			<li><a href="${path}/user?a=joinform">회원가입</a><li>
		</c:if>
		
		<c:if test="${not empty authUser }">
			<li><a href="${path}/user?a=updateform">회원정보수정</a><li>
			<li><a href="${path}/user?a=logout">로그아웃</a><li>
			<li><%=authUser.getName() %>님 안녕하세요 ^^;</li>
		</c:if>

	</ul>
</div>