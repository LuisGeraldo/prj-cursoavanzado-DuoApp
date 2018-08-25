package net.azurewebsites.luiscontrerasdev.duokapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.referencedatabase.DatabaseReferenceClass;
import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.adapter.PublicacionAdapter;
import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.modelo.Publicacion;

import java.util.LinkedList;

import static objetos.BaseReferencias.PUBLICACION;


public class ListFotosFragment extends Fragment {
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager Manager;
    DatabaseReferenceClass reference;
    private LinkedList items;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        reference = new DatabaseReferenceClass();
        items = new LinkedList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        MostrarLista();
        return inflater.inflate(R.layout.fragment_list_fotos, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        recycler = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        adapter = new PublicacionAdapter(getContext(), items);
        //recycler.setHasFixedSize(true);
        Manager = new LinearLayoutManager(getContext());
        recycler.setLayoutManager(Manager);
        recycler.setAdapter(adapter);
    }


    public void MostrarLista(){

        reference.getReference().child(PUBLICACION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(final DataSnapshot datasnapshot : dataSnapshot.getChildren()) {

                    reference.getReference().child(PUBLICACION).child(datasnapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                            Publicacion publicacionPojo = datasnapshot.getValue(Publicacion.class);

                            String userId = publicacionPojo.getUserId();
                            String userName = publicacionPojo.getUserName();
                            String userEmail = publicacionPojo.getUserEmail();
                            String userFotoPerfil = publicacionPojo.getUserFotoPerfil();
                            String userUbicacion = publicacionPojo.getUserUbicacion();
                            String urlFoto = publicacionPojo.getUrlFoto();
                            String descripcion = publicacionPojo.getDescripcion();


                            items.addFirst(new Publicacion(userId, userName, userEmail, userFotoPerfil, userUbicacion, urlFoto, descripcion));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
