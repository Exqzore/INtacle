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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/entry-page_style.css">
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

        <div class="profile__all-entries">
            <c:choose>
                <c:when test="${entries.size() == 0}">
                    <div class="if-is-empty-text"><fmt:message key="news.empty"/></div>
                </c:when>
            </c:choose>
            <c:forEach items="${entries}" var="entry">
                <div class="profile__entry">
                    <div class="profile__entry-author">
                        <a class="entry__author-login"
                           href="${pageContext.request.contextPath}/main?command=show_profile&login=${entry.author.login}">
                                ${entry.author.login}
                        </a>
                        <div class="entry__author-text"><fmt:message key="profile.entry.text"/></div>
                        <c:choose>
                            <c:when test="${canEdit}">
                                <a href="${pageContext.request.contextPath}/main?command=remove_entry&entry=${entry.id}"
                                   class="profile__entry-delete d">
                                    X
                                </a>
                            </c:when>
                        </c:choose>
                    </div>
                    <div class="profile__entry-title">
                        <a class="entry__title"
                           href="${pageContext.request.contextPath}/main?command=show_entry&entry=${entry.id}">
                                ${entry.title}
                        </a>
                        <div class="entry__creation-date">
                            <fmt:formatDate value="${entry.updateDate}" type="both"/>
                        </div>
                    </div>
                    <c:choose>
                        <c:when test="${entry.previewImagePath != null}">
                            <div class="entry__preview-image-container">
                                <img class="entry__preview-image" alt="preview image" decoding="async"
                                     src="${pageContext.request.contextPath}/main?command=take_file&file_name=${entry.previewImagePath}"/>
                            </div>
                        </c:when>
                    </c:choose>
                    <div class="profile__entry-summary">${entry.summary}</div>
                    <div class="profile__entry-action">
                        <a class="entry__like-button"
                                <c:choose>
                                    <c:when test="${entry.liked}">
                                        href="#" onclick="unlikeEntry('${entry.id}')">
                                        <i id="like_entry-${entry.id}" class="fa fa-heart liked"></i>
                                    </c:when>
                                    <c:otherwise>
                                        href="#" onclick="likeEntry('${entry.id}')">
                                        <i id="like_entry-${entry.id}" class="fa fa-heart-o"></i>
                                    </c:otherwise>
                                </c:choose>
                        <div id="count_likes-${entry.id}" class="profile__entry-like-count">${entry.likesCount}</div>
                        </a>
                        <c:choose>
                            <c:when test="${canEdit}">
                                <a class="entry__edit-button"
                                   href="${pageContext.request.contextPath}/main?command=go_edit_entry&entry=${entry.id}">
                                    <i class="fa fa-pencil-square-o"></i>
                                </a>
                            </c:when>
                        </c:choose>
                    </div>
                </div>
            </c:forEach>
        </div>

    </div>
    <div class="empty__page-layout"></div>
</div>

<script src="${pageContext.request.contextPath}/js/header.js"></script>
<script src="${pageContext.request.contextPath}/js/entry.js"></script>
</body>
</html>
