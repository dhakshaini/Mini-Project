package com.e.certificate;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Header;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.*;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
public class Loan extends AppCompatActivity {
    private static final int STORAGE_CODE=1000;
    private Button Scan;
    TextView Name,Pid,academic,dept,aca,he,pass;
    ImageView image;
    TextView bon1,thiss,roll,free,gov,fac,princi,hod,tech,sted;
    EditText dat;
    Button btn1;
    //qr code scanner object
    private IntentIntegrator qrScan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan);
        Scan = (Button) findViewById(R.id.buttonScan);
        btn1=(Button)findViewById(R.id.button);
        dat=(EditText) findViewById(R.id.editText);
        Name = (TextView) findViewById(R.id.name);
        Pid = (TextView) findViewById(R.id.textView9);
        dept= (TextView) findViewById(R.id.textView17);
        academic = (TextView) findViewById(R.id.textView10);
        sted= (TextView) findViewById(R.id.textView8);
        bon1=(TextView)findViewById(R.id.textView3);
        thiss=(TextView)findViewById(R.id.textView5);
        aca=(TextView)findViewById(R.id.textView18);
        roll=(TextView)findViewById(R.id.textView7);
        free=(TextView)findViewById(R.id.textView12);
        gov=(TextView)findViewById(R.id.textView14);
        tech=(TextView)findViewById(R.id.textView15);
        fac=(TextView)findViewById(R.id.textView2);
        hod=(TextView)findViewById(R.id.textView13);
        princi=(TextView)findViewById(R.id.textView11);
        he=(TextView)findViewById(R.id.textView15);
        pass=(TextView)findViewById(R.id.textView16);
        //image=(ImageView)findViewById(R.id.imageView);
        //bonafide=(TextView)findViewById(R.id.textView3);
        //intializing scan object
        qrScan = new IntentIntegrator(this);
        Scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //initiating the qr code scan
                qrScan.initiateScan();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED) {
                        String [] permissions={Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permissions,STORAGE_CODE);

                    } else {
                        savePdf();
                    }
                }
                else
                {                    Toast.makeText(getApplicationContext(),"befor save pdf",Toast.LENGTH_LONG).show();

                    savePdf();
                    Toast.makeText(getApplicationContext(),"after save pdf",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    private void savePdf()
    {
        Document mdoc=new Document();
        //String filename = "sample/src/main/res/drawable/logo";
        String filename= new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
        String ww=Name.getText().toString();
        String yy=ww+filename;

        String filepath= getExternalCacheDir() + "/"+yy + " .pdf";
        try {
            PdfWriter.getInstance(mdoc,new FileOutputStream(filepath));
            mdoc.open();
            String bonaf=bon1.getText().toString();
            String de=dept.getText().toString();
            String aaa=aca.getText().toString();
            String sss=sted.getText().toString();
            String g=gov.getText().toString();
            String te=tech.getText().toString();
            String fa=fac.getText().toString();
            String h=hod.getText().toString();
            String p=princi.getText().toString();
            String first=thiss.getText().toString();
            String name=Name.getText().toString();
            String id=Pid.getText().toString();
            String reg=roll.getText().toString();
            //String yea=year.getText().toString();
            String aca1=academic.getText().toString();
            String bon=free.getText().toString();
            String dd=dat.getText().toString();
            String heee=he.getText().toString();
            String ppas=pass.getText().toString();
            String f="                                                                    "+"Date:"+dd+"\n"+"\n"+"                                         "+g+" "+"\n"+"\n"+"                                                            "+ bonaf+" \n" +"\n"+first+" "+name+" "+reg+" "+id+" "+sss+" "+de+" "+aaa+" "+aca1+" "+bon+" "+heee+" "+ppas+"\n"+"\n"+"                 "+p+"                                      "+h+"                                      "+fa;

            //Image img = Image("arvind-rai.png");
            mdoc.addAuthor("saran sowmi");
            mdoc.add(new Paragraph(f));
            // mdoc.add(iv);






            //mdoc.add(new Paragraph(id));
            mdoc.close();
            Toast.makeText(this,yy+".pdf\n is saved to"+filepath,Toast.LENGTH_SHORT).show();
            Intent i=new Intent(getApplicationContext(),Cseupload.class);
            i.putExtra("dept",de);
            startActivity(i);
        }catch (Exception e)
        {
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode)
        {
            case STORAGE_CODE:{
                if(grantResults.length > 0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED)
                {
                    savePdf();
                }
                else
                {
                    Toast.makeText(this,"PERMISSION DENIED",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());
                    //setting values to textviews
                    Name.setText(obj.getString("name"));
                    Pid.setText(obj.getString("reg"));
                    academic.setText(obj.getString("year"));
                    dept.setText(obj.getString("dept"));
                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
