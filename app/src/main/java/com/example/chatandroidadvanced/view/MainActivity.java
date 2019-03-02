package com.example.chatandroidadvanced.view;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatandroidadvanced.R;
import com.example.chatandroidadvanced.model.Participant;
import com.example.chatandroidadvanced.model.ParticipantService;
import com.example.chatandroidadvanced.viewmodel.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    //variable layout edittext
    private TextInputLayout ltFirstName;
    private TextInputLayout ltLastName;
    private TextInputLayout ltEmail;

    //variable edittext
    private EditText txtFristname;
    private EditText txtLastname;
    private EditText txtEmail;

    //variable button
    private Button btnCreateUser;
    private Button btnCancelCreateUser;

    RetrofitInstance retrofitInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //get layout edittext
        ltFirstName = findViewById(R.id.layoutInputFirstName);
        ltLastName = findViewById(R.id.layoutInputLastName);
        ltEmail = findViewById(R.id.layoutInputEmail);

        //get edittext
        txtFristname = findViewById(R.id.textInputFirstName);
        txtLastname = findViewById(R.id.textInputLastName);
        txtEmail = findViewById(R.id.textInputEmail);

        //get buttons
        btnCreateUser = findViewById(R.id.btnCreateUser);
        btnCancelCreateUser = findViewById(R.id.btnCancelCreateUser);
    }

    public void createUserClicked(View view) {
        //boolean to check if all inupts are correct
        boolean missedInput = false;

        //get text from textlabels and store
        String firstName = txtFristname.getText().toString();
        String lastName = txtLastname.getText().toString();
        String eMail = txtEmail.getText().toString();

        if(firstName.isEmpty()) {
            Toast.makeText(getApplicationContext() ,"Please enter valid first name!", Toast.LENGTH_LONG).show();
            missedInput = true;
        }
        if(lastName.isEmpty()) {
            Toast.makeText(getApplicationContext() ,"Please enter valid last name!", Toast.LENGTH_LONG).show();
            missedInput = true;
        }
        if(eMail.isEmpty()) {
            Toast.makeText(getApplicationContext() ,"Please enter valid e-mail!", Toast.LENGTH_LONG).show();
            missedInput = true;
        }

        if(!missedInput){
            //todo create the user on server if all required inputs are valid start activity give parameters with putextra that in conversionactivity all
            //conversations of the user can be loaded

            /*todo create participant on server with method in retrofit object
            retrofitInstance = new RetrofitInstance();
            Participant participant = new Participant(eMail, firstName, lastName);
            retrofitInstance.createParticipant(participant, getApplicationContext());*/

            //todo delete is executed in Participantretrofit class
            Participant participant = new Participant(eMail, firstName, lastName);
            retrofitInstance = new RetrofitInstance();
            ParticipantService participantService = retrofitInstance.getParticipantService();
            Call<Participant> call = participantService.createParticipant(participant);
            call.enqueue(new Callback<Participant>() {
                @Override
                public void onResponse(Call<Participant> call, Response<Participant> response) {
                    if(!response.isSuccessful()){
                        Log.d("create participant not successfull", String.valueOf(response.code()));
                        Toast.makeText(getApplicationContext() ,"Something went wrong during creating user, please try again.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    //Log.d("created participant", response.body().getfirstName());
                    Toast.makeText(getApplicationContext() ,"firstName: " + response.body().getfirstName() + " lastName: " + response.body().getlastName() + " email: " + response.body().getEmail(), Toast.LENGTH_LONG).show();
                    Intent intentConversations = new Intent(getApplicationContext(), ConversationActivity.class);
                    //todo add current participant object
                    intentConversations.putExtra("currentUser", response.body());
                    startActivity(intentConversations);
                    finish();
                }

                @Override
                public void onFailure(Call<Participant> call, Throwable t) {
                    Log.d("create participant not successfull", t.toString());
                    Toast.makeText(getApplicationContext() ,"Something went wrong during creating user, please try again.", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Toast.makeText(getApplicationContext() ,"Something went wrong during creating user, please try again.", Toast.LENGTH_LONG).show();
        }

    }

    public void cancelCreateUserClicked(View view) {
        Toast.makeText(getApplicationContext() ,"cancel create user clicked", Toast.LENGTH_SHORT).show();
        txtFristname.setText("");
        txtLastname.setText("");
        txtEmail.setText("");
    }
}
