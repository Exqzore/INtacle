package com.exqzore.intacle.model.entity;

import java.util.Date;
import java.util.List;

public class Entry {
    private long id;
    private String title;
    private String content;
    private String previewImagePath;
    private List<Comment> comments;
    private Date updateDate;
    private int likesCount;
    private String authorLogin;
    private String authorAvatarPath;
    private boolean liked;

    public Entry() {
    }

    public Entry(long id) {
        this.id = id;
    }

    public Entry(long id, String title, String content, String previewImagePath, List<Comment> comments, Date updateDate, int likesCount, String authorLogin, String authorAvatarPath, boolean liked) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.previewImagePath = previewImagePath;
        this.comments = comments;
        this.updateDate = updateDate;
        this.likesCount = likesCount;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPreviewImagePath() {
        return previewImagePath;
    }

    public void setPreviewImagePath(String previewImagePath) {
        this.previewImagePath = previewImagePath;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
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
        Entry entry = (Entry) o;
        return id == entry.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Entry{");
        sb.append("id=").append(id);
        sb.append(", title='").append(title).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", previewImagePath='").append(previewImagePath).append('\'');
        sb.append(", comments=").append(comments);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", likesCount=").append(likesCount);
        sb.append(", authorLogin='").append(authorLogin).append('\'');
        sb.append(", authorAvatarPath='").append(authorAvatarPath).append('\'');
        sb.append(", liked=").append(liked);
        sb.append('}');
        return sb.toString();
    }
}
