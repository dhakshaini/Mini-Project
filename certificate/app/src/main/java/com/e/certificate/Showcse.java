package com.e.certificate;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.annotation.NonNull;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;

public class Showcse extends AppCompatActivity {
    ListView listView;
    FirebaseDatabase database;
    DatabaseReference ref;
    FirebaseStorage firebaseStorage;
    StorageReference store,ref1;
    private FirebaseAuth firebaseAuth;
    List<URLCLASS> list;
    URLCLASS file;
    ArrayAdapter<URLCLASS> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showcse);
        listView = (ListView) findViewById(R.id.listView);
        database = FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        list = new ArrayList<>();
        file= new URLCLASS();
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Students").child(FirebaseAuth.getInstance().getUid()).child("certificate");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    file= ds.getValue(URLCLASS.class);
                    list.add(file);
                }
                String[] uploads = new String[list.size()];
                for (int i = 0; i < uploads.length; i++) {
                    uploads[i] = list.get(i).getName();
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, uploads);
                listView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                URLCLASS urlclass=list.get(i);
                download(urlclass.getUrl());
            }
        });
    }
    public  void  download(String url){
        store=firebaseStorage.getInstance().getReference();
        ref1=store.child("Files").child("FILES");
        downloadfiles(getApplicationContext(),"file1",".pdf", Environment.DIRECTORY_DOWNLOADS,url);

    }
    public void downloadfiles(Context context,String filename, String fileextension,String destination,String url) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, destination, filename + fileextension);
        downloadManager.enqueue(request);

    }
}
