package com.example.sentimental_analyzer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.sentimental_analyzer.firestore_sefrvices.FireStoreService;
import com.example.sentimental_analyzer.firestore_sefrvices.retrieveData;
import com.example.sentimental_analyzer.models.NotesModel;

import java.util.ArrayList;
import java.util.List;


public class UserNotes extends AppCompatActivity implements retrieveData {

    FireStoreService fireStoreService = new FireStoreService(this);
    private String userId;
    List<NotesModel> userNotes = new ArrayList<NotesModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        userId = getIntent().getStringExtra("userId");

//        Button createNotesButton = findViewById(R.id.createNewNote);
//        createNotesButton.setOnClickListener(view -> {
//            Intent intent = Intent(this, NewNotes.class);
//            startActivity(intent);
//        });

        fireStoreService.getAllNotes(userId);
    }

    @Override
    public List<NotesModel> getUserNotes(List<NotesModel> list) {
        Log.d("TAG", "getUserNotes: This is the demo" + list.toString());
        userNotes.addAll(list);
        return null;
    }
}