package com.example.sentimental_analyzer.firestore_sefrvices;

import android.util.Log;


import com.example.sentimental_analyzer.models.NotesModel;
import com.example.sentimental_analyzer.models.UserModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.type.DateTime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireStoreService{
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    List<NotesModel> list = new ArrayList<>();
    retrieveData getData;

    public FireStoreService(retrieveData getData) {
        this.getData = getData;
    }

    public void putUser(UserModel userData){
        String ID = userData.getUid();

        Map<String, Object> user = new HashMap<>();
        user.put("userName", userData.getUserName());
        user.put("userEmail", userData.getUserEmail());
        user.put("uid", ID);
        user.put("userPhoto", userData.getUserPhoto());

        db.collection("users")
                .document(ID)
                .set(user);
    }

    public void getAllNotes(String uid){
        db.collection("users")
                .document(uid)
                .collection("notes")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> notes = document.getData();
                            Log.d("Notes", "getAllNotes: " + notes);
                            list.add(new NotesModel(
                                    (String) notes.get("id"),
                                    (String) notes.get("notesTitle"),
                                    (String) notes.get("uid"),
                                    (String) notes.get("notesContent"),
                                    (String) notes.get("dateTime")
                            ));
                        }
                        getData.getUserNotes(list);
                    }
                });
    }


    public void putUserNote(NotesModel notes){

        Map<String, Object> note = new HashMap<>();
        note.put("id", notes.getId());
        note.put("notesTitle", notes.getNotesTitle());
        note.put("dateTime", notes.getDateTime());
        note.put("uid", notes.getUid());
        note.put("notesContent", notes.getNotesContent());
        Log.d("TAG", "putUserNote: " + notes.getUid());
        db.collection("users")
                .document(notes.getUid())
                .collection("notes")
                .document(notes.getId())
                .set(note);
    }

    public void updateUserNotes(NotesModel notes){

        Map<String, Object> note = new HashMap<>();
        Log.d("TAG", "putUserNote: This is the updateFunc" + notes.getId());
        note.put("id", notes.getId());
        note.put("notesTitle", notes.getNotesTitle());
        note.put("dateTime", notes.getDateTime());
        note.put("uid", notes.getUid());
        note.put("notesContent", notes.getNotesContent());
        db.collection("users")
                .document(notes.getUid())
                .collection("notes")
                .document(notes.getId())
                .update(note);
    }

    public void initializeMusic(){
        Map<String, String[]> musicPlaylist = new HashMap<>();
        musicPlaylist.put("compound", new String[]{"", ""});
        musicPlaylist.put("neu",  new String[]{"", ""});
        musicPlaylist.put("pos",  new String[]{"", ""});
        musicPlaylist.put("neg",  new String[]{"", ""});
    }

}