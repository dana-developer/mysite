<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%pageContext.setAttribute("newLine", "\n");%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="" method="post">
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
						
					<c:set var="count" value="${fn:length(list)}" />
					<c:forEach items="${list}" var="vo" varStatus="status">		
						
					<tr>
						<td>3</td>
						<td style="text-align:left; padding-left:${vo.depth * 20}px">
							<c:if test="${vo.depth > 0}">
								<img src="${pageContext.request.contextPath}/assets/images/reply.png">
							</c:if>
								<a href="${pageContext.request.contextPath}/board?a=view&pageId=${vo.id}">${vo.title}</a>
						</td>
						<td>${vo.userName}</td>
						<td>${vo.hit}</td>
						<td>${vo.regDate}</td>
						<td>
							<c:if test="${vo.userId == authUser.id}">
								<a href="" class="del">삭제</a>
							</c:if>
						</td>
					</tr>
					</c:forEach>
				</table>
				
				<!-- pager 추가 -->
				<c:set var="count" value="${currentPage}" />
				<div class="pager">
					<ul>
						<li><a href="">◀</a></li>
						<c:choose>
							<c:when test="${count == currentPage}">
								<li class="selected">${count}</li>
							</c:when>
							<c:when test="${(count >= beginPage) and (count <= endPage)}">
								<li><a href="${pageContext.request.contextPath}/board?page=${count}">${count}</a></li>
							</c:when>
							<c:otherwise>
								<li>${count}</li>
							</c:otherwise>
						</c:choose>
						
						<c:choose>
							<c:when test="${(count+1) == currentPage}">
								<li class="selected">${count+1}</li>
							</c:when>
							<c:when test="${((count+1) >= beginPage) and ((count+1) <= endPage)}">
								<li><a href="${pageContext.request.contextPath}/board?page=${(count+1)}">${(count+1)}</a></li>
							</c:when>
							<c:otherwise>
								<li>${count+1}</li>
							</c:otherwise>
						</c:choose>
						
						<c:choose>
							<c:when test="${(count+2) == currentPage}">
								<li class="selected">${count+2}</li>
							</c:when>
							<c:when test="${((count+2) >= beginPage) and ((count+2) <= endPage)}">
								<li><a href="${pageContext.request.contextPath}/board?page=${(count+2)}">${(count+2)}</a></li>
							</c:when>
							<c:otherwise>
								<li>${count+2}</li>
							</c:otherwise>
						</c:choose>
						
						<c:choose>
							<c:when test="${(count+3) == currentPage}">
								<li class="selected">${count+3}</li>
							</c:when>
							<c:when test="${((count+3) >= beginPage) and ((count+3) <= endPage)}">
								<li><a href="${pageContext.request.contextPath}/board?page=${(count+3)}">${(count+3)}</a></li>
							</c:when>
							<c:otherwise>
								<li>${count+3}</li>
							</c:otherwise>
						</c:choose>
						
						<c:choose>
							<c:when test="${(count+4) == currentPage}">
								<li class="selected">${count+4}</li>
							</c:when>
							<c:when test="${((count+4) >= beginPage) and ((count+4) <= endPage)}">
								<li><a href="${pageContext.request.contextPath}/board?page=${(count+4)}">${(count+4)}</a></li>
							</c:when>
							<c:otherwise>
								<li>${count+4}</li>
							</c:otherwise>
						</c:choose>
												
						<li><a href="">▶</a></li>
					</ul>
				</div>					
				<!-- pager 추가 -->
				
				<c:if test="${not empty authUser.id}">
					<div class="bottom">
						<a href="" id="new-book">글쓰기</a>
					</div>
				</c:if>			
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>