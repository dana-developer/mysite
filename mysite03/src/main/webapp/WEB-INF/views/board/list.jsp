<%@ taglib uri="jakarta.tags.core" prefix="c"%>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

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
				<form id="search_form" action="${pageContext.request.contextPath}/board" method="post">
					<input type="text" id="kwd" name="kwd" value="${list.keyword}">
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
					
					<c:forEach items="${result.list}" var="vo" varStatus="status">		
						<tr>
							<td>${result.cntPages - (result.currentPage - 1) * 5 - status.index}</td>
							<td style="text-align:left; padding-left:${vo.depth * 20}px">
								<c:if test="${vo.depth > 0}">
									<img src="${pageContext.request.contextPath}/assets/images/reply.png">
								</c:if>
									<a href="${pageContext.request.contextPath}/board/view?boardId=${vo.id}">${vo.title}</a>
							</td>
							<td>${vo.userName}</td>
							<td>${vo.hit}</td>
							<td>${vo.regDate}</td>
							<td>
								<c:if test="${vo.userId == authUser.id}">
									<a href="${pageContext.request.contextPath}/board/delete?boardId=${vo.id}" class="del">삭제</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				
				<div class="pager">
					<c:if test="${not empty result.keyword}">
						<c:set var="pageKeyword" value = "&kwd=${result.keyword}"></c:set>
					</c:if>
					<ul>
						<li>
							<c:choose>
								<c:when test="${result.beginPage > 1}">
									<a href="${pageContext.request.contextPath}/board?page=${result.beginPage-1}${pageKeyword}">◀</a>
								</c:when>
								<c:otherwise>◀</c:otherwise>
							</c:choose>
						</li>
						<c:forEach var='i' begin="${result.beginPage}" end ="${result.endPage}">
							<c:choose>
								<c:when test="${i == result.currentPage}">
									<li class="selected">${i}</li>
								</c:when>
								<c:when test="${(i >= result.startPage) and (i <= result.totalPage)}">
									<li><a href="${pageContext.request.contextPath}/board?page=${i}${pageKeyword}">${i}</a></li>
								</c:when>
								<c:otherwise>
									<li>${i}</li>
								</c:otherwise>
							</c:choose>
						</c:forEach>
						<li>
							<c:choose>
								<c:when test="${result.endPage < result.totalPage}">
									<a href="${pageContext.request.contextPath}/board?page=${result.endPage+1}${pageKeyword}">▶</a>
								</c:when>
								<c:otherwise>▶</c:otherwise>
							</c:choose>
						</li>	
					</ul>
				</div>					
				
				<c:if test="${not empty authUser.id}">
					<div class="bottom">
						<a href="${pageContext.request.contextPath}/board/write?type=origin" id="new-book">글쓰기</a>
					</div>
				</c:if>			
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"/>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>