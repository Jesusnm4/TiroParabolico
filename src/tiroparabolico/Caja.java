package tiroparabolico;


/**
 * Clase Caja
 *
 * @author Antonio Mejorado
 * @version 1.00 2008/6/13
 */
import java.awt.Image;
import java.awt.Toolkit;

public class Caja extends Base {
    
    private static final String PAUSADO = "PAUSADO";

    /**
     * Metodo constructor que hereda los atributos de la clase
     * <code>Base</code>.
     *
     * @param posX es la <code>posiscion en x</code> del objeto elefante.
     * @param posY es el <code>posiscion en y</code> del objeto elefante.
     * @param image es la <code>imagen</code> del objeto elefante.
     */
    public Caja(int posX, int posY) {
        super(posX, posY);
        Image goku1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/goku1.gif"));
        Image goku2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("imagenes/goku2.gif"));
        animacion = new Animacion();
        animacion.sumaCuadro(goku1, 300);
        animacion.sumaCuadro(goku2, 300);
    }

    /**
     * Metodo que regresa el string "Pausado".
     *
     * @return String de la clase Caja "Pausado".
     */
    public String muestraPausa() {
        return PAUSADO;
    }
}
