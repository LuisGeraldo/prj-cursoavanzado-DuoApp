package net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.adapter;

import android.content.Context;
import android.graphics.Bitmap;

import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import net.azurewebsites.luiscontrerasdev.duokapp.R;
import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.modelo.Publicacion;
import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.utilidades.Util;
import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.vista.ConfigureActivity;
import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.vista.Perfil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedList;

public class PublicacionAdapter extends RecyclerView.Adapter<PublicacionAdapter.PublicacionViewHolder> {
    private Context context;
    private LinkedList<Publicacion> items;
    Util util;

    public PublicacionAdapter(Context context, LinkedList<Publicacion> items) {
        this.context = context;
        this.items = items;
        util = new Util(context);
    }

    @NonNull
    @Override
    public PublicacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_inicio, parent, false);
        return new PublicacionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PublicacionViewHolder holder, int position) {
        Publicacion item = items.get(position);

        holder.txtViewNombre.setText(item.getUserName());
        holder.txtViewUbicacion.setText(item.getUserUbicacion());
        holder.txtViewDescripcion.setText(item.getDescripcion());

        RequestOptions fotoPublicacion = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.ajaxloader);

        Glide.with(context)
                .load(item.getUrlFoto())
                .apply(fotoPublicacion)
                .into(holder.imgViewFotoPublicacion);

        FotoPerfil(item, holder);

        holder.btnCompartirFoto.setOnClickListener((v) ->{
            util.ShareImage(holder.imgViewFotoPublicacion, item);
        });

        holder.imgViewFotoPerfil.setOnClickListener((v) -> {
          new Perfil(context, item.getUserFotoPerfil(), item.getUserName(), item.getUserEmail());
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class PublicacionViewHolder extends RecyclerView.ViewHolder  {
        public TextView txtViewNombre;
        public TextView txtViewUbicacion;
        public TextView txtViewDescripcion;
        public CheckBox likeCheckBox;
        public ImageView imgViewFotoPerfil;
        public ImageView imgViewFotoPublicacion;
        public ImageView btnCompartirFoto;

        public PublicacionViewHolder(View itemView) {
            super(itemView);
            txtViewNombre = (TextView) itemView.findViewById(R.id.user_name);
            txtViewUbicacion = (TextView) itemView.findViewById(R.id.ubicacion_user);
            txtViewDescripcion = (TextView) itemView.findViewById(R.id.descripcion_publicacion);
            likeCheckBox = (CheckBox) itemView.findViewById(R.id.like);
            imgViewFotoPerfil = (ImageView) itemView.findViewById(R.id.user_foto_principal);
            imgViewFotoPublicacion = (ImageView) itemView.findViewById(R.id.img_publicacion);
            btnCompartirFoto = (ImageView) itemView.findViewById(R.id.btn_config);
        }
    }

    public void FotoPerfil(Publicacion item, PublicacionViewHolder holder){
        RequestOptions options = new RequestOptions().centerCrop().placeholder(R.drawable.user);
        Glide.with(context).asBitmap().load(item.getUserFotoPerfil()).apply(options).into(new BitmapImageViewTarget(holder.imgViewFotoPerfil) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                holder.imgViewFotoPerfil.setImageDrawable(circularBitmapDrawable);
            }
        });
    }
}