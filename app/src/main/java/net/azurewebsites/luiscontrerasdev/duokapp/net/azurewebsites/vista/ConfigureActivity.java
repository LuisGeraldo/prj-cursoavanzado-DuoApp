package net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.vista;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.StorageReference;
import com.myhexaville.smartimagepicker.ImagePicker;

import net.azurewebsites.luiscontrerasdev.duokapp.R;
import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.referencedatabase.FirebaseUserAuth;
import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.referencedatabase.ReferenceClass;
import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.utilidades.Util;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

import static objetos.Constantes.SELECT_PICTURE;


public class ConfigureActivity extends AppCompatActivity {

    @BindView(R.id.foto_perfil)
    ImageView fotoPerfil;

    @BindView(R.id.user_name)
    EditText userNameText;

    @BindView(R.id.user_email)
    EditText userEmailText;

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    Util util;
    ReferenceClass reference;
    FirebaseUserAuth firebaseUserAuth;
    ProgressDialog progressDialog;
    ImagePicker imagePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        util = new Util(this);
        reference = new ReferenceClass();
        firebaseUserAuth = new FirebaseUserAuth();

        progressDialog = new ProgressDialog(this);
        getSupportActionBar().setTitle(firebaseUserAuth.getUser().getDisplayName());
        util.MakeImageRound(String.valueOf(firebaseUserAuth.getUser().getPhotoUrl()), fotoPerfil);

        userNameText.setText(firebaseUserAuth.getUser().getDisplayName());
        userEmailText.setText(firebaseUserAuth.getUser().getEmail());

        imagePicker = new ImagePicker(this, null, imageUri -> { });

        fab.setOnClickListener(view -> {
            imagePicker.choosePicture(true);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.handleActivityResult(resultCode,requestCode, data);

        File file = imagePicker.getImageFile();

        if (requestCode == SELECT_PICTURE && data != null){
            subirFoto(data.getData());
        }else if(resultCode != RESULT_CANCELED) {
            subirFoto(Uri.fromFile(file));
        }
    }

    public void subirFoto(Uri uri){
        progressDialog.setTitle(R.string.subiendo);
        progressDialog.setMessage("Subiendo foto a DuoApp");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StorageReference filePath =  reference.getStorageReference().child(firebaseUserAuth.getUser().getEmail()+"/fotos").child(uri.getLastPathSegment());
        filePath.putFile(uri).addOnSuccessListener(taskSnapshot -> {
            progressDialog.dismiss();

            Uri descagarFoto = taskSnapshot.getDownloadUrl();

            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest
                    .Builder()
                    .setPhotoUri(descagarFoto)
                    .build();

            firebaseUserAuth.getUser().updateProfile(profileUpdates);
        });

        firebaseUserAuth.getUser().reload();
    }
}
