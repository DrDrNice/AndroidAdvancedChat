package com.example.chatandroidadvanced.view;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatandroidadvanced.R;
import com.example.chatandroidadvanced.model.Conversation;
import com.example.chatandroidadvanced.model.ConversationService;
import com.example.chatandroidadvanced.model.Message;
import com.example.chatandroidadvanced.model.MessageService;
import com.example.chatandroidadvanced.model.Participant;
import com.example.chatandroidadvanced.viewmodel.MessageListAdapter;
import com.example.chatandroidadvanced.viewmodel.RetrofitInstance;

import java.io.IOException;
import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    private Participant participant;
    private Participant currentUser;
    private Conversation currentConversation;
    private EditText inputText;
    private RetrofitInstance retrofitInstanceMessage;
    MessageService messageService;

    private MessageListAdapter messageListAdapter;
    private LinkedList<Message> messageList = new LinkedList<>();

    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //todo delete get data from bundle
        //Bundle extras = getIntent().getExtras();
        //participant = (Participant) extras.getSerializable("contact");
        //currentUser = (Participant) extras.getSerializable("currentUser");

        //get clicked contact from contaclistadapter
        participant = (Participant)getIntent().getSerializableExtra("contact");
        //get current user
        currentUser = (Participant)getIntent().getSerializableExtra("currentUser");
        //get current conversation
        currentConversation = (Conversation)getIntent().getSerializableExtra("currentConversation");

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarChat);
        setSupportActionBar(toolbar);

        //get clicked contact from contactlistadapter and set it as toolbar header
        String firstName = participant.getfirstName();
        String lastName = participant.getlastName();
        toolbar.setTitle(firstName + " " + lastName);

        inputText = (EditText)findViewById(R.id.chatInputText);

        recyclerView = (RecyclerView)findViewById(R.id.rcvChat);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        messageListAdapter = new MessageListAdapter(this, messageList);
        recyclerView.setAdapter(messageListAdapter);

        retrofitInstanceMessage = new RetrofitInstance();
        messageService = retrofitInstanceMessage.getMessageService();

        //load data from server in messagelist
        /*Call<List<Message>> call = messageService.getAllMessages();
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if(!response.isSuccessful()){
                    Log.d("get prev messages not successfull", String.valueOf(response.code()));
                    return;
                }

                List<Message> posts = response.body();
                //Log.d("get last participant", response.body().get(posts.size()-1).getfirstName() + " " + response.body().get(posts.size()-1).getlastName());
                for (Message message : posts) {
                    if (message.getConversationId() == currentConversation.getId()) {
                        //Log.d("get participant", participant.getfirstName() + " " + participant.getlastName());
                        messageList.addLast(message);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Log.d("get prev messages failed", t.toString());
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.btnBackToConversations){
            Toast.makeText(getApplicationContext() ,"Back to conversations!", Toast.LENGTH_LONG).show();
            Intent intentConversations = new Intent(this, ConversationActivity.class);
            intentConversations.putExtra("currentUser", currentUser);
            startActivity(intentConversations);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void sendText(View view) {
        //todo method that data keeps updated and gets called when message is received

        //if message list empty it is a new conversation so create one conversation if a message is sent
        //push conversation on server and insert conversation in list
        /*if(messageList.isEmpty()) {
            //todo push new conversation to server and save pushed object in currentconversation so that the id can be used for messages
            //todo not working because method runs parallel.. es muss gewartet werden bis conversation erstellt wurde und dann kann message gesendet werden
            Conversation conversation = new Conversation(inputText.getText().toString());
            RetrofitInstance retrofitInstanceConversation = new RetrofitInstance();
            ConversationService conversationService = retrofitInstanceConversation.getConversationService();
            Call<Conversation> callConversation = conversationService.createConversation(conversation);
            callConversation.enqueue(new Callback<Conversation>() {
                @Override
                public void onResponse(Call<Conversation> call, Response<Conversation> response) {
                    if(!response.isSuccessful()){
                        Log.d("create conversation not successfull", String.valueOf(response.code()));
                        Toast.makeText(getApplicationContext() ,"Something went wrong during creating conversation, please try again.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    currentConversation = response.body();
                    Log.d("create conversation success", response.body().getTopic() + " " + response.body().getId());
                    Log.d("create conversation success", currentConversation.getTopic() + " " + currentConversation.getId());
                }

                @Override
                public void onFailure(Call<Conversation> call, Throwable t) {
                    Log.d("create conversation not successfull", t.toString());
                    Toast.makeText(getApplicationContext() ,"Something went wrong during creating conversation, please try again.", Toast.LENGTH_LONG).show();
                }
            });
        }*/

        //push written message on server and update recycler view
        Message message = new Message(inputText.getText().toString(), participant.getId(), currentUser.getId(), currentConversation.getId());
        Call<Message> callMessage = messageService.createMessage(message);
        callMessage.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(Call<Message> call, Response<Message> response) {
                if(!response.isSuccessful()){
                    Log.d("create message not successfull", String.valueOf(response.code()));
                    Toast.makeText(getApplicationContext() ,"Something went wrong during creating message, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
                Log.d("create message success", response.body().getContent() + " " + response.body().getSenderId() + " " + response.body().getReceiverId());
                messageList.addLast(response.body());
                int wordListSize = messageList.size();
                recyclerView.getAdapter().notifyItemInserted(wordListSize);
                recyclerView.smoothScrollToPosition(wordListSize);
            }

            @Override
            public void onFailure(Call<Message> call, Throwable t) {
                Log.d("create message not successfull", t.toString());
                Toast.makeText(getApplicationContext() ,"Something went wrong during creating message, please try again.", Toast.LENGTH_LONG).show();
            }
        });
        inputText.setText("");
    }
}
