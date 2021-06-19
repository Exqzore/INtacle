function createComment(form) {
    let elem = document.getElementById('comment_input')
    let content = elem.value
    if (!(content.length === 0 || !content.trim())) {
        form.submit()
    }
    return false
}

function likeEntry(entryId) {
    let currentOffset = window.pageYOffset
    let url = 'rest?command=like_entry&entry=' + entryId
    fetch(url).then(response => {
        window.scrollTo(0, currentOffset)
        if (response.ok) {
            response.json().then(json => {
                if (json.status === 'SUCCESS') {
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
        if (response.ok) {
            response.json().then(json => {
                if (json.status === 'SUCCESS') {
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

function likeComment(commentId) {
    let currentOffset = window.pageYOffset
    let url = 'rest?command=like_comment&comment=' + commentId
    fetch(url).then(response => {
        window.scrollTo(0, currentOffset)
        if (response.ok) {
            response.json().then(json => {
                if (json.status === 'SUCCESS') {
                    let elem = document.getElementById('like_comment-' + commentId)
                    elem.classList.add('liked')
                    elem.classList.add('fa-heart')
                    elem.classList.remove('fa-heart-o')
                    elem.parentElement.setAttribute('onclick', 'unlikeComment("' + commentId + '")')
                    elem = document.getElementById('count_comment_likes-' + commentId)
                    let num = parseInt(elem.innerHTML)
                    num++
                    elem.innerHTML = num.toString()
                }
            })
        }
    })
}

function unlikeComment(commentId) {
    let currentOffset = window.pageYOffset
    let url = 'rest?command=unlike_comment&comment=' + commentId
    fetch(url).then(response => {
        window.scrollTo(0, currentOffset)
        if (response.ok) {
            response.json().then(json => {
                if (json.status === 'SUCCESS') {
                    let elem = document.getElementById('like_comment-' + commentId)
                    elem.classList.add('fa-heart-o')
                    elem.classList.remove('fa-heart')
                    elem.classList.remove('liked')
                    elem.parentElement.setAttribute('onclick', 'likeComment("' + commentId + '")')
                    elem = document.getElementById('count_comment_likes-' + commentId)
                    let num = parseInt(elem.innerHTML)
                    num--
                    elem.innerHTML = num.toString()
                }
            })
        }
    })
}