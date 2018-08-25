package net.azurewebsites.luiscontrerasdev.duokapp;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class InicioActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TextView appName;
    private Typeface tituloFuente;
    private ProgressDialog progressDialog;
    private StorageReference storageReference;
    private static final int GALERIAINTENT = 1;
    private TextView userNameView;
    private TextView userEmailView;
    FragmentManager fragment;
    String userEmail;
    String userId;
    String urlFoto;
    String userName;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        progressDialog = new ProgressDialog(this);
        storageReference = FirebaseStorage.getInstance().getReference();
        tituloFuente = Typeface.createFromAsset(getAssets(), "fuentes/fuenteA.ttf");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView titleTool = (TextView) findViewById(R.id.toolbar_title);

        userNameView = (TextView) findViewById(R.id.user_name);
        userEmailView = (TextView) findViewById(R.id.user_email);

        titleTool.setTypeface(tituloFuente);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragment = getSupportFragmentManager();
        fragment.beginTransaction().replace(R.id.contenedor, new ListFotosFragment()).commit();

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

//        this.userEmail = bundle.getString("userEmail");
//        this.userId = bundle.getString("idUser");

//        userEmailView.setText(userEmail);

//        userNameView.setText("ll");
//        userEmailView.setText("lrafaelcg@gmail.com");


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.inicio, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.nav_Lis){
            fragment.beginTransaction().replace(R.id.contenedor, new ListFotosFragment()).commit();

        }else if (id == R.id.nav_camera) {

            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/");
            startActivityForResult(intent, GALERIAINTENT);

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GALERIAINTENT && resultCode == RESULT_OK){
            progressDialog.setTitle("Subiendo...");
            progressDialog.setMessage("Subiendo foto a DuoApp");
            progressDialog.setCancelable(false);
            progressDialog.show();

            Uri uri = data.getData();


            StorageReference filePath =  storageReference.child(userEmail+"/fotos").child(uri.getLastPathSegment());
            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();

                    Uri descagarFoto = taskSnapshot.getDownloadUrl();
                    urlFoto =  String.valueOf(descagarFoto);

                    new SubirArchivos(InicioActivity.this, userId, "Luis Rafael", userEmail, "iiii",urlFoto);

                    Toast.makeText( InicioActivity.this, "Se subio correctamente al storage", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

