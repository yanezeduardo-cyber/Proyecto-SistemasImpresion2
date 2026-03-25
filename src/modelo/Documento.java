package modelo;

/**
 * Representa un documento creado por un usuario en el sistema.
 * Contiene la información básica del archivo antes y durante su paso
 * por la cola de impresión.
 * * @author [Tu Nombre/Matrícula Aquí]
 */
public class Documento {
    private String nombre;
    private int tamaño; // Cantidad de páginas o tamaño en KB
    private String tipo;  // Ejemplo: "PDF", "Word", etc.
    private String nombreUsuario;

    /**
     * Constructor de la clase Documento.
     * * @param nombre El nombre del archivo (ej. "Tesis Final").
     * @param tamaño El peso del archivo o cantidad de páginas.
     * @param tipo La extensión o formato del archivo (ej. "PDF").
     * @param nombreUsuario El identificador del usuario que creó este documento.
     */
    public Documento(String nombre, int tamaño, String tipo, String nombreUsuario) {
        this.nombre = nombre;
        this.tamaño = tamaño;
        this.tipo = tipo;
        this.nombreUsuario = nombreUsuario;
    }

    /**
     * Obtiene el nombre del documento.
     * * @return El nombre del archivo.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modifica el nombre del documento.
     * * @param nombre El nuevo nombre a asignar.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el tamaño del documento.
     * * @return El tamaño numérico (páginas o KB).
     */
    public int getTamaño() {
        return tamaño;
    }

    /**
     * Modifica el tamaño del documento, validando que sea un valor lógico.
     * * @param tamaño El nuevo tamaño a asignar.
     * @throws IllegalArgumentException si el tamaño es menor o igual a cero.
     */
    public void setTamaño(int tamaño) {
        if (tamaño <= 0) { // Un documento debe tener al menos 1 página/KB
            throw new IllegalArgumentException("El tamaño debe ser mayor a cero.");
        }
        this.tamaño = tamaño;
    }

    /**
     * Obtiene el tipo o formato del documento.
     * * @return La extensión del archivo.
     */
    public String getTipo() {
        return tipo;
    }
    
    /**
     * Obtiene el nombre del usuario creador del documento.
     * * @return El identificador del usuario propietario.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }

    /**
     * Modifica el tipo o formato del documento.
     * * @param tipo El nuevo formato a asignar (ej. "DOCX").
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    /**
     * Genera una representación en texto de los datos del documento.
     * * @return Cadena de caracteres con el nombre, tipo y usuario propietario.
     */
    @Override
    public String toString() {
        return nombre + " (" + tipo + ") - Usuario: " + nombreUsuario;
    }
}