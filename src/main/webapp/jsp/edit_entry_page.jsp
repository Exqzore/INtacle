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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/edit-entry_style.css">
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

                <div class="accordion__entries" style="display: block">
                    <div class="accordion__content">
                        <form method="post" action="${pageContext.request.contextPath}/upload" hidden
                              enctype="multipart/form-data" class="load-form__preview" id="preview_image-form" >
                            <input type="file" name="uploadImage" id="preview_image_select" accept="image/jpeg,image/png">
                        </form>
                        <form method="post" action="${pageContext.request.contextPath}/main" class="entry__content-form">
                            <input name="command" value="edit_entry" hidden>
                            <input name="entry" value="${entry.id}" hidden>
                            <div class="entry__content-top">
                                <div class="entry__content-image">
                                    <c:choose>
                                        <c:when test="${entry.previewImagePath != null}">
                                            <img id="preview_image"
                                                 src="${pageContext.request.contextPath}/main?command=take_file&file_name=${entry.previewImagePath}"
                                                 class="entry__preview-image-create" alt="preview" onclick="loadPreviewImage()"/>
                                            <input id="preview_image_path" name="imagePath" value="${entry.previewImagePath}" hidden>
                                        </c:when>
                                        <c:otherwise>
                                            <img id="preview_image"
                                                 src="${pageContext.request.contextPath}/main?command=take_file&file_name=empty_image.png"
                                                 class="entry__preview-image-create" alt="preview" onclick="loadPreviewImage()"/>
                                            <input id="preview_image_path" name="imagePath" value="empty_image.png" hidden>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="entry__subcontent-form">
                                    <input type="text" placeholder="<fmt:message key="profile.entry.title"/>"
                                           class="title-input textarea-panel" maxlength="64"
                                           name="title" value="${entry.title}">
                                    <textarea id="summary-text" placeholder="<fmt:message key="profile.entry.summary"/>"
                                              class="summary-textarea textarea-panel" maxlength="256" name="summary">${entry.summary}</textarea>
                                </div>
                            </div>
                            <textarea id="content-text" placeholder="<fmt:message key="profile.entry.content"/>"
                                      class="content-textarea textarea-panel" maxlength="4096" name="content">${entry.content}</textarea>
                            <button type="submit" class="entry__action-btn">
                                <fmt:message key="profile.entry.save.btn"/>
                            </button>
                        </form>
                    </div>
                </div>

            </div>

        </div>

    </div>
    <div class="empty__page-layout"></div>
</div>

<script src="${pageContext.request.contextPath}/js/header.js"></script>
<script src="${pageContext.request.contextPath}/js/edit_entry.js"></script>
</body>
</html>
