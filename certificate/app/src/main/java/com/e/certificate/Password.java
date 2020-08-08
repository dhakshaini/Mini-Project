package com.e.certificate;

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
public class Password extends AppCompatActivity {
    Button btn;
    EditText email, phn;
    Button sms;
    String mobileNumber;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
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
                    Toast.makeText(Password.this, "Please enter all details", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseAuth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                //sendmessage();
                                Toast.makeText(Password.this, "Password reset email sent!", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(new Intent(Password.this, MainActivity.class));
                            } else {
                                Toast.makeText(Password.this, "Error in sending password reset email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }/*
    public void sendmessage(){
        //Toast.makeText(getApplicationContext(),"message",Toast.LENGTH_LONG).show();
        int permissioncheck= ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECEIVE_SMS);
        int permissioncheck1= ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS);
        if (permissioncheck== PackageManager.PERMISSION_GRANTED && permissioncheck1==PackageManager.PERMISSION_GRANTED){
            // Toast.makeText(getApplicationContext(),"message2",Toast.LENGTH_LONG).show();

            Mymessage();
        }
        else{
            // Toast.makeText(getApplicationContext(),"message3",Toast.LENGTH_LONG).show();

            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECEIVE_SMS},0);
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.SEND_SMS},1);
            Mymessage();
            //ActivityCompat.requestPermissions(getApplicationContext(),new String[]{Manifest.permission.SEND_SMS},0);
        }
    }*/
    /*private boolean phValidator(String phone) {
        Pattern pattern;
        Matcher matcher;
        final String PHONE_PATTERN = "^[0-9]{2}[0-9]{8}$";
        pattern = Pattern.compile(PHONE_PATTERN);
        matcher = pattern.matcher(phone);
        return matcher.matches();
    }/*
    private void Mymessage(){
        //Toast.makeText(getApplicationContext(),"Mymessage",Toast.LENGTH_LONG).show();
        String phnno=phn.getText().toString().trim();
        String msg="PASSWORD RESET MAIL SENT!!";
        /*if(!phn.getText().toString().equals("")) {
            //Toast.makeText(getApplicationContext(),"message4",Toast.LENGTH_LONG).show();
            SmsManager smsManager = SmsManager.getDefault();
            //smsManager.
            smsManager.sendTextMessage(phnno, null, msg, null, null);
        }else {
            Toast.makeText(getApplicationContext(),"Enter valid phone number",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case  0:
                if(grantResults.length>=0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Mymessage();
                }else {
                    Toast.makeText(getApplicationContext(),"You don't have permission",Toast.LENGTH_LONG).show();
                }

        }
    }*/
}
