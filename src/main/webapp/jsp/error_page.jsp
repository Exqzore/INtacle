<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle scope="session" basename="properties.language"/>

<html>
<head>
    <title>INtacle</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/top-line_style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/activation_page_style.css">
    <script src="https://kit.fontawesome.com/f5dea48adc.js" crossorigin="anonymous"></script>
</head>

<body>
<%@ include file="header.jsp" %>
<div class="main-holder">
    <div class="empty-place"></div>
    <div class="activation-description">
        <h2>ERROR!</h2>
        <h3>${pageContext.response.status}</h3>
        <c:choose>
            <c:when test="${exception.getMessage() != null}">
                ${exception.getMessage()}<br>
                ${exception.getClass()}
            </c:when>
        </c:choose>
    </div>
    <div class="empty-place"></div>
</div>
<script src="${pageContext.request.contextPath}/js/header.js"></script>
</body>
</html>