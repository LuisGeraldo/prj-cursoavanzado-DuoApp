package net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import net.azurewebsites.luiscontrerasdev.duokapp.R;
import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.modelo.Publicacion;

import java.util.List;

public class PublicacionAdapter extends RecyclerView.Adapter<PublicacionAdapter.PublicacionViewHolder> {
    private Context context;
    private List<Publicacion> items;

    public PublicacionAdapter(Context context, List<Publicacion> items) {
        this.context = context;
        this.items = items;
    }

    public int getItemView(int position) {
        return 0;
    }

    @NonNull
    @Override
    public PublicacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_inicio, parent, false);
        PublicacionViewHolder holder = new PublicacionViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull PublicacionViewHolder holder, int position) {
        holder.nombre.setText(items.get(position).getUserName());
        holder.ubicacion.setText(items.get(position).getUserUbicacion());
        holder.descripcion.setText(items.get(position).getDescripcion());

        RequestOptions fotoPublicacion = new RequestOptions().centerCrop().placeholder(R.drawable.ajaxloader);
        RequestOptions fotoPerfil = new RequestOptions().centerCrop().placeholder(R.drawable.user);

        Glide.with(context).load(items.get(position).getUrlFoto()).apply(fotoPublicacion).into(holder.fotoPublicacion);
        Glide.with(context).load(items.get(position).getUserFotoPerfil()).apply(fotoPerfil).into(holder.fotoPerfil);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class PublicacionViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre;
        public TextView ubicacion;
        public TextView descripcion;
        public CheckBox likeCheckBox;
        public ImageView fotoPerfil;
        public ImageView fotoPublicacion;

        public PublicacionViewHolder(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.user_name);
            ubicacion = (TextView) itemView.findViewById(R.id.ubicacion_user);
            descripcion = (TextView) itemView.findViewById(R.id.descripcion_publicacion);
            likeCheckBox = (CheckBox) itemView.findViewById(R.id.like);
            fotoPerfil = (ImageView) itemView.findViewById(R.id.user_foto_principal);
            fotoPublicacion = (ImageView) itemView.findViewById(R.id.img_publicacion);
        }
    }
}