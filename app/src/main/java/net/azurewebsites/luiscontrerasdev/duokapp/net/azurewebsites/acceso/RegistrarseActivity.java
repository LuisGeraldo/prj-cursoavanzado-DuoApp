package net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.acceso;


import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.UserProfileChangeRequest;

import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.referencedatabase.FirebaseUserAuth;
import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.vista.CuadroDialogo;
import net.azurewebsites.luiscontrerasdev.duokapp.R;
import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.utilidades.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrarseActivity extends AppCompatActivity {

    @BindView(R.id.text_app_name)
    TextView tituloActivity;

    @BindView(R.id.edit_text_correo)
    EditText emailEdit;

    @BindView(R.id.edit_text_name)
    EditText userNameEdit;

    @BindView(R.id.edit_text_password)
    EditText passworEdit;

    @BindView(R.id.btn_iniciar_sesion)
    Button btnIniciarSesion;

    @BindView(R.id.btn_registrase)
    Button btnRegistrarse;

    String email;
    String password;
    String userName;
    Util util;
    FirebaseUserAuth firebaseUserAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        ButterKnife.bind(this);
        util = new Util();
        firebaseUserAuth = new FirebaseUserAuth();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Registrando...");
        progressDialog.setMessage("Registrando usuario");
        progressDialog.setCancelable(false);

        tituloActivity.setTypeface(util.MakeFont(this, "fuentes/fuenteA.ttf"));

        btnIniciarSesion.setOnClickListener((v) ->{
            startActivity(new Intent(this, LoginActivity.class));
            onStop();
        });

        btnRegistrarse.setOnClickListener((v) ->{
            email = emailEdit.getText().toString();
            password = passworEdit.getText().toString();
            userName = userNameEdit.getText().toString();

            Pattern patron = Pattern.compile("[@ | - | & | # | _ | %]");
            Matcher matcher = patron.matcher(password);

            if(email.isEmpty() || password.isEmpty() || userName.isEmpty()){
                new CuadroDialogo(this, "VOLVER A INTENTAR", "Debes llenar todos los campos", "warn.json");
            }else if(matcher.find()){
                Registrarse(email, password, userName);
            }else{
                new CuadroDialogo(this, "VOLVER A INTENTAR", "Su password debe contener numeros y caracteres especiales como @ , -", "warn.json");
            }
        });
    }

    private void Registrarse(String email, String password, String userName) {
        progressDialog.show();

        firebaseUserAuth.getAuth().createUserWithEmailAndPassword(email, password).addOnCompleteListener((Task<AuthResult> task) -> {

            if (task.isSuccessful()) {
                progressDialog.dismiss();
                try{
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest
                            .Builder()
                            .setDisplayName(userName)
                            .build();

                    firebaseUserAuth.getUser().updateProfile(profileUpdates);

                    new CuadroDialogo(this, "INICIAR SESION", "Registro completado correctamente, verifique su correo", "send.json", "");

                    firebaseUserAuth.getAuth().getCurrentUser().sendEmailVerification().addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            Log.i("Correo", "Correo enviado");
                        }
                    });

                }catch (Exception ex){
                    Log.e("Error", "error"+ex);
                }

            }else{
                progressDialog.dismiss();
                new CuadroDialogo(this, "VOLVER A INTENTAR", "No se pudo registrar el usuario "+ task.getException().getMessage(), "warn.json");
            }
        });
    }
}
