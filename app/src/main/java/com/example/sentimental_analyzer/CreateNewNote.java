package com.example.sentimental_analyzer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
        String notes_Title = getIntent().getStringExtra("notesTitle") == null ? "" : getIntent().getStringExtra("notesTitle");
        String notes_Content = getIntent().getStringExtra("notesContent") == null ? "" : getIntent().getStringExtra("notesContent");

        setContentView(R.layout.activity_create_new_note);
        EditText notesTitle = findViewById(R.id.notesTitle);
        EditText notesContent = findViewById(R.id.notesContent);
        ImageView save = findViewById(R.id.save);
        ImageView back = findViewById(R.id.back);

        notesContent.setText(notes_Content);
        notesTitle.setText(notes_Title);

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
                            DateTime.getDefaultInstance().getHours() + ":" +DateTime.getDefaultInstance().getMinutes()
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