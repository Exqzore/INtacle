const messageInput = document.querySelector('input[class$="message-input"]')
const chatId = messageInput.id.substring(14)

const chatPanel = document.getElementsByClassName('chat-panel')[0]
chatPanel.scrollTop = chatPanel.scrollHeight

setInterval(async () => {
    let url = 'rest?command=find_new_messages&chat=' + chatId;
    let response = await fetch(url);
    let json = await response.json();
    if (json.status === 'SUCCESS') {
        console.log(JSON.stringify(json))
        for(let i in json.messages) {
            let newElem = document.createElement('div')
            newElem.setAttribute('class', 'row row-left')
            newElem.innerHTML = `
                <img class="message-image" src="image/avatar/` + json.messages[i].avatarImagePath + `" alt="User avatar"
                width="44" height="44"/>
                <div class="chat-bubble chat-bubble__left">
                    <div class="message-title">
                        <div class="message-author">` + json.messages[i].login + `</div>
                        <div class="message-date">` + json.messages[i].creationDate + `</div>
                    </div>` + json.messages[i].content + `
                </div>`
            chatPanel.append(newElem)
            chatPanel.scrollTop = chatPanel.scrollHeight
        }
    }
}, 1500)

async function createMessage(form) {
    let elem = form.querySelector('input')
    let content = elem.value
    if (content !== '') {
        let url = 'rest?command=create_message&chat=' + chatId + '&content=' + content
        let response = await fetch(url)
        let json = await response.json();
        if (json.status === 'SUCCESS') {
            let newElem = document.createElement('div')
            newElem.setAttribute('class', 'row row-right')
            newElem.innerHTML = `
                    <div class="chat-bubble chat-bubble__right">
                        <div class="message-title title-right">
                            <div class="message-date">` + json.creationDate + `</div>
                            <div class="message-author">` + json.login + `</div>
                        </div>` + json.content + `
                    </div>
                    <img class="message-image" src="image/avatar/` + json.avatarImagePath + `" alt="User avatar" 
                    width="44" height="44"/>`
            chatPanel.append(newElem)
            chatPanel.scrollTop = chatPanel.scrollHeight
            elem.value = ''
        }
    }
}