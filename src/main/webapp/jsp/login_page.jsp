<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>INtacle</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/top-line_style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login-page_style.css">
    <script src="https://kit.fontawesome.com/f5dea48adc.js" crossorigin="anonymous"></script>
</head>
<c:choose>
<c:when test="${requestUser != null}">
<body onload="loadPage('${locale}', '${requestUser.login}')"></c:when>
<c:otherwise>
<body onload="loadPage('${locale}')"></c:otherwise>
</c:choose>
<%@ include file="header.jsp" %>
<div class="main-holder">
    <div class="empty-place"></div>
    <%@ include file="login_section.jsp" %>
    <div class="empty-place"></div>
</div>
<script src="${pageContext.request.contextPath}/js/header.js"></script>
<script src="${pageContext.request.contextPath}/js/login_page.js"></script>
</body>
</html>
