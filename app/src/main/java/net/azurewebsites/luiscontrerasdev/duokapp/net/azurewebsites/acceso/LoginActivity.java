package net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.acceso;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.azurewebsites.luiscontrerasdev.duokapp.CuadroDialogo;
import net.azurewebsites.luiscontrerasdev.duokapp.InicioActivity;
import net.azurewebsites.luiscontrerasdev.duokapp.R;
import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.utilidades.Util;


public class LoginActivity extends AppCompatActivity{
    Util util;
    private Context context;
    private TextView tituloText;
    private EditText emailText;
    private EditText passwordText;
    private Button btnIniciarSesion;
    private Button btnRegistrarse;
    FirebaseAuth.AuthStateListener authStateListener;
    FirebaseUser user;
    String email;
    String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        util = new Util();
        context = this;

        tituloText = (TextView) findViewById(R.id.text_app_name);
        emailText = (EditText) findViewById(R.id.edit_text_correo);
        passwordText = (EditText) findViewById(R.id.edit_text_password);
        btnIniciarSesion = (Button) findViewById(R.id.btn_iniciar_sesion);
        btnRegistrarse = (Button) findViewById(R.id.btn_registrase);

        tituloText.setTypeface(util.MakeFont(getAssets(), "fuentes/fuenteA.ttf"));

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();

                if (user != null) {
                    Log.i("SESION", "Sesion iniciada con el email " + user.getEmail());

                } else {
                    Log.i("SESION", "sesion cerrada");
                }
            }
        };


        btnRegistrarse.setOnClickListener((v) ->{
            Intent intent = new Intent(this, RegistrarseActivity.class);
            startActivity(intent);
        });

        btnIniciarSesion.setOnClickListener((v) ->{
            email = emailText.getText().toString();
            password = passwordText.getText().toString();

                if(email.equals("") || password.equals("")){
                    new CuadroDialogo(context, "VOLVER A INTENTAR", "Debes llenar todos los campos", "warn.json");

                }else{
                    user.reload();
                    IniciarSesion(email, password);
                }
        });
    }

    private void IniciarSesion(final String email, String password) {
        user.reload();
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

               if(task.isSuccessful()){
                    if (user.isEmailVerified()) {
                        Intent intent = new Intent(context, InicioActivity.class);
                        intent.putExtra("userEmail", user.getEmail());
                        intent.putExtra("idUser", user.getUid());

                        startActivity(intent);
                       }else{
                        new CuadroDialogo(context, "VOLVER A INTENTAR", "Debe confirmar su cuenta, visite el correo electronico ", "warn.json");
                    }

               }else{
                   new CuadroDialogo(context, "VOLVER A INTENTAR", "No se pudo iniciar sesion "+ task.getException().getMessage(), "warn.json");
               }
            }
        });
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
