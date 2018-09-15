package net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.vista;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

import net.azurewebsites.luiscontrerasdev.duokapp.R;
import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.acceso.LoginActivity;

public class CuadroDialogo{
    private Button aceptar;
    private Button aceptarIntent;
    private TextView textNotificacion;
    private LottieAnimationView animationView;
    Context context;
    final Dialog dialog;

    public CuadroDialogo(Context context, String textButton, String textNotific, String animation){
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog);

        aceptar = (Button)  dialog.findViewById(R.id.btn_aceptar);
        textNotificacion = (TextView) dialog.findViewById(R.id.text_notificacion);
        animationView = (LottieAnimationView) dialog.findViewById(R.id.animation);

        aceptar.setOnClickListener((v) ->{
            dialog.dismiss();
        });

        aceptar.setText(textButton);
        textNotificacion.setText(textNotific);
        animationView.setAnimation(animation);

        dialog.show();
    }

    public CuadroDialogo(Context context, String textButton, String textNotific, String animation, String className){
        this.context = context;
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog);

        aceptarIntent = (Button)  dialog.findViewById(R.id.btn_aceptar);
        textNotificacion = (TextView) dialog.findViewById(R.id.text_notificacion);
        animationView = (LottieAnimationView) dialog.findViewById(R.id.animation);

        aceptarIntent.setOnClickListener((v) ->{
            context.startActivity(new Intent(context, LoginActivity.class));
        });

        aceptarIntent.setText(textButton);
        textNotificacion.setText(textNotific);
        animationView.setAnimation(animation);

        dialog.show();
    }
}
