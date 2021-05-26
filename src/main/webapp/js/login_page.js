function toLogin(form) {
    let result = true
    let elemPassword = form.querySelector('input[id$="password-input"]')
    let elemLogin = form.querySelector('input[id$="login-input"]')
    elemPassword.classList.remove('error-input')
    elemLogin.classList.remove('error-input')
    let elemPasswordLabel = form.querySelector('label[for$="password-input"]')
    let elemLoginLabel = form.querySelector('label[for$="login-input"]')
    elemPasswordLabel.classList.remove('error-label')
    elemLoginLabel.classList.remove('error-label')
    if (elemPassword.value === '') {
        elemPassword.classList.add('error-input')
        elemPasswordLabel.classList.add('error-label')
        result = false
    }
    if (elemLogin.value === '') {
        elemLogin.classList.add('error-input')
        elemLoginLabel.classList.add('error-label')
        result = false
    }
    if (result) {
        form.submit()
    }
}

function inputChanged(elem) {
    elem.classList.remove('error-input')
    document.querySelector('label[for$="' + elem.id + '"]').classList.remove('error-label')
}