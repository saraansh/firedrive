package com.example.saraansh.firedrive;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class TrainingActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private EditText bookName, bookGenre, bookReferences;
    private Button buttonSave;
    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        bookName = (EditText) findViewById(R.id.book_name);
        bookGenre = (EditText) findViewById(R.id.book_genre);
        bookReferences = (EditText) findViewById(R.id.book_references);
        buttonSave = (Button) findViewById(R.id.button_save);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null){
                    Intent intent = new Intent(TrainingActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        };

        buttonSave.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String bname = bookName.getText().toString().trim();
                String bgenre = bookGenre.getText().toString().trim();
                String brefer = bookReferences.getText().toString().trim();
                UserInformation userInformation = new UserInformation(bname, bgenre, brefer);
                FirebaseUser user = mAuth.getCurrentUser();
                databaseReference.child(user.getUid()).setValue(userInformation);
                Toast.makeText(TrainingActivity.this, "Information Saved...", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void onLogout(View view){
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(TrainingActivity.this, "SIGNOUT", Toast.LENGTH_SHORT).show();
    }



}
