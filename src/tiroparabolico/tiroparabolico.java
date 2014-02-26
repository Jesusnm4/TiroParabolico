/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiroparabolico;

import javax.swing.JOptionPane;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.awt.Toolkit;
import java.awt.Cursor;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.Graphics;
import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.net.URL;

public class tiroparabolico extends JFrame implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 1L;
    private boolean colXD;  // Verifica que el caja no colisione en la derecha
    private boolean colXI;  // Verifica que el caja no colisione en la izquierda
    private Caja box;     // Personaje caja
    private Pelota ball;  // Personaje pelota
    private long tiempoActual;  // Contador de tiempo.
    private long tiempoInicial; // Contador de tiempo.
    private int auxiliar; // ayuda a generar el numero de pelotas
    private int cantidad;  //numeros de pelotas a celebrar
    private Image dbImage; // Imagen a proyectar.
    private Graphics dbg; // Objeto grafico
    private boolean pausa;  // Verifica si el juego se tiene que pausar
    private int score;  //Variable para llevar la cuenta del puntaje
    private static final int WIDTH = 1000;    //Ancho del JFrame
    private static final int HEIGHT = 600;    //Alto del JFrame
    private boolean izquierda;
    private boolean derecha;
    private SoundClip atrapaPelota;	//Sonido acierto
    private SoundClip perdio; //Sonido chocaPelota con applet
    private boolean teclaDerecha; //Para saber cuando el usuario oprime la tecla de la flecha derecha
    private boolean teclaIzquierda; //Para saber cuando el usuario oprime la tecla de la flecha izq
    private boolean play;
    private boolean sonidos; // bool que determina si se pueden hacer sonidos o no
    private boolean objColision;
    private boolean instrucciones;
    private boolean grabar; //Bandera para saber cuando grabar
    private boolean cargar; //Bandera para saber cuando cargar
    private String nombreArchivo; //Archivo donde guardo a la hora de grabar
    private int posX_Canasta; 
    private int posY_Canasta;
    private int ultX;
    private int ultY;
    private int posX_Pelota;
    private int posY_Pelota;
    private String[] arr;
    private Image tubo;
    private int ballXinicial;
    private int ballYinicial;
    private int velocidadXin;
    private int velocidadYin;
    private double tiempoP;

    
    
    public  tiroparabolico() {
        posX_Canasta = 0;
        posY_Canasta = 0;
        ultX = 0;
        ultY = 0;
        velocidadXin = 0;
        velocidadYin = 0;
        posX_Pelota = 0;
        posY_Pelota = 0;
        tiempoP = 0;
        nombreArchivo = "TiroParabolico.txt";
        play = true;
        grabar = false;
        cargar = false;
        grabar = false;
        instrucciones = false;
        objColision = false;
        this.setSize(700, 600);
        sonidos = true;
        teclaDerecha = false;
        teclaIzquierda = false;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        atrapaPelota = new SoundClip("Sonidos/Atrapa.wav");
        perdio = new SoundClip("Sonidos/Cae.wav");
        pausa = false;
        izquierda = false;
        derecha = false;
        score = 0;
        ballXinicial = 0;
        ballYinicial = (getHeight() - 120 - 112);
        ball = new Pelota (0,0);
        ball.setPosX(ballXinicial);
        ball.setPosY(ballYinicial);
        box = new Caja(0, 0);
        box.setPosX((getWidth() / 2 - box.getAncho() / 2));
        box.setPosY((getHeight() - box.getAlto()));
        setBackground(Color.BLACK);
        setForeground(Color.RED);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        URL eURL = this.getClass().getResource("tube.png");
        tubo = Toolkit.getDefaultToolkit().getImage(eURL);
        auxiliar = (int) (Math.random() * 3 + 1);
        // Declaras un hilo
        Thread th = new Thread(this);
        // Empieza el hilo
        th.start();
    }

    public void stop() {

    }

    public void destroy() {

    }

    public void run() {
        while (true) {

            //Actualiza la animación
            if (!pausa) {
                actualiza();
            }
            checaColision();
            //Manda a llamar al método paint() para mostrar en pantalla la animación
            repaint();

            //Hace una pausa de 200 milisegundos
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
            }
        }

    }

    public void actualiza() {
        
        long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;
        tiempoActual += tiempoTranscurrido;
        ball.actualiza(tiempoTranscurrido);
       
        if(ball.getMove()) {
           tiempoP+=.2;
           ultX = (int) (velocidadXin * .8060 * tiempoP);
           ultY = (int) ((velocidadYin * 40.51 *tiempoP) - (.5 * 9.8 * tiempoP * tiempoP));
           ball.setPosX(ball.getPosX() + ultX);
           ball.setPosY(-ultY + ball.getPosY());
        }
        
        if(ball.getPosX() == ballXinicial && ball.getPosY() == ballYinicial) {
            ball.setClickable(true);
        }
        else{
            ball.setClickable(false);
        }
        
        if (cargar) {
            leeArchivo();
            cargar = false;
        }

        if (grabar) {
            grabaArchivo();
            grabar = false;
        }
        if (teclaDerecha || teclaIzquierda) {
            box.actualiza(tiempoTranscurrido);
        }

        if (colXI) {
            box.setPosX(111);
            colXI = false;
        }
        if (colXD) {
            box.setPosX(getWidth() - box.getAncho());
            colXD = false;
        }

        if (teclaDerecha) {
            box.setPosX(box.getPosX() + 15);
            teclaDerecha = false;
        }
        if (teclaIzquierda) {
            box.setPosX(box.getPosX() - 15);
            teclaIzquierda = false;
        }
      
        if (objColision) {
            if (sonidos) {
                atrapaPelota.play();
                objColision = false;
            } else {
                objColision = false;
            }

        }

    }


    public void checaColision() {
        if (box.getPosX() < 111) {

            colXI = true;

        } else {
            colXI = false;
        }

        if (box.getPosX() + box.getAncho() > getWidth()) {
            colXD = true;

        } else {
            colXD = false;
        }
        
        if (ball.intersecta(box)) {
            objColision = true;
            ball.setMove();
            ball.setPosX(ballXinicial);
            ball.setPosY(ballYinicial);
            tiempoP=0;
        }
        if (ball.getPosY() + ball.getAlto() > getHeight()) {
            ball.setMove();
            ball.setPosX(ballXinicial);
            ball.setPosY(ballYinicial);
            tiempoP=0;
        }

    }

    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_P) {
            pausa = !pausa;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
           sonidos = !sonidos;
        }
        
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            teclaIzquierda = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            teclaDerecha = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_I){
            instrucciones = !instrucciones;
            pausa = !pausa;
        }
        if (e.getKeyCode() == KeyEvent.VK_C){
            cargar = !cargar;
        }
        if(e.getKeyCode() == KeyEvent.VK_G){
            grabar = !grabar;
        }
       
    }

    
    public void keyTyped(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

    }

    public void mouseClicked(MouseEvent e) {
        if( ball.intersecta(e.getX(),e.getY()) && ball.getClickable()) {
            // x 93   y 120
            velocidadXin = (int) (Math.random() * 3 + 1);
            velocidadYin = (int) (Math.random() * 10 + 1);
            ball.setMove();
        }
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseMoved(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
     
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }
    public void leeArchivo()  {
          try
          {
                BufferedReader fileIn;
                try {
                        fileIn = new BufferedReader(new FileReader(nombreArchivo));
                } catch (FileNotFoundException e){
                        File archivo = new File(nombreArchivo);
                        PrintWriter fileOut = new PrintWriter(archivo);
                        fileOut.println("50,50,100,100,10");
                        fileOut.close();
                        fileIn = new BufferedReader(new FileReader(nombreArchivo));
                }
                String dato = fileIn.readLine();
                arr = dato.split (",");
                int posX_Canasta = (Integer.parseInt(arr[0]));
                int posY_Canasta = (Integer.parseInt(arr[1]));
                int posX_Pelota = (Integer.parseInt(arr[2]));
                int posY_Pelota = (Integer.parseInt(arr[3]));
                int velocidad = (Integer.parseInt(arr[4]));
               
                fileIn.close();
          }
          catch(IOException ioe) {
              int posX_Canasta = 0;
              int posY_Canasta = 0;
              int posX_Pelota = 0;
              int posY_Pelota = 0;
              int velocidad = 0;
          }
        }
 
    public void grabaArchivo() {
        try {
            PrintWriter fileOut = new PrintWriter(new FileWriter(nombreArchivo));

            fileOut.println("" + box.getPosX() + "," + box.getPosY() + "," + ball.getPosX() + "," + ball.getPosY() + "," + ultX + "," + ultY);
            fileOut.close();
        } catch (IOException ioe) {

        }
       
    }
    public void paint(Graphics g) {

        // Inicializan el DoubleBuffer
        if (dbImage == null) {

            dbImage = createImage(this.getSize().width, this.getSize().height);
            dbg = dbImage.getGraphics();
        }
        // Actualiza la imagen de fondo
        dbg.setColor(getBackground());
        dbg.fillRect(0, 0, this.getSize().width, this.getSize().height);
        // Actualiza el Foreground
        dbg.setColor(getForeground());
        paint1(dbg);
        // Dibuja la imagen actualizada y con esto ya no se ve parpadeo
        g.drawImage(dbImage, 0, 0, this);
    }

    public void paint1(Graphics g) {
        
        
        g.setColor(Color.ORANGE);
        g.drawString("posX:" + ball.getPosX(), 600, 50);
        g.drawString("posY:" + ball.getPosY(), 600, 70);
        g.drawString("velX:" + ultX, 600, 90);
        g.drawString("velY:" + ultY, 600, 110);
        
        if(tubo !=null) {
            g.drawImage(tubo, 20, getHeight() - 126 , this);
        }
        
        if (box != null && ball != null) {

            g.drawImage(box.getImagenI(), box.getPosX(), box.getPosY(), this);
            g.drawImage(ball.getImagenI(), ball.getPosX(), ball.getPosY(), this);
            
            
            /*
            ** Si pausa es verdadera y no las instrucciones 
            ** se pone el anuncio de pausa
            */
            
            
            if ((pausa) && (!instrucciones)) {
                g.setFont(new Font("default", Font.BOLD, 16));
                g.drawString(box.muestraPausa(), (box.getPosX()), (box.getPosY() + box.getAlto() / 2));
            }
            
            /*
            ** Si pausa es verdadera e instrucciones tambien 
            ** entonces se debe desplegar la imagen de instrucciones
            */
            if((pausa) && (instrucciones)){
                g.setFont(new Font("default", Font.BOLD, 16));
                g.drawString("INSTRUCCIONES",box.getPosX(),box.getPosY());
            }

        }
    }

}
