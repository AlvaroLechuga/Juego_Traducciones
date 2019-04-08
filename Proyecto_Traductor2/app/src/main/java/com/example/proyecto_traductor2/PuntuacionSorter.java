package com.example.proyecto_traductor2;

import java.util.Comparator;

public class PuntuacionSorter implements Comparator<Palabra> {
    @Override
    public int compare(Palabra o1, Palabra o2) {
        return o1.getAciertos()-o2.getAciertos();
    }
}
