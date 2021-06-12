package com.exqzore.intacle.model.entity;

public class Chat {
    private long id;
    private long recipientId;
    private String recipientLogin;
    private String recipientAvatarPath;

    public Chat() {
    }

    public Chat(long id, long recipientId, String recipientLogin, String recipientAvatarPath) {
        this.id = id;
        this.recipientId = recipientId;
        this.recipientLogin = recipientLogin;
        this.recipientAvatarPath = recipientAvatarPath;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(long recipientId) {
        this.recipientId = recipientId;
    }

    public String getRecipientLogin() {
        return recipientLogin;
    }

    public void setRecipientLogin(String recipientLogin) {
        this.recipientLogin = recipientLogin;
    }

    public String getRecipientAvatarPath() {
        return recipientAvatarPath;
    }

    public void setRecipientAvatarPath(String recipientAvatarPath) {
        this.recipientAvatarPath = recipientAvatarPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Chat chat = (Chat) o;
        return id == chat.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Chat{");
        sb.append("id=").append(id);
        sb.append(", recipientId=").append(recipientId);
        sb.append(", recipientLogin='").append(recipientLogin).append('\'');
        sb.append(", recipientAvatarPath='").append(recipientAvatarPath).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
