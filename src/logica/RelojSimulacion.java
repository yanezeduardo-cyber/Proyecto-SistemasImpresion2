package logica;

/**
 * Representa el reloj global del sistema operativo para la simulación de la cola.
 * Genera las etiquetas de tiempo base y aplica los modificadores matemáticos
 * de prioridad y urgencia para determinar la posición final de un documento 
 * en el Montículo Binario (Min-Heap).
 * * @author [Tu Nombre/Matrícula Aquí]
 */
public class RelojSimulacion {
    private int contador;

    /**
     * Constructor que inicializa el reloj de la simulación en cero.
     */
    public RelojSimulacion() {
        this.contador = 0;
    }

    /**
     * Avanza el contador del reloj en una unidad de tiempo.
     * Debe llamarse cada vez que ocurra un evento en el sistema (ej. creación de un documento).
     */
    public void avanzar() {
        this.contador++;
    }

    /**
     * Calcula la etiqueta de tiempo final para un documento utilizando una fórmula matemática.
     * Las prioridades más altas reciben descuentos en su tiempo base, generando 
     * etiquetas numéricamente menores, lo que les otorga mayor prioridad en el Min-Heap.
     * * @param prioridad Cadena que indica el nivel del usuario ("prioridad_alta", "prioridad_media", "prioridad_baja").
     * @param esUrgente Bandera booleana que indica si el documento tiene un pase de urgencia extrema.
     * @return El valor entero que se usará para ordenar el documento en la cola.
     */
    public int calcularEtiqueta(String prioridad, boolean esUrgente) {
        int tiempoBase = contador;

        // Validamos y aplicamos el bono según el tipo de usuario
        if (prioridad != null) {
            switch (prioridad.toLowerCase()) {
                case "prioridad_alta":
                    tiempoBase -= 1000; // Sube radicalmente en la cola
                    break;
                case "prioridad_media":
                    tiempoBase -= 500;  // Sube de forma moderada
                    break;
                case "prioridad_baja":
                default:
                    // No se resta nada, compite únicamente por su tiempo de llegada
                    break;
            }
        }

        // Si el documento está marcado como urgente, recibe un bono masivo adicional
        if (esUrgente) {
            tiempoBase -= 2000;
        }

        return tiempoBase;
    }

    /**
     * Obtiene el valor cronológico actual del reloj (sin modificadores).
     * * @return El valor entero del contador.
     */
    public int getContador() {
        return contador;
    }

    /**
     * Reinicia el contador del reloj a cero. 
     * Útil para reiniciar la simulación sin tener que crear una nueva instancia.
     */
    public void reiniciar() {
        this.contador = 0;
    }
}