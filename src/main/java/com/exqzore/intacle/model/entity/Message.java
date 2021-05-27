package com.exqzore.intacle.model.entity;

import java.util.Date;

public class Message {
    private long id;
    private long chatId;
    private String content;
    private Date creationDate;
    private Date updateDate;
    private long authorId;
    private String authorLogin;
    private String authorImagePath;

    public Message() {
    }

    public Message(long id) {
        this.id = id;
    }

    public Message(long id, long chatId, String content, Date creationDate, Date updateDate, long authorId, String authorLogin, String authorImagePath) {
        this.id = id;
        this.chatId = chatId;
        this.content = content;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.authorId = authorId;
        this.authorLogin = authorLogin;
        this.authorImagePath = authorImagePath;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
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

    public long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(long authorId) {
        this.authorId = authorId;
    }

    public String getAuthorLogin() {
        return authorLogin;
    }

    public void setAuthorLogin(String authorLogin) {
        this.authorLogin = authorLogin;
    }

    public String getAuthorImagePath() {
        return authorImagePath;
    }

    public void setAuthorImagePath(String authorImagePath) {
        this.authorImagePath = authorImagePath;
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
        sb.append(", chatId=").append(chatId);
        sb.append(", content='").append(content).append('\'');
        sb.append(", creationDate=").append(creationDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", authorId=").append(authorId);
        sb.append(", authorLogin='").append(authorLogin).append('\'');
        sb.append(", authorImagePath='").append(authorImagePath).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
