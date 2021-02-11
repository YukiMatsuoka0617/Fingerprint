package com.example.fingerprint;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button button;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setButton();
    }

    void setButton() {
        button = findViewById(R.id.button);
        button.setOnClickListener(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void onClick(View view) {
        MakeFingerprintDialog();
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    BiometricPrompt.AuthenticationCallback getAuthenticationCallback() {
        BiometricPrompt.AuthenticationCallback authenticationCallback =
                new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                Toast.makeText(getApplicationContext(),
                        "Authentication error: ", Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                Toast.makeText(getApplicationContext(),
                        "Authentication help: ", Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationFailed() {
                Toast.makeText(getApplicationContext(),
                        "Authentication failed: ", Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                Toast.makeText(getApplicationContext(),
                        "Authentication succeeded: ", Toast.LENGTH_SHORT)
                        .show();
            }
        };
        return authenticationCallback;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    void MakeFingerprintDialog() {
        CancellationSignal cancellationSignal = new CancellationSignal();
        Executor mExecutor = ContextCompat.getMainExecutor(this);

        BiometricPrompt.Builder builder = new BiometricPrompt.Builder(this);
        builder.setTitle("Title");
        builder.setSubtitle("Subtitle");
        builder.setDescription("Description");
        builder.setConfirmationRequired(false);
        builder.setNegativeButton("Negative", mExecutor, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        BiometricPrompt prompt = builder.build();

        prompt.authenticate(cancellationSignal, mExecutor, getAuthenticationCallback());

    }
}