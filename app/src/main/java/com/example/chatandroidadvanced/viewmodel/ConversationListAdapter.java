package com.example.chatandroidadvanced.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.chatandroidadvanced.R;
import com.example.chatandroidadvanced.model.Conversation;
import com.example.chatandroidadvanced.model.GlideApp;
import com.example.chatandroidadvanced.model.Message;
import com.example.chatandroidadvanced.model.MessageService;
import com.example.chatandroidadvanced.model.Participant;
import com.example.chatandroidadvanced.view.ChatActivity;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConversationListAdapter extends RecyclerView.Adapter<ConversationListAdapter.ConversationViewHolder> {

    private LayoutInflater inflater;
    private LinkedList<Conversation> conversationList;
    private LinkedList<Message> messageList;
    private LinkedList<Participant> participantList;
    private LinkedList<Integer> receiverIdList;
    private Context context;
    private Participant currentUser;

    public ConversationListAdapter(Context context, LinkedList<Conversation> conversationList, Participant currentUser) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.conversationList = conversationList;
        this.currentUser = currentUser;
    }

    @NonNull
    @Override
    public ConversationListAdapter.ConversationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.conversation_item, parent, false);
        return new ConversationListAdapter.ConversationViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ConversationListAdapter.ConversationViewHolder holder, int position) {

        //todo get firstname lastname and content

        //todo get messages where clicked conversation id is equal message id and show these users and last message content
        /*RetrofitInstance retrofitInstance = new RetrofitInstance();
        MessageService messageService = retrofitInstance.getMessageService();
        Call<List<Message>> call = messageService.getAllMessages();
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                if(!response.isSuccessful()){
                    Log.d("get conversation not successfull", String.valueOf(response.code()));
                    return;
                }

                List<Message> posts = response.body();
                //Log.d("get last participant", response.body().get(posts.size()-1).getfirstName() + " " + response.body().get(posts.size()-1).getlastName());
                for (Message message : posts) {
                    if (message.getSenderId().equals(currentUser.getId())) {
                        messageList.addLast(message);
                    }
                }

                int wordListSize = conversationList.size();
                recyclerView.getAdapter().notifyItemInserted(wordListSize);
                recyclerView.smoothScrollToPosition(wordListSize);
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {
                Log.d("get conversation failed", t.toString());
            }
        });*/

        /*String firstName = conversationList.get(position).getFirstName();
        String lastName = conversationList.get(position).getLastName();
        holder.conversationPartner.setText(firstName + " " + lastName);

        String content = conversationList.get(position).getContent();
        holder.conversationStatus.setText(content);

        String time = conversationList.get(position).getTime();
        holder.conversationTime.setText(time);


        String image = conversationList.get(position).getCreatedBy() + firstName + lastName;*/

        //todo delete only testing
        holder.conversationPartner.setText(conversationList.get(position).getCreatedBy());
        String content = conversationList.get(position).getTopic();
        holder.conversationStatus.setText(content);
        String time = conversationList.get(position).getCreatedDate();
        holder.conversationTime.setText(time);
        String image = conversationList.get(position).getCreatedBy() + conversationList.get(position).getCreatedBy();
        //todo delete till here

        GlideApp.with(context)
                .load(image)
                .placeholder(R.drawable.ic_loading_image)
                .error(R.drawable.ic_loading_error)
                .into(holder.conversationImage);


    }

    @Override
    public int getItemCount() {
        return conversationList.size();
    }

    class ConversationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView conversationImage;
        public final TextView conversationPartner;
        public final TextView conversationStatus;
        public final TextView conversationTime;

        final ConversationListAdapter adapter;

        public ConversationViewHolder(@NonNull View itemView, ConversationListAdapter adapter) {
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
            //Todo if clicked on contact it should be added to the database to see it in conversations
            Context context = v.getContext();
            Intent intentChat = new Intent(context, ChatActivity.class);
            intentChat.putExtra("contact", conversationList.get(position));
            intentChat.putExtra("currentUser", currentUser);
            //todo put conversation id of clicked conversation
            context.startActivity(intentChat);
            adapter.notifyDataSetChanged();
            ((Activity) context).finish();
        }
    }
}
