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
    <script src="https://kit.fontawesome.com/f5dea48adc.js" crossorigin="anonymous"></script>
</head>

<body>
<%@ include file="header.jsp" %>
<div class="page-layout">
    <div class="empty__page-layout"></div>
    <%@ include file="left_section.jsp" %>
    <div class="right__page-layout">
        <div class="empty-top"></div>
        <div class="profile__entry-content">
            <div class="profile__entry">
                <div class="profile__entry-author">
                    <a class="entry__author-login" href="#">${entry.authorLogin}</a>
                </div>
                <div class="profile__entry-title">
                    <a class="entry__title">${entry.authorLogin}</a>
                    <div class="entry__creation-date">
                        ${entry.getUpdateDateInFormat("dd MMM yyyy HH:mm:ss")}
                    </div>
                </div>
                <div class="entry__preview-image-container">
                    <img class="entry__preview-image" alt="preview image" decoding="async"
                         src="${pageContext.request.contextPath}/image/avatar/${entry.previewImage}"/>
                </div>
                <div class="profile__entry-summary">${entry.content}</div>
                <div class="profile__entry-action">
                    <a class="entry__like-button"
                            <c:choose>
                                <c:when test="${entry.liked}">
                                    href="#" >
                                    <i class="fa fa-heart"></i>
                                </c:when>
                                <c:otherwise>
                                    href="#" >
                                    <i class="fa fa-heart-o"></i>
                                </c:otherwise>
                            </c:choose>
                    <div class="profile__entry-like-count">${entry.likesCount}</div>
                    </a>
                    <c:choose>
                        <c:when test="${canEdit}">
                            <a class="entry__edit-button" href="#">
                                <i class="fa fa-pencil-square-o"></i>
                            </a>
                        </c:when>
                    </c:choose>
                </div>
            </div>
        </div>

    </div>
    <div class="empty__page-layout"></div>
</div>

<script src="${pageContext.request.contextPath}/js/header.js"></script>
</body>
</html>
