package net.azurewebsites.luiscontrerasdev.duokapp;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class CuadroDialogo implements View.OnClickListener {
    private Button aceptar;
    private TextView textNotificacion;
    private LottieAnimationView animationView;
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

        aceptar.setOnClickListener(this);
        aceptar.setText(textButton);
        textNotificacion.setText(textNotific);
        animationView.setAnimation(animation);

        dialog.show();
    }


    @Override
    public void onClick(View v) {
        if(v == aceptar){
            dialog.dismiss();
        }
    }
}
