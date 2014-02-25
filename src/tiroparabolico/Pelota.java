package tiroparabolico;


/**
 * Clase Pelota
 *
 * @author Antonio Mejorado
 * @version 1.00 2008/6/13
 */
import java.awt.Image;
import java.awt.Toolkit;

public class Pelota extends Base {

    private int velocidad = ((int) (Math.random() * 6 + 3));  //genera la velocidad entre 3 y 6
    private static int score = 0;

    /**
     * Metodo constructor que hereda los atributos de la clase
     * <code>Base</code>.
     *
     * @param posX es la <code>posiscion en x</code> del objeto raton.
     * @param posY es el <code>posiscion en y</code> del objeto raton.
     * @param image es la <code>imagen</code> del objeto raton.
     */

    public Pelota(int posX, int posY) {
        super(posX, posY);
        Image freezer1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/freezer1.gif"));
        Image freezer2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/freezer2.gif"));
        animacion = new Animacion();
        animacion.sumaCuadro(freezer1, 300);
        animacion.sumaCuadro(freezer2, 300);
    }

    /**
     * Metodo que genera un numero al azar que servira como velocidad.
     *
     * @return un numero al azar entre 1 y 10.
     */
    public int getVelocidad() {
        return velocidad;
    }

    /**
     * Metodo que permite obtener el score.
     *
     * @return la cantidad de colisiones.
     */
    public int getScore() {
        return score;
    }

    /**
     * Metodo que permite aumentar el score en 1.
     *
     */
    public void aumentaScore() {
        score++;
    }

}
