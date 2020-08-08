package com.e.certificate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Studpass extends AppCompatActivity {
    Button btn;
    EditText email, phn;
    Button sms;
    String mobileNumber;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studpass);
        btn = (Button) findViewById(R.id.button10);
        //sms=(Button)findViewById(R.id.button4);
        email = (EditText) findViewById(R.id.editText9);
        //phn = (EditText) findViewById(R.id.phn);
        firebaseAuth = FirebaseAuth.getInstance();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String useremail = email.getText().toString().trim();
                //String phno = phn.getText().toString().trim();
                if (useremail.equals("")) {
                    Toast.makeText(Studpass.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                //sendmessage();
                                Toast.makeText(Studpass.this, "Password reset email sent!", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(Studpass.this, MainActivity.class));
                            } else {
                                Toast.makeText(Studpass.this, "Error in sending password reset email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }/*
    private boolean phValidator(String phone) {
        Pattern pattern;
        Matcher matcher;
        final String PHONE_PATTERN = "^[0-9]{2}[0-9]{8}$";
        pattern = Pattern.compile(PHONE_PATTERN);
        matcher = pattern.matcher(phone);
        return matcher.matches();
    }*/

}
