package com.example.sentimental_analyzer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.sentimental_analyzer.Adapter.CardViewAdapter;
import com.example.sentimental_analyzer.firestore_sefrvices.FireStoreService;
import com.example.sentimental_analyzer.firestore_sefrvices.retrieveData;
import com.example.sentimental_analyzer.models.NotesModel;

import java.util.ArrayList;
import java.util.List;


public class UserNotes extends AppCompatActivity implements retrieveData {

    FireStoreService fireStoreService = new FireStoreService(this);
    CardViewAdapter cardViewAdapter;
    private String userId;
    List<NotesModel> userNotes = new ArrayList<NotesModel>();
    RecyclerView userNotesRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = getIntent().getStringExtra("userId");
        setContentView(R.layout.activity_create_note);

        userNotesRecyclerView = findViewById(R.id.userNotes);
        userNotesRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        fireStoreService.getAllNotes(userId);
        Button createNotesButton = findViewById(R.id.createNewNote);
        createNotesButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, CreateNewNote.class);
            intent.putExtra("userId", userId);
            startActivity(intent);
        });
    }

    @Override
    public List<NotesModel> getUserNotes(List<NotesModel> list) {
        Log.d("TAG", "getUserNotes: This is the demo" + list.toString());
        userNotes.addAll(list);
        cardViewAdapter = new CardViewAdapter(getApplicationContext(), userNotes);
        userNotesRecyclerView.setAdapter(cardViewAdapter);
        return null;
    }
}