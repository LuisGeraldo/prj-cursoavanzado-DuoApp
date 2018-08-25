package net.azurewebsites.luiscontrerasdev.duokapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import static objetos.BaseReferencias.PUBLICACION;


public class SubirFotosFragment extends Fragment implements View.OnClickListener {

    private ImageView sFoto;
    private TextView descripcionText;
    private TextView ubicacionText;
    private EditText descripcionEdit;
    private Button btnUbicacion;
    private Button btnCompartir;
    DatabaseReference reference;
    String urlFoto;
    String userId;
    String userEmail;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        reference = FirebaseDatabase.getInstance().getReference();
        urlFoto = getArguments().getString("urlFoto");
        userId = getArguments().getString("userId");
        userEmail = getArguments().getString("userEmail");

        return inflater.inflate(R.layout.fragment_subir_fotos, container, false);
    }




    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        sFoto = view.findViewById(R.id.imagen_arriba);
        descripcionText = view.findViewById(R.id.text_descripcion);
        ubicacionText = view.findViewById(R.id.text_ubicacion);
        btnCompartir = view.findViewById(R.id.btn_compartir);
        btnUbicacion = view.findViewById(R.id.btn_ubicacion);
        descripcionEdit = view.findViewById(R.id.edit_text_descripcion);

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ajaxloader);

        Glide.with(this)
                .load(urlFoto)
                .apply(options)
                .into(sFoto);

        btnCompartir.setOnClickListener(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void onClick(View v) {

        if(v==btnCompartir){

            try {
                String descripcion = descripcionEdit.getText().toString();
                Map<String, Object> datosuser = new HashMap<>();
                datosuser.put("Foto", urlFoto);
                datosuser.put("Descripcion", descripcion);
                datosuser.put("Ubicacion", "Custom");
                reference.child(PUBLICACION).push().setValue(datosuser);
                new CuadroDialogo(getContext(), "VOLVER AL INICIO", "Foto publicada correctamente", "send.json");

            }catch (Exception ex){


            }
        }
    }
}
