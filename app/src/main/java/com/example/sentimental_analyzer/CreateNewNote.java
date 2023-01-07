package com.example.sentimental_analyzer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.sentimental_analyzer.firestore_sefrvices.FireStoreService;
import com.example.sentimental_analyzer.firestore_sefrvices.retrieveData;
import com.example.sentimental_analyzer.models.NotesModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.DateTime;

import java.util.List;

public class CreateNewNote extends AppCompatActivity implements retrieveData {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FireStoreService fireStoreService = new FireStoreService(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String userId = getIntent().getStringExtra("userId");
        String notesId = getIntent().getStringExtra("notesId") == null? "": getIntent().getStringExtra("notesId");
        String notes_Title = getIntent().getStringExtra("notesTitle") == null ? "" : getIntent().getStringExtra("notesTitle");
        String notes_Content = getIntent().getStringExtra("notesContent") == null ? "" : getIntent().getStringExtra("notesContent");
        Boolean editable = getIntent().getBooleanExtra("editable", false);

        setContentView(R.layout.activity_create_new_note);
        EditText notesTitle = findViewById(R.id.notesTitle);
        EditText notesContent = findViewById(R.id.notesContent);
        ImageView save = findViewById(R.id.save);
        ImageView back = findViewById(R.id.back);

        notesContent.setText(notes_Content);
        notesContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String[] arrOfStr = str.split("@", -2);

                for (String a : arrOfStr)
                    System.out.println(a);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        notesTitle.setText(notes_Title);

        back.setOnClickListener(view ->{
            Intent intent = new Intent(this, UserNotes.class);
            startActivity(intent);
            finish();
        });

        save.setOnClickListener(view ->{

            String Id = db.collection("users")
                    .document()
                    .getId();
            if(editable){
                fireStoreService.updateUserNotes(
                        new NotesModel(
                                notesId,
                                notesTitle.getText().toString(),
                                userId,
                                notesContent.getText().toString(),
                                DateTime.getDefaultInstance().getHours() + ":" +DateTime.getDefaultInstance().getMinutes()
                        )
                );
            }else{
                fireStoreService.putUserNote(
                        new NotesModel(
                                Id,
                                notesTitle.getText().toString(),
                                userId,
                                notesContent.getText().toString(),
                                DateTime.getDefaultInstance().getHours() + ":" +DateTime.getDefaultInstance().getMinutes()
                        )
                );
            }
            Intent intent = new Intent(this, UserNotes.class);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public List<NotesModel> getUserNotes(List<NotesModel> list) {
        return null;
    }
}