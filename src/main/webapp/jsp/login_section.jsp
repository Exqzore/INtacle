<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle scope="session" basename="properties.language"/>

<div class="login-section">
    <div class="login-section__text"><fmt:message key="login.form.title"/></div>
    <form action="${pageContext.request.contextPath}/main?command=login" method="post"
          onsubmit="toLogin(this); return false">
        <c:choose>
            <c:when test="${isNotActivated}">
                <div class="error-description"><fmt:message key="login.error.00"/></div>
            </c:when>
            <c:when test="${isInvalidParams}">
                <div class="error-description"><fmt:message key="login.error.02"/></div>
            </c:when>
            <c:when test="${isUserLoginError}">
                <div class="error-description"><fmt:message key="login.error.01"/></div>
            </c:when>
        </c:choose>
        <div class="input_holder">
            <input id="login-input" class="input-data" type="text" name="login" pattern="[a-zA-Z][a-zA-Z0-9._-]{3,20}"
                   readonly onfocus="this.removeAttribute('readonly')" required onkeydown="inputChanged(this)">
            <label class="input_label" for="login-input"><i class="fas fa-user"></i>
                <fmt:message key="login.form.login"/>
            </label>
        </div>
        <div class="input_holder">
            <input id="password-input" class="input-data" type="password" name="password" pattern="[a-zA-Z\d_-]{8,25}"
                   readonly onfocus="this.removeAttribute('readonly')" required onkeydown="inputChanged(this)">
            <label class="input_label" for="password-input"><i class="fas fa-key"></i>
                <fmt:message key="login.form.password"/>
            </label>
        </div>
        <button type="submit" class="form_submit"><fmt:message key="login.submit.login"/></button>
        <div class="signup-link">
            <fmt:message key="login.form.question"/>
            <a href="${pageContext.request.contextPath}/main?command=to_registration_page">
                <fmt:message key="login.form.goRegistration"/>
            </a>
        </div>
    </form>
</div>

