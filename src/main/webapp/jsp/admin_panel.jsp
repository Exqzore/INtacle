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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin-panel.css">
    <script src="https://kit.fontawesome.com/f5dea48adc.js" crossorigin="anonymous"></script>
</head>

<body>
<%@ include file="header.jsp" %>
<div class="page-layout">
    <div class="empty__page-layout"></div>
    <%@ include file="left_section.jsp" %>
    <div class="right__page-layout">
        <div class="empty-top"></div>
        <c:choose>
            <c:when test="${users.size() == 0}">
                <div class="if-is-empty-text"><fmt:message key="admin.empty"/></div>
            </c:when>
            <c:otherwise>
                <div class="admin__user-wrapper">
                    <c:forEach items="${users}" var="foundUser">
                        <form action="${pageContext.request.contextPath}/main" method="post" class="admin__user-container">
                            <a href="${pageContext.request.contextPath}/main?command=show_profile&login=${foundUser.login}"
                               class="admin__user-title">
                                    ${foundUser.login}
                            </a>
                            <input value="change_role" name="command" hidden>
                            <input value="${foundUser.id}" name="userId" hidden>
                            <div>
                                <c:choose>
                                    <c:when test="${foundUser.role.name().equals('USER')}">
                                        <div class="admin__radio-btn">
                                            <input type="radio" id="user-role-${foundUser.id}" value="USER"
                                                   name="user_role-${foundUser.id}" checked>
                                            <label for="user-role-${foundUser.id}">User</label>
                                        </div>
                                        <div class="admin__radio-btn">
                                            <input type="radio" id="editor-role-${foundUser.id}" value="EDITOR"
                                                   name="user_role-${foundUser.id}">
                                            <label for="editor-role-${foundUser.id}">User + Editor</label>
                                        </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="admin__radio-btn">
                                            <input type="radio" id="user-role-${foundUser.id}" value="USER"
                                                   name="user_role-${foundUser.id}">
                                            <label for="user-role-${foundUser.id}">User</label>
                                        </div>
                                        <div class="admin__radio-btn">
                                            <input type="radio" id="editor-role-${foundUser.id}" value="EDITOR"
                                                   name="user_role-${foundUser.id}" checked>
                                            <label for="editor-role-${foundUser.id}">User + Editor</label>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                            <button type="submit" class="admin__submit"><fmt:message key="admin.confirm"/></button>
                        </form>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="empty__page-layout"></div>
</div>

<script src="${pageContext.request.contextPath}/js/header.js"></script>
</body>
</html>