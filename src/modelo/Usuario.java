package modelo;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

/**
 * Representa a un usuario del sistema operativo que puede crear documentos
 * y enviarlos a la cola de impresión.
 * * @author [Tu Nombre/Matrícula Aquí]
 */
public class Usuario {
    private String nombreUsuario;
    private String tipoPrioridad; 
    
    
    /** Lista estática de prioridades permitidas en el sistema. */
    private static final List<String> PRIORIDADES_VALIDAS = Arrays.asList("prioridad_alta", "prioridad_media", "prioridad_baja");
    
    /** Lista de documentos creados por el usuario que aún NO han sido enviados a la cola. */
    private List<Documento> documentosBorrador;
    
    /**
     * Constructor de la clase Usuario.
     * * @param nombreUsuario El identificador único del usuario.
     * @param tipoPrioridad El nivel de prioridad asignado (alta, media o baja).
     * @throws IllegalArgumentException si el tipo de prioridad no es válido.
     */
    public Usuario(String nombreUsuario, String tipoPrioridad) {
        this.nombreUsuario = nombreUsuario;
        setTipoPrioridad(tipoPrioridad);
        this.documentosBorrador = new ArrayList<>();
    }
    
    /**
     * Añade un documento a la lista de borradores del usuario.
     * * @param doc El objeto Documento a guardar.
     */
    public void agregarDocumentoBorrador(Documento doc) {
        this.documentosBorrador.add(doc);
    }

    // Getters y Setters
    /**
     * @return El nombre del usuario.
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    /**
     * @param nombreUsuario El nuevo nombre a asignar al usuario.
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    /**
     * @return El tipo de prioridad actual del usuario.
     */
    public String getTipoPrioridad() {
        return tipoPrioridad;
    }
    
    /**
     * Establece y valida el tipo de prioridad del usuario.
     * * @param tipoPrioridad La nueva prioridad a asignar.
     * @throws IllegalArgumentException si la prioridad no está en la lista de PRIORIDADES_VALIDAS.
     */
    public void setTipoPrioridad(String tipoPrioridad) {
        if (!PRIORIDADES_VALIDAS.contains(tipoPrioridad)) {
            throw new IllegalArgumentException("Tipo de prioridad inválido. Debe ser: " + PRIORIDADES_VALIDAS);
        }
        this.tipoPrioridad = tipoPrioridad;
    }
    
    /**
     * Elimina un documento de la lista de borradores (antes de enviarlo a imprimir).
     * * @param nombreDoc El nombre del documento a eliminar.
     * @return true si el documento fue eliminado con éxito, false en caso contrario.
     */
    public boolean eliminarDocumentoBorrador(String nombreDoc) {
        return documentosBorrador.removeIf(d -> d.getNombre().equals(nombreDoc));
    }
    
    /**
     * @return La lista actual de documentos en borrador.
     */
    public List<Documento> getDocumentosBorrador() {
        return documentosBorrador;
    }
    
    /** * Convierte el texto de la prioridad en un "Bono de tiempo" matemático
     * para alterar la etiqueta de tiempo en la cola de impresión.
     * * @return La cantidad de unidades de tiempo a descontar de la etiqueta original.
     */
    public int getBonoPrioridad() {
        switch (this.tipoPrioridad) {
            case "prioridad_alta":
                return 1000; // Resta 1000 al reloj (Sube mucho en la cola)
            case "prioridad_media":
                return 500;  // Resta 500 al reloj (Sube un poco)
            case "prioridad_baja":
            default:
                return 0;    // No resta nada (Compite normalmente)
        }
    }
    
    /**
     * Representación en formato cadena de texto del usuario.
     * * @return Nombre del usuario acompañado de su tipo de prioridad.
     */
    @Override
    public String toString() {
        return nombreUsuario + " (" + tipoPrioridad + ")";
    }
}   