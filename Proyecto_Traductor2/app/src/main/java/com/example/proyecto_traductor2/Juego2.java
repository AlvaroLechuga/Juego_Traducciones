package com.example.proyecto_traductor2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class Juego2 extends SurfaceView implements SurfaceHolder.Callback {

    private HiloPantalla2 hiloPantalla;
    List<Sprite> sprites = new ArrayList<>();

    List<Bitmap> interfazUsuario = new ArrayList<>();

    Bitmap background;

    Sprite sprite;

    List<Rectangulo> ui = new ArrayList<>();

    int anchoPantalla;
    int altoPantalla;

    int score = 0;

    Paint paintTexto;

    String palabra;

    public Juego2(Context context) {
        super(context);
        setBackgroundColor(Color.BLACK);
        getHolder().addCallback(this);
        paintTexto = new Paint();

        palabra = generatePalabra();
    }

    @Override
    public void onDraw(final Canvas canvas){
        super.onDraw(canvas);

        canvas.drawBitmap(background, 0f, 0f, null);

        paintTexto.setColor(Color.WHITE);
        paintTexto.setTextSize(60);
        paintTexto.setTextAlign(Paint.Align.CENTER);

        for(Sprite sprite: sprites){
            sprite.onDraw(canvas);
        }

        for(Rectangulo rect: ui){
            rect.dibujar(canvas);
        }

        // Dibujar la interfaz
        canvas.drawBitmap(interfazUsuario.get(0), ui.get(0).getX(), ui.get(0).getY(), null);
        canvas.drawBitmap(interfazUsuario.get(1), ui.get(1).getX(), ui.get(1).getY(), null);
        canvas.drawBitmap(interfazUsuario.get(2), 850, 0, null);
        canvas.drawBitmap(interfazUsuario.get(3), 0, 0, null);

        // Puntuación
        canvas.drawText(String.valueOf(score), 75,80, paintTexto);

        // Palabra
        canvas.drawText(palabra, 1100,100, paintTexto);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        hiloPantalla = new HiloPantalla2(getHolder(), this);
        hiloPantalla.setRunning(true);
        hiloPantalla.start(); //Ejecuta el metodo run del hilo

        anchoPantalla=getWidth();
        altoPantalla=getHeight();

        background = BitmapFactory.decodeResource(getResources(), R.drawable.bg);
        background = background.createScaledBitmap(background, getWidth(), getHeight(), true);

        createSprites();
        createUI();
    }

    private void createSprites(){
        sprites.add(createSprites(R.drawable.prueba));
    }

    private void createUI() {
        ui.add(new Rectangulo(40f, 400f, 250f, 250f));
        ui.add(new Rectangulo(1900f, 400f, 250f, 250f));

        // Establecer interfaz imagen

        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.btnizq);
        bmp = bmp.createScaledBitmap(bmp, Math.round(ui.get(0).getAncho()), Math.round(ui.get(0).getAlto()), true);
        interfazUsuario.add(bmp);

        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.btnder);
        bmp = bmp.createScaledBitmap(bmp, Math.round(ui.get(1).getAncho()), Math.round(ui.get(1).getAlto()), true);
        interfazUsuario.add(bmp);

        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.text);
        bmp = bmp.createScaledBitmap(bmp, Math.round(500), Math.round(200), true);
        interfazUsuario.add(bmp);

        bmp = BitmapFactory.decodeResource(getResources(), R.drawable.text);
        bmp = bmp.createScaledBitmap(bmp, Math.round(150), Math.round(150), true);
        interfazUsuario.add(bmp);
    }

    private String generatePalabra(){
        String palabra = "";

        int numero = (int)(Math.random()*8);

        switch (numero){
            case 0:
                palabra = "BANANA";
                break;
            case 1:
                palabra = "MANGO";
                break;
            case 2:
                palabra = "ORANGE";
                break;
            case 3:
                palabra = "GRAPE";
                break;
            case 4:
                palabra = "PEPPER";
                break;
            case 5:
                palabra = "PEANOUT";
                break;
            case 6:
                palabra = "CHERRY";
                break;
            case 7:
                palabra = "ONION";
                break;
            case 8:
                palabra = "APPLE";
                break;
        }
        return palabra;
    }

    private Sprite createSprites(int resource){
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
        bmp = bmp.createScaledBitmap(bmp, (int)(getWidth()*0.5), (int)(getHeight()*0.5), true);
        sprite = new Sprite(this, bmp, 0, 920);
        return sprite;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        boolean retry = true;
        hiloPantalla.setRunning(false); //Paramos, ya que si está pintando no podemos destruirlo

        while(retry){
            try{
                hiloPantalla.join();
                retry = false;
            }
            catch(InterruptedException e){

            }
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:

                if(ui.get(0).comprobarSiTocoDentro(event.getX(), event.getY())){
                    sprites.get(0).setParado(false);
                    sprites.get(0).setDireccion(1);
                }

                if(ui.get(1).comprobarSiTocoDentro(event.getX(), event.getY())){
                    sprites.get(0).setParado(false);
                    sprites.get(0).setDireccion(2);
                }

                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:

                sprites.get(0).setParado(true);

                break;
        }
        return true;
    }
}
