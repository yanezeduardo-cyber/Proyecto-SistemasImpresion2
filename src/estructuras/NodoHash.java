package estructuras;

import modelo.Usuario; 
import modelo.RegistroCola;

/**
 * Representa un nodo individual dentro de la Tabla de Dispersión.
 * Actúa como un contenedor que enlaza a un usuario con su documento en la cola,
 * y mantiene una referencia al siguiente nodo para resolver colisiones por encadenamiento.
 * * @author [Tu Nombre/Matrícula Aquí]
 */
public class NodoHash {
    private Usuario usuario; 
    private RegistroCola registro;
    private NodoHash siguiente;

    /**
     * Constructor principal que inicializa el nodo con datos válidos.
     * * @param usuario El objeto Usuario propietario del documento.
     * @param registro El registro del documento que fue enviado a la cola.
     */
    public NodoHash(Usuario usuario, RegistroCola registro) {
        this.usuario = usuario; 
        this.registro = registro;
        this.siguiente = null;
    }

    /**
     * Constructor por defecto que inicializa un nodo vacío.
     */
    public NodoHash() {
        this.usuario = null;
        this.registro = null;
        this.siguiente = null;
    }

    /**
     * Obtiene el nombre del usuario de forma segura, evitando errores si el usuario es nulo.
     * * @return El nombre del usuario o "Sin nombre" si no hay usuario asignado.
     */
    public String getNombreUsuario() {
        return (usuario != null) ? usuario.getNombreUsuario() : "Sin nombre";
    }

    /**
     * Obtiene la referencia al objeto Usuario almacenado en este nodo.
     * * @return El objeto Usuario.
     */
    public Usuario getUsuario() {
        return usuario;
    }
    
    /**
     * Obtiene el registro de la cola asociado a este usuario.
     * * @return El objeto RegistroCola.
     */
    public RegistroCola getRegistro() {
        return registro;
    }

    /**
     * Obtiene el siguiente nodo en la lista enlazada (usado para resolver colisiones).
     * * @return El siguiente NodoHash, o null si es el último de la cadena.
     */
    public NodoHash getSiguiente() {
        return siguiente;
    }

    /**
     * Establece el enlace hacia el siguiente nodo en caso de colisión.
     * * @param siguiente El NodoHash que seguirá a este en la lista.
     */
    public void setSiguiente(NodoHash siguiente) {
        this.siguiente = siguiente;
    }

    /**
     * Actualiza o asigna un nuevo registro de documento a este nodo.
     * * @param registro El nuevo RegistroCola a almacenar.
     */
    public void setRegistro(RegistroCola registro) {
        this.registro = registro;
    }

    /**
     * Compara de forma segura si el nombre proporcionado coincide con el del usuario de este nodo.
     * Incluye tolerancia a fallos verificando que ni el usuario interno ni el parámetro sean nulos.
     * * @param nombre El nombre de usuario a buscar.
     * @return true si los nombres coinciden exactamente, false en caso contrario.
     */
    public boolean equalsPorNombre(String nombre) {
        if (usuario == null || nombre == null) return false;
        return this.usuario.getNombreUsuario().equals(nombre); // Comparamos contra el getter de Usuario
    }

    /**
     * Genera una representación en formato texto del nodo para facilitar la depuración.
     * * @return Cadena de texto con los datos del usuario y su registro.
     */
    @Override
    public String toString() {
        return "NodoHash{" +
                "usuario=" + (usuario != null ? usuario.getNombreUsuario() : "null") +
                ", registro=" + registro +
                '}';
    }
}