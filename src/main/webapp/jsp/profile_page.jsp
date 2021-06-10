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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile-content_style.css">
    <script src="https://kit.fontawesome.com/f5dea48adc.js" crossorigin="anonymous"></script>
</head>
<c:choose>
<c:when test="${requestedUser != null}">
<body onload="loadPage('${locale}', '${requestedUser.login}')"></c:when>
<c:otherwise>
<body onload="loadPage('${locale}')"></c:otherwise>
</c:choose>
<%@ include file="header.jsp" %>
<div class="page-layout">
    <div class="empty__page-layout"></div>
    <%@ include file="left_section.jsp" %>
    <div class="right__page-layout">
        <div class="profile__content">
            <div class="profile__all-info">
                <div class="profile__main-image">
                    <img src="${pageContext.request.contextPath}/image/avatar/${requestedUser.avatarPath}"
                         class="main-image" alt="avatar" width="200" height="200"/>
                </div>
                <div class="profile__main-info" action="#" method="post">
                    <div>
                        <input id="profile__login" class="profile__login" type="text" value="${requestedUser.login}"
                               name="login" readonly>
                    </div>
                    <div class="profile__decoration"></div>
                    <c:choose>
                        <c:when test="${requestedUser.name != null || requestedUser.surname != null}">
                            <div class="profile__name-surname">
                                <c:choose>
                                    <c:when test="${requestedUser.name != null}">
                                        <div class="profile__input-name-surname">
                                            <label class="profile__input-label" for="profile__name">
                                                <fmt:message key="profile.name"/>
                                            </label>
                                            <input id="profile__name" class="profile__name" type="text"
                                                   value="${requestedUser.name}" name="name" readonly>
                                        </div>
                                    </c:when>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${requestedUser.surname != null}">
                                        <div class="profile__input-name-surname">
                                            <label class="profile__input-label" for="profile__surname">
                                                <fmt:message key="profile.surname"/>
                                            </label>
                                            <input id="profile__surname" class="profile__name" type="text"
                                                   value="${requestedUser.surname}" name="surname" readonly>
                                        </div>
                                    </c:when>
                                </c:choose>
                            </div>
                            <div class="profile__decoration"></div>
                        </c:when>
                    </c:choose>
                    <div class="profile__email">
                        <label class="profile__input-label" for="profile__email">
                            <fmt:message key="profile.email"/>
                        </label>
                        <input id="profile__email" class="profile__email-input" type="text"
                               value="${requestedUser.email}"
                               name="email" readonly>
                    </div>
                    <div class="profile__decoration bottom__decoration"></div>
                    <c:choose>
                        <c:when test="${canEdit}">
                            <div class="profile__actions">
                                <button type="submit" class="profile__action-btn">
                                    <fmt:message key="profile.edit"/>
                                </button>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="profile__actions">
                                <a href="${pageContext.request.contextPath}/main?command=go_chat&id=${requestedUser.id}"
                                   class="profile__action-btn">
                                    <fmt:message key="profile.writeMessage"/>
                                </a>
                                <c:choose>
                                    <c:when test="${isSubscribe}">
                                        <a onclick="unsubscribe(this)" href="#" class="profile__action-btn">
                                            <fmt:message key="profile.unsubscribe"/>
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <a onclick="subscribe(this)" href="#" class="profile__action-btn">
                                            <fmt:message key="profile.subscribe"/>
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </c:otherwise>
                    </c:choose>
                    <div class="profile__decoration full-decoration"></div>
                    <div class="profile__subscribers-subscriptions">
                        <a class="profile__subscribers"
                           href="${pageContext.request.contextPath}/main?command=show_subscriptions&login=${requestedUser.login}">
                            <label id="subscriptions-number" class="subscribers-number">
                                ${requestedUser.subscriptionsCount}
                            </label>
                            <label class="subscribers-text"><fmt:message key="profile.subscriptions"/></label>
                        </a>
                        <a class="profile__subscribers"
                           href="${pageContext.request.contextPath}/main?command=show_subscribers&login=${requestedUser.login}">
                            <label id="subscribers-number" class="subscribers-number">
                                ${requestedUser.subscribersCount}
                            </label>
                            <label class="subscribers-text"><fmt:message key="profile.subscribers"/></label>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="empty__page-layout"></div>
</div>

<script src="${pageContext.request.contextPath}/js/header.js"></script>
</body>
</html>
