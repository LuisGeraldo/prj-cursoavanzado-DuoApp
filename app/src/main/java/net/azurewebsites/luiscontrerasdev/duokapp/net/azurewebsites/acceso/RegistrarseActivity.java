package net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.acceso;

import android.content.Context;
import android.content.Intent;
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
import net.azurewebsites.luiscontrerasdev.duokapp.R;
import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.referencedatabase.DatabaseReferenceClass;
import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.utilidades.Util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static objetos.BaseReferencias.PUBLICACION;

public class RegistrarseActivity extends AppCompatActivity {
    Util util;
    private TextView tituloActivity;
    private EditText emailEdit;
    private EditText userNameEdit;
    private EditText passworEdit;
    private Button btnIniciarSesion;
    private Button btnRegistrarse;
    private Context context;
    private DatabaseReferenceClass reference;
    FirebaseUser user;
    String email;
    String userName;
    String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrarse);

        util = new Util();
        reference = new DatabaseReferenceClass();
        context = this;
        tituloActivity = (TextView) findViewById(R.id.text_app_name);
        emailEdit = (EditText) findViewById(R.id.edit_text_correo);
        userNameEdit = (EditText) findViewById(R.id.user_name);
        passworEdit = (EditText) findViewById(R.id.edit_text_password);

        btnIniciarSesion = (Button) findViewById(R.id.btn_iniciar_sesion);
        btnRegistrarse = (Button) findViewById(R.id.btn_registrase);

        tituloActivity.setTypeface(util.MakeFont(getAssets(), "fuentes/fuenteA.ttf"));

        btnIniciarSesion.setOnClickListener((v) ->{
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        });

        btnRegistrarse.setOnClickListener((v) ->{
            email = emailEdit.getText().toString();
            userName = userNameEdit.getText().toString();
            password = passworEdit.getText().toString();

            Pattern patron = Pattern.compile("[@ | - | & | # | _ | %]");
            Matcher matcher = patron.matcher(password);

            if(email.equals("") || password.equals("")){
                new CuadroDialogo(context, "VOLVER A INTENTAR", "Debes llenar todos los campos", "warn.json");

            }else if(matcher.find()){
                Registrarse(email, password);
                InfoComplete(userName, email);

            }else{
                new CuadroDialogo(context, "VOLVER A INTENTAR", "Su password debe contener numeros y caracteres especiales como @ , -", "warn.json");
            }
        });
    }

    private void Registrarse(String email, String password) {
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    new CuadroDialogo(context, "INICIAR SESION", "Registro completado correctamente, verifique su correo", "send.json");
                    LimpiarCampos();

                    user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.i("CORREO", "Correo enviado");
                            }
                        }
                    });

                }else{
                    new CuadroDialogo(context, "VOLVER A INTENTAR", "No se pudo registrar el usuario "+ task.getException().getMessage(), "warn.json");
                }
            }
        });
    }


    public void InfoComplete(String userName, String email){
            try {

                Map<String, Object> datosuser = new HashMap<>();
                datosuser.put("Name", userName);
                datosuser.put("Email", email);
                reference.getReference().child(user.getUid()).push().setValue(datosuser);

            } catch (Exception ex) {
                Log.i("ERROR", "Error al registrar el usuario");
            }
    }

    public void LimpiarCampos(){
        emailEdit.setText("");
        userNameEdit.setText("");
        passworEdit.setText("");
    }
}
