package com.example.sentimental_analyzer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.sentimental_analyzer.firestore_sefrvices.FireStoreService;
import com.example.sentimental_analyzer.firestore_sefrvices.retrieveData;
import com.example.sentimental_analyzer.models.NotesModel;
import com.google.type.DateTime;

import java.util.List;

public class CreateNewNote extends AppCompatActivity implements retrieveData {

    FireStoreService fireStoreService = new FireStoreService(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String userId = getIntent().getStringExtra("userId");
        setContentView(R.layout.activity_create_new_note);
        EditText notesTitle = findViewById(R.id.notesTitle);
        EditText notesContent = findViewById(R.id.notesContent);
        ImageView save = findViewById(R.id.save);
        ImageView back = findViewById(R.id.back);

        back.setOnClickListener(view ->{
            Intent intent = new Intent(this, UserNotes.class);
            startActivity(intent);
            finish();
        });

        save.setOnClickListener(view ->{
            fireStoreService.putUserNote(
                    new NotesModel(
                            notesTitle.getText().toString(),
                            userId,
                            notesContent.getText().toString(),
                            DateTime.getDefaultInstance().toString()
                    )
            );
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