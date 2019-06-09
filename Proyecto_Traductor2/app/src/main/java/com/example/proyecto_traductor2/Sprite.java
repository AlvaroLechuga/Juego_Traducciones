package com.example.proyecto_traductor2;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

public class Sprite {

    private static final int BMP_COLUMS = 3;
    private static final int BMP_ROWS = 4;
    private int x;
    private int y;
    private int xSpeed = 7;
    private int ySpeed = 7;
    private Juego2 gameView;
    private Bitmap bmp;
    private int currentFrame = 0;
    private int width;
    private int height;
    private Boolean parado = true;
    private int direccion = 3;

    //direction = 0 up, 1 left, 2 down, 3 right
    //animation = 3 up, 1 left, 0 down, 2 right
    int [] DIRECTION_TO_ANIMATION_MAP = {3, 1, 0, 2};


    public Sprite(Juego2 gameView, Bitmap bmp, int X, int Y) {
        this.gameView = gameView;
        this.bmp = bmp;
        this.width = bmp.getWidth() / BMP_COLUMS;
        this.height = bmp.getHeight() / BMP_ROWS;
        this.x = X;
        this.y = Y;
    }

    private void update(){

        if(!parado) {
            if (direccion == 3) {
                xSpeed = 0;
                ySpeed = -10;
                x = x + xSpeed;
                y = y + ySpeed;
            } else if (direccion == 0) {
                xSpeed = 0;
                ySpeed = 10;
                x = x + xSpeed;
                y = y + ySpeed;
            } else if (direccion == 1) {
                xSpeed = -30;
                ySpeed = 0;
                x = x + xSpeed;
                y = y + ySpeed;
            } else if (direccion == 2) {
                xSpeed = 30;
                ySpeed = 0;
                x = x + xSpeed;
                y = y + ySpeed;
            }
        }

        if (x>gameView.getWidth()-width-xSpeed || x+xSpeed<0){
            xSpeed=-xSpeed;
        }

        if(y>gameView.getHeight()-height-ySpeed || y+ySpeed<0){
            ySpeed=-ySpeed;
        }

        if(!parado)
            currentFrame=++currentFrame%BMP_COLUMS;
        else
            currentFrame=1;
    }

    public void onDraw(Canvas canvas){
        update();

        int srcX = currentFrame * width;
        int srcY = getAnimationRow() * height;
        Rect src  = new Rect(srcX, srcY,  srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x+width, y+height);
        canvas.drawBitmap(bmp, src, dst, null);
    }

    private int getAnimationRow() {
        double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);
        int direction = (int) Math.round(dirDouble) % BMP_ROWS;
        return DIRECTION_TO_ANIMATION_MAP[direction];
    }

    public Boolean getParado() {
        return parado;
    }

    public void setParado(Boolean parado) {
        this.parado = parado;
    }

    public int getDireccion() {
        return direccion;
    }

    public void setDireccion(int direccion) {
        this.direccion = direccion;
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
}
