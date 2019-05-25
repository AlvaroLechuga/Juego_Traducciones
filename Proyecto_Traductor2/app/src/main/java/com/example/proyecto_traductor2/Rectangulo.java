package com.example.proyecto_traductor2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Rectangulo {

    Float x;
    Float y;
    Float ancho;
    Float alto;
    Rect rect;

    public Rectangulo() {
    }

    public Rectangulo(Float x, Float y, Float ancho, Float alto) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        rect = new Rect();
    }

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }

    public Float getAncho() {
        return ancho;
    }

    public void setAncho(Float ancho) {
        this.ancho = ancho;
    }

    public Float getAlto() {
        return alto;
    }

    public void setAlto(Float alto) {
        this.alto = alto;
    }

    public Rect getRect(){
        return rect;
    }

    public void dibujar(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(Color.YELLOW);
        p.setStyle(Paint.Style.FILL);
        rect.set(Math.round(x), Math.round(y), Math.round(ancho+x), Math.round(alto+y));
        canvas.drawRect(rect,p);
    }

    public boolean comprobarSiTocoDentro(Float x, Float y) {
        boolean devolver= false;

        if(x>getX() && x< getX()+getAncho()
                && y>getY() && y<getY()+getAlto()){

            devolver= true;

        }

        return devolver;
    }

    public void actualizar(Float x, Float y) {
        setX(getX()+x);
        setY(getY()+y);
    }

}
