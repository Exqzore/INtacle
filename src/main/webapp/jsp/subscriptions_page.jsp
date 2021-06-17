<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle scope="session" basename="properties.language"/>

<html>
<head>
    <meta charset="UTF-8">
    <title>INtacle profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/general_style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/top-line_style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/body-page_style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/left-navbar_style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/subscriptions-page_style.css">
    <script src="https://kit.fontawesome.com/f5dea48adc.js" crossorigin="anonymous"></script>
</head>

<body>
<%@ include file="header.jsp" %>
<div class="page-layout">
    <div class="empty__page-layout"></div>
    <%@ include file="left_section.jsp" %>
    <div class="right__page-layout">
        <div class="main-container">
            <div class="title-text"><fmt:message key="subscriptions.title"/></div>
            <div class="decoration-text"></div>
            <c:choose>
                <c:when test="${users.size() == 0}">
                    <div class="if-is-empty-text"><fmt:message key="subscriptions.empty"/></div>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${users}" var="subscription">
                        <div class="user-elem__container">
                            <a href="${pageContext.request.contextPath}/main?command=show_profile&login=${subscription.login}"
                               class="user-elem__page">
                                <img class="user-elem__profile-image" src="image/avatar/${subscription.avatarImagePath}"
                                     alt="User avatar" width="70" height="70"/>
                                <div class="user-elem__login">${subscription.login}</div>
                            </a>
                            <div class="user-elem__chat-link">
                                <c:choose>
                                    <c:when test="${!subscription.login.equals(user.login)}">
                                        <a href="${pageContext.request.contextPath}/main?command=create_chat&user_id=${subscription.id}"
                                           class="chat-link">
                                            <fmt:message key="subscriptions.message"/>
                                        </a>
                                    </c:when>
                                </c:choose>
                            </div>
                        </div>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    <div class="empty__page-layout"></div>
</div>

<script src="${pageContext.request.contextPath}/js/header.js"></script>
</body>
</html>
