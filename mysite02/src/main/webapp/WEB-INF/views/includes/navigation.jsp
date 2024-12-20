<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="navigation">
	<c:set var="path" value = "${pageContext.request.contextPath}"/>
	<ul>
		<li><a href="${path}">안대혁</a></li>
		<li><a href="${path}%>/guestbook">방명록</a></li>
		<li><a href="${path}/board ">게시판</a></li>
	</ul>
</div>