<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>INtacle</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/top-line_style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login-page_style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/registration-page_style.css">
    <script src="https://kit.fontawesome.com/f5dea48adc.js" crossorigin="anonymous"></script>
</head>
<body>
<%@ include file="header.jsp" %>
<div class="main-holder">
    <div class="empty-place"></div>
    <%@ include file="registration_section.jsp" %>
    <div class="empty-place"></div>
</div>
<script src="${pageContext.request.contextPath}/js/header.js"></script>
<script src="${pageContext.request.contextPath}/js/registration_page.js"></script>
</body>
</html>
