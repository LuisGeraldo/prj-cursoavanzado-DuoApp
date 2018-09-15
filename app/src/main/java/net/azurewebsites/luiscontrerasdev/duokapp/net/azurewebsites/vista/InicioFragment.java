package net.azurewebsites.luiscontrerasdev.duokapp.net.azurewebsites.vista;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.azurewebsites.luiscontrerasdev.duokapp.R;


public class InicioFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_inicio, container, false);
    }
}
