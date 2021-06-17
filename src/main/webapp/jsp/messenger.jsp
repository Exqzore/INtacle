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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/messenger_style.css">
    <script src="https://kit.fontawesome.com/f5dea48adc.js" crossorigin="anonymous"></script>
</head>

<body>
<%@ include file="header.jsp" %>
<div class="page-layout">
    <div class="empty__page-layout"></div>
    <%@ include file="left_section.jsp" %>
    <div class="right__page-layout">
        <div class="main-container">
            <div class="title-text"><fmt:message key="messenger.title"/></div>
            <div class="decoration-text"></div>
            <c:choose>
                <c:when test="${chats.size() == 0}">
                    <div class="if-is-empty-text"><fmt:message key="messenger.empty"/></div>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${chats}" var="chat">
                        <a href="${pageContext.request.contextPath}/main?command=show_chat&chat=${chat.id}"
                           class="user-elem__container">
                            <div id="chat-${chat.id}" class="user-elem__page">
                                <img class="user-elem__profile-image"
                                     src="${pageContext.request.contextPath}/main?command=take_file&file_name=${chat.recipient.avatarImagePath}"
                                     alt="User avatar" width="70" height="70"/>
                                <div>${chat.recipient.login}</div>
                            </div>
                        </a>
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
