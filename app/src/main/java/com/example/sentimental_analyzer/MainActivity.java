package com.example.sentimental_analyzer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.sentimental_analyzer.firestore_sefrvices.FireStoreService;
import com.example.sentimental_analyzer.firestore_sefrvices.retrieveData;
import com.example.sentimental_analyzer.models.NotesModel;
import com.example.sentimental_analyzer.models.UserModel;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;

import java.util.List;

public class MainActivity extends AppCompatActivity implements retrieveData {

    GoogleSignInClient mGoogleSignInClient;
    FireStoreService fireStoreService = new FireStoreService(this);
    private static int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseApp.initializeApp(this);
        setContentView(R.layout.activity_main);

        SignInButton signInButton = findViewById(R.id.SignIn);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null){
            updateUI(account);
        }

        signInButton.setOnClickListener(view -> {
            Log.d("TAG", "signInFunc: Here is me");
            signInFunc();
        });

    }

    private void updateUI(GoogleSignInAccount account) {
        Intent intent = new Intent(this, UserNotes.class);
        intent.putExtra("userId", account.getId());
        this.startActivity ( intent );
    }

    void signInFunc(){
        Log.d("TAG", "signInFunc: Here is me");
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {

            UserModel userModel;
            userModel = new UserModel(
                    acct.getDisplayName(),
                    acct.getEmail(),
                    acct.getId(),
                    acct.getPhotoUrl().toString()
                    );
            fireStoreService.putUser(userModel);
            Intent intent = new Intent(this, UserNotes.class);
            intent.putExtra("userId", acct.getId());
            this.startActivity ( intent );
        }
    }

    @Override
    public List<NotesModel> getUserNotes(List<NotesModel> list) {
        return null;
    }
}