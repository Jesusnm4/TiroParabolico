/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tiroparabolico;

import javax.swing.JFrame;
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

public class tiroparabolico extends JFrame implements Runnable, KeyListener, MouseListener, MouseMotionListener {

    private static final long serialVersionUID = 1L;
    private boolean colXD;  // Verifica que el caja no colisione en la derecha
    private boolean colXI;  // Verifica que el caja no colisione en la izquierda
    private Caja box;     // Personaje caja
    private Pelota ball;  // Personaje pelota
    private long tiempoActual;  // Contador de tiempo.
    private long tiempoInicial; // Contador de tiempo.
    private int auxiliar; // ayuda a generar el numero de pelotas
    private int randX; //numero al azar usado para posicion en X
    private int randY; //numero al azar usado para posicion en X
    private int cantidad;  //numeros de pelotas a celebrar
    private Image dbImage; // Imagen a proyectar.
    private Graphics dbg; // Objeto grafico
    private boolean pausa;  // Verifica si el juego se tiene que pausar
    private int score;  //Variable para llevar la cuenta del puntaje
    private boolean izquierda;
    private boolean derecha;
    private SoundClip atrapaPelota;	//Sonido acierto
    private SoundClip perdio; //Sonido chocaPelota con applet
    
    public  tiroparabolico() {
        this.setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        atrapaPelota = new SoundClip("Sonidos/Atrapa.wav");
        perdio = new SoundClip("Sonidos/Cae.wav");
        pausa = false;
        izquierda = false;
        derecha = false;
        score = 0;
        box = new Caja(0, 0);
        box.setPosX((getWidth() / 2 - box.getAncho() / 2));
        box.setPosY((getHeight() / 2 - box.getAlto() / 2));
        setBackground(Color.BLACK);
        setForeground(Color.RED);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
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
        box.actualiza(tiempoTranscurrido);
      
            if (colXI) {
                box.setPosX(0);
                colXI = false;
            }
            if (colXD) {
                box.setPosX(getWidth() - box.getAncho());
                colXD = false;
            }

        }
    

    public void checaColision() {
        if (box.getPosX() < 0) {

            colXI = true;

        } else {
            colXI = false;
        }

        if (box.getPosX() + box.getAncho() > getWidth()) {
            colXD = true;

        } else {
            colXD = false;
        }
        
    }

    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_P) {
            pausa = !pausa;
        }
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

    }

    public void mouseClicked(MouseEvent e) {
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
        g.drawString("Score:" + ball.getScore(), (getWidth() / 2) - 40, 60);

        if (box != null && ball != null) {

            g.drawImage(box.getImagenI(), box.getPosX(), box.getPosY(), this);

            if (pausa) {
                g.setFont(new Font("default", Font.BOLD, 16));
                g.drawString(box.muestraPausa(), (box.getPosX()), (box.getPosY() + box.getAlto() / 2));
            }

        }
    }

}
