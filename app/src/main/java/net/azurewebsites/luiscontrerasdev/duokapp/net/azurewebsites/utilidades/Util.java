package net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.utilidades;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.view.View;

public class Util {
    public Typeface fuente;
    public Context context;
    public AssetManager assets;
    public String ruta;


    public Util(){

    }

    public void setAssets(AssetManager assets) {
        this.assets = assets;
    }

    public void setRutaAndAsset(AssetManager assets, String ruta){
        this.assets = assets;
        this.ruta = ruta;
    }


    //Metodos para poner la fuente con diferentes parametros

    public Typeface MakeFont(AssetManager assets, String ruta){
       fuente = Typeface.createFromAsset(assets,ruta);
        return fuente;
    }

    public Typeface MakeFont(String ruta){
        fuente = Typeface.createFromAsset(assets,ruta);
        return fuente;
    }

    public Typeface MakeFont(){
        fuente = Typeface.createFromAsset(assets,ruta);
        return fuente;
    }
}
