package com.example.proyecto_traductor2;

import android.os.Parcel;
import android.os.Parcelable;

public class Palabra implements Parcelable {

    private String palabraSP;
    private String palabraEN;
    private String fechaIntroduccion;
    private String fechaUltimoTest;
    private int aciertos;

    public Palabra() {
    }

    public Palabra(String palabraSP, String palabraEN, String fechaIntroduccion, String fechaUltimoTest, int aciertos) {
        this.palabraSP = palabraSP;
        this.palabraEN = palabraEN;
        this.fechaIntroduccion = fechaIntroduccion;
        this.fechaUltimoTest = fechaUltimoTest;
        this.aciertos = aciertos;
    }

    public String getPalabraSP() {
        return palabraSP;
    }

    public void setPalabraSP(String palabraSP) {
        this.palabraSP = palabraSP;
    }

    public String getPalabraEN() {
        return palabraEN;
    }

    public void setPalabraEN(String palabraEN) {
        this.palabraEN = palabraEN;
    }

    public String getFechaIntroduccion() {
        return fechaIntroduccion;
    }

    public void setFechaIntroduccion(String fechaIntroduccion) {
        this.fechaIntroduccion = fechaIntroduccion;
    }

    public String getFechaUltimoTest() {
        return fechaUltimoTest;
    }

    public void setFechaUltimoTest(String fechaUltimoTest) {
        this.fechaUltimoTest = fechaUltimoTest;
    }

    public int getAciertos() {
        return aciertos;
    }

    public void setAciertos(int aciertos) {
        this.aciertos = aciertos;
    }

    @Override
    public String toString() {
        return "Palabra{" +
                "palabraSP='" + palabraSP + '\'' +
                ", palabraEN='" + palabraEN + '\'' +
                ", fechaIntroduccion='" + fechaIntroduccion + '\'' +
                ", fechaUltimoTest='" + fechaUltimoTest + '\'' +
                ", aciertos=" + aciertos +
                '}';
    }

    protected Palabra(Parcel in) {
        palabraSP = in.readString();
        palabraEN = in.readString();
        fechaIntroduccion = in.readString();
        fechaUltimoTest = in.readString();
        aciertos = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(palabraSP);
        dest.writeString(palabraEN);
        dest.writeString(fechaIntroduccion);
        dest.writeString(fechaUltimoTest);
        dest.writeInt(aciertos);
    }

    @SuppressWarnings("unused")
    public static final Creator<Palabra> CREATOR = new Creator<Palabra>() {
        @Override
        public Palabra createFromParcel(Parcel in) {
            return new Palabra(in);
        }

        @Override
        public Palabra[] newArray(int size) {
            return new Palabra[size];
        }
    };
}
