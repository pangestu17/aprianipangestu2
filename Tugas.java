/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tugas;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author ASUS
 */
public class Tugas extends Application implements Runnable{
    
    //Loop Parameters
    private final static int MAX_FPS = 60;
    private final static int MAX_FRAME_SKIPS = 5;
    private final static int FRAME_PERIOD = 1000 / MAX_FPS;

    //Thread
    private Thread thread;
    private volatile boolean running = true;

    //Canvas
    Canvas canvas = new Canvas(700,700);
    //ATRIBUT KOTAK
    float cx = 200f;
    float cy = (float) (canvas.getHeight()/2.0f);
    float size = 60f;
    
    //simulasi gerak jatuh bebas
    float g = 0.1f; //percepatan gravitasi
    float v = 0f; //kecepatan jatuh
    float t = 0f; //waktu
    float vUP = 10;

    //KEYBOARD HANDLER
    ArrayList<String> inputKeyboard = new ArrayList<String>();
    
    
        
    
    public Tugas() {
        resume();
    }
@Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        Scene scene = new Scene(root);
        canvas = new Canvas(1024, 700);
        root.getChildren().add(canvas);
        //HANDLING KEYBOARD EVENT
        scene.setOnKeyPressed(
                new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                String code = e.getCode().toString();
                if (!inputKeyboard.contains(code)) {
                    inputKeyboard.add(code);
                    System.out.println(code);
                }
            }
        }
        );

        scene.setOnKeyReleased(
                new EventHandler<KeyEvent>() {
            public void handle(KeyEvent e) {
                String code = e.getCode().toString();
                inputKeyboard.remove(code);
            }
        }
        );


//primaryStagegetIcons().add(new Image (getClass().getResourceAsStream("logo.jpg")));
 primaryStage.setTitle("Visual Loop");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
//THREAD
       private void resume() {
        reset();
        thread = new Thread (this);
        running = true;
        thread.start();
    }

    //THREAD
    private void pause() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    //LOOP
    private void reset() {
        
    }

    //LOOP
    private void update() {
            if(inputKeyboard.contains("SPACE")&&cy>0){
                cy-=vUP; //melompat keatas dengan kecepatan vUP
                t=0;
                
            }
            //gravitasi
            if (cy<(canvas.getHeight()-0.1f*size)){
                t++;
                v=g*t;
                cy+=v;
            }

    }
        //LOOP
    private void draw() {
        try {
            if (canvas != null) {
                GraphicsContext gc = canvas.getGraphicsContext2D();
                gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
                //CONTOH MENGGAMBAR KOTAK YANG DAPAT DITRANSLASI DAN DI ROTASI
                gc.save();
                gc.translate(cx, cy);
                gc.setFill(Color.DEEPSKYBLUE);
                gc.fillRect(cx,cy,100,100);
                gc.restore();
                //cek input keyboard
                if (inputKeyboard.contains("SPACE")){
                    gc.setFill(Color.RED);
                    gc.fillText("SPACE", 300, 300);
                }
                
              
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void run() {
        long beginTime;
        long timeDiff;
        int sleepTime = 0;
        int framesSkipped;
        //LOOP WHILE running = true; 
        while (running) {
            try {
                synchronized (this) {
                    beginTime = System.currentTimeMillis();
                    framesSkipped = 0;
                    update();
                    draw();
                }
                timeDiff = System.currentTimeMillis() - beginTime;
                sleepTime = (int) (FRAME_PERIOD - timeDiff);
                if (sleepTime > 0) {
                    try {
                        Thread.sleep(sleepTime);
                    } catch (InterruptedException e) {
                    }
                }
                while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS) {
                    update();
                    sleepTime += FRAME_PERIOD;
                    framesSkipped++;
                }
            } finally {


                        
                        
                        
                        
                        
                        
                        
                        
                        
                    }
                }
            }
}