package com.example.chatandroidadvanced.model;

//import android.arch.persistence.room.ColumnInfo;
//import android.arch.persistence.room.Entity;
//import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

//@Entity(tableName = "participant_table")
public class Participant implements Serializable {

    //@PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    private Integer id;

    //@PrimaryKey(autoGenerate = true)
    @SerializedName("createdBy")
    @Expose
    private String createdBy;

    //@PrimaryKey(autoGenerate = true)
    @SerializedName("createdDate")
    @Expose
    private String createdDate;

    //@PrimaryKey(autoGenerate = true)
    @SerializedName("lastModifiedBy")
    @Expose
    private String lastModifiedBy;

    //@PrimaryKey(autoGenerate = true)
    @SerializedName("lastModifiedDate")
    @Expose
    private String lastModifiedDate;

    //@PrimaryKey(autoGenerate = true)
    @SerializedName("avatar")
    @Expose
    private String avatar;

    //@NonNull
    //@ColumnInfo(name = "email")
    @SerializedName("email")
    @Expose
    private String email;

    //@NonNull
    //@ColumnInfo(name = "firstName")
    @SerializedName("firstName")
    @Expose
    private String firstName;

    //@NonNull
    //@ColumnInfo(name = "lastName")
    @SerializedName("lastName")
    @Expose
    private String lastName;

    public Participant(){

    }

    public Participant(@NonNull String email, String firstName, String lastName) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public String getEmail() {
        return this.email;
    }

    public String getfirstName() {
        return this.firstName;
    }

    public String getlastName() {
        return this.lastName;
    }

    public Integer getId() {
        return id;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public String getLastModifiedDate() {
        return lastModifiedDate;
    }
}
