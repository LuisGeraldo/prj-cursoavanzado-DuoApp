package net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.utilidades;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import net.azurewebsites.luiscontrerasdev.duokapp.R;
import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.modelo.Publicacion;

import java.io.File;
import java.io.FileOutputStream;

public class Util {
    public Typeface fuente;
    public Context context;

    public Util(){

    }

    public Util(Context context){
        this.context = context;
    }

    //Metodos para poner la fuente
    public Typeface MakeFont(Context context, String ruta){
       fuente = Typeface.createFromAsset(context.getAssets(),ruta);
       return fuente;
    }

    //Poner fotos redondeadas
    public void MakeImageRound(String urlFoto, ImageView imageView){
        RequestOptions options = new RequestOptions().centerCrop().placeholder(R.drawable.ajaxloader);

        Glide.with(context).asBitmap().load(urlFoto).apply(options).into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    public void ShareImage(ImageView imgView, Publicacion item){
        imgView.buildDrawingCache();
        Bitmap bitmap = imgView.getDrawingCache();

        try {

            File file = new File(context.getCacheDir(), bitmap + ".png");
            FileOutputStream fOut = null;
            fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            final Intent share = new Intent(android.content.Intent.ACTION_SEND);

            share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            share.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            share.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
            share.putExtra(Intent.EXTRA_TEXT,  "\n "+ item.getDescripcion() + "\n " + item.getUserUbicacion());
            share.setType("image/");

            context.startActivity(Intent.createChooser(share, "Compartir en redes sociales"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
