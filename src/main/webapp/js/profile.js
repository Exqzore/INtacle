let currentName
let currentSurname

function activatePanel(elem) {
    let accordionContent = elem.nextElementSibling
    elem.classList.toggle('accordion__button--active')
    accordionContent.style.display = elem.classList.contains('accordion__button--active') ? 'block' : 'none'
    let textarea = document.getElementById('summary-text')
    textarea.value = ''
    textarea = document.getElementById('content-text')
    textarea.value = ''
}

function changeProfile(elem) {
    let saveBtn = document.getElementById('profile__save-btn')
    saveBtn.style.display = 'block'
    let cancelBtn = document.getElementById('profile__cancel-btn')
    cancelBtn.style.display = 'block'
    elem.style.display = 'none'

    let elemName = document.getElementById('profile__input-name')
    let elemSurname = document.getElementById('profile__input-surname')
    let elemMain = document.getElementById('profile__name-surname')
    elemMain.style.display = 'flex'
    let elemDecoration = document.getElementById('profile__decoration')
    elemDecoration.style.display = 'block'

    let inputName = document.getElementById('profile__name')
    let inputSurname = document.getElementById('profile__surname')

    if (elemName.style.display === 'none') {
        currentName = ''
        elemName.style.display = 'block'
    } else {
        currentName = inputName.value
    }

    if (elemSurname.style.display === 'none') {
        currentSurname = ''
        elemSurname.style.display = 'block'
    } else {
        currentSurname = inputSurname.value
    }

    inputName.removeAttribute('readonly')
    inputSurname.removeAttribute('readonly')

    inputName.classList.add('edit_input')
    inputSurname.classList.add('edit_input')
}

function cancelChangeProfile(elem) {
    let saveBtn = document.getElementById('profile__save-btn')
    saveBtn.style.display = 'none'
    let editBtn = document.getElementById('profile__edit-btn')
    editBtn.style.display = 'block'
    elem.style.display = 'none'

    let elemName = document.getElementById('profile__input-name')
    let elemSurname = document.getElementById('profile__input-surname')
    let elemMain = document.getElementById('profile__name-surname')
    let elemDecoration = document.getElementById('profile__decoration')

    let inputName = document.getElementById('profile__name')
    let inputSurname = document.getElementById('profile__surname')

    if (currentName === '') {
        inputName.value = ''
        elemName.style.display = 'none'
    } else {
        inputName.value = currentName
    }

    if (currentSurname === '') {
        inputSurname.value = ''
        elemSurname.style.display = 'none'
    } else {
        inputSurname.value = currentSurname
    }

    if (currentSurname === '' && currentName === '') {
        elemMain.style.display = 'none'
        elemDecoration.style.display = 'none'
    }

    inputName.setAttribute('readonly', 'readonly')
    inputSurname.setAttribute('readonly', 'readonly')

    inputName.classList.remove('edit_input')
    inputSurname.classList.remove('edit_input')
}

function appModalWindow() {
    let elem = document.querySelector('div[class$="modal-background"]')
    elem.style.display = 'flex'
}

function closeModalWindow() {
    let elem = document.querySelector('div[class$="modal-background"]')
    elem.style.display = 'none'
}

const uploadAvatarImageForm = document.getElementById('avatar_image-form')
const mainImage = document.getElementById('main-image-profile')
const closeBtn = document.getElementById('close-modal_btn')

async function loadAvatarImage(url) {
    closeBtn.click()
    let response = await fetch(uploadAvatarImageForm.action, {
        method: uploadAvatarImageForm.method,
        body: new FormData(uploadAvatarImageForm)
    })
    if (response.ok) {
        response.text().then(result => {
            let url = 'rest?command=change_avatar_image&imagePath=' + result
            fetch(url).then(response2 => {
                if(response2.ok) {
                    response2.json().then(json => {
                        if (json.status === 'SUCCESS') {
                            mainImage.src = 'main?command=take_file&file_name=' + result
                        }
                    })
                }
            })
        })
    }
}

const uploadPreviewImageForm = document.getElementById('preview_image-form')
const previewImageSelect = document.getElementById('preview_image_select')
const previewImage = document.getElementById('preview_image')
const inputPreviewImagePath = document.getElementById('preview_image_path')

function loadPreviewImage() {
    previewImageSelect.click()
    previewImageSelect.onchange = e => {
        fetch(uploadPreviewImageForm.action, {
            method: uploadPreviewImageForm.method,
            body: new FormData(uploadPreviewImageForm)
        }).then(response => {
            if (response.ok) {
                response.text().then(path => {
                    previewImage.src = 'main?command=take_file&file_name=' + path
                    inputPreviewImagePath.value = path
                })
            }
        })
    }
}

function likeEntry(entryId) {
    let currentOffset = window.pageYOffset
    let url = 'rest?command=like_entry&entry=' + entryId
    fetch(url).then(response => {
        window.scrollTo(0, currentOffset)
        if(response.ok) {
            response.json().then(json => {
                if(json.status === 'SUCCESS') {
                    let elem = document.getElementById('like_entry-' + entryId)
                    elem.classList.add('liked')
                    elem.classList.add('fa-heart')
                    elem.classList.remove('fa-heart-o')
                    elem.parentElement.setAttribute('onclick', 'unlikeEntry("' + entryId + '")')
                    elem = document.getElementById('count_likes-' + entryId)
                    let num = parseInt(elem.innerHTML)
                    num++
                    elem.innerHTML = num.toString()
                }
            })
        }
    })
}

function unlikeEntry(entryId) {
    let currentOffset = window.pageYOffset
    let url = 'rest?command=unlike_entry&entry=' + entryId
    fetch(url).then(response => {
        window.scrollTo(0, currentOffset)
        if(response.ok) {
            response.json().then(json => {
                if(json.status === 'SUCCESS') {
                    let elem = document.getElementById('like_entry-' + entryId)
                    elem.classList.add('fa-heart-o')
                    elem.classList.remove('fa-heart')
                    elem.classList.remove('liked')
                    elem.parentElement.setAttribute('onclick', 'likeEntry("' + entryId + '")')
                    elem = document.getElementById('count_likes-' + entryId)
                    let num = parseInt(elem.innerHTML)
                    num--
                    elem.innerHTML = num.toString()
                }
            })
        }
    })
}