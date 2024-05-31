package com.example.memoryrush;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.Collections;
import java.util.ArrayList;

public class MemoryGridAdapter extends BaseAdapter {

    private Context context;
    private int itemCount;
    private LayoutInflater inflater;
    private ArrayList<Integer> cardValues; // Speichert die Werte der Karten
    private boolean[] cardFlipped; // Zustand jeder Karte
    private int lastFlippedPosition = -1; // Letzte umgedrehte Position
    private int pairsFound = 0; // Gefundene Paare
    private OnPairsMatchListener listener;

    public MemoryGridAdapter(Context context, int pairCount) {
        this.context = context;
        this.itemCount = pairCount * 2;
        this.inflater = LayoutInflater.from(context);
        this.cardValues = new ArrayList<>();
        this.cardFlipped = new boolean[itemCount];
        initializeCards();
    }

    private void initializeCards() {
        for (int i = 0; i < itemCount / 2; i++) {
            cardValues.add(i);
            cardValues.add(i); // Pärchen hinzufügen
        }
        Collections.shuffle(cardValues); // Karten mischen
    }

    @Override
    public int getCount() {
        return itemCount;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = inflater.inflate(R.layout.card_layout, parent, false);
        }

        TextView cardBack = view.findViewById(R.id.card_back);
        TextView cardFront = view.findViewById(R.id.card_front);

        cardBack.setText(String.valueOf(cardValues.get(position))); // Nummer setzen

        if (cardFlipped[position]) {
            cardBack.setVisibility(View.VISIBLE);
            cardFront.setVisibility(View.INVISIBLE);
        } else {
            cardBack.setVisibility(View.INVISIBLE);
            cardFront.setVisibility(View.VISIBLE);
        }

        view.setOnClickListener(v -> {
            if (!cardFlipped[position]) {
                cardFlipped[position] = true;
                notifyDataSetChanged();

                if (lastFlippedPosition == -1) {
                    lastFlippedPosition = position;
                } else {
                    final int currentPos = position;
                    if (cardValues.get(lastFlippedPosition).equals(cardValues.get(currentPos))) {
                        pairsFound++;
                        if (pairsFound == itemCount / 2) {
                            if (listener != null) {
                                listener.onAllPairsMatched();
                            }
                        }
                        lastFlippedPosition = -1;
                    } else {
                        new Handler().postDelayed(() -> {
                            cardFlipped[lastFlippedPosition] = false;
                            cardFlipped[currentPos] = false;
                            lastFlippedPosition = -1;
                            notifyDataSetChanged();
                        }, 1000); // Karten nach einer Sekunde umdrehen, wenn sie nicht übereinstimmen
                    }
                }
            }
        });

        return view;
    }

    public interface OnPairsMatchListener {
        void onAllPairsMatched();
    }

    public void setOnPairsMatchListener(OnPairsMatchListener listener) {
        this.listener = listener;
    }

}
