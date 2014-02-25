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
        Image goku1 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Caja/frame_000.gif"));
        Image goku2 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Caja/frame_001.gif"));
        Image goku3 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Caja/frame_002.gif"));
        Image goku4 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Caja/frame_003.gif"));
        Image goku5 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Caja/frame_004.gif"));
        Image goku6 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Caja/frame_005.gif"));
        Image goku7 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Caja/frame_006.gif"));
        Image goku8 = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("Caja/frame_007.gif"));
        
        animacion = new Animacion();
        animacion.sumaCuadro(goku1, 300);
        animacion.sumaCuadro(goku2, 300);
        animacion.sumaCuadro(goku3, 300);
        animacion.sumaCuadro(goku4, 300);
        animacion.sumaCuadro(goku5, 300);
        animacion.sumaCuadro(goku6, 300);
        animacion.sumaCuadro(goku7, 300);
        
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
