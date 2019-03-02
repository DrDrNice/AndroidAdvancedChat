package com.example.chatandroidadvanced.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Conversation implements Serializable {

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

    @SerializedName("topic")
    @Expose
    private String topic;

    public Conversation(String topic) {
        this.topic = topic;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getCreatedBy() {
        return createdBy;
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

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }

    public String getTopic() {
        return topic;
    }


}
