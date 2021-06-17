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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/chat_style.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <script src="https://kit.fontawesome.com/f5dea48adc.js" crossorigin="anonymous"></script>
</head>

<body>
<%@ include file="header.jsp" %>
<div class="page-layout">
    <div class="empty__page-layout"></div>
    <%@ include file="left_section.jsp" %>
    <div class="right__page-layout">
        <div class="empty-top"></div>
        <div class="chat-container">
            <div class="title-text"><fmt:message key="chat.title"/></div>
            <div class="decoration-text__chat"></div>
            <div class="chat-panel">
                <c:forEach items="${messages}" var="message">
                    <c:choose>
                        <c:when test="${message.author.id == user.id}">
                            <div class="row row-right">
                                <div class="chat-bubble chat-bubble__right">
                                    <div class="message-title title-right">
                                        <div class="message-date">${message.creationDate}</div>
                                        <div class="message-author">${message.author.login}</div>
                                    </div>
                                        ${message.content}
                                </div>
                                <img class="message-image"
                                     src="${pageContext.request.contextPath}/main?command=take_file&file_name=${message.author.avatarImagePath}"
                                     alt="User avatar" width="44" height="44"/>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="row row-left">
                                <img class="message-image"
                                     src="${pageContext.request.contextPath}/main?command=take_file&file_name=${message.author.avatarImagePath}"
                                     alt="User avatar" width="44" height="44"/>
                                <div class="chat-bubble chat-bubble__left">
                                    <div class="message-title">
                                        <div class="message-author">${message.author.login}</div>
                                        <div class="message-date">${message.creationDate}</div>
                                    </div>
                                        ${message.content}
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </div>
            <div class="decoration-input__chat"></div>
            <form class="chat-box-tray" method="post" onsubmit="createMessage(this); return false">
                <label for="message_input-${chat}"></label>
                <input id="message_input-${chat}" type="text" class="message-input"
                       placeholder="<fmt:message key="chat.message.input"/>"/>
                <button type="submit"><i class="material-icons">send</i></button>
            </form>
        </div>
    </div>
    <div class="empty__page-layout"></div>
</div>

<script src="${pageContext.request.contextPath}/js/chat.js"></script>
<script src="${pageContext.request.contextPath}/js/header.js"></script>
</body>
</html>
