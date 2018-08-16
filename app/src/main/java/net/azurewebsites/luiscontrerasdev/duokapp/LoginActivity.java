package net.azurewebsites.luiscontrerasdev.duokapp;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private TextView tituloText;
    private Typeface tituloFuente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tituloText = (TextView) findViewById(R.id.text_app_name);


        tituloFuente = Typeface.createFromAsset(getAssets(), "fuentes/fuenteA.ttf");

        tituloText.setTypeface(tituloFuente);


    }
}
