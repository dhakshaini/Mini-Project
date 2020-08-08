package com.e.certificate;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class Cseupload extends AppCompatActivity {
    Button btn1,btn2;
    URLCLASS file;
    EditText name,roll;
    private static final int PICK_FILE=1;
    private DatabaseReference ref,ref2;
    private FirebaseAuth firebaseAuth;
    String f,dept;
    TextView show;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cseupload);
        dept=getIntent().getStringExtra("dept");
        btn1=(Button)findViewById(R.id.button8);
        btn2=(Button)findViewById(R.id.button9);
        roll=(EditText) findViewById(R.id.editText11);


        firebaseAuth=FirebaseAuth.getInstance();
        name=(EditText)findViewById(R.id.editText3);
        dialog=new ProgressDialog(Cseupload.this);
        ref= FirebaseDatabase.getInstance().getReference().child("FILES").child(dept);
        ref2= FirebaseDatabase.getInstance().getReference().child("Students");
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),Demo.class);
                startActivity(i);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                f=roll.getText().toString();
                Fileupload(view);
            }
        });

        dialog = new ProgressDialog(Cseupload.this);
        // Setting up message in Progress dialog.
       // dialog.setMessage("Uploading Images");

        // Showing progress dialog.
        //dialog.show();
    }
    public void Fileupload(View view){
        Intent intent=new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        startActivityForResult(intent,PICK_FILE);
        // dialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_FILE){
            if(resultCode==RESULT_OK){
                assert data != null;
                Uri fileuri=data.getData();
                StorageReference Folder=FirebaseStorage.getInstance().getReference().child("Files").child(dept);
                assert fileuri != null;
                final StorageReference file_name=Folder.child("file"+fileuri.getLastPathSegment());
                file_name.putFile(fileuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        final int[] flag = {0};
                        file_name.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                // HashMap<String ,String> hashMap=new HashMap<>();
                                // hashMap.put("url",String.valueOf(uri));
                                String url=String.valueOf(uri);
                                String file_name=name.getText().toString().trim();
                                file=new URLCLASS(file_name,url);
                                ref.push().setValue(file);
                                //Toast.makeText(getApplicationContext(),"1st push",Toast.LENGTH_LONG).show();
                                ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for(DataSnapshot ds:dataSnapshot.getChildren()){
                                            String rollno= Objects.requireNonNull(ds.child("age").getValue()).toString();
                                            String  key= ds.getKey();
                                            if(rollno.equals(f)){
                                                assert key != null;
                                                Toast.makeText(getApplicationContext(),""+key,Toast.LENGTH_LONG).show();
                                                ref2.child(key).child("certificate").push().setValue(file);
                                                flag[0] =1;
                                                break;
                                            }if (flag[0] ==1){
                                                Toast.makeText(getApplicationContext(),"Student verified",Toast.LENGTH_LONG).show();
                                            break;
                                            }

                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                                Toast.makeText(getApplicationContext(),"File Uploaded", Toast.LENGTH_SHORT).show();
                                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(i);
                            }
                        });
                    }
                });
            }
        }
    }
}
