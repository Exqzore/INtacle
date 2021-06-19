let currentLocale

function setLocaleEN(path) {
    currentLocale = 'en_EN'
    document.location.replace(path + '/main?command=change_language&locale=en_EN')
}

function setLocaleRU(path) {
    currentLocale = 'ru_RU'
    document.location.replace(path + '/main?command=change_language&locale=ru_RU')
}

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

async function search(e, path) {
    if (e.keyCode === 13) {
        let partLogin = document.getElementById('search-input').value
        document.location.replace(path + '/main?command=find_users&part=' + partLogin)
    }
}