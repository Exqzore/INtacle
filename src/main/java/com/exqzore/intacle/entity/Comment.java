package com.exqzore.intacle.entity;

import java.io.Serializable;
import java.util.Date;

public class Comment implements Serializable {
    private long id;
    private String content;
    private Date creationDate;
    private Date updateDate;
    private Entry entry;
    private User author;
    private int likesCount;
    private boolean isLiked;

    public Comment() {
    }

    public Comment(long id, String content, Date creationDate, Date updateDate, Entry entry, User author,
                   int likesCount, boolean isLiked) {
        this.id = id;
        this.content = content;
        this.creationDate = creationDate;
        this.updateDate = updateDate;
        this.entry = entry;
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


    public Entry getEntry() {
        return entry;
    }

    public void setEntry(Entry entry) {
        this.entry = entry;
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
        isLiked = liked;
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
        sb.append(", creationDate=").append(creationDate);
        sb.append(", updateDate=").append(updateDate);
        sb.append(", entry=").append(entry);
        sb.append(", author=").append(author);
        sb.append(", likesCount=").append(likesCount);
        sb.append(", isLiked=").append(isLiked);
        sb.append('}');
        return sb.toString();
    }
}
