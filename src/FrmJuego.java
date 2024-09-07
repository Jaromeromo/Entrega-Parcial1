import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

public class FrmJuego extends JFrame {

    private final JButton btnRepartir;
    private final JButton btnVerificar;
    private final JButton btnVerificarEscaleras;
    private final JButton btnOrdenar;
    private final JPanel pnlJugador1;
    private final JPanel pnlJugador2;
    private final JTabbedPane tpJugadores;

    Jugador jugador1 = new Jugador();
    Jugador jugador2 = new Jugador();

    public FrmJuego() {
        btnRepartir = new JButton();
        btnVerificar = new JButton();
        btnVerificarEscaleras = new JButton();
        btnOrdenar = new JButton();
        tpJugadores = new JTabbedPane();
        pnlJugador1 = new JPanel();
        pnlJugador2 = new JPanel();

        setSize(600, 300);
        setTitle("Juego de Cartas");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        pnlJugador1.setBackground(new Color(153, 255, 51));
        pnlJugador1.setLayout(null);
        pnlJugador2.setBackground(new Color(0, 255, 255));
        pnlJugador2.setLayout(null);

        tpJugadores.setBounds(10, 40, 550, 170);
        tpJugadores.addTab("Martín Estrada Contreras", pnlJugador1);
        tpJugadores.addTab("Raul Vidal", pnlJugador2);

        btnRepartir.setBounds(10, 10, 100, 25);
        btnRepartir.setText("Repartir");
        btnRepartir.addActionListener(e -> btnRepartirClick());

        btnVerificar.setBounds(120, 10, 100, 25);
        btnVerificar.setText("Verificar");
        btnVerificar.addActionListener(e -> btnVerificarClick());

        btnVerificarEscaleras.setBounds(230, 10, 150, 25);
        btnVerificarEscaleras.setText("Verificar Escaleras");
        btnVerificarEscaleras.addActionListener(e -> btnVerificarEscalerasClick());

        btnOrdenar.setBounds(390, 10, 100, 25);
        btnOrdenar.setText("Ordenar");
        btnOrdenar.addActionListener(e -> btnOrdenarClick());

        getContentPane().setLayout(null);
        getContentPane().add(tpJugadores);
        getContentPane().add(btnRepartir);
        getContentPane().add(btnVerificar);
        getContentPane().add(btnVerificarEscaleras);
        getContentPane().add(btnOrdenar);
    }

    private void btnRepartirClick() {
        jugador1.repartir();
        jugador1.mostrar(pnlJugador1);

        jugador2.repartir();
        jugador2.mostrar(pnlJugador2);
    }

    private void btnVerificarClick() {
        int pestañaEscogida = tpJugadores.getSelectedIndex();
        String mensaje = switch (pestañaEscogida) {
            case 0 -> jugador1.getGrupos() + "\nPuntaje de cartas no en grupos: " + jugador1.calcularPuntaje();
            case 1 -> jugador2.getGrupos() + "\nPuntaje de cartas no en grupos: " + jugador2.calcularPuntaje();
            default -> "No se encontró información para el jugador seleccionado.";
        };
        JOptionPane.showMessageDialog(null, mensaje);
    }

    private void btnVerificarEscalerasClick() {
        int pestañaEscogida = tpJugadores.getSelectedIndex();
        String escaleras = switch (pestañaEscogida) {
            case 0 -> jugador1.obtenerEscalerasDePinta();
            case 1 -> jugador2.obtenerEscalerasDePinta();
            default -> "";
        };
        JOptionPane.showMessageDialog(null, escaleras);
    }

    private void btnOrdenarClick() {
        int pestañaEscogida = tpJugadores.getSelectedIndex();
        switch (pestañaEscogida) {
            case 0 -> {
                jugador1.ordenarCartas();
                jugador1.mostrar(pnlJugador1);
            }
            case 1 -> {
                jugador2.ordenarCartas();
                jugador2.mostrar(pnlJugador2);
            }
        }
    }
}


