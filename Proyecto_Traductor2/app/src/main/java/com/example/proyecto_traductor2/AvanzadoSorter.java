package com.example.proyecto_traductor2;

import java.util.Comparator;

public class AvanzadoSorter implements Comparator<Palabra> {
    @Override
    public int compare(Palabra o1, Palabra o2) {
        int resultado = o1.getAciertos()-o2.getAciertos();

        if(resultado != 0){
            return resultado;
        }else{
            return o1.getFechaIntroduccion().compareTo(o2.getFechaIntroduccion());
        }
    }
}
