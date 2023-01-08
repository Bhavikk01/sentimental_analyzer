package com.example.sentimental_analyzer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.sentimental_analyzer.firestore_sefrvices.FireStoreService;
import com.example.sentimental_analyzer.firestore_sefrvices.retrieveData;
import com.example.sentimental_analyzer.models.NotesModel;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.DateTime;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateNewNote extends AppCompatActivity implements retrieveData {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FireStoreService fireStoreService = new FireStoreService(this);

    Button prev_btn, play_btn, next_btn,pause_btn;
    int song_cnt=1;

    int cnt = 1;

    ML_MODEL ml_model;
    Map<String, String[]> musicPlaylist = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final int[] size = {0};
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

        prev_btn = findViewById(R.id.previousButton);
        play_btn = findViewById(R.id.playButton);
        next_btn = findViewById(R.id.nextButton);
        pause_btn = findViewById(R.id.pauseButton);


        // creating a map
        creatingMap();

        // ML CODE

        ml_model = new ML_MODEL(this);
        ml_model.predict("I am happy!");

//        runMediaPLayer();

        // Creating a object
        MediaPlayer mediaPlayer = new MediaPlayer();

        // YOu need to justify the type of audio it will be
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        String aPath =  "android.resource://" + this.getPackageName()+"/raw/happy"+song_cnt;


//        mediaPlayer.setDataSource(aPath);  this will run but no efficient instead create URI

        Uri uri = Uri.parse(aPath);

        try {
            mediaPlayer.setDataSource(this,uri);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("UPDATE_SONG",e.getLocalizedMessage());
        }


        pause_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
                play_btn.setVisibility(View.VISIBLE);
                pause_btn.setVisibility(View.GONE);
            }
        });

        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mediaPlayer.start();

                play_btn.setVisibility(View.GONE);
                pause_btn.setVisibility(View.VISIBLE);


            }
        });

        next_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ++song_cnt;
                mediaPlayer.reset();

                String tempPath =  "android.resource://" + CreateNewNote.this.getPackageName()+"/raw/happy"+song_cnt;
                Uri tempuri = Uri.parse(tempPath);
                try {
                    mediaPlayer.setDataSource(getApplicationContext(),tempuri);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("UPDATE_SONG",e.getLocalizedMessage());
                }



            }
        });


        prev_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                --song_cnt;

                mediaPlayer.reset();
                String tempPath =  "android.resource://" + CreateNewNote.this.getPackageName()+"/raw/happy"+song_cnt;
                Uri tempuri = Uri.parse(tempPath);
                try {
                    mediaPlayer.setDataSource(getApplicationContext(),tempuri);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("UPDATE_SONG",e.getLocalizedMessage());
                }

            }
        });

        notesContent.setText(notes_Content);
        notesContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = notesContent.getText().toString();
                String[] arrOfStr = str.split("\\.", str.length());
                if (size[0] < arrOfStr.length) {
                    size[0] = arrOfStr.length;
                    if(str.length() >= 2){
                        String s = arrOfStr[arrOfStr.length - 2];
                    System.out.println(s);
                    }else{
                        String s = arrOfStr[arrOfStr.length - 1];
                    System.out.println(s);
                    }
                }
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

    private void creatingMap() {

        musicPlaylist.put("neu",  new String[]{"neu1", "neu2","neu3"});
        musicPlaylist.put("pos",  new String[]{"happy1", "happy2","happy3"});
        musicPlaylist.put("neg",  new String[]{"sad1","sad2","sad3"});
    }

    private void runMediaPLayer() {

    }

    @Override
    public List<NotesModel> getUserNotes(List<NotesModel> list) {
        return null;
    }
}