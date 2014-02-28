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
    private boolean objColision; //checaColision de la pelota con el bote
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
    private Image tubo; //Imagen del bote base
    private int ballXinicial;
    private int ballYinicial;
    private int velocidadXin;
    private int velocidadYin;
    private double tiempoP;
    private boolean move;
    private int vidas;
    private int perdidos;
    private double dificultad;
    private Image instrucc; //Imagen de las instrucciones

    
    /*
     * Método constructor de la clase TiroParabolico
     * Inicializa variables, carga imágenes y sonidos, 
     * crea los componentes de la interface y crea e
     * inicializa el thread de la aplicación.
     */
    public  tiroparabolico() {
        posX_Canasta = 0;
        posY_Canasta = 0;
        ultX = 0;
        ultY = 0;
        velocidadXin = 0;
        move = false;
        velocidadYin = 0;
        posX_Pelota = 0;
        posY_Pelota = 0;
        tiempoP = 0;
        vidas = 5;
        perdidos = 0;
        nombreArchivo = "TiroParabolico.txt";
        play = true;
        grabar = false;
        cargar = false;
        grabar = false;
        instrucciones = false;
        dificultad = .20;
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
        URL aURL = this.getClass().getResource("Instrucciones.png");
        tubo = Toolkit.getDefaultToolkit().getImage(eURL);
        instrucc = Toolkit.getDefaultToolkit().getImage(aURL);
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
    /**
     * Metodo run sobrescrito de la clase Thread. En este metodo se ejecuta el
     * hilo, es un ciclo indefinido.
     */
    public void run() {
        tiempoActual = System.currentTimeMillis();
        while (true) {

            //Actualiza la animación
            if (!pausa) {
                actualiza();
                checaColision();
            }
            
            //Manda a llamar al método paint() para mostrar en pantalla la animación
            repaint();

            //Hace una pausa de 200 milisegundos
            try {
                Thread.sleep(20);
            } catch (InterruptedException ex) {
            }
        }

    }
    /**
     * Metodo usado para actualizar la posicion de objetos y actualizar el
     * tiempo de los frames
     */

    public void actualiza() {
        
        long tiempoTranscurrido = System.currentTimeMillis() - tiempoActual;
        tiempoActual += tiempoTranscurrido;
        ball.actualiza(tiempoTranscurrido);
        
        if (move) {
            ball.setMove(true);
            ball.setClickable(false);
            move = false;
        }
        
        if (ball.getMove()) {
           tiempoP += dificultad;
           ultX = (int) (velocidadXin * .5253 * tiempoP);
           ultY = (int) ((velocidadYin * .8509 *tiempoP) - (.5 * 9.8 * tiempoP * tiempoP));
           System.out.println(" ultx " + ultX);
           System.out.println(" ultY " + ultY);
           ball.setPosX(ultX);
           ball.setPosY(-ultY + (380));
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
      
       
        

    }
    /**
     * Metodo usado para checar las colisiones del objeto con las orillas del
     * <code>Applet</code>.
     */
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
            System.out.println("Colisiona con objeto");
            ball.setMove(false);
            ball.setClickable(true);
            ball.setPosX(ballXinicial);
            ball.setPosY(ballYinicial);
            tiempoP=0;
            ball.aumentaScore();
            atrapaPelota.play();
        }
        if ((ball.getPosY() + ball.getAlto()) > getHeight()) {
            ball.setMove(false);
            perdio.play();
            ball.setClickable(true);
            repaint();
            ball.setPosX(ballXinicial);
            ball.setPosY(ballYinicial);
            tiempoP=0;
            perdidos++;
            if(perdidos == 3) {
                vidas--;
                dificultad+=.1;
                perdidos = 0;
            }
            
        }
        

    }
    /**
     * Metodo <I>keyPressed</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar cualquier la
     * tecla.
     *
     * @param e es el <code>evento</code> generado al presionar las teclas.
     */
    public void keyPressed(KeyEvent e) {
        //Si presiona tecla P bandera prende/apaga de pausa
        if (e.getKeyCode() == KeyEvent.VK_P) {
            pausa = !pausa;
        }
        
       //Si presiona tecla S bandera prende/apaga mute or not al sonido
        if (e.getKeyCode() == KeyEvent.VK_S) {
            sonidos = !sonidos;
        }
       //Si presiona tecla flecha izq bandera prende/apaga para mover el bote
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            teclaIzquierda = true;
        }
        //Si presiona tecla flecha der bandera prende/apaga para mover el bote
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            teclaDerecha = true;
        }
        //Si presiona tecla I bandera prende/apaga para mostrar/quitar instrucciones
        if (e.getKeyCode() == KeyEvent.VK_I){
            instrucciones = !instrucciones;
            pausa = !pausa;
        }
        //Si presiona tecla C bandera prende/apaga para cargar el juego (lee archivo)
        if (e.getKeyCode() == KeyEvent.VK_C){
            cargar = !cargar;
        }
        //Si presiona tecla G bandera prende/apaga para grabar el juego
        if(e.getKeyCode() == KeyEvent.VK_G){
            grabar = true;
        }
       
    }

      /**
     * Metodo <I>keyTyped</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al presionar una tecla que
     * no es de accion.
     *
     * @param e es el <code>evento</code> que se genera en al presionar las
     * teclas.
     */
    public void keyTyped(KeyEvent e) {

    }
    /**
     * Metodo <I>keyReleased</I> sobrescrito de la interface
     * <code>KeyListener</code>.<P>
     * En este metodo maneja el evento que se genera al soltar la tecla
     * presionada.
     *
     * @param e es el <code>evento</code> que se genera en al soltar las teclas.
     */
    public void keyReleased(KeyEvent e) {

    }

    public void mouseClicked(MouseEvent e) {
        if( ball.intersecta(e.getX(),e.getY()) && ball.getClickable() && !move) {
            // x 93   y 120
            velocidadXin = (int) (Math.random() * (130 - 85)) + 25;
            velocidadYin = (int) (Math.random() * (120 - 85)) + 65;
            move = true;
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
     /**
     * Metodo que lee a informacion de un archivo y 
     * actualiza variables
     * @throws IOException
     */
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
 
    /**
     * Metodo que agrega la informacion de variables al archivo.
     *
     * @throws IOException
     */
    public void grabaArchivo() {
        try {
            PrintWriter fileOut = new PrintWriter(new FileWriter(nombreArchivo));

            fileOut.println("" + box.getPosX() + "," + box.getPosY() + "," + ball.getPosX() + "," + ball.getPosY() + "," + ultX + "," + ultY);
            fileOut.close();
        } catch (IOException ioe) {

        }
       
    }
    /**
     * Metodo paint sobrescrito de la clase Applet, heredado de la clase
     * Container. En este metodo se dibuja la imagen de fondo 
     * g es el objeto grafico usado para dibujar.
     */
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
        g.drawString("Score: " + ball.getScore(), 600 , 50);
        g.drawString("Vidas: " + vidas, 100 , 50);
        if(tubo !=null) {
            g.drawImage(tubo, 20, getHeight() - 126 , this);
        }
        
         /*
         ** Si pausa es verdadera e instrucciones tambien 
         ** entonces se debe desplegar la imagen de instrucciones
         */
        if(instrucc != null && instrucciones && pausa){
            g.drawImage(instrucc,0,0,this);
        }
        
        if (box != null && ball != null && !instrucciones) {

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

        }
    }

}
