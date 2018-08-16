package net.azurewebsites.luiscontrerasdev.duokapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import objetos.BaseReferencias;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText emailEdit;
    private EditText passwordEdit;
    private Button btnRegistrarse;
    private Button btnIniciarSesion;
    FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailEdit = (EditText) findViewById(R.id.edit_text_correo);
        passwordEdit = (EditText) findViewById(R.id.edit_text_password);
        btnIniciarSesion = (Button) findViewById(R.id.btn_iniciar_sesion);
        btnRegistrarse = (Button) findViewById(R.id.btn_registrase);

        btnRegistrarse.setOnClickListener(this);
        btnIniciarSesion.setOnClickListener(this);

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();

                if(user != null){
                    Log.i("Sesion","Sesion iniciada con el email "+ user.getEmail());

                }else{

                    Log.i("sesion","sesion cerrada");
                }
            }
        };
    }

    private void Registrarse(String email, String password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.i("REGISTRO", "Registro completado");

                }else{
                    Log.i("REGISTRO", "El registro no se completo");

                }
            }
        });
    }

    private void IniciarSesion(String email, String password) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Log.i("SESION", "Se ha iniciado sesion");

                }else{
                    Log.i("SESION", "No se pudo iniciar sesion" + task.getException().getMessage());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == btnIniciarSesion){
            String email = emailEdit.getText().toString();
            String password = passwordEdit.getText().toString();
            IniciarSesion(email, password);
        }

        if(v == btnRegistrarse){
            String email = emailEdit.getText().toString();
            String password = passwordEdit.getText().toString();
            Registrarse(email, password);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().removeAuthStateListener(authStateListener);
    }
}
