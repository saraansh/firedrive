package com.example.saraansh.firedrive;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TrainingActivity extends AppCompatActivity {

    private DatabaseReference databaseReference;
    private EditText bookName, bookGenre, bookReferences;
    private Button buttonSave;
    private ListView listViewBooks;
    private List<BookInformation> bookList;
    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        databaseReference = FirebaseDatabase.getInstance().getReference("bookInformation");
        mAuth = FirebaseAuth.getInstance();
        bookName = (EditText) findViewById(R.id.book_name);
        bookGenre = (EditText) findViewById(R.id.book_genre);
        bookReferences = (EditText) findViewById(R.id.book_references);
        buttonSave = (Button) findViewById(R.id.button_save);
        listViewBooks = (ListView) findViewById(R.id.listViewBooks);
        bookList = new ArrayList<>();
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
                String bid = databaseReference.push().getKey();
                BookInformation bookInformation = new BookInformation(bid, bname, bgenre, brefer);
                databaseReference.child(bid).setValue(bookInformation);
                Toast.makeText(TrainingActivity.this, "Information Saved!", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                bookList.clear();
                for (DataSnapshot bookSnapshot : dataSnapshot.getChildren()) {
                    BookInformation bookInformation = bookSnapshot.getValue(BookInformation.class);
                    bookList.add(bookInformation);
                }

                DisplayAdapter adapter = new DisplayAdapter(TrainingActivity.this, bookList);
                listViewBooks.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Toast.makeText(TrainingActivity.this,databaseError.toString().trim(),Toast.LENGTH_LONG).show();
            }
        });
        mAuth.addAuthStateListener(mAuthListener);
    }

    public void onLogout(View view){
        FirebaseAuth.getInstance().signOut();
        Toast.makeText(TrainingActivity.this, "SIGNOUT", Toast.LENGTH_SHORT).show();
    }

}
