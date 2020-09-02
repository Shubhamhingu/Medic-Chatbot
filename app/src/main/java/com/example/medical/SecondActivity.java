package com.example.medical;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.kommunicate.KmConversationBuilder;
import io.kommunicate.Kommunicate;
import io.kommunicate.callbacks.KMLogoutHandler;
import io.kommunicate.callbacks.KmCallback;

public class SecondActivity extends AppCompatActivity {


    private FirebaseAuth firebaseAuth;
    private Button logout;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);



        WebView webView =new WebView(this);
        WebSettings webSettings =webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        firebaseAuth = FirebaseAuth.getInstance();

        logout = (Button)findViewById(R.id.btnLogout);

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(SecondActivity.this,"This is Floating Action Button",Toast.LENGTH_SHORT).show();
                Kommunicate.init(SecondActivity.this, "3e0609d29a9836b16605d52ee23594414");
                new KmConversationBuilder(SecondActivity.this)
                        .launchConversation(new KmCallback() {
                            @Override
                            public void onSuccess(Object message) {
                                Log.d("Conversation", "Success : " + message);
                            }

                            @Override
                            public void onFailure(Object error) {
                                Log.d("Conversation", "Failure : " + error);
                            }
                        });
            }
        });
    }

    private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(SecondActivity.this, MainActivity.class));
        Kommunicate.logout(this, new KMLogoutHandler() {
            @Override
            public void onSuccess(Context context) {
                Log.i("Logout","Success");
            }

            @Override
            public void onFailure(Exception exception) {
                Log.i("Logout","Failed");

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case R.id.logoutMenu:{
                Logout();
                break;
            }
            case R.id.profileMenu: {
                startActivity(new Intent(SecondActivity.this, ProfileActivity.class));
                break;
            }
            case R.id.scanner: {
                startActivity(new Intent(SecondActivity.this, ScannerActivity.class));
                break;
            }


        }
        return super.onOptionsItemSelected(item);
    }
}
