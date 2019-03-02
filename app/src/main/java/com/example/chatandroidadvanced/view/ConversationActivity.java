package com.example.chatandroidadvanced.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.chatandroidadvanced.R;
import com.example.chatandroidadvanced.model.Conversation;
import com.example.chatandroidadvanced.model.ConversationService;
import com.example.chatandroidadvanced.model.Participant;
import com.example.chatandroidadvanced.model.ParticipantService;
import com.example.chatandroidadvanced.viewmodel.ConversationListAdapter;
import com.example.chatandroidadvanced.viewmodel.RetrofitInstance;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConversationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private Participant currentUser;
    RetrofitInstance retrofitInstance;
    private ConversationListAdapter conversationListAdapter;
    private LinkedList<Conversation> conversationList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        //get current user which is logged in
        currentUser = (Participant)getIntent().getSerializableExtra("currentUser");
        Log.d("current user in conversationActivity is", currentUser.getfirstName()+ " " + currentUser.getlastName() + " " + currentUser.getId());

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarConversations);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.rcvConversations);
        conversationListAdapter = new ConversationListAdapter(this, conversationList, currentUser);
        recyclerView.setAdapter(conversationListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Todo get list of conversations from server with get method should be done everytime this activity is started to get actual list
        //conversationslist = get....;

        //todo delete this example list
        //only for testing self defined fix list.

        //conversationList.addLast(new Conversation("jim rogers", "jim rogers", "bla bla bla"));
        //conversationList.addLast(new Conversation("jim sanders", "jim sanders", "keine ahnung"));

        retrofitInstance = new RetrofitInstance();
        //todo not sure if needed
        //getConversations();

        /*int wordListSize = conversationList.size();
        recyclerView.getAdapter().notifyItemInserted(wordListSize);
        recyclerView.smoothScrollToPosition(wordListSize);*/
    }

    private void getConversations(){
        ConversationService conversationService = retrofitInstance.getConversationService();
        Call<List<Conversation>> call = conversationService.getAllConversations();
        call.enqueue(new Callback<List<Conversation>>() {
            @Override
            public void onResponse(Call<List<Conversation>> call, Response<List<Conversation>> response) {
                if(!response.isSuccessful()){
                    Log.d("get conversation not successfull", String.valueOf(response.code()));
                    return;
                }

                List<Conversation> posts = response.body();
                //Log.d("get last participant", response.body().get(posts.size()-1).getfirstName() + " " + response.body().get(posts.size()-1).getlastName());
                for (Conversation conversation : posts) {
                    if (!conversationList.contains(conversation.getId())) {
                        //Log.d("get conversation", conversation.getfirstName() + " " + conversation.getlastName());
                        conversationList.addLast(conversation);
                    }
                }

                int wordListSize = conversationList.size();
                recyclerView.getAdapter().notifyItemInserted(wordListSize);
                recyclerView.smoothScrollToPosition(wordListSize);
            }

            @Override
            public void onFailure(Call<List<Conversation>> call, Throwable t) {
                Log.d("get conversation failed", t.toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_conversation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.btnAddConversation){
            Toast.makeText(getApplicationContext() ,"Add new conversation!", Toast.LENGTH_LONG).show();
            //create new conversation if button is clicked
            Intent intentNewConversation = new Intent(this, AddConversationActivity.class);
            intentNewConversation.putExtra("currentUser", currentUser);
            startActivity(intentNewConversation);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
