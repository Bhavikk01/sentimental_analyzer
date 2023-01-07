package com.example.sentimental_analyzer.firestore_sefrvices;

import android.util.Log;


import com.example.sentimental_analyzer.models.NotesModel;
import com.example.sentimental_analyzer.models.UserModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireStoreService {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<NotesModel> list = new ArrayList<>();

    public String putUser(UserModel userData){
        String ID = db.collection("users")
                .document()
                .getId();

        Map<String, Object> user = new HashMap<>();
        user.put("userName", userData.getUserName());
        user.put("userEmail", userData.getUserEmail());
        user.put("userUid", userData.getUid());
        user.put("docId", ID);
        user.put("userPhoto", userData.getUserPhoto());

        db.collection("users")
                .document(ID)
                .set(user);
        return ID;
    }

    public void getAllNotes(String uid){
//        list = new ArrayList<>();
        db.collection("users")
                .document("jkWgvyU5TsMnJwlv9sVQ")
                .collection("notes")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> notes = document.getData();
                            Log.d("Notes", "getAllNotes: " + notes);
                            list.add(new NotesModel(
                                    notes.get("notesTitle").toString(),
                                    notes.get("dateTime").toString(),
                                    notes.get("uid").toString(),
                                    notes.get("notesContent").toString()
                            ));
                        }
                    }
                    Log.d("Notes", "getAllNotes: " + list.size());
                });
    }

    public List<NotesModel> getList() {
        Log.d("Notes", "get list notes: " + list.size());
        return list;
    }

    public void putUserNote(NotesModel notes, String id){

        Map<String, Object> note = new HashMap<>();
        note.put("notesTitle", notes.getNotesTitle());
        note.put("dateTime", notes.getDateTime());
        note.put("uid", notes.getUid());
        note.put("notesContent", notes.getNotesContent());

        db.collection("users")
                .document(id)
                .collection("notes")
                .document()
                .set(note);
    }
}
