package com.example.proyectoindividual1;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//Éste fragmento es únicamente un fragmento en blanco para que sustituya el fragmento de datos_leyendo cuando es necesario.
public class fragmentoblanco extends Fragment {

    public fragmentoblanco() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fragmentobalnco, container, false);
    }
}