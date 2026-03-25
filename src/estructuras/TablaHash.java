package estructuras;

import modelo.RegistroCola;
import modelo.Usuario;

/**
 * Implementación de una Tabla de Dispersión (Hash Table) desde cero.
 * Utiliza el método de Encadenamiento (Chaining) para la resolución de colisiones.
 * Cumple con el requerimiento de garantizar un acceso cercano a O(1) para buscar
 * a los usuarios y sus documentos encolados.
 * * @author [Tu Nombre/Matrícula Aquí]
 */
public class TablaHash {
    private NodoHash[] tabla;
    private int tamaño;
    private final int CAPACIDAD = 101; // Número primo para mejorar la distribución uniforme

    /**
     * Constructor que inicializa el arreglo de la tabla hash con una capacidad fija.
     */
    public TablaHash() {
        this.tabla = new NodoHash[CAPACIDAD];
        this.tamaño = 0;
    }

    /**
     * Función de dispersión matemática que convierte el nombre del usuario en un índice.
     * Utiliza un algoritmo de suma ponderada polinomial (base 31) para minimizar colisiones.
     * * @param clave El nombre de usuario que sirve como llave primaria.
     * @return El índice calculado dentro de los límites del arreglo (0 a CAPACIDAD - 1).
     */
    private int funcionHash(String clave) {
        // Tolerancia a fallos: Si por error llega un null, devolvemos un índice por defecto (0)
        // evitando que el programa se cierre inesperadamente.
        if (clave == null) return 0; 

        int hash = 0;
        for (int i = 0; i < clave.length(); i++) {
            hash = (31 * hash + clave.charAt(i)) % CAPACIDAD;
        }
        return Math.abs(hash); // Asegura que no haya índices negativos
    }

    /**
     * Inserta o actualiza un usuario y su documento en la tabla hash.
     * Maneja colisiones enlazando los nodos mediante referencias (encadenamiento).
     * * @param usuario El objeto de tipo Usuario a almacenar.
     * @param registro El registro del documento encolado asociado a ese usuario.
     */
    public void insertar(Usuario usuario, RegistroCola registro) {
        String nombreUsuario= usuario.getNombreUsuario();
        int indice = funcionHash(nombreUsuario);
        NodoHash actual = tabla[indice];

        // Si el usuario ya existe, actualizamos el registro (Nota: A futuro esto se puede
        // adaptar para permitir múltiples registros por usuario en una lista).
        while (actual != null) {
            if (actual.getNombreUsuario().equals(nombreUsuario)) {
                actual.setRegistro(registro);
                return;
            }
            actual = actual.getSiguiente();
        }

        // Si no existe, insertamos el nuevo nodo al inicio de la lista enlazada (O(1))
        NodoHash nuevo = new NodoHash(usuario, registro);
        nuevo.setSiguiente(tabla[indice]);
        tabla[indice] = nuevo;
        tamaño++;
    }

    /**
     * Busca un usuario en la tabla hash a partir de su nombre con complejidad O(1) promedio.
     * * @param nombreUsuario El identificador único del usuario a buscar.
     * @return El NodoHash correspondiente si se encuentra, o null si no existe.
     */
    public NodoHash buscar(String nombreUsuario) {
        int indice = funcionHash(nombreUsuario);
        NodoHash actual = tabla[indice];
        while (actual != null) {
            if (actual.getNombreUsuario().equals(nombreUsuario)) return actual;
            actual = actual.getSiguiente();
        }
        return null;
    }

    /**
     * Elimina un usuario de la tabla hash y reestructura los enlaces de colisión si es necesario.
     * * @param nombreUsuario El nombre del usuario a dar de baja.
     * @return true si la eliminación fue exitosa, false si el usuario no fue encontrado.
     */
    public boolean eliminar(String nombreUsuario) {
        int indice = funcionHash(nombreUsuario);
        NodoHash actual = tabla[indice];
        NodoHash anterior = null;

        while (actual != null) {
            if (actual.getNombreUsuario().equals(nombreUsuario)) {
                if (anterior == null) {
                    // Si es el primer nodo de la lista, la cabeza pasa a ser el siguiente
                    tabla[indice] = actual.getSiguiente();
                } else {
                    // Si está en el medio, "saltamos" el nodo para desconectarlo
                    anterior.setSiguiente(actual.getSiguiente());
                }
                tamaño--;
                return true;
            }
            anterior = actual;
            actual = actual.getSiguiente();
        }
        return false;
    }

    /**
     * Devuelve la cantidad de usuarios únicos actualmente registrados en la tabla.
     * * @return El número de elementos almacenados.
     */
    public int getTamaño() {
        return tamaño;
    }

    /**
     * Imprime por consola el estado interno del arreglo de la tabla hash.
     * Muy útil para auditar visualmente cómo se están resolviendo las colisiones.
     */
    public void imprimirTabla() {
        for (int i = 0; i < CAPACIDAD; i++) {
            NodoHash actual = tabla[i];
            if (actual != null) {
                System.out.print("Índice " + i + ": ");
                while (actual != null) {
                    System.out.print("[" + actual.getNombreUsuario() + " -> " + actual.getRegistro() + "] -> ");
                    actual = actual.getSiguiente();
                }
                System.out.println("null");
            }
        }
    }
}