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
import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.modelo.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{
    private Context context;
    private List<User> items;

    public  UserAdapter(Context context, List<User> items){
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_inicio, parent, false);
        UserAdapter.UserViewHolder holder = new UserAdapter.UserViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView nombre;
        public TextView correo;
        public ImageView fotoPerfil;


        public UserViewHolder(View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.user_name);
            fotoPerfil = (ImageView) itemView.findViewById(R.id.user_foto_principal);
        }
    }
}
