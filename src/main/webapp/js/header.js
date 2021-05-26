function displayProfileMenu() {
    let elem = document.getElementById('profile-wrapper')
    let elemClass = elem.getAttribute('class')
    if (elemClass && !elemClass.includes('profile-wrapper__selected')) {
        elem.classList.add('profile-wrapper__selected')
        document.getElementById('profile-arrow__menu').classList.add('profile-arrow__selection-selected')
    } else {
        elem.classList.remove('profile-wrapper__selected')
        document.getElementById('profile-arrow__menu').classList.remove('profile-arrow__selection-selected')
    }
}

function goLoginPage(elem) {
    document.location.href = elem.querySelector('a').href
}