<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<header class="top-line">
    <a href="#" class="logo">
        <span class="logo__img-flex">
            <span class="logo__img-wrapper">
                <img src="${pageContext.request.contextPath}/image/icon/logo-img.svg" width="40" height="40"
                     alt="INtacle" class="logo__images">
            </span>
        </span>
        <span class="logo__text"><b>INtacle</b></span>
    </a>
    <div class="top-line__decoration"></div>
    <div class="top-line__search">
        <c:choose>
            <c:when test="${user != null}">
                <input type="text" onmousedown="cancelBubble = true;" ontouchstart="event.cancelBubble = true;"
                       class="search-wrapper__input" autocomplete="off" placeholder="Search">
            </c:when>
            <c:otherwise><span class="top-line__greeting">Welcome!</span></c:otherwise>
        </c:choose>
    </div>
    <div class="top-line__decoration"></div>
    <div class="top-line__profile-container">
        <span id="profile-wrapper" class="profile-wrapper profile-wrapper__checked" onmouseup="displayProfileMenu()">
            <c:choose>
                <c:when test="${user != null}">
                    <span class="profile-name profile-wrapper__checked">${user.login}</span>
                    <img class="profile-image profile-wrapper__checked" width="48" height="48"
                         src="${pageContext.request.contextPath}/image/avatar/${user.avatarPath}" alt="User Login"/>
                </c:when>
                <c:otherwise><span class="profile-name profile-wrapper__checked">Menu</span></c:otherwise>
            </c:choose>
            <span class="profile-arrow profile-wrapper__checked">
                <i class="fas fa-chevron-down profile-wrapper__checked"></i>
            </span>
        </span>
        <div id="profile-arrow__menu" class="profile-arrow__selection">
            <ul class="profile-arrow__menu">
                <li class="profile-arrow__menu-elem">
                    Languages
                    <ul class="profile-arrow__languages-menu">
                        <a class="profile-arrow__menu-elem" href="#">Russian</a>
                        <a class="profile-arrow__menu-elem" href="#">English</a>
                    </ul>
                </li>
                <c:choose>
                    <c:when test="${user != null}">
                        <a class="profile-arrow__menu-elem"
                           href="${pageContext.request.contextPath}/main?command=logout">
                            Log Out
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a class="profile-arrow__menu-elem"
                           href="${pageContext.request.contextPath}/main?command=to_login_page">
                            Log in
                        </a>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</header>