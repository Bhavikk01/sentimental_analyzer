package com.example.sentimental_analyzer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.sentimental_analyzer.firestore_sefrvices.FireStoreService;
import com.example.sentimental_analyzer.models.NotesModel;

import java.util.ArrayList;
import java.util.List;


public class create_note extends AppCompatActivity {

    FireStoreService fireStoreService = new FireStoreService();
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        userId = getIntent().getStringExtra("userId");
        Button createNotesButton = findViewById(R.id.createNotes);

//        createNotesButton.setOnClickListener(view -> {
//            fireStoreService.putUserNote(
//                    new NotesModel(
//
//                    ),
//                    userId
//            );
//        });
        fireStoreService.getAllNotes(userId);
        List<NotesModel> userNotes = new ArrayList<>(fireStoreService.getList());
        Log.d("TAG", "onCreate: List of notes" + userNotes.size());
    }
}