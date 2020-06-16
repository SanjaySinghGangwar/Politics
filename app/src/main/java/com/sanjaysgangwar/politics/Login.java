package com.sanjaysgangwar.politics;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class Login extends AppCompatActivity {
    EditText phoneNumber;
    Button verify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        verify = findViewById(R.id.verifyBt);
        phoneNumber = findViewById(R.id.phoneNumber);

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Login.this);
                View view = getLayoutInflater().inflate(R.layout.terms_and_conditions, null);
                builder.setView(view);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });
    }
}