package com.exqzore.intacle.model.entity;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private long id;
    private Chat chat;
    private String content;
    private Date creationDate;
    private Date updateDate;
    private User author;

    public Message() {
    }

    public Message(long id) {
        this.id = id;
    }

    public Message(long id, Chat chat, String content, Date creationDate, Date updateDate, User author) {
        this.id = id;
        this.chat = chat;
        this.content = content;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Message message = (Message) o;
        return id == message.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Message{");
        sb.append("id=").append(id);
        sb.append(", chat=").append(chat);
        sb.append(", content='").append(content).append('\'');
        sb.append(", creationDate=").append(creationDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", author=").append(author);
        sb.append('}');
        return sb.toString();
    }
}
