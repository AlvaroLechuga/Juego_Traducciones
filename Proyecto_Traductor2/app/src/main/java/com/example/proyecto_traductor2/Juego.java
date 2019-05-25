package com.example.proyecto_traductor2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.example.proyecto_traductor2.DAO.DAOPalabra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Juego extends SurfaceView implements SurfaceHolder.Callback {

    private HiloPantalla hiloPantalla;
    PalabraHelper dbHelper;
    List<Palabra> palabras;
    List<Palabra> palabrasJuego = new ArrayList<>();
    HashMap<Integer, String> palabrasEsp = new HashMap<Integer, String>();

    List<Rectangulo> ui;

    Paint paintTexto;
    Paint paintTexto2;
    Paint gameOver;
    Paint pincel1;

    int anchoPantalla;
    int altoPantalla;

    float posX;
    float posY;

    Rectangulo r1;

    Random rand = new Random();

    boolean juego = true;

    String opcionING = "";
    String opcionESP = "";

    boolean acierto0IN = false;
    boolean acierto1IN = false;
    boolean acierto2IN = false;
    boolean acierto3IN = false;
    boolean acierto4IN = false;

    boolean acierto0ES = false;
    boolean acierto1ES = false;
    boolean acierto2ES = false;
    boolean acierto3ES = false;
    boolean acierto4ES = false;

    int intentos = 0;
    double porcentaje = 0;

    public Juego(Context context) {
        super(context);
        setBackgroundColor(Color.BLACK);
        getHolder().addCallback(this);
        paintTexto = new Paint();
        paintTexto2 = new Paint();
        gameOver = new Paint();
        pincel1 = new Paint();
        dbHelper = new PalabraHelper(getContext(), "traductor", null, 1);
        ui = new ArrayList<>();
    }

    @Override
    public void onDraw(final Canvas canvas){
        super.onDraw(canvas);

        if(juego){
            paintTexto.setColor(Color.WHITE);
            paintTexto2.setColor(Color.WHITE);
            paintTexto.setTextSize(60);
            paintTexto2.setTextSize(60);

            paintTexto2.setTextAlign(Paint.Align.RIGHT);

            gameOver.setColor(Color.RED);
            gameOver.setTextSize(100);
            gameOver.setTextAlign(Paint.Align.CENTER);

            pincel1.setARGB(255,255,0,0);
            pincel1.setStrokeWidth(4);
            canvas.drawRect(10,100,500f,altoPantalla-100,pincel1);
            canvas.drawRect(1700,100,2500,altoPantalla-100,pincel1);


            r1.dibujar(canvas);

            // Escribir palabras en ingles
            canvas.drawText(palabrasJuego.get(0).getPalabraEN(), 450,180, paintTexto2);
            canvas.drawText(palabrasJuego.get(1).getPalabraEN(), 450,360, paintTexto2);
            canvas.drawText(palabrasJuego.get(2).getPalabraEN(), 450,540, paintTexto2);
            canvas.drawText(palabrasJuego.get(3).getPalabraEN(), 450,720, paintTexto2);
            canvas.drawText(palabrasJuego.get(4).getPalabraEN(), 450,900, paintTexto2);

            // Escribir palabras en español
            canvas.drawText(palabrasEsp.get(0), 1750, 180, paintTexto);
            canvas.drawText(palabrasEsp.get(1), 1750, 360, paintTexto);
            canvas.drawText(palabrasEsp.get(2), 1750, 540, paintTexto);
            canvas.drawText(palabrasEsp.get(3), 1750, 720, paintTexto);
            canvas.drawText(palabrasEsp.get(4), 1750, 900, paintTexto);

            // Dibujar cuadrados UI comprobando si ya se han acertado

            if(!acierto0IN){
                ui.get(0).dibujar(canvas);
            }else{
                canvas.drawLine(ui.get(0).getX(), ui.get(0).getY(), 1750, ui.get(sacarCuadro(0)).getY()+50, pincel1);
            }

            if(!acierto1IN){
                ui.get(1).dibujar(canvas);
            }else{
                canvas.drawLine(ui.get(1).getX(), ui.get(1).getY(), 1750, ui.get(sacarCuadro(1)).getY()+50, pincel1);
            }

            if(!acierto2IN){
                ui.get(2).dibujar(canvas);
            }else {
                canvas.drawLine(ui.get(2).getX(), ui.get(2).getY(), 1750, ui.get(sacarCuadro(2)).getY()+50, pincel1);
            }

            if(!acierto3IN){
                ui.get(3).dibujar(canvas);
            }else {
                canvas.drawLine(ui.get(3).getX(), ui.get(3).getY(), 1750, ui.get(sacarCuadro(3)).getY()+50, pincel1);
            }

            if(!acierto4IN){
                ui.get(4).dibujar(canvas);
            }else{
                canvas.drawLine(ui.get(4).getX(), ui.get(4).getY(), 1750, ui.get(sacarCuadro(4)).getY()+50, pincel1);
            }

            if(!acierto0ES){
                ui.get(5).dibujar(canvas);
            }

            if(!acierto1ES){
                ui.get(6).dibujar(canvas);
            }

            if(!acierto2ES){
                ui.get(7).dibujar(canvas);
            }

            if(!acierto3ES){
                ui.get(8).dibujar(canvas);
            }

            if(!acierto4ES){
                ui.get(9).dibujar(canvas);
            }

            if(opcionING != "" && opcionESP != ""){
                if(palabrasJuego.get(Integer.parseInt(opcionING)).getPalabraSP().equals(palabrasEsp.get(Integer.parseInt(opcionESP)))){
                    Toast.makeText(getContext(), "Acierto", Toast.LENGTH_LONG).show();

                    switch (opcionING){
                        case "0":
                            acierto0IN = true;
                            break;
                        case "1":
                            acierto1IN = true;
                            break;
                        case "2":
                            acierto2IN = true;
                            break;
                        case "3":
                            acierto3IN = true;
                            break;
                        case "4":
                            acierto4IN = true;
                            break;
                    }

                    switch (opcionESP){
                        case "0":
                            acierto0ES = true;
                            break;
                        case "1":
                            acierto1ES = true;
                            break;
                        case "2":
                            acierto2ES = true;
                            break;
                        case "3":
                            acierto3ES = true;
                            break;
                        case "4":
                            acierto4ES = true;
                            break;
                    }

                    opcionING = "";
                    opcionESP = "";
                    intentos++;
                }else{
                    Toast.makeText(getContext(), "Error", Toast.LENGTH_LONG).show();
                    opcionING = "";
                    opcionESP = "";
                    intentos++;
                }
            }

            if(acierto0ES && acierto0IN && acierto1ES && acierto0IN && acierto2ES && acierto2IN && acierto3ES && acierto3IN && acierto4ES && acierto4ES && acierto4IN){
                juego = false;
            }

        }else{
            porcentaje = (5 * 100) / intentos;
            canvas.drawText("Fin de la partida, porcentaje de acierto = "+porcentaje+"%", getWidth()/2,getHeight()/2, gameOver);
        }

    }

    private int sacarCuadro(int numero) {

        for (Integer i : palabrasEsp.keySet()) {

            if(palabrasJuego.get(numero).getPalabraSP().equals(palabrasEsp.get(i))){
                return i;
            }

        }

        return 0;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        hiloPantalla = new HiloPantalla(getHolder(), this);
        hiloPantalla.setRunning(true);
        hiloPantalla.start(); //Ejecuta el metodo run del hilo

        anchoPantalla=getWidth();
        altoPantalla=getHeight();

        DAOPalabra dao = new DAOPalabra();
        palabras = dao.ObtenerPalabras(dbHelper);

        palabrasJuego = ObtenerPalabras();

        r1 = new Rectangulo(anchoPantalla/2f-120,100f, 250f,250f);

        iniciarInterfaz();
    }

    private void iniciarInterfaz() {
        // Inglés
        ui.add(new Rectangulo(480f, 130f, 70f, 70f)); // 0
        ui.add(new Rectangulo(480f, 310f, 70f, 70f)); // 1
        ui.add(new Rectangulo(480f, 490f, 70f, 70f)); // 2
        ui.add(new Rectangulo(480f, 670f, 70f, 70f)); // 3
        ui.add(new Rectangulo(480f, 850f, 70f, 70f)); // 4
        // Español
        ui.add(new Rectangulo(1660f, 130f, 70f, 70f)); // 5
        ui.add(new Rectangulo(1660f, 310f, 70f, 70f)); // 6
        ui.add(new Rectangulo(1660f, 490f, 70f, 70f)); // 7
        ui.add(new Rectangulo(1660f, 670f, 70f, 70f)); // 8
        ui.add(new Rectangulo(1660f, 850f, 70f, 70f)); // 9

        Integer contador = 0;
        int n;
        while(contador != 5){

            n = rand.nextInt(palabrasJuego.size());

            if(!palabrasEsp.containsValue(palabrasJuego.get(n).getPalabraSP())){
                palabrasEsp.put(contador, palabrasJuego.get(n).getPalabraSP());
                contador++;
            }

        }

    }

    private List<Palabra> ObtenerPalabras() {
        List<Palabra> test = new ArrayList<>();
        boolean busqueda;
        for(int i = 0; i < 5;  i++) {
            busqueda = true;
            while (busqueda) {
                int n = rand.nextInt(palabras.size());
                if(!test.contains(palabras.get(n))) {
                    test.add(palabras.get(n));
                    busqueda = false;
                }
            }
        }

        return  test;
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

                if(r1.comprobarSiTocoDentro(event.getX(), event.getY())){
                    System.exit(0);
                }

                for(int i = 0; i < ui.size(); i++){
                    if(ui.get(i).comprobarSiTocoDentro(event.getX(), event.getY())){
                        switch (i){
                            case 0:
                                opcionING = "0";
                                break;
                            case 1:
                                opcionING = "1";
                                break;
                            case 2:
                                opcionING = "2";
                                break;
                            case 3:
                                opcionING = "3";
                                break;
                            case 4:
                                opcionING = "4";
                                break;
                            case 5:
                                opcionESP = "0";
                                break;
                            case 6:
                                opcionESP = "1";
                                break;
                            case 7:
                                opcionESP = "2";
                                break;
                            case 8:
                                opcionESP = "3";
                                break;
                            case 9:
                                opcionESP = "4";
                                break;
                        }
                    }
                }

                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
