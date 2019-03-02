package com.example.chatandroidadvanced.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chatandroidadvanced.R;
import com.example.chatandroidadvanced.model.Conversation;
import com.example.chatandroidadvanced.model.ConversationService;
import com.example.chatandroidadvanced.model.GlideApp;
import com.example.chatandroidadvanced.model.Participant;
import com.example.chatandroidadvanced.view.ChatActivity;

import java.util.LinkedList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {

    private LayoutInflater inflater;
    private LinkedList<Participant> contactList;
    private Context context;
    private Participant currentUser;

    public ContactListAdapter(Context context, LinkedList<Participant> contactList, Participant currentUser){
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.contactList = contactList;
        this.currentUser = currentUser;
    }

    @NonNull
    @Override
    public ContactListAdapter.ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.conversation_item, parent, false);
        return new ContactViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactListAdapter.ContactViewHolder holder, int position) {

        //todo get first an last name from conversation partner
        String firstName = contactList.get(position).getfirstName();
        String lastName = contactList.get(position).getlastName();
        holder.conversationPartner.setText(firstName + " " + lastName);

        String email = contactList.get(position).getEmail();
        holder.conversationStatus.setText(email);

        holder.conversationTime.setText("");

        String image = contactList.get(position).getAvatar();

        GlideApp.with(context)
                .load(image)
                .placeholder(R.drawable.ic_loading_image)
                .error(R.drawable.ic_loading_error)
                .into(holder.conversationImage);

    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    class ContactViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final ImageView conversationImage;
        public final TextView conversationPartner;
        public final TextView conversationStatus;
        public final TextView conversationTime;

        final ContactListAdapter adapter;

        public ContactViewHolder(@NonNull View itemView, ContactListAdapter adapter) {
            super(itemView);
            conversationImage = itemView.findViewById(R.id.conversationImage);
            conversationPartner = itemView.findViewById(R.id.conversationPartner);
            conversationStatus = itemView.findViewById(R.id.conversationMessage);
            conversationTime = itemView.findViewById(R.id.conversationTime);
            this.adapter = adapter;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            //String element = contactList.get(position).getFirstName();
            //contactList.get(position).setFirstName("Clicked");
            //contactList.set(position, "Clicked" + element);
            //Todo if clicked on contact it should be added to the database to see it in conversations
            Context context = v.getContext();
            //todo only for testing cause app works only online with this method
            createNewConversation(context, position);
            //todo not delete
            /*Intent intentChat = new Intent(context, ChatActivity.class);
            intentChat.putExtra("contact", contactList.get(position));
            intentChat.putExtra("currentUser", currentUser);
            context.startActivity(intentChat);
            adapter.notifyDataSetChanged();
            ((Activity)context).finish();*/
        }

        private void createNewConversation(final Context context, final int pos){
            Conversation conversation = new Conversation("example topic");
            RetrofitInstance retrofitInstanceConversation = new RetrofitInstance();
            ConversationService conversationService = retrofitInstanceConversation.getConversationService();
            Call<Conversation> callConversation = conversationService.createConversation(conversation);
            callConversation.enqueue(new Callback<Conversation>() {
                @Override
                public void onResponse(Call<Conversation> call, Response<Conversation> response) {
                    if(!response.isSuccessful()){
                        Log.d("create conversation not successfull", String.valueOf(response.code()));
                        Toast.makeText(context ,"Something went wrong during creating conversation, please try again.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    Intent intentChat = new Intent(context, ChatActivity.class);
                    intentChat.putExtra("contact", contactList.get(pos));
                    intentChat.putExtra("currentUser", currentUser);
                    intentChat.putExtra("currentConversation", response.body());
                    context.startActivity(intentChat);
                    adapter.notifyDataSetChanged();
                    ((Activity)context).finish();
                    Log.d("create conversation success", response.body().getTopic() + " " + response.body().getId());
                }

                @Override
                public void onFailure(Call<Conversation> call, Throwable t) {
                    Log.d("create conversation not successfull", t.toString());
                    Toast.makeText(context ,"Something went wrong during creating conversation, please try again.", Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
