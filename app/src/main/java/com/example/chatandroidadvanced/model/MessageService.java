package com.example.chatandroidadvanced.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface MessageService {

    @GET("/api/messages")
    Call<List<Message>> getAllMessages();

    @POST("/api/messages")
    Call<Message> createMessage(@Body Message message);

    @PUT("/api/messages")
    Call<Message> updateMessage();

    @GET("/api/messages/count")
    Call<Message> countMessages();

    @DELETE("/api/messages/{id}")
    Call<Message> deleteMessage(@Path("id") int postId);

    @GET("/api/messages/{id}")
    Call<Message> getMessage(@Path("id") int postId);
}
