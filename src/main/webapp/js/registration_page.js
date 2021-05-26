function toRegister(form) {
    let result = true
    let elemPassword = form.querySelector('input[id$="password-input"]')
    let elemRepeatPassword = form.querySelector('input[id$="repeat_password-input"]')
    let elemLogin = form.querySelector('input[id$="login-input"]')
    let elemEmail = form.querySelector('input[id$="email-input"]')
    let elemName = form.querySelector('input[id$="name-input"]')
    let elemSurname = form.querySelector('input[id$="surname-input"]')
    elemPassword.classList.remove('error-input')
    elemRepeatPassword.classList.remove('error-input')
    elemLogin.classList.remove('error-input')
    elemEmail.classList.remove('error-input')
    elemName.classList.remove('error-input')
    elemSurname.classList.remove('error-input')
    let elemPasswordLabel = form.querySelector('label[for$="password-input"]')
    let elemRepeatPasswordLabel = form.querySelector('label[for$="repeat_password-input"]')
    let elemLoginLabel = form.querySelector('label[for$="login-input"]')
    let elemEmailLabel = form.querySelector('label[for$="email-input"]')
    let elemNameLabel = form.querySelector('label[for$="name-input"]')
    let elemSurnameLabel = form.querySelector('label[for$="surname-input"]')
    elemPasswordLabel.classList.remove('error-label')
    elemRepeatPasswordLabel.classList.remove('error-label')
    elemLoginLabel.classList.remove('error-label')
    elemEmailLabel.classList.remove('error-label')
    elemNameLabel.classList.remove('error-label')
    elemSurnameLabel.classList.remove('error-label')
    if (elemPassword.value === ''|| elemPassword.value !== elemRepeatPassword.value) {
        elemPassword.classList.add('error-input')
        elemRepeatPassword.classList.add('error-input')
        elemPasswordLabel.classList.add('error-label')
        elemRepeatPasswordLabel.classList.add('error-label')
        result = false
    }
    if (elemLogin.value === '') {
        elemLogin.classList.add('error-input')
        elemLoginLabel.classList.add('error-label')
        result = false
    }
    if (elemEmail.value === '') {
        elemEmail.classList.add('error-input')
        elemEmailLabel.classList.add('error-label')
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