package com.example.julian96.horarioweb;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> implements View.OnClickListener{
    Calendar cal = Calendar.getInstance();
    int hora = cal.get(Calendar.HOUR_OF_DAY);
    int dia = cal.get(Calendar.DAY_OF_WEEK);
    private View.OnClickListener listener; //Escuchador para el evento click

    ArrayList<Materias> lista;
    public Adapter(ArrayList<Materias> lista){
        this.lista = lista;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item,null,false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        //condicion para que pinte el item correspondiente a la hora actual
        if(hora >= lista.get(i).getHoraInicio() && hora < lista.get(i).getHoraFin()){
            viewHolder.itemView.setBackgroundColor(Color.GREEN);
        }
        viewHolder.etiMateria.setText(lista.get(i).getMateria());
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    //Método que se llamara desde el mainActivity
    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView etiMateria;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            etiMateria = (TextView) itemView.findViewById(R.id.materia);
        }
    }
}
