package estructuras;

import modelo.RegistroCola;

/**
 * Implementación de un Montículo Binario de Mínimo (Min-Heap).
 * Mantiene los documentos ordenados por su etiqueta de tiempo (prioridad).
 * Cumple estrictamente con las restricciones de TDA, evitando recorridos lineales.
 * * @author [Tu Nombre/Matrícula Aquí]
 */
public class MonticuloBinario {
    private RegistroCola[] heap;
    private int tamaño;
    private int capacidad;

    /**
     * Constructor que inicializa el montículo con una capacidad dada.
     * @param capacidad Capacidad máxima del montículo.
     */
    public MonticuloBinario(int capacidad) {
        this.capacidad = capacidad;
        this.tamaño = 0;
        // Índice 0 no se usa para facilitar los cálculos matemáticos de hijos/padres
        this.heap = new RegistroCola[capacidad + 1];
    }

    /**
     * Inserta un nuevo registro y lo posiciona correctamente (Up-heap).
     * Complejidad: O(log n)
     * @param nuevo RegistroCola a insertar.
     * @throws IllegalStateException si el montículo excede su capacidad.
     */
    public void insertar(RegistroCola nuevo) {
        if (tamaño >= capacidad) {
            throw new IllegalStateException("Error: Montículo lleno.");
        }

        tamaño++;
        int actual = tamaño;
        heap[actual] = nuevo;
        nuevo.setIndiceHeap(actual); // Guardamos su "lugar en la cola" (Req 11)

        // Up-heap (flotar)
        while (actual > 1 && heap[actual].compararPorTiempo(heap[actual / 2]) < 0) {
            intercambiar(actual, actual / 2);
            actual = actual / 2;
        }
    }

    /**
     * Elimina y retorna el registro con menor etiqueta de tiempo (raíz).
     * Representa la función "Liberar Impresora" (Req 10.1).
     * Complejidad: O(log n)
     * @return RegistroCola extraído, o null si está vacío.
     */
    public RegistroCola eliminarMin() {
        if (estaVacio()) return null;

        RegistroCola min = heap[1];
        heap[1] = heap[tamaño];
        
        if (tamaño > 1) {
            heap[1].setIndiceHeap(1); // Actualizamos el índice del que subió
        }
        
        heap[tamaño] = null; // Ayuda al recolector de basura
        tamaño--;

        hundir(1);

        return min;
    }
    
    /**
     * Elimina un documento específico desde cualquier posición de la cola.
     * Cumple con el Req 10.2: Cambia la etiqueta a prioridad máxima, lo flota a la raíz
     * y luego aplica eliminarMin. No usa bucles 'for' para buscar (Req 11).
     * Complejidad: O(log n)
     * @param registro El registro a eliminar (obtenido en O(1) desde la Tabla Hash).
     */
    public void eliminarDocumentoDeCola(RegistroCola registro) {
        int pos = registro.getIndiceHeap(); // Obtenemos su lugar exacto sin recorrer el arreglo

        // Validamos que la posición sea correcta y corresponda al registro
        if (pos > 0 && pos <= tamaño && heap[pos] == registro) {
            // Bajamos la etiqueta al mínimo absoluto para que suba a la raíz
            registro.setEtiquetaTiempo(-999999); 
            
            // Flotamos el elemento (Up-heap manual)
            int actual = pos;
            while (actual > 1 && heap[actual].compararPorTiempo(heap[actual / 2]) < 0) {
                intercambiar(actual, actual / 2);
                actual = actual / 2;
            }
            
            // Una vez en la raíz, lo extraemos con la primitiva estándar
            eliminarMin();
            System.out.println("Documento cancelado y extraído de la cola.");
        }
    }
    
    /**
     * Retorna el registro de mayor prioridad sin eliminarlo.
     * @return RegistroCola mínimo o null si está vacío.
     */
    public RegistroCola obtenerMin() {
        if (estaVacio()) return null;
        return heap[1];
    }

    /**
     * Verifica si el montículo está vacío.
     * @return true si no contiene elementos.
     */
    public boolean estaVacio() {
        return tamaño == 0;
    }

    /**
     * Limpia el montículo por completo.
     */
    public void limpiar() {
        for (int i = 1; i <= tamaño; i++) {
            heap[i] = null;
        }
        tamaño = 0;
    }

    /**
     * Restaura la propiedad de orden del montículo moviendo un nodo hacia abajo.
     * @param pos Índice del nodo a hundir.
     */
    private void hundir(int pos) {
        int menor = pos;
        int izq = 2 * pos;
        int der = 2 * pos + 1;

        if (izq <= tamaño && heap[izq].compararPorTiempo(heap[menor]) < 0) {
            menor = izq;
        }
        if (der <= tamaño && heap[der].compararPorTiempo(heap[menor]) < 0) {
            menor = der;
        }

        if (menor != pos) {
            intercambiar(pos, menor);
            hundir(menor);
        }
    }

    /**
     * Intercambia dos nodos en el arreglo y actualiza sus índices internos.
     * Fundamental para cumplir el Req 11 de rastreo O(1).
     */
    private void intercambiar(int i, int j) {
        RegistroCola temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
        
        // ACTUALIZACIÓN CRÍTICA DE ÍNDICES
        heap[i].setIndiceHeap(i);
        heap[j].setIndiceHeap(j);
    }
    
    /**
     * Retorna el arreglo interno para la representación en la interfaz.
     * @return Arreglo de registros.
     */
    public RegistroCola[] getElementos() {
        return this.heap;
    }

    /**
     * Retorna la cantidad de documentos encolados.
     * @return Tamaño del montículo.
     */
    public int getTamaño() {
        return this.tamaño;
    }
}