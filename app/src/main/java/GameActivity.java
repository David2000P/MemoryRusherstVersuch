package com.example.memoryrush;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {

    private GridView gridView;
    private MemoryGridAdapter adapter;
    private int level = 1;  // Startlevel

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        gridView = findViewById(R.id.gridView);
        setupGame();
    }

    private void setupGame() {
        int pairCount = calculatePairCount(level);
        adapter = new MemoryGridAdapter(this, pairCount);
        gridView.setAdapter(adapter);

        adapter.setOnPairsMatchListener(new MemoryGridAdapter.OnPairsMatchListener() {
            @Override
            public void onAllPairsMatched() {
                if (level < 10) {
                    level++;
                    setupGame();  // Starte das nächste Level
                } else {
                    Toast.makeText(GameActivity.this, "You've reached the maximum level. No more pairs will be added.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private int calculatePairCount(int level) {
        int basePairs = 2;  // Starte mit 2 Pärchen im Level 1
        return basePairs + level - 1; // Jedes Level fügt ein weiteres Pärchen hinzu
    }
}
