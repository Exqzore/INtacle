<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="login-section">
    <div class="login-section__text">INtacle entrance</div>
    <form action="${pageContext.request.contextPath}/main?command=login" method="post"
          onsubmit="toLogin(this); return false">
        <c:choose>
            <c:when test="${isNotActivated}">
                <div class="error-description">This account is not activated</div>
            </c:when>
            <c:when test="${isInvalidParams}">
                <div class="error-description">Incorrectly entered data</div>
            </c:when>
            <c:when test="${isUserLoginError}">
                <div class="error-description">Failed to login user</div>
            </c:when>
        </c:choose>
        <div class="input_holder">
            <input id="login-input" class="input-data" type="text" name="login" pattern="[a-zA-Z][a-zA-Z0-9._-]{3,20}"
                   readonly onfocus="this.removeAttribute('readonly')" required onkeydown="inputChanged(this)">
            <label class="input_label" for="login-input"><i class="fas fa-user"></i>login</label>
        </div>
        <div class="input_holder">
            <input id="password-input" class="input-data" type="password" name="password" pattern="[a-zA-Z\d_-]{8,25}"
                   readonly onfocus="this.removeAttribute('readonly')" required onkeydown="inputChanged(this)">
            <label class="input_label" for="password-input"><i class="fas fa-key"></i>password</label>
        </div>
        <button type="submit" class="form_submit">Login</button>
        <div class="signup-link">
            Not a member?
            <a href="${pageContext.request.contextPath}/main?command=to_registration_page">Signup now</a>
        </div>
    </form>
</div>

