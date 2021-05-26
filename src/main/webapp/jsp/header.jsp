<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>

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

        <span class="top-line__greeting">Welcome!</span>

        <%--            <input type="text" onmousedown="event.cancelBubble = true;" ontouchstart="event.cancelBubble = true;"--%>
        <%--                   class="search-wrapper__input" autocomplete="off" placeholder="Search">--%>
    </div>
    <div class="top-line__decoration"></div>
    <div class="top-line__profile-container">

        <%--                        <span id="profile-wrapper" class="profile-wrapper profile-wrapper__checked"--%>
        <%--                              onmouseup="displayProfileMenu()">--%>
        <%--                            <span class="profile-name profile-wrapper__checked">Exqzore</span>--%>
        <%--                            <img class="profile-image profile-wrapper__checked" width="48" height="48"--%>
        <%--                                 src="${pageContext.request.contextPath}/image/icon/default_avatar-mini.png"--%>
        <%--                                 alt="User Login"/>--%>
        <%--                            <span class="profile-arrow profile-wrapper__checked">--%>
        <%--                                <i class="fas fa-chevron-down profile-wrapper__checked"></i>--%>
        <%--                            </span>--%>
        <%--                        </span>--%>
        <%--                        <div id="profile-arrow__menu" class="profile-arrow__selection">--%>
        <%--                            <ul class="profile-arrow__menu">--%>
        <%--                                <li class="profile-arrow__menu-elem">--%>
        <%--                                    <a href="#">Languages</a>--%>
        <%--                                    <ul class="profile-arrow__languages-menu">--%>
        <%--                                        <li class="profile-arrow__menu-elem"><a href="#">Russian</a></li>--%>
        <%--                                        <li class="profile-arrow__menu-elem"><a href="#">English</a></li>--%>
        <%--                                    </ul>--%>
        <%--                                </li>--%>
        <%--                                <li class="profile-arrow__menu-elem"><a href="#">Log Out</a></li>--%>
        <%--                            </ul>--%>
        <%--                        </div>--%>

        <span id="profile-wrapper" class="profile-wrapper profile-wrapper__checked" onmouseup="displayProfileMenu()">
            <span class="profile-name profile-wrapper__checked">Menu</span>
            <span class="profile-arrow profile-wrapper__checked">
                <i class="fas fa-chevron-down profile-wrapper__checked"></i>
            </span>
        </span>
        <div id="profile-arrow__menu" class="profile-arrow__selection">
            <ul class="profile-arrow__menu">
                <li class="profile-arrow__menu-elem">
                    <a href="#">Languages</a>
                    <ul class="profile-arrow__languages-menu">
                        <li class="profile-arrow__menu-elem"><a href="#">Russian</a></li>
                        <li class="profile-arrow__menu-elem"><a href="#">English</a></li>
                    </ul>
                </li>
                <li class="profile-arrow__menu-elem" onclick="goLoginPage(this)">
                    <a href="${pageContext.request.contextPath}/main?command=to_login_page">Log in</a>
                </li>
            </ul>
        </div>

    </div>
</header>