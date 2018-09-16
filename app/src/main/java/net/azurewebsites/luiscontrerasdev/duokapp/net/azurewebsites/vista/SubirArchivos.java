package net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.vista;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import net.azurewebsites.luiscontrerasdev.duokapp.R;
import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.referencedatabase.ReferenceClass;
import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.ubicacion.Ubicacion;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static objetos.BaseReferencias.PUBLICACION;

public class SubirArchivos implements Ubicacion.LocationStringListener{

    ReferenceClass referenceClass;
    private ImageView sFoto;
    private TextView descripcionText;
    private TextView ubicacionText;
    private EditText descripcionEdit;
    private Button btnUbicacion;
    private Button btnCompartir;
    private String userId;
    private String userName;
    private String userEmail;
    private String userFotoPerfil;
    private String userUbicacion;
    private String urlFoto;
    private String descripcion;

    private Dialog dialog;
    public Context context;
    Ubicacion ubicacion;

    public SubirArchivos(){

    }


    public SubirArchivos(Context context, String userId, String userName, String userEmail, String userFotoPerfil, String urlFoto){
        referenceClass = new ReferenceClass();
        this.userId = userId;
        this.userName = userName;
        this.userEmail = userEmail;
        this.urlFoto = urlFoto;
        this.userFotoPerfil = userFotoPerfil;
        this.userUbicacion = "...";
        this.descripcion = "...";
        this.context = context;


        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.subir_archivos);


        sFoto =  (ImageView) dialog.findViewById(R.id.imagen_arriba);
        descripcionText = (TextView) dialog.findViewById(R.id.text_descripcion);
        ubicacionText = (TextView) dialog.findViewById(R.id.text_ubicacion);
        btnCompartir = (Button) dialog.findViewById(R.id.btn_compartir);
        btnUbicacion = (Button) dialog.findViewById(R.id.btn_ubicacion);
        descripcionEdit = (EditText) dialog.findViewById(R.id.edit_text_descripcion);

        ubicacion = new Ubicacion(this ,context);

        ubicacionText.setVisibility(View.INVISIBLE);

        RequestOptions options = new RequestOptions().centerCrop().placeholder(R.drawable.ajaxloader);

        Glide.with(dialog.getContext())
                .load(urlFoto)
                .apply(options)
                .into(sFoto);

        btnCompartir.setOnClickListener((v) ->{
            SubirPublicacion();
        });

        btnUbicacion.setOnClickListener((v) ->{
            userUbicacion = ubicacionText.getText().toString();
            Intent intent = new Intent(context, MapsActivity.class);
            context.startActivity(intent);
        });

        dialog.show();
    }

    public void SubirPublicacion(){
            try {
                descripcion = descripcionEdit.getText().toString();
                Map<String, Object> datosuser = new HashMap<>();

                datosuser.put("userId", userId);
                datosuser.put("userName", userName);
                datosuser.put("userEmail", userEmail);
                datosuser.put("userFotoPerfil", userFotoPerfil);
                datosuser.put("userUbicacion", userUbicacion);
                datosuser.put("urlFoto", urlFoto);
                datosuser.put("descripcion", descripcion);

                referenceClass.getDatabaseReference().child(PUBLICACION).push().setValue(datosuser);
                dialog.dismiss();

                new CuadroDialogo(dialog.getContext(), "ACEPTAR", "Foto publicada correctamente", "send.json");

            } catch (Exception ex) {

                new CuadroDialogo(dialog.getContext(), "VOLVER A INTENTAR", "La foto no se pudo publicar", "warn.json");
            }
        }

    @Override
    public void updateAddress(String address) {
        ubicacionText.setText(address);
    }
}

