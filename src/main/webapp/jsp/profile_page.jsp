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

<body>
<%@ include file="header.jsp" %>
<div class="page-layout">
    <div class="empty__page-layout"></div>
    <%@ include file="left_section.jsp" %>
    <div class="right__page-layout">
        <div class="empty-top"></div>
        <div class="profile__content">
            <div class="profile__all-info">
                <div class="profile__main-image">
                    <c:choose>
                        <c:when test="${canEdit}">
                            <img id="main-image-profile"
                                 src="${pageContext.request.contextPath}/main?command=take_file&file_name=${requestedUser.avatarImagePath}"
                                 class="main-image" alt="avatar" width="200" height="200" onclick="appModalWindow()"/>
                        </c:when>
                        <c:otherwise>
                            <img id="main-image-profile"
                                 src="${pageContext.request.contextPath}/main?command=take_file&file_name=${requestedUser.avatarImagePath}"
                                 class="main-image" alt="avatar" width="200" height="200"/>
                        </c:otherwise>
                    </c:choose>
                </div>
                <form method="post" action="${pageContext.request.contextPath}/main?command=edit_profile"
                      class="profile__main-info">
                    <div id="profile__login" class="profile__login">${requestedUser.login}</div>
                    <div class="profile__decoration"></div>
                    <c:choose>
                        <c:when test="${requestedUser.name != null || requestedUser.surname != null}">
                            <div id="profile__name-surname" class="profile__name-surname">
                        </c:when>
                        <c:otherwise>
                            <div id="profile__name-surname" class="profile__name-surname" style="display: none">
                        </c:otherwise>
                    </c:choose>
                        <c:choose>
                            <c:when test="${requestedUser.name != null}">
                                <div id="profile__input-name" class="profile__input-name-surname">
                            </c:when>
                            <c:otherwise>
                                <div id="profile__input-name" class="profile__input-name-surname" style="display: none">
                            </c:otherwise>
                        </c:choose>
                            <label class="profile__input-label" for="profile__name">
                                <fmt:message key="profile.name"/>
                            </label>
                            <input id="profile__name" class="profile__name" type="text" name="name" readonly
                                    pattern="[a-zA-Z][a-zA-Z0-9_]{2,20}" value="${requestedUser.name}">
                        </div>
                        <c:choose>
                            <c:when test="${requestedUser.surname != null}">
                                <div id="profile__input-surname" class="profile__input-name-surname">
                            </c:when>
                            <c:otherwise>
                                <div id="profile__input-surname" class="profile__input-name-surname"
                                     style="display: none">
                            </c:otherwise>
                        </c:choose>
                            <label class="profile__input-label" for="profile__surname">
                                <fmt:message key="profile.surname"/>
                            </label>
                            <input id="profile__surname" class="profile__name" type="text" name="surname" readonly
                                   pattern="[a-zA-Z][a-zA-Z0-9_]{2,20}" value="${requestedUser.surname}">
                        </div>
                    </div>
                    <c:choose>
                        <c:when test="${requestedUser.name != null || requestedUser.surname != null}">
                            <div id="profile__decoration" class="profile__decoration"></div>
                        </c:when>
                        <c:otherwise>
                            <div id="profile__decoration" class="profile__decoration" style="display: none"></div>
                        </c:otherwise>
                    </c:choose>
                    <div class="profile__email">
                        <label class="profile__input-label" for="profile__email">
                            <fmt:message key="profile.email"/>
                        </label>
                        <input id="profile__email" class="profile__email-input" type="text" readonly
                                value="${requestedUser.email}">
                    </div>
                    <div class="profile__decoration bottom__decoration"></div>
                    <c:choose>
                        <c:when test="${canEdit}">
                            <div class="profile__actions">
                                <div class="profile__action-btn" onclick="changeProfile(this)" id="profile__edit-btn">
                                    <fmt:message key="profile.edit"/>
                                </div>
                                <button class="profile__action-btn" type="submit" id="profile__save-btn"
                                        style="display: none">
                                    <fmt:message key="profile.save"/>
                                </button>
                                <div class="profile__action-btn" onclick="cancelChangeProfile(this)"
                                     style="height: 20px;padding-top: 3px; display: none" id="profile__cancel-btn">
                                    <fmt:message key="profile.cancel"/>
                                </div>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="profile__actions">
                                <a href="${pageContext.request.contextPath}/main?command=create_chat&user_id=${requestedUser.id}"
                                        class="profile__action-btn">
                                    <fmt:message key="profile.writeMessage"/>
                                </a>
                                <c:choose>
                                    <c:when test="${isSubscribe}">
                                        <a href="${pageContext.request.contextPath}/main?command=unsubscribe&login=${requestedUser.login}"
                                           class="profile__action-btn">
                                            <fmt:message key="profile.unsubscribe"/>
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <a href="${pageContext.request.contextPath}/main?command=subscribe&login=${requestedUser.login}"
                                           class="profile__action-btn">
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
                </form>
            </div>
        </div>

        <c:choose>
            <c:when test="${canEdit}">
                <div class="profile__create-entry">
                    <div class="profile__entries-title">
                        <fmt:message key="profile.entry.create.title"/>
                    </div>
                    <div class="accordion__entries">
                        <button type="button" class="accordion__button" onclick="activatePanel(this)">
                            <fmt:message key="profile.entry.creation"/>
                        </button>
                        <div class="accordion__content">
                            <form method="post" action="${pageContext.request.contextPath}/upload" hidden
                                  enctype="multipart/form-data" class="load-form__preview" id="preview_image-form" >
                                <input type="file" name="uploadImage" id="preview_image_select" accept="image/jpeg,image/png">
                            </form>
                            <form method="post" action="${pageContext.request.contextPath}/main" class="entry__content-form">
                                <input name="command" value="create_entry" hidden>
                                <div class="entry__content-top">
                                    <div class="entry__content-image">
                                        <img id="preview_image"
                                             src="${pageContext.request.contextPath}/main?command=take_file&file_name=empty_image.png"
                                             class="entry__preview-image-create" alt="preview" onclick="loadPreviewImage()"/>
                                        <input id="preview_image_path" name="imagePath" value="empty_image.png" hidden>
                                    </div>
                                    <div class="entry__subcontent-form">
                                        <input type="text" placeholder="<fmt:message key="profile.entry.title"/>"
                                               class="title-input textarea-panel" maxlength="64" readonly
                                               onfocus="this.removeAttribute('readonly')" name="title">
                                        <textarea id="summary-text" placeholder="<fmt:message key="profile.entry.summary"/>"
                                                  class="summary-textarea textarea-panel" maxlength="256" name="summary">
                                </textarea>
                                    </div>
                                </div>
                                <textarea id="content-text" placeholder="<fmt:message key="profile.entry.content"/>"
                                          class="content-textarea textarea-panel" maxlength="4096" name="content">
                                </textarea>
                                <button type="submit" class="entry__action-btn">
                                    <fmt:message key="profile.entry.create.btn"/>
                                </button>
                            </form>
                        </div>
                    </div>
                </div>
            </c:when>
        </c:choose>

        <div class="profile__all-entries">
            <c:forEach items="${entries}" var="entry">
                <div class="profile__entry">
                    <div class="profile__entry-author">
                        <a class="entry__author-login">${entry.author.login}</a>
                        <div class="entry__author-text"><fmt:message key="profile.entry.text"/></div>
                        <c:choose>
                            <c:when test="${canEditEntries}">
                                <a href="${pageContext.request.contextPath}/main?command=remove_entry&entry=${entry.id}"
                                    class="profile__entry-delete">
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
                            <c:when test="${canEditEntries}">
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

    <div class="modal-background">
        <div class="modal-window">
            <div class="modal-window__title">
                <div class="modal-window__title-text"><fmt:message key="profile.modal.title"/></div>
                <button id="close-modal_btn" class="modal-window__btn-close" onclick="closeModalWindow()">X</button>
            </div>
            <form id="avatar_image-form" method="post" class="modal-window__content" action="${pageContext.request.contextPath}/upload"
                  enctype="multipart/form-data">
                <div class="modal-window__text"><fmt:message key="profile.modal.text"/></div>
                <label for="avatar_image-select" class="modal-window__input">
                    <fmt:message key="profile.modal.button"/>
                </label>
                <input id="avatar_image-select" type="file" name="uploadAvatarImage" accept="image/jpeg,image/png"
                       style="display: none;" onchange="loadAvatarImage()">
            </form>
        </div>
    </div>

    <script src="${pageContext.request.contextPath}/js/header.js"></script>
    <script src="${pageContext.request.contextPath}/js/profile.js"></script>
</body>
</html>
