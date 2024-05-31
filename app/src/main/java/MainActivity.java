package com.example.memoryrush;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button logoutButton;
    private Button playButton;
    private Button highscoreButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        logoutButton = findViewById(R.id.logoutButton);
        playButton = findViewById(R.id.playButton);
        highscoreButton = findViewById(R.id.highscoreButton);

        // Listener für den Logout-Button
        logoutButton.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            finish();
        });

        // Listener für den Play-Button
        playButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        });

        // Eventueller Platz für die Highscore Button Implementierung
        highscoreButton.setOnClickListener(v -> {
            // Hier kannst du Code einfügen, um die Highscore-Aktivität zu starten
            // Beispiel:
            // Intent intent = new Intent(this, HighscoreActivity.class);
            // startActivity(intent);
        });
    }


    // Implement playButton and highscoreButton actions here
    }
