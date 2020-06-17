package com.sanjaysgangwar.politics;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Login extends AppCompatActivity {
    EditText phoneNumber;
    Button verify;
    String genCode;
    FirebaseAuth auth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks callback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(Login.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            genCode = s;
        }

        @Override
        public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
            super.onCodeAutoRetrievalTimeOut(s);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        verify = findViewById(R.id.verifyBt);
        phoneNumber = findViewById(R.id.phoneNumber);
        auth = FirebaseAuth.getInstance();
        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = "+91" + phoneNumber.getText().toString().trim();
                if (number.isEmpty() || number.length() > 13 || number.length() < 13) {
                } else {
                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(Login.this);
                    View view = getLayoutInflater().inflate(R.layout.get_otp, null);
                    builder.setView(view);
                    AlertDialog alertDialog = builder.create();
                    // alertDialog.setCancelable(false);

                    //send the code then open the dialog
                    VerifyPhone(number);
                    alertDialog.show();
                    final EditText otpNumber = view.findViewById(R.id.OTPet);
                    Button otpBt = view.findViewById(R.id.OTPbt);


                    otpBt.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String otp = otpNumber.getText().toString().trim();
                            if (otp.isEmpty()) {

                            } else {
                                verifyCode(otp);
                            }
                        }
                    });


                }


            }
        });
    }

    private void VerifyPhone(String number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(number, 60, TimeUnit.SECONDS, Login.this, callback);
    }

    private void verifyCode(String otp) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(genCode, otp);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Intent intent = new Intent(Login.this, Home.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}