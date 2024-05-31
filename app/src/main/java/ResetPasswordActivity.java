package com.example.memoryrush;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    private EditText emailEditText;
    private Button resetPasswordButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        // Initialisiere Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Verknüpfe die UI-Komponenten
        emailEditText = findViewById(R.id.editTextEmailReset);
        resetPasswordButton = findViewById(R.id.buttonResetPassword);

        // Setze den Listener für den Button, um die Passwort-Reset-E-Mail zu senden
        resetPasswordButton.setOnClickListener(view -> {
            // Extrahiere die E-Mail aus dem Textfeld
            String email = emailEditText.getText().toString().trim();

            // Überprüfe, ob das E-Mail-Feld leer ist
            if (email.isEmpty()) {
                Toast.makeText(ResetPasswordActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                return;
            }

            // Senden einer Anforderung zum Zurücksetzen des Passworts
            mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            // Erfolgsmeldung und Schließen der Activity
                            Toast.makeText(ResetPasswordActivity.this, "Reset password instructions have been sent to your email.", Toast.LENGTH_LONG).show();
                            finish();  // Schließen der Activity nach erfolgreicher Operation
                        } else {
                            // Fehlermeldung, wenn das Senden fehlschlägt
                            Toast.makeText(ResetPasswordActivity.this, "Failed to send reset email!", Toast.LENGTH_LONG).show();
                        }
                    });
        });
    }
}
