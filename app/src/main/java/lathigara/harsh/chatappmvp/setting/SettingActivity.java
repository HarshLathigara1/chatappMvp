package lathigara.harsh.chatappmvp.setting;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;
import lathigara.harsh.chatappmvp.R;
import lathigara.harsh.chatappmvp.root.App;

public class SettingActivity extends AppCompatActivity implements SettingActivityMVP.View {

    private static final int GALLERY_PICK = 1;
    private Button btnChangeImage, btnChangeStatus;
    String uid;
    private StorageReference storageReference;
    DatabaseReference databaseReference;

    private TextView txtName, txtStatus;
   private EditText editText;

    private EditText texttoDialog;


    public SettingActivity() {

    }


    @Inject
    SettingActivityMVP.Presenter presenter;
    CircleImageView circleImageView;
    Glide glide;
    FirebaseUser user;
    FirebaseStorage firebaseStorage;


    String status;
    Button btnSaveChanges;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);

        ((App) getApplication()).getComponent().injectTo(this);
        txtName = findViewById(R.id.setting_name);
        txtStatus = findViewById(R.id.setting_status);
                circleImageView = findViewById(R.id.circleImageView);
        user = FirebaseAuth.getInstance().getCurrentUser();
        uid = user.getUid();
        storageReference = firebaseStorage.getInstance().getReference().child("Images").child(uid);
        texttoDialog = findViewById(R.id.edtchangeStatus);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("USERS").child(uid);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String image = dataSnapshot.child("image").getValue().toString();
                 status = dataSnapshot.child("status").getValue().toString();
                String name = dataSnapshot.child("displayName").getValue().toString();
                if (!image.equals("default")) {
                    glide.with(getApplicationContext()).load(image).into(circleImageView);
                }


                txtName.setText(name);
                txtStatus.setText(status);
                //texttoDialog.setText(status);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btnChangeImage = findViewById(R.id.button_image_change_setting);
        btnChangeStatus = findViewById(R.id.button_status_change_setting);
        btnChangeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.imageChangeButtonClicked(SettingActivity.this);
            }
        });

        btnChangeStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        SettingActivity.this);
                View view1 = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_layout, null);
                builder.setCancelable(true);

               // TextView builderText = builder.f
                // builder.setTitle("Opss!!");
                  editText = view1.findViewById(R.id.edtchangeStatus);
                btnSaveChanges = view1.findViewById(R.id.btnSaveChanges);

                btnSaveChanges.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        databaseReference.child("status").setValue(editText.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"changed",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
                  editText.setTextColor(R.color.colorPrimaryDark);
                  editText.setText(status);

                builder.setView(view1);
               // builder.setPositiveButton(new On)
                // builder.setMessage("You Don't have anough coins to withdraw. ");
                // builder.setMessage("Please read the Withdraw rules.");
                // builder.setInverseBackgroundForced(true);

                builder.create().show();

            }
        });
    }

    @Override
    public void imageChanged() {
        Toast.makeText(this, "Image Changed", Toast.LENGTH_LONG).show();

    }

    @Override
    public void statusUpdated() {
        Toast.makeText(this, "Status Updated", Toast.LENGTH_LONG).show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_PICK && resultCode == RESULT_OK) {

            Uri imageUri = data.getData();
            String uri = imageUri.toString();
            // Glide.with(getApplicationContext()).load(imageUri).into(circleImageView);


            storageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    if (taskSnapshot != null) {
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String down = uri.toString();
                                databaseReference.child("image").setValue(down).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getApplicationContext(), "Sucess", Toast.LENGTH_LONG).show();

                                        }


                                    }
                                });

                            }
                        });
                    }

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle any errors
                        }
                    });


        }
    }
}
