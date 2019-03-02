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

import com.example.chatandroidadvanced.model.Participant;
import com.example.chatandroidadvanced.model.ParticipantService;
import com.example.chatandroidadvanced.viewmodel.ContactListAdapter;
import com.example.chatandroidadvanced.R;
import com.example.chatandroidadvanced.viewmodel.RetrofitInstance;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddConversationActivity extends AppCompatActivity {

    private RecyclerView recyclerview;
    private ContactListAdapter contactListAdapter;
    private Participant currentUser;
    RetrofitInstance retrofitInstance;
    private LinkedList<Participant> contactList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_conversation);

        //get current user which is logged in
        currentUser = (Participant)getIntent().getSerializableExtra("currentUser");
        Log.d("current user in addConversationActivity is", currentUser.getfirstName()+ " " + currentUser.getlastName() + " " + currentUser.getId());

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarNewConversation);
        setSupportActionBar(toolbar);

        recyclerview = findViewById(R.id.rcvContacts);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        contactListAdapter = new ContactListAdapter(this, contactList, currentUser);
        recyclerview.setAdapter(contactListAdapter);

        retrofitInstance = new RetrofitInstance();
        ParticipantService participantService = retrofitInstance.getParticipantService();
        Call<List<Participant>> call = participantService.getAllParticipants();
        call.enqueue(new Callback<List<Participant>>() {
            @Override
            public void onResponse(Call<List<Participant>> call, Response<List<Participant>> response) {
                if(!response.isSuccessful()){
                    Log.d("get participants not successfull", String.valueOf(response.code()));
                    return;
                }

                List<Participant> posts = response.body();
                //Log.d("get last participant", response.body().get(posts.size()-1).getfirstName() + " " + response.body().get(posts.size()-1).getlastName());
                for (Participant participant : posts) {
                    if (!participant.getEmail().equals(currentUser.getEmail()) &&
                            !participant.getfirstName().equals(currentUser.getfirstName()) && !participant.getlastName().equals(currentUser.getlastName())) {
                        //Log.d("get participant", participant.getfirstName() + " " + participant.getlastName());
                        contactList.addLast(participant);
                    }
                }

                int wordListSize = contactList.size();
                recyclerview.getAdapter().notifyItemInserted(wordListSize);
                recyclerview.smoothScrollToPosition(wordListSize);
            }

            @Override
            public void onFailure(Call<List<Participant>> call, Throwable t) {
                Log.d("get participants failed", t.toString());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_conversation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.btnCancelAddConversation){
            //cancel activity of creating new conversation and go back to list of conversations
            Toast.makeText(getApplicationContext() ,"Cancel create conversation!", Toast.LENGTH_LONG).show();
            Intent intentConversations = new Intent(this, ConversationActivity.class);
            intentConversations.putExtra("currentUser", currentUser);
            startActivity(intentConversations);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
