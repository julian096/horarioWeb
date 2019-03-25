package com.example.julian96.horarioweb;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements FragmentListaMaterias.OnFragmentInteractionListener,
        FragmentMateria.OnFragmentInteractionListener,
        IComunicarFragments{

    FragmentListaMaterias fragmentListaMaterias;
    FragmentMateria fragmentMateria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentListaMaterias = new FragmentListaMaterias();

        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragment,fragmentListaMaterias).commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void enviarMateria(Materias materia) {
        fragmentMateria = new FragmentMateria();
        Bundle bundle = new Bundle();
        bundle.putSerializable("objeto", materia);
        fragmentMateria.setArguments(bundle);

        //cargar el fragment en el activity
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragment,fragmentMateria).addToBackStack(null).commit();
    }
}
