package com.exqzore.intacle.model.entity;

import java.util.Date;

public class Comment {
    private long id;
    private String content;
    private int likesCount;
    private Date creationDate;
    private String authorLogin;
    private String authorAvatarPath;
    private boolean liked;

    public Comment() {
    }

    public Comment(long id) {
        this.id = id;
    }

    public Comment(long id, String content, int likesCount, Date creationDate, String authorLogin, String authorAvatarPath, boolean liked) {
        this.id = id;
        this.content = content;
        this.likesCount = likesCount;
        this.creationDate = creationDate;
        this.authorLogin = authorLogin;
        this.authorAvatarPath = authorAvatarPath;
        this.liked = liked;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getAuthorLogin() {
        return authorLogin;
    }

    public void setAuthorLogin(String authorLogin) {
        this.authorLogin = authorLogin;
    }

    public String getAuthorAvatarPath() {
        return authorAvatarPath;
    }

    public void setAuthorAvatarPath(String authorAvatarPath) {
        this.authorAvatarPath = authorAvatarPath;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comment comment = (Comment) o;
        return id == comment.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Comment{");
        sb.append("id=").append(id);
        sb.append(", content='").append(content).append('\'');
        sb.append(", likesCount=").append(likesCount);
        sb.append(", creationDate=").append(creationDate);
        sb.append(", authorLogin='").append(authorLogin).append('\'');
        sb.append(", authorAvatarPath='").append(authorAvatarPath).append('\'');
        sb.append(", liked=").append(liked);
        sb.append('}');
        return sb.toString();
    }
}
