import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import javax.swing.JPanel;

public class Jugador {

    private static final int TOTAL_CARTAS = 10;
    private static final int MARGEN = 10;
    private static final int DISTANCIA = 50;

    private final Carta[] cartas = new Carta[TOTAL_CARTAS];
    private final Random r = new Random();

    // Repartir las cartas, utilizando un bucle for mejorado (enhanced for loop).
    public void repartir() {
        for (int i = 0; i < cartas.length; i++) {
            cartas[i] = new Carta(r); // Generamos una nueva carta
        }
    }

    // Método para mostrar las cartas en el panel
    public void mostrar(JPanel pnl) {
        pnl.removeAll();
        int i = 0;
        for (Carta c : cartas) {
            c.mostrar(pnl, MARGEN + i++ * DISTANCIA, MARGEN);
        }
        pnl.repaint();
    }

    // Método para ordenar las cartas por pinta y luego por nombre usando lambdas
    public void ordenarCartas() {
        Arrays.sort(cartas, Comparator.comparing(Carta::getPinta)
                                      .thenComparing(c -> c.getNombre().ordinal()));
    }

    // Método para obtener grupos (pares, tríos, etc.) de cartas
    public String getGrupos() {
        StringBuilder mensaje = new StringBuilder("No se encontraron grupos");
        int[] contadores = new int[NombreCarta.values().length];

        // Contar cuántas cartas de cada valor tiene el jugador
        for (Carta c : cartas) {
            contadores[c.getNombre().ordinal()]++;
        }

        boolean hayGrupos = false;
        for (int i = 0; i < contadores.length; i++) {
            if (contadores[i] >= 2) {
                if (!hayGrupos) {
                    hayGrupos = true;
                    mensaje = new StringBuilder("Los grupos que se encontraron fueron:\n");
                }

                // Asignar el nombre adecuado al grupo
                String nombreGrupo = switch (contadores[i]) {
                    case 2 -> "Par";
                    case 3 -> "Terna";
                    case 4 -> "Cuarta";
                    default -> contadores[i] + " cartas"; // Para casos de más de 4 cartas (poco común)
                };

                // Añadir el grupo al mensaje
                mensaje.append(nombreGrupo)
                       .append(" de ")
                       .append(NombreCarta.values()[i]) // Nombre de la carta
                       .append("\n");
            }
        }

        return mensaje.toString();
    }

    // Método para obtener escaleras de cartas de la misma pinta
    public String obtenerEscalerasDePinta() {
        Arrays.sort(cartas, Comparator.comparing(Carta::getPinta)
                                      .thenComparing(c -> c.getNombre().ordinal()));

        List<List<Carta>> escaleras = new ArrayList<>();
        List<Carta> secuenciaActual = new ArrayList<>();

        for (Carta cartaActual : cartas) {
            if (secuenciaActual.isEmpty()) {
                secuenciaActual.add(cartaActual);
            } else {
                Carta ultimaCarta = secuenciaActual.get(secuenciaActual.size() - 1);

                if (cartaActual.getPinta().equals(ultimaCarta.getPinta()) &&
                    cartaActual.getNombre().ordinal() == ultimaCarta.getNombre().ordinal() + 1) {
                    secuenciaActual.add(cartaActual);
                } else {
                    if (secuenciaActual.size() >= 3) {
                        escaleras.add(new ArrayList<>(secuenciaActual));
                    }
                    secuenciaActual.clear();
                    secuenciaActual.add(cartaActual);
                }
            }
        }

        if (secuenciaActual.size() >= 3) {
            escaleras.add(secuenciaActual);
        }

        if (escaleras.isEmpty()) {
            return "No se encontraron escaleras de la misma pinta.";
        }

        StringBuilder mensaje = new StringBuilder("Las escaleras que se encontraron fueron:\n");
        for (List<Carta> escalera : escaleras) {
            mensaje.append("Escalera de ").append(escalera.get(0).getPinta()).append(": ");
            for (Carta c : escalera) {
                mensaje.append(c.getNombre()).append(" ");
            }
            mensaje.append("\n");
        }

        return mensaje.toString();
    }

    // Método para calcular el puntaje de las cartas que no están en grupos
    public int calcularPuntaje() {
        List<Carta> cartasEnGrupos = new ArrayList<>();
        int[] contadores = new int[NombreCarta.values().length];

        for (Carta c : cartas) {
            contadores[c.getNombre().ordinal()]++;
        }

        // Identificar cartas que están en grupos (pares o más).
        for (int i = 0; i < contadores.length; i++) {
            if (contadores[i] >= 2) {
                for (Carta c : cartas) {
                    if (c.getNombre() == NombreCarta.values()[i]) {
                        cartasEnGrupos.add(c);
                    }
                }
            }
        }

        // Cartas que no están en grupos
        int puntaje = 0;
        for (Carta c : cartas) {
            if (!cartasEnGrupos.contains(c)) {
                puntaje += obtenerValorCarta(c);
            }
        }

        return puntaje;
    }

    // Método privado para obtener el valor de una carta
    private int obtenerValorCarta(Carta carta) {
        return switch (carta.getNombre()) {
            case AS, JACK, QUEEN, KING -> 10;
            default -> carta.getNombre().ordinal() + 1;
        };
    }
}




