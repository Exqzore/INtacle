<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle scope="session" basename="properties.language"/>

<div class="register-section">
    <div class="login-section__text"><fmt:message key="registration.form.title"/></div>
    <form action="${pageContext.request.contextPath}/main" method="post"
          onsubmit="toRegister(this); return false">
        <c:choose>
            <c:when test="${isLoginBusy}">
                <div class="error-description"><fmt:message key="registration.error.01"/></div>
            </c:when>
            <c:when test="${isUserCreationError}">
                <div class="error-description"><fmt:message key="registration.error.00"/></div>
            </c:when>
            <c:when test="${isInvalidParams}">
                <div class="error-description"><fmt:message key="registration.error.02"/></div>
            </c:when>
        </c:choose>
        <input name="command" value="register" hidden>
        <div class="div-container">
            <div class="input-container">
                <div class="input_holder">
                    <input id="login-input" class="input-data" type="text" name="login" readonly
                           pattern="[a-zA-Z][a-zA-Z0-9._-]{3,20}" onfocus="this.removeAttribute('readonly')" required
                           onkeydown="inputChanged(this)">
                    <label class="input_label" for="login-input">
                        <i class="fas fa-user"></i>
                        <fmt:message key="registration.form.login"/>
                    </label>
                </div>
                <div class="input_holder">
                    <input id="password-input" class="input-data" type="password" name="password"
                           pattern="[a-zA-Z\d_-]{8,25}" readonly onfocus="this.removeAttribute('readonly')" required
                           onkeydown="inputChanged(this)">
                    <label class="input_label" for="password-input">
                        <i class="fas fa-key"></i>
                        <fmt:message key="registration.form.password"/>
                    </label>
                </div>
                <div class="input_holder">
                    <input id="repeat_password-input" class="input-data" type="password" name="repeat_password"
                           pattern="[a-zA-Z\d_-]{8,25}" readonly onfocus="this.removeAttribute('readonly')" required
                           onkeydown="inputChanged(this)">
                    <label class="input_label lwi" for="repeat_password-input">
                        <fmt:message key="registration.form.repeatPassword"/>
                    </label>
                </div>
            </div>
            <div class="register-section__decoration"></div>
            <div class="input-container">
                <div class="input_holder">
                    <input id="email-input" class="input-data" type="text" name="email" readonly
                           pattern="[A-Za-z0-9-]+(\.[A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,100})"
                           onfocus="this.removeAttribute('readonly')" required onkeydown="inputChanged(this)">
                    <label class="input_label lwi" for="email-input">
                        <fmt:message key="registration.form.email"/>
                    </label>
                </div>
                <div class="input_holder">
                    <input id="name-input" class="input-data" type="text" name="name" readonly
                           pattern="[a-zA-Z][a-zA-Z0-9_]{2,20}" onfocus="this.removeAttribute('readonly')">
                    <label class="input_label lwi" for="name-input">
                        <fmt:message key="registration.form.name"/>
                    </label>
                </div>
                <div class="input_holder">
                    <input id="surname-input" class="input-data" type="text" name="surname" readonly
                           pattern="[a-zA-Z][a-zA-Z0-9_]{2,20}" onfocus="this.removeAttribute('readonly')">
                    <label class="input_label lwi" for="surname-input">
                        <fmt:message key="registration.form.surname"/>
                    </label>
                </div>
            </div>
        </div>
        <button type="submit" class="form_submit"><fmt:message key="registration.submit.register"/></button>
        <div class="signup-link">
            <fmt:message key="registration.form.question"/>
            <a href="${pageContext.request.contextPath}/main?command=to_login_page">
                <fmt:message key="registration.form.goLogin"/>
            </a>
        </div>
    </form>
</div>
