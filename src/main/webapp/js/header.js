let currentLocale
let requestUserLogin

function loadPage(locale, login) {
    currentLocale = locale
    if(login) {
        requestUserLogin = login
    }
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

function setLocaleEN(path) {
    currentLocale = 'en_EN'
    document.location.replace(path + '/main?command=change_language&locale=en_EN')
}

function setLocaleRU(path) {
    currentLocale = 'ru_RU'
    document.location.replace(path + '/main?command=change_language&locale=ru_RU')
}

async function subscribe(elem) {
    let url = 'rest?command=subscribe&login=' + requestUserLogin;
    let response = await fetch(url);
    let json = await response.json();
    console.log(json.status)
    if(json.status === 'SUCCESS') {
        let newLink = document.createElement('a');
        newLink.setAttribute('href', '#')
        newLink.setAttribute('onclick', 'unsubscribe(this)')
        newLink.setAttribute('class', 'profile__action-btn')
        if(currentLocale === 'ru_RU') {
            newLink.innerHTML = 'отписаться'
        } else {
            newLink.innerHTML = 'unsubscribe'
        }
        elem.after(newLink)
        elem.remove()
        let num = document.getElementById('subscribers-number')
        let value = parseInt(num.innerHTML)
        value++
        num.innerHTML = value.toString()
    }
}

async function unsubscribe(elem) {
    let url = 'rest?command=unsubscribe&login=' + requestUserLogin;
    let response = await fetch(url);
    let json = await response.json();
    console.log(json.status)
    if(json.status === 'SUCCESS') {
        let newLink = document.createElement('a');
        newLink.setAttribute('href', '#')
        newLink.setAttribute('onclick', 'subscribe(this)')
        newLink.setAttribute('class', 'profile__action-btn')
        if(currentLocale === 'ru_RU') {
            newLink.innerHTML = 'подписаться'
        } else {
            newLink.innerHTML = 'subscribe'
        }
        elem.after(newLink)
        elem.remove()
        let num = document.getElementById('subscribers-number')
        let value = parseInt(num.innerHTML)
        value--
        num.innerHTML = value.toString()
    }
}