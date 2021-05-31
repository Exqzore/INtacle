<%@ page contentType="text/html;charset=UTF-8" %>

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
<body>
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
                                            <label class="profile__input-label" for="profile__name">name:</label>
                                            <input id="profile__name" class="profile__name" type="text"
                                                   value="${requestedUser.name}" name="name" readonly>
                                        </div>
                                    </c:when>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${requestedUser.surname != null}">
                                        <div class="profile__input-name-surname">
                                            <label class="profile__input-label" for="profile__surname">surname:</label>
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
                        <label class="profile__input-label" for="profile__email">email:</label>
                        <input id="profile__email" class="profile__email-input" type="text" value="${requestedUser.email}"
                               name="email" readonly>
                    </div>
                    <div class="profile__decoration bottom__decoration"></div>
                    <c:choose>
                        <c:when test="${canEdit}">
                            <button type="submit" class="profile__action-btn">Edit</button>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${isSubscribe}">
                                    <a href="${pageContext.request.contextPath}/main?command=unsubscribe&login=${requestedUser.login}"
                                       class="profile__action-btn">
                                        unsubscribe
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a href="${pageContext.request.contextPath}/main?command=subscribe&login=${requestedUser.login}"
                                       class="profile__action-btn">
                                        subscribe
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>
                    <div class="profile__decoration full-decoration"></div>
                    <div class="profile__subscribers-subscriptions">
                        <a class="profile__subscribers"
                           href="${pageContext.request.contextPath}/main?command=show_subscriptions&login=${requestedUser.login}">
                            <label class="subscribers-number">${requestedUser.subscriptionsCount}</label>
                            <label class="subscribers-text">Subscriptions</label>
                        </a>
                        <a class="profile__subscribers"
                           href="${pageContext.request.contextPath}/main?command=show_subscribers&login=${requestedUser.login}">
                            <label class="subscribers-number">${requestedUser.subscribersCount}</label>
                            <label class="subscribers-text">Subscribers</label>
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
