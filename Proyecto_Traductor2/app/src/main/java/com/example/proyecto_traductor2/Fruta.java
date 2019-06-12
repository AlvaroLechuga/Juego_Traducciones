package com.example.proyecto_traductor2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

public class Fruta {

    private static final int BMP_COLUMS = 1;
    private static final int BMP_ROWS = 1;
    private int x;
    private int y;
    private int xSpeed = 7;
    private int ySpeed = 7;
    private Juego2 gameView;
    private Bitmap bmp;
    private int currentFrame = 0;
    private int width;
    private int height;
    private  String palabra;

    //direction = 0 up, 1 left, 2 down, 3 right
    //animation = 3 up, 1 left, 0 down, 2 right
    int [] DIRECTION_TO_ANIMATION_MAP = {3, 1, 0, 2};


    public Fruta(Juego2 gameView, Bitmap bmp, int X, int Y) {
        this.gameView = gameView;
        this.bmp = bmp;
        this.width = bmp.getWidth() / BMP_COLUMS;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.x = X;
        this.y = Y;
    }

    private void update(){

    }

    public String getPalabra() {
        return palabra;
    }

    public void setPalabra(String palabra) {
        this.palabra = palabra;
    }

    public void onDraw(Canvas canvas){
        update();

        int srcX = currentFrame * width;
        int srcY = 0 * height;
        Rect src  = new Rect(srcX, srcY,  srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x+width, y+height);
        canvas.drawBitmap(bmp, src, dst, null);
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean comprobarDentro(Sprite sprite) {
        Boolean colision = false;
        if (x + width  > sprite.getX() &&
                x < sprite.getX() + sprite.getWidth() &&
                y + height > sprite.getY() &&
                y < sprite.getY() + sprite.getHeight()) {

            colision = true;
        }
        return colision;
    }
}
