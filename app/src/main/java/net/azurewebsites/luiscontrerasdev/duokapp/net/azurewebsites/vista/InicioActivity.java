package net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.vista;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.storage.StorageReference;
import com.myhexaville.smartimagepicker.ImagePicker;

import net.azurewebsites.luiscontrerasdev.duokapp.R;
import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.acceso.LoginActivity;
import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.referencedatabase.FirebaseUserAuth;
import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.referencedatabase.ReferenceClass;
import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.utilidades.Util;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static objetos.BaseReferencias.GALERIAINTENT;
import static objetos.BaseReferencias.MY_PERMISSIONS_REQUEST_CAMERA;
import static objetos.BaseReferencias.MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE;
import static objetos.BaseReferencias.PETICION_PERMISO_LOCALIZACION;
import static objetos.BaseReferencias.REQUEST_LOCATION;
import static objetos.BaseReferencias.SELECT_PICTURE;


public class InicioActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.toolbar_title)
    TextView titleTool;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;

    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private TextView correoText;
    private TextView userText;
    private ImageView fotoPerfil;
    private ProgressDialog progressDialog;
    private ImagePicker imagePicker;

    FragmentManager fragment;
    String userEmail;
    String userId;
    String urlFoto;
    String userName;
    String userFoto;

    ListFotosFragment fragmentListFoto;
    FirebaseUserAuth firebaseUserAuth;
    ReferenceClass reference;
    Util util;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        ButterKnife.bind(this);
        firebaseUserAuth = new FirebaseUserAuth();
        reference = new ReferenceClass();
        fragmentListFoto = new ListFotosFragment();
        progressDialog = new ProgressDialog(this);
        util = new Util(this);

        progressDialog.setTitle(R.string.subiendo);
        progressDialog.setMessage("Subiendo foto a DuoApp");
        progressDialog.setCancelable(false);

        titleTool.setTypeface(util.MakeFont(this, "fuentes/fuenteA.ttf"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        View view = navigationView.getHeaderView(0);
        correoText = (TextView) view.findViewById(R.id.user_email);
        fotoPerfil = (ImageView) view.findViewById(R.id.foto_perfil);
        userText = (TextView) view.findViewById(R.id.user_name);

        InformacionUsuario();

        fragment = getSupportFragmentManager();
        fragmentListFoto.MostrarPublicaciones();
        fragment.beginTransaction().replace(R.id.contenedor, fragmentListFoto).commit();

        fotoPerfil.setOnClickListener((v) -> {
            Intent intent = new Intent(this, ConfigureActivity.class);
            startActivity(intent);
        });

        imagePicker = new ImagePicker(this, null, imageUri -> { });

        Permisos();
    }

    @Override
    public void onBackPressed() {
        firebaseUserAuth.getUser().reload();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.inicio, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, ConfigureActivity.class));
            return true;

        }else if(id == R.id.action_signout){
            firebaseUserAuth.getAuth().signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.nav_Lis){
            fragmentListFoto.MostrarPublicaciones();
            fragment.beginTransaction().replace(R.id.contenedor, fragmentListFoto).commit();
        }else if (id == R.id.nav_camera) {
           imagePicker.choosePicture(true);
        } else if (id == R.id.nav_gallery) {
            fragmentListFoto.MostrarPublicaciones(firebaseUserAuth.getUser().getEmail());
            fragment.beginTransaction().replace(R.id.contenedor, fragmentListFoto).commit();
        } else if (id == R.id.nav_manage) {
            startActivity(new Intent(this, ConfigureActivity.class));
        } else if (id == R.id.nav_share) {
            startActivity(new Intent(this, MapsActivity.class));
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void InformacionUsuario(){
        this.userEmail = firebaseUserAuth.getAuth().getCurrentUser().getEmail();
        this.userId = firebaseUserAuth.getAuth().getCurrentUser().getProviderId();
        this.userName = firebaseUserAuth.getAuth().getCurrentUser().getDisplayName();
        this.userFoto = String.valueOf(firebaseUserAuth.getAuth().getCurrentUser().getPhotoUrl());

        correoText.setText(userEmail);
        userText.setText(userName);
        util.MakeImageRound(userFoto, fotoPerfil);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        imagePicker.handleActivityResult(resultCode,requestCode, data);

        File file = imagePicker.getImageFile();

        if (requestCode == SELECT_PICTURE && data != null) {
            subirFoto(data.getData());
        }

        if(resultCode != RESULT_CANCELED && resultCode == RESULT_OK) {
            subirFoto(Uri.fromFile(file));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imagePicker.handlePermission(requestCode, grantResults);
    }

    public void subirFoto(Uri uri){
        progressDialog.show();
        StorageReference filePath =  reference.getStorageReference().child(userEmail+"/fotos").child(uri.getLastPathSegment());
        filePath.putFile(uri).addOnSuccessListener(taskSnapshot -> {
            progressDialog.dismiss();

            Uri descagarFoto = taskSnapshot.getDownloadUrl();
            urlFoto =  String.valueOf(descagarFoto);
            new SubirArchivos(InicioActivity.this, userId, userName, userEmail, userFoto, urlFoto);
            new MapsActivity();
            Toast.makeText( InicioActivity.this, R.string.subio_correctamente, Toast.LENGTH_SHORT).show();
        });
    }

    public void Permisos() {

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }
        }


        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
            }

            if(ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(InicioActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                  return;
            }
        }
    }
}

