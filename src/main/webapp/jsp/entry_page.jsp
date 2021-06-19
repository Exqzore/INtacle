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
        <div class="profile__entry-content">
            <div class="profile__entry">
                <div class="profile__entry-author">
                    <a class="entry__author-login"
                       href="${pageContext.request.contextPath}/main?command=show_profile&login=${entry.author.login}">
                        ${entry.author.login}
                    </a>
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
                    <a class="entry__title">${entry.title}</a>
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
                <div class="profile__entry-summary">${entry.content}</div>
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

            <div class="entry__comments-container">
                <form action="${pageContext.request.contextPath}/main" class="comment-box-tray" method="post"
                      onsubmit="return createComment(this)">
                    <input name="command" value="create_comment" hidden>
                    <input value="${entry.id}" name="entry" hidden>
                    <label for="comment_input"></label>
                    <input id="comment_input" type="text" class="comment-input" name="content"
                           placeholder="<fmt:message key="entry.comment.input"/>"/>
                    <button type="submit"><i class="material-icons">send</i></button>
                </form>

                <c:forEach items="${comments}" var="comment">
                    <div class="comment__author-container">
                        <c:choose>
                            <c:when test="${canEdit}">
                                <a href="${pageContext.request.contextPath}/main?command=remove_comment&comment=${comment.id}&entry=${entry.id}"
                                    class="comment-delete">
                                    X
                                </a>
                            </c:when>
                        </c:choose>
						<span class="comment__author-avatar-container">
							<img src="${pageContext.request.contextPath}/main?command=take_file&file_name=${comment.author.avatarImagePath}"
                                 alt="User avatar" class="comment__author-avatar" width="50" height="50"/>
						</span>
                        <div class="comment__content-container">
                            <a href="${pageContext.request.contextPath}/main?command=show_profile&login=${comment.author.login}"
                               class="comment__author-login">
                                    ${comment.author.login}
                            </a>
                            <div class="comment__text">${comment.content}</div>
                            <div class="comment__bottom-container">
                                <div class="comment__creation-date">
                                    <fmt:formatDate value="${comment.creationDate}" type="both"/>
                                </div>
                                <a class="comment__like-button"
                                    <c:choose>
                                        <c:when test="${comment.liked}">
                                            href="#" onclick="unlikeComment('${comment.id}')">
                                            <i id="like_comment-${comment.id}" class="fa fa-heart liked"></i>
                                        </c:when>
                                        <c:otherwise>
                                            href="#" onclick="likeComment('${comment.id}')">
                                            <i id="like_comment-${comment.id}" class="fa fa-heart-o"></i>
                                    </c:otherwise>
                                </c:choose>
                                <div id="count_comment_likes-${comment.id}" class="comment__like-count">
                                        ${comment.likesCount}
                                </div>
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
<script src="${pageContext.request.contextPath}/js/entry.js"></script>
</body>
</html>
