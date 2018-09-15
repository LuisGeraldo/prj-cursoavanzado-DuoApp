package net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.acceso;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;

import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.referencedatabase.FirebaseUserAuth;
import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.vista.CuadroDialogo;
import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.vista.InicioActivity;
import net.azurewebsites.luiscontrerasdev.duokapp.R;
import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.utilidades.Util;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity{

    @BindView(R.id.text_app_name)
    TextView tituloText;

    @BindView(R.id.edit_text_correo)
    EditText emailText;

    @BindView(R.id.edit_text_password)
    EditText passwordText;

    @BindView(R.id.btn_iniciar_sesion)
    Button btnIniciarSesion;

    @BindView(R.id.btn_registrase)
    Button btnRegistrarse;

    Util util;
    FirebaseUserAuth firebaseUserAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);
        util = new Util();
        firebaseUserAuth = new FirebaseUserAuth();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Iniciando...");
        progressDialog.setMessage("Iniciando sesion");
        progressDialog.setCancelable(false);


        tituloText.setTypeface(util.MakeFont(this, "fuentes/fuenteA.ttf"));

        btnRegistrarse.setOnClickListener((v) ->{
            startActivity(new Intent(this, RegistrarseActivity.class));
            onStop();
        });

        btnIniciarSesion.setOnClickListener((v) ->{
            String email = emailText.getText().toString();
            String password = passwordText.getText().toString();

            if(email.isEmpty() || password.isEmpty()){
                new CuadroDialogo(this, "VOLVER A INTENTAR", "Debes llenar todos los campos", "warn.json");
            }else{
                IniciarSesion(email, password);
            }
        });
    }

    private void IniciarSesion(final String email, String password) {
        progressDialog.show();
        firebaseUserAuth.getAuth().signInWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                if(firebaseUserAuth.getUser().isEmailVerified()){
                    Intent intent = new Intent(this, InicioActivity.class);
                    startActivity(intent);
                }else{
                    progressDialog.dismiss();
                    new CuadroDialogo(this, "VOLVER A INTENTAR", "Debe confirmar su cuenta, visite el correo electronico ", "warn.json");
                }
            }else {
                    progressDialog.dismiss();
                    new CuadroDialogo(this, "VOLVER A INTENTAR", "No se pudo iniciar sesion "+ task.getException().getMessage(), "warn.json");

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseAuth.getInstance().addAuthStateListener(firebaseUserAuth.getAuthStateListener());

        if(firebaseUserAuth.getUser() != null && firebaseUserAuth.getUser().isEmailVerified()){
            startActivity(new Intent(this, InicioActivity.class));
            finish();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().addAuthStateListener(firebaseUserAuth.getAuthStateListener());
    }
}