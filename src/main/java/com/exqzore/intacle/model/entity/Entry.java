package com.exqzore.intacle.model.entity;

import java.io.Serializable;
import java.util.Date;

public class Entry implements Serializable {
    private long id;
    private String title;
    private String summary;
    private String content;
    private String previewImagePath;
    private Date creationDate;
    private Date updateDate;
    private User author;
    private int likesCount;
    private boolean isLiked;

    public Entry() {
    }

    public Entry(long id, String title, String summary, String content, String previewImagePath, Date creationDate,
                 Date updateDate, User author, int likesCount, boolean isLiked) {
        this.id = id;
        this.title = title;
        this.summary = summary;
        this.content = content;
        this.previewImagePath = previewImagePath;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.author = author;
        this.likesCount = likesCount;
        this.isLiked = isLiked;
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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public boolean isLiked() {
        return isLiked;
    }

    public void setLiked(boolean liked) {
        this.isLiked = liked;
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
        sb.append(", summary='").append(summary).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append(", previewImagePath='").append(previewImagePath).append('\'');
        sb.append(", creationDate=").append(creationDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", author=").append(author);
        sb.append(", likesCount=").append(likesCount);
        sb.append(", isLiked=").append(isLiked);
        sb.append('}');
        return sb.toString();
    }
}
