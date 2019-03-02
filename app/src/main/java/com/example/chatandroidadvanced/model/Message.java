package com.example.chatandroidadvanced.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Message {

    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("conversationId")
    @Expose
    private Integer conversationId;

    @SerializedName("createdBy")
    @Expose
    private String createdBy;

    @SerializedName("createdDate")
    @Expose
    private String createdDate;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("lastModifiedBy")
    @Expose
    private String lastModifiedBy;

    @SerializedName("lastModifiedDate")
    @Expose
    private String lastModifiedDate;

    @SerializedName("receiverId")
    @Expose
    private Integer receiverId;

    @SerializedName("senderId")
    @Expose
    private Integer senderId;

    public Message(String content, Integer receiverId, Integer senderId) {
        this.content = content;
        this.conversationId = conversationId;
        this.receiverId = receiverId;
        this.senderId = senderId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getConversationId() {
        return conversationId;
    }

    public void setConversationId(Integer conversationId) {
        this.conversationId = conversationId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public Integer getId() {
        return id;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }
}
