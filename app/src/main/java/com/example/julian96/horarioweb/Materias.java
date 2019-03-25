package com.example.julian96.horarioweb;

import java.io.Serializable;

public class Materias implements Serializable {
    private String materia;
    private String aula;
    private int horaInicio;
    private int horaFin;
    private String profe;
    private int dia;

    /*public Materias(String materia, String aula, int horaInicio, int horaFin, String profe, int dia){
        this.materia = materia;
        this.aula = aula;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.profe = profe;
        this.dia = dia;
    }*/

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public int getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(int horaInicio) {
        this.horaInicio = horaInicio;
    }

    public int getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(int horaFin) {
        this.horaFin = horaFin;
    }

    public String getProfe() {
        return profe;
    }

    public void setProfe(String profe) {
        this.profe = profe;
    }

    public int getDia(){
        return dia;
    }

    public void setDia(int dia){
        this.dia = dia;
    }
}
