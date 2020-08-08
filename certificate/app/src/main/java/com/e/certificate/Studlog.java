package com.e.certificate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class Studlog extends AppCompatActivity {
    Button btn,t;
    TextView tv1,tv2,tv3;
    EditText mail,pass;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studlog);
        mail=(EditText)findViewById(R.id.editTexts);
        pass=(EditText)findViewById(R.id.editText2);
        btn=(Button)findViewById(R.id.button5);
        tv1=(TextView)findViewById(R.id.textView4);
        tv2=(TextView)findViewById(R.id.textView6);
        //tv3=(TextView)findViewById(R.id.textView16);
        //t=(Button)findViewById(R.id.button10);
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser userfb1=firebaseAuth.getCurrentUser();
        progressDialog=new ProgressDialog(this );
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String users, passwords;
                users = mail.getText().toString();
                passwords = pass.getText().toString();
                progressDialog.setMessage("Wait for a moment");
                firebaseAuth.signInWithEmailAndPassword(users, passwords).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(getApplicationContext(), Showcse.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), "LOGIN FAILED", Toast.LENGTH_LONG).show();
                        }
                    }

                });
            }
        });

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent j=new Intent(getApplicationContext(),Studreg.class);
                startActivity(j);
            }
        });
        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k=new Intent(getApplicationContext(),Studpass.class);
                startActivity(k);
            }
        });/*
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ko=new Intent(getApplicationContext(),Pdf.class);
                startActivity(ko);
            }
        });*/

    }
}
