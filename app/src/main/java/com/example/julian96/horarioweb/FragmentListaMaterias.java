package com.example.julian96.horarioweb;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FragmentListaMaterias.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FragmentListaMaterias#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentListaMaterias extends Fragment implements Response.Listener<JSONObject>,Response.ErrorListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    //iniciado de variables
    Calendar cal = Calendar.getInstance();
    int dia = cal.get(Calendar.DAY_OF_WEEK);
    int hora = cal.get(Calendar.HOUR_OF_DAY);
    TextView etiDia;

    ArrayList<Materias> listaMaterias, listaMateriasFiltrada;
    RecyclerView recyclerMaterias;

    RequestQueue request;
    JsonObjectRequest jsonObjectRequest;

    Activity activity;
    IComunicarFragments interfaceComunicador;

    public FragmentListaMaterias() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentListaMaterias.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentListaMaterias newInstance(String param1, String param2) {
        FragmentListaMaterias fragment = new FragmentListaMaterias();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_lista_materias, container, false);

        etiDia = (TextView) view.findViewById(R.id.etiDia);
        switch (dia){
            case 2:
                etiDia.setText("Lunes");
                break;
            case 3:
                etiDia.setText("Martes");
                break;
            case 4:
                etiDia.setText("Miércoles");
                break;
            case 5:
                etiDia.setText("Jueves");
                break;
            case 6:
                etiDia.setText("Viernes");
                break;
            default:
                etiDia.setText("No hay clase");
        }

        listaMaterias = new ArrayList<>();
        listaMateriasFiltrada = new ArrayList<>();
        recyclerMaterias = (RecyclerView) view.findViewById(R.id.rv);
        recyclerMaterias.setLayoutManager(new LinearLayoutManager(getContext()));

        request = Volley.newRequestQueue(getContext());

        cargarMaterias();

        /*llenarMaterias();
        if(dia != 1 && dia != 7){
            for(int j=0; j<listaMaterias.size(); j++){
                if(listaMaterias.get(j).getDia() == dia){
                    listaMateriasFiltrada.add(listaMaterias.get(j));
                }
            }
            Adapter adaptador = new Adapter(listaMateriasFiltrada);
            recyclerMaterias.setAdapter(adaptador);
            adaptador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),"Materia: "+listaMateriasFiltrada.get(recyclerMaterias.getChildAdapterPosition(v)).getMateria(),Toast.LENGTH_SHORT).show();

                    interfaceComunicador.enviarMateria(listaMateriasFiltrada.get(recyclerMaterias.getChildAdapterPosition(v)));
                }
            });

        }*/

        return view;
    }

    private void cargarMaterias() {
        String url = "http://192.168.43.194:5002/assignatures";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        request.add(jsonObjectRequest);

    }

    @Override
    public void onResponse(JSONObject response) {
        Materias m = null;
        JSONArray json = response.optJSONArray("objectoMandadoParaQueSeaMostradoElArrayDeMateriasEnLaAppParaPasarLaMateriaDeDesarrolloMovil");
        try {

            for(int i=0; i<json.length(); i++){
                m = new Materias();
                JSONObject jsonObject = null;
                jsonObject = json.getJSONObject(i);

                m.setMateria(jsonObject.optString("name"));
                m.setAula(jsonObject.optString("classroom"));
                m.setHoraInicio(Integer.parseInt(jsonObject.optString("hourstart")));
                m.setHoraFin(Integer.parseInt(jsonObject.optString("hourend")));
                m.setProfe(jsonObject.optString("teacher"));
                m.setDia(jsonObject.getInt("day"));
                listaMaterias.add(m);
            }

            if(dia != 1 && dia != 7){
                for(int j=0; j<listaMaterias.size(); j++){
                    if(listaMaterias.get(j).getDia() == dia){
                        listaMateriasFiltrada.add(listaMaterias.get(j));
                    }
                }
            }
            Adapter adaptador = new Adapter(listaMateriasFiltrada);
            recyclerMaterias.setAdapter(adaptador);
            adaptador.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(),"Materia: "+listaMateriasFiltrada.get(recyclerMaterias.getChildAdapterPosition(v)).getMateria(),Toast.LENGTH_SHORT).show();

                    interfaceComunicador.enviarMateria(listaMateriasFiltrada.get(recyclerMaterias.getChildAdapterPosition(v)));
                }
            });

        }
        catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(),"no jala",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getContext(),"No se pueden obtener los datos     "+error.toString(),Toast.LENGTH_SHORT).show();
    }

    /*private void llenarMaterias() {
        listaMaterias.add(new Materias("Desarrollo móvil", "Lab. Comp. 2", 8,10, "Hugo",2));
        listaMaterias.add(new Materias("Cómputo forense", "Lab. Comp. 5",10,12,"Mata",2));
        listaMaterias.add(new Materias("Programación web", "Lab. Comp. 2", 10,12, "James",3));
        listaMaterias.add(new Materias("Cómputo forense", "Lab. Redes",12,14,"Mata",3));
        listaMaterias.add(new Materias("Desarrollo web", "Lab. Comp. 2",12,13, "James",4));
        listaMaterias.add(new Materias("Admon. Redes", "Cisco", 13,15, "Uribe",4));
        listaMaterias.add(new Materias("Taller de Inv. 2", "A6", 15,17, "Franco",4));
        listaMaterias.add(new Materias("Prog. Lógica y Func.", "Lab. Comp. 4",17,17, "Abundis",4));
        listaMaterias.add(new Materias("Desarrollo móvil", "Lab. Comp. 2",8,9, "Hugo",5));
        listaMaterias.add(new Materias("Programación web", "Lab. Comp. 2", 9,11, "James",5));
        listaMaterias.add(new Materias("Cómputo forense","Cisco",11,12,"Mata",5));
        listaMaterias.add(new Materias("Admon. Redes","Cisco",12,14,"Uribe",5));
        listaMaterias.add(new Materias("Desarrollo móvil","Lab. Comp. 2",13,15,"Hugo",6));
        listaMaterias.add(new Materias("Taller de Inv. 2","A6",15,17,"Franco",6));
        listaMaterias.add(new Materias("Prog. Lógica y func.","Lab. Comp. 4",17,19,"Abundis",6));
    }*/

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof Activity){
            this.activity = (Activity) context;
            interfaceComunicador = (IComunicarFragments) this.activity;
        }
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
