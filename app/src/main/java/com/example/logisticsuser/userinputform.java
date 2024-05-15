package com.example.logisticsuser;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class userinputform extends AppCompatActivity {

    EditText from,to,name,number,product,date;
    AppCompatButton upload;
    ImageView imgupbtn;

    Uri imguri;

    DatabaseReference db = FirebaseDatabase.getInstance().getReference("USERSJOB");
    StorageReference sb = FirebaseStorage.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_userinputform);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        from = findViewById(R.id.from);
        to = findViewById(R.id.to);
        name = findViewById(R.id.name);
        number = findViewById(R.id.number);
        product = findViewById(R.id.product);
        date = findViewById(R.id.date);
        upload = findViewById(R.id.upload);
        imgupbtn = findViewById(R.id.imgchoose);


        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult o) {
                        if(o.getResultCode() == Activity.RESULT_OK){
                            Intent data = o.getData();
                            imguri = data.getData();
                            imgupbtn.setImageURI(imguri);
                        }
                        else {
                            Toast.makeText(userinputform.this, "No image is selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );


        imgupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photopicker = new Intent();
                photopicker.setAction(Intent.ACTION_GET_CONTENT);
                photopicker.setType("image/*");
                activityResultLauncher.launch(photopicker);
            }
        });


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String froms,tos,names,numbers,products,dates;
                froms = from.getText().toString();
                tos = to.getText().toString();
                names = name.getText().toString();
                numbers = number.getText().toString();
                products = product.getText().toString();
                dates = date.getText().toString();


                if(names.isEmpty() || froms.isEmpty() || tos.isEmpty() || numbers.isEmpty() || products.isEmpty() || dates.isEmpty()){
                    Toast.makeText(userinputform.this, "Please Enter all information", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(numbers.length()!=10){
                    Toast.makeText(userinputform.this, "Please enter valid phone number", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    if(imguri!=null){
                        uploadtofirebase(imguri,names,froms,tos,numbers,products,dates);
                    }
                    else{
                        Toast.makeText(userinputform.this, "Please select image", Toast.LENGTH_SHORT).show();

                    }
                }


            }
        });


    }

    public void uploadtofirebase(Uri uri,String names,String froms, String tos, String numbers,String products, String dates){
        StorageReference imgref = sb.child(froms+tos+names+numbers+products);
        imgref.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imgref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        dataclass dc = new dataclass(uri.toString(),froms,tos,names,numbers,products,dates);
                        db.child(froms+tos+names+numbers+products).setValue(dc);
                        Toast.makeText(userinputform.this, "Succesfully uploaded", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(userinputform.this,homepage.class);
                        startActivity(i);
                        finish();

                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(userinputform.this, "Uploading failure", Toast.LENGTH_SHORT).show();

            }
        });


    }
}