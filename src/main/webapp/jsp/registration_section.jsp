<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="register-section">
    <div class="login-section__text">INtacle account creation</div>
    <form action="${pageContext.request.contextPath}/main?command=register" method="post"
          onsubmit="toRegister(this); return false">
        <c:choose>
            <c:when test="${isLoginBusy}">
                <div class="error-description">This login is already in use</div>
            </c:when>
            <c:when test="${isUserCreationError}">
                <div class="error-description">Failed to create account</div>
            </c:when>
            <c:when test="${isInvalidParams}">
                <div class="error-description">Incorrectly entered data</div>
            </c:when>
        </c:choose>
        <div class="div-container">
            <div class="input-container">
                <div class="input_holder">
                    <input id="login-input" class="input-data" type="text" name="login" readonly
                           pattern="[a-zA-Z][a-zA-Z0-9._-]{3,20}" onfocus="this.removeAttribute('readonly')" required
                           onkeydown="inputChanged(this)">
                    <label class="input_label" for="login-input"><i class="fas fa-user"></i>login *</label>
                </div>
                <div class="input_holder">
                    <input id="password-input" class="input-data" type="password" name="password"
                           pattern="[a-zA-Z\d_-]{8,25}" readonly onfocus="this.removeAttribute('readonly')" required
                           onkeydown="inputChanged(this)">
                    <label class="input_label" for="password-input"><i class="fas fa-key"></i>password *</label>
                </div>
                <div class="input_holder">
                    <input id="repeat_password-input" class="input-data" type="password" name="repeat_password"
                           pattern="[a-zA-Z\d_-]{8,25}" readonly onfocus="this.removeAttribute('readonly')" required
                           onkeydown="inputChanged(this)">
                    <label class="input_label lwi" for="repeat_password-input">repeat password *</label>
                </div>
            </div>
            <div class="register-section__decoration"></div>
            <div class="input-container">
                <div class="input_holder">
                    <input id="email-input" class="input-data" type="text" name="email" readonly
                           pattern="[A-Za-z0-9-]+(\.[A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\.[A-Za-z0-9]+)*(\.[A-Za-z]{2,100})"
                           onfocus="this.removeAttribute('readonly')" required onkeydown="inputChanged(this)">
                    <label class="input_label lwi" for="email-input">email *</label>
                </div>
                <div class="input_holder">
                    <input id="name-input" class="input-data" type="text" name="name" readonly
                           pattern="[a-zA-Z][a-zA-Z0-9_]{2,20}" onfocus="this.removeAttribute('readonly')">
                    <label class="input_label lwi" for="name-input">name</label>
                </div>
                <div class="input_holder">
                    <input id="surname-input" class="input-data" type="text" name="surname" readonly
                           pattern="[a-zA-Z][a-zA-Z0-9_]{2,20}" onfocus="this.removeAttribute('readonly')">
                    <label class="input_label lwi" for="surname-input">surname</label>
                </div>
            </div>
        </div>
        <button type="submit" class="form_submit">Register</button>
        <div class="signup-link">
            Have an account?
            <a href="${pageContext.request.contextPath}/main?command=to_login_page">Login now</a>
        </div>
    </form>
</div>
