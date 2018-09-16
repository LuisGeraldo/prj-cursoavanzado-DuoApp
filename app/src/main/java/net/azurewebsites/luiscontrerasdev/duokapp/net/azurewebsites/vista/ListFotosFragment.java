package net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.vista;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import net.azurewebsites.luiscontrerasdev.duokapp.R;
import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.referencedatabase.FirebaseUserAuth;
import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.referencedatabase.ReferenceClass;
import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.adapter.PublicacionAdapter;
import net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.modelo.Publicacion;
import java.util.LinkedList;

import static objetos.BaseReferencias.PUBLICACION;

public class ListFotosFragment extends Fragment {

    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    ReferenceClass reference;
    FirebaseUserAuth firebaseUserAuth;
    private LinkedList<Publicacion> items;
    TextView textException;

    public ListFotosFragment(){
        firebaseUserAuth = new FirebaseUserAuth();
        reference = new ReferenceClass();
        items = new LinkedList<>();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_list_fotos, container, false);
        recycler = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        textException = (TextView) v.findViewById(R.id.text_no_publicacion);
        LinearLayoutManager lnManager = new LinearLayoutManager(getActivity());
        lnManager.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(lnManager);

        adapter = new PublicacionAdapter(getActivity(), items);
        recycler.setAdapter(adapter);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

    }

    public void MostrarPublicaciones() {
        reference.getFirebaseDatabase().getReference(PUBLICACION).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                items.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Publicacion publicacion = snapshot.getValue(Publicacion.class);

                    items.addFirst(publicacion);
                }

                adapter.notifyDataSetChanged();

                if(items.size() == 0){
                    textException.setText(R.string.no_publicacion_home);
                }else{
                    textException.setText("");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void MostrarPublicaciones(String email) {

        reference.getFirebaseDatabase().getReference(PUBLICACION).orderByChild("userEmail").equalTo(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                items.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Publicacion publicacion = snapshot.getValue(Publicacion.class);
                    items.addFirst(publicacion);
                }

                adapter.notifyDataSetChanged();

                if(items.size() == 0){
                    textException.setText(R.string.no_publicacion);
                }else{
                    textException.setText("");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
