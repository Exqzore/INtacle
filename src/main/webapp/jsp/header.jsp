<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle scope="session" basename="properties.language"/>

<header class="top-line">
    <c:choose>
    <c:when test="${user != null}">
    <a href="${pageContext.request.contextPath}/main?command=show_profile&login=${user.login}" class="logo">
        </c:when>
        <c:otherwise>
        <a href="${pageContext.request.contextPath}/main?command=to_login_page" class="logo">
            </c:otherwise>
            </c:choose>
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
                           class="search-wrapper__input" autocomplete="off" id="search-input"
                           onkeypress="return search(event, '${pageContext.request.contextPath}')"
                           placeholder="<fmt:message key="header.search"/>">
                </c:when>
                <c:otherwise><span class="top-line__greeting"><fmt:message key="header.greeting"/></span></c:otherwise>
            </c:choose>
        </div>
        <div class="top-line__decoration"></div>
        <div class="top-line__profile-container">
        <span id="profile-wrapper" class="profile-wrapper profile-wrapper__checked" onmouseup="displayProfileMenu()">
            <c:choose>
                <c:when test="${user != null}">
                    <span class="profile-name profile-wrapper__checked">${user.login}</span>
                    <img class="profile-image profile-wrapper__checked" width="48" height="48"
                         src="${pageContext.request.contextPath}/main?command=take_file&file_name=${user.avatarImagePath}"
                         alt="User avatar"/>
                </c:when>
                <c:otherwise>
                    <span class="profile-name profile-wrapper__checked"><fmt:message key="header.menu"/></span>
                </c:otherwise>
            </c:choose>
            <span class="profile-arrow profile-wrapper__checked">
                <i class="fas fa-chevron-down profile-wrapper__checked"></i>
            </span>
        </span>
            <div id="profile-arrow__menu" class="profile-arrow__selection">
                <ul class="profile-arrow__menu">
                    <li class="profile-arrow__menu-elem">
                        <fmt:message key="header.menu.languages"/>
                        <ul class="profile-arrow__languages-menu">
                            <a class="profile-arrow__menu-elem"
                               onclick="setLocaleRU('${pageContext.request.contextPath}')" href="#">
                                <fmt:message key="header.menu.languages.russian"/>
                            </a>
                            <a class="profile-arrow__menu-elem"
                               onclick="setLocaleEN('${pageContext.request.contextPath}')" href="#">
                                <fmt:message key="header.menu.languages.english"/>
                            </a>
                        </ul>
                    </li>
                    <c:choose>
                        <c:when test="${user != null}">
                            <a class="profile-arrow__menu-elem"
                               href="${pageContext.request.contextPath}/main?command=logout">
                                <fmt:message key="header.menu.logout"/>
                            </a>
                            <c:choose>
                                <c:when test="${user.role.name().equals('ADMIN')}">
                                    <a class="profile-arrow__menu-elem admin-panel"
                                       href="${pageContext.request.contextPath}/main?command=logout">
                                        <fmt:message key="header.menu.admin"/>
                                    </a>
                                </c:when>
                            </c:choose>
                        </c:when>
                        <c:otherwise>
                            <a class="profile-arrow__menu-elem"
                               href="${pageContext.request.contextPath}/main?command=to_login_page">
                                <fmt:message key="header.menu.login"/>
                            </a>
                        </c:otherwise>
                    </c:choose>
                </ul>
            </div>
        </div>
</header>