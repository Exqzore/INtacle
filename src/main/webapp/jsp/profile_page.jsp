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
                    <img id="main-image-profile" src="${pageContext.request.contextPath}/main?command=take_file&file_name=${requestedUser.avatarImagePath}"
                         class="main-image" alt="avatar" width="200" height="200" onclick="appModalWindow()"/>
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

        <div class="profile__create-entry">
            <div class="profile__entries-title">
                Add new entry
            </div>
            <div class="accordion__entries">
                <button type="button" class="accordion__button" onclick="activatePanel(this)">create a new entry</button>
                <div class="accordion__content">

                    <form method="post" action="${pageContext.request.contextPath}/upload" hidden
                          enctype="multipart/form-data" class="load-form__preview" id="upload_form" accept="image/jpeg,image/png">
                        <input type="file" name="uploadFile" id="file_select">
                    </form>

                    <form class="entry__content-form">
                        <div class="entry__content-top">
                            <div class="entry__content-image">
                                <img src="images/1.jpg" class="entry__preview-image-create" alt="avatar" />
                            </div>
                            <div class="entry__subcontent-form">
                                <input type="text" placeholder="title" class="title-input textarea-panel" maxlength="64" readonly
                                       onfocus="this.removeAttribute('readonly')">
                                <textarea id="summary-text" placeholder="summary" class="summary-textarea textarea-panel"
                                          maxlength="256">
										</textarea>
                            </div>
                        </div>
                        <textarea id="content-text" placeholder="content" class="content-textarea textarea-panel"
                                  maxlength="4096">
								</textarea>
                        <button type="submit" class="entry__action-btn">Create</button>
                    </form>

                </div>
            </div>
        </div>

        <div class="profile__all-entries">
            <c:forEach items="${entries}" var="entry">
                <div class="profile__entry">
                    <div class="profile__entry-author">
                        <a class="entry__author-login">${entry.authorLogin}</a>
                        <div class="entry__author-text"><fmt:message key="profile.entry.text"/></div>
                    </div>
                    <div class="profile__entry-title">
                        <a class="entry__title">${entry.title}</a>
                        <div class="entry__creation-date">
                            ${entry.getUpdateDateInFormat("dd MMM yyyy HH:mm:ss")}
                        </div>
                    </div>
                    <div class="entry__preview-image-container">
                        <img class="entry__preview-image" alt="preview image" decoding="async"
                             src="${pageContext.request.contextPath}/image/avatar/${entry.previewImagePath}"/>
                    </div>
                    <div class="profile__entry-summary">${entry.summary}</div>
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
