package net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.vista;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import net.azurewebsites.luiscontrerasdev.duokapp.R;
import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.utilidades.Util;


public class Perfil {;

    final Dialog dialog;
    ImageView fotoPerfil;
    TextView nombreUsuario;
    Util util;
    EditText userNameText;
    EditText userEmailText;
    TextView verPublicaciones;
    FragmentManager fragment;

    public Perfil(Context context, String url, String userName, String userEmail){
        util = new Util(context);

        dialog = new Dialog(context);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.perfil_activity);

        nombreUsuario = (TextView) dialog.findViewById(R.id.text_notificacion);

        fotoPerfil = (ImageView) dialog.findViewById(R.id.foto_perfil);

        userNameText =  (EditText) dialog.findViewById(R.id.user_name);
        userEmailText = (EditText) dialog.findViewById(R.id.user_email);
        verPublicaciones = (TextView) dialog.findViewById(R.id.ver_publicaciones);

        userNameText.setText(userName);
        userEmailText.setText(userEmail);

        util.MakeImageRound(url,fotoPerfil);

        verPublicaciones.setOnClickListener((v) ->{
        });

        dialog.show();
    }
}
