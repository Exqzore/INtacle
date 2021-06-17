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

            <div class="entry__comments-container">
                <form class="chat-box-tray" method="post" onsubmit="createComment(this); return false">
                    <label for="message_input-${chat}"></label>
                    <input id="message_input-${chat}" type="text" class="message-input"
                           placeholder="Type your comment here..."/>
                    <button type="submit"><i class="material-icons">send</i></button>
                </form>

                <c:forEach items="${commnets}" var="comment">
                    <div class="comment__author-container">
						<span class="comment__author-avatar-container">
							<img src="${pageContext.request.contextPath}/image/avatar/${comment.authorAvatarPath}"
                                 alt="User avatar" class="comment__author-avatar" width="50" height="50"/>
						</span>
                        <div class="comment__content-container">
                            <a href="#" class="comment__author-login">${comment.authorLogin}</a>
                            <div class="comment__text">${comment.content}</div>
                            <div class="comment__bottom-container">
                                <div class="comment__creation-date">
                                        ${comment.getCreationDateInFormat("dd MMM yyyy HH:mm:ss")}
                                </div>
                                <a class="comment__like-button"
                                        <c:choose>
                                    <c:when test="${comment.liked}">
                                        href="#" >
                                        <i class="fa fa-heart"></i>
                                    </c:when>
                                    <c:otherwise>
                                        href="#" >
                                        <i class="fa fa-heart-o"></i>
                                    </c:otherwise>
                                </c:choose>
                                    <div class="comment__like-count">${comment.likesCount}</div>
                                </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>

        </div>

    </div>
    <div class="empty__page-layout"></div>
</div>

<script src="${pageContext.request.contextPath}/js/header.js"></script>
</body>
</html>
