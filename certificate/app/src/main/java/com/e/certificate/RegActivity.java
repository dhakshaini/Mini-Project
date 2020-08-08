package com.e.certificate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import android.text.TextUtils;
import android.util.Patterns;
import com.google.firebase.database.FirebaseDatabase;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class RegActivity extends AppCompatActivity {
    EditText name,mail,pass,cpass,age,phone,dpt;
    TextView tv;
    Button btn;
    private FirebaseAuth firebaseAuth;
    public RegActivity() {
    }
    public boolean isValid(String s) {
        return (!s.trim().isEmpty());
    }
    public boolean passIsValid(String s) {
        return (s.length() >= 5 && isValid(s));
    }
    public int C2I(String st) {
        return Integer.parseInt(st);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        name=(EditText)findViewById(R.id.editText6);
        mail=(EditText)findViewById(R.id.email);
        pass=(EditText)findViewById(R.id.editText7);
        cpass=(EditText)findViewById(R.id.editText4);
        phone=(EditText)findViewById(R.id.editText5);
        age=(EditText)findViewById(R.id.editText8);
        dpt=(EditText)findViewById(R.id.editText9);
        tv=(TextView)findViewById(R.id.textViews);
        btn=(Button)findViewById(R.id.button6);
        firebaseAuth=FirebaseAuth.getInstance();
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user,email,password,pas,ages,cnt,dpts;
                user =name.getText().toString().trim();
                email =mail.getText().toString().trim();
                password =pass.getText().toString().trim();
                pas =cpass.getText().toString().trim();
                cnt=phone.getText().toString().trim();
                ages =age.getText().toString().trim();
                dpts=dpt.getText().toString().trim();
                if (TextUtils.isEmpty(name.getText().toString().trim()) &&
                        TextUtils.isEmpty(mail.getText().toString().trim()) && TextUtils.isEmpty(pass.getText().toString().trim())
                        && TextUtils.isEmpty(cpass.getText().toString().trim()) && TextUtils.isEmpty(phone.getText().toString().trim()) && TextUtils.isEmpty(age.getText().toString().trim())&& TextUtils.isEmpty(dpt.getText().toString().trim())) {
                    name.setError("Required");
                    mail.setError("Required");
                    pass.setError("Required");
                    cpass.setError("Required");
                    phone.setError("Required");
                    age.setError("Required");
                    dpt.setError("Required");
                } else if (TextUtils.isEmpty(name.getText().toString().trim())) {
                    name.setError("Enter Username");
                }  else if (!emailValidator(mail.getText().toString())) {
                    mail.setError("Please Enter Valid Email Address");
                } else if (TextUtils.isEmpty(pass.getText().toString().trim())) {
                    pass.setError("Required");
                } else if (TextUtils.isEmpty(age.getText().toString().trim())) {
                    age.setError("Enter Username");
                } else if (!ageValidator(age.getText().toString())) {
                    age.setError("Age must be Between 0 to 100");
                } else if (TextUtils.isEmpty(phone.getText().toString().trim())) {
                    phone.setError("Required");
                } else if (!phValidator(phone.getText().toString())) {
                    phone.setError("Please Enter a Valid Mobile Number");
                }else if (TextUtils.isEmpty(dpt.getText().toString().trim())) {
                    phone.setError("Required");
                }
                else {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful())
                            {
                                userdata();
                                Toast.makeText(getApplicationContext(), "Registered successfully!!", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                            } else
                            {
                                Toast.makeText(getApplicationContext(), "Enter the valid details???", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
            public void userdata(){
                String user,email,password,pas,ages,cnt,dpts;
                user =name.getText().toString().trim();
                email =mail.getText().toString().trim();
                password =pass.getText().toString().trim();
                pas =cpass.getText().toString().trim();
                cnt=phone.getText().toString().trim();
                ages =age.getText().toString().trim();
                dpts=dpt.getText().toString().trim();
                FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                DatabaseReference register = firebaseDatabase.getReference().child("users").child("Register").child(firebaseAuth.getUid());
                Register registers= new Register(user,email,password,cnt,ages,dpts);
                register.setValue(registers);
            }
            public boolean emailValidator(String email)
            {
                return(!TextUtils.isEmpty(email)&& Patterns.EMAIL_ADDRESS.matcher(email).matches());
            }
            private boolean phValidator(String phone){
                Pattern pattern;
                Matcher matcher;
                final String PHONE_PATTERN="^[2-9]{2}[0-9]{8}$";
                pattern=Pattern.compile(PHONE_PATTERN);
                matcher=pattern.matcher(phone);
                return matcher.matches();
            }
            private boolean ageValidator(String age){
                Pattern pattern;
                Matcher matcher;
                final String PHONE_PATTERN="^[0-9]{2}$";
                pattern=Pattern.compile(PHONE_PATTERN);
                matcher=pattern.matcher(age);
                return matcher.matches();
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });

    }
}
