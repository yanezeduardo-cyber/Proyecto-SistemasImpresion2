package logica;

import estructuras.MonticuloBinario;
import estructuras.NodoHash;
import estructuras.TablaHash;
import modelo.Documento;
import modelo.RegistroCola;
import modelo.Usuario;

/**
 * Clase principal que orquesta la lógica de negocio del sistema de impresión.
 * Conecta las estructuras de datos (Tabla Hash y Montículo Binario) con la 
 * simulación de tiempo y la gestión de usuarios.
 * @author [Tu Nombre/Matrícula Aquí]
 */
public class SistemaImpresion {
    private TablaHash tablaUsuarios;
    private MonticuloBinario colaPrioridad;
    private RelojSimulacion reloj;

    /**
     * Constructor con capacidad configurable para la cola de prioridad.
     * @param capacidadCola Capacidad máxima de documentos en cola.
     */
    public SistemaImpresion(int capacidadCola) {
        this.tablaUsuarios = new TablaHash();
        this.colaPrioridad = new MonticuloBinario(capacidadCola);
        this.reloj = new RelojSimulacion();
    }

    /**
     * Constructor por defecto con capacidad de 100 documentos en cola.
     */
    public SistemaImpresion() {
        this(100);
    }

    /**
     * Registra un nuevo usuario en la tabla hash.
     * @param nombreUsuario Nombre del usuario.
     * @param prioridad Tipo de prioridad ("prioridad_alta", "prioridad_media", "prioridad_baja").
     */
    public void registrarUsuario(String nombreUsuario, String prioridad) {
        Usuario nuevoU = new Usuario(nombreUsuario, prioridad);
        tablaUsuarios.insertar(nuevoU, null); // RegistroCola inicia en null
        System.out.println("Usuario registrado: " + nombreUsuario + " con prioridad " + prioridad);
    }
    
    /**
     * Elimina un usuario del sistema (sus documentos en cola no se ven afectados directamente aquí).
     * @param nombreUsuario Nombre del usuario a dar de baja.
     */
    public void eliminarUsuarioSistema(String nombreUsuario) {
        if (tablaUsuarios.buscar(nombreUsuario) != null) {
            boolean exito = tablaUsuarios.eliminar(nombreUsuario);
            if (exito) {
                System.out.println("Usuario '" + nombreUsuario + "' eliminado con éxito.");
                System.out.println("Sus documentos en cola permanecen ahí.");
            }
        } else {
            System.err.println("Error: El usuario '" + nombreUsuario + "' no existe en el sistema.");
        }
    }
    
    /**
     * Elimina un documento que un usuario guardó como borrador (no encolado).
     */
    public void cancelarDocumentoBorrador(String nombreUsuario, String nombreDoc) {
        NodoHash nodo = tablaUsuarios.buscar(nombreUsuario);
        if (nodo != null) {
            boolean eliminado = nodo.getUsuario().eliminarDocumentoBorrador(nombreDoc);
            if (eliminado) {
                System.out.println("Documento '" + nombreDoc + "' eliminado de los borradores.");
            } else {
                System.err.println("Documento no encontrado en borradores.");
            }
        }
    }
        
    /**
     * Envía un documento a imprimir, calculando su prioridad y posicionándolo en el Montículo.
     * @param nombreUsuario Nombre del usuario que envía el documento.
     * @param nombreDoc Nombre del documento.
     * @param tipoDoc Tipo del documento (ej. "PDF", "Word").
     * @param tamañoDoc Tamaño del documento.
     */
    public void enviarAImprimir(String nombreUsuario, String nombreDoc, String tipoDoc, int tamañoDoc) {
        System.out.println("Intentando buscar usuario: [" + nombreUsuario + "]");
        NodoHash nodo = tablaUsuarios.buscar(nombreUsuario);

        if (nodo != null) {
            Usuario user = nodo.getUsuario();
            Documento doc = new Documento(nombreDoc, tamañoDoc, tipoDoc, nombreUsuario);
            reloj.avanzar();

            String prioridadReal = user.getTipoPrioridad();
            
            // Calculamos la etiqueta basándonos en el Reloj y la Prioridad
            int etiqueta = reloj.calcularEtiqueta(prioridadReal, false);
            RegistroCola nuevoRegistro = new RegistroCola(doc, etiqueta);
            
            // 1. Insertamos en el Montículo (O(log n))
            colaPrioridad.insertar(nuevoRegistro);
            
            // 2. Actualizamos el puntero en la Tabla Hash (O(1))
            nodo.setRegistro(nuevoRegistro);

            System.out.println("Documento encolado: " + nombreDoc + " para " + nombreUsuario + " con etiqueta " + etiqueta);
        } else {
            System.err.println("Usuario no registrado: " + nombreUsuario);
        }
    }
    
    /**
     * Sobrecarga del método enviarAImprimir que recibe un objeto Documento directamente.
     * @param doc Objeto Documento a encolar.
     */
    public void enviarAImprimir(Documento doc) {
        this.enviarAImprimir(
            doc.getNombreUsuario(), 
            doc.getNombre(), 
            doc.getTipo(), 
            doc.getTamaño()
        );
    }

    /**
     * Extrae de la cola el documento con mayor prioridad (menor etiqueta de tiempo).
     * @return El documento a imprimir o null si la cola está vacía.
     */
    public Documento procesarImpresion() {
        RegistroCola registro = colaPrioridad.eliminarMin();
        if (registro != null) {
            System.out.println("🖨️ Imprimiendo documento: " + registro.getDocumento().getNombre());
            return registro.getDocumento();
        } else {
            System.out.println("No hay documentos en espera para imprimir.");
            return null;
        }
    }

    /**
     * SOLUCIÓN AL ERROR ROJO (Requerimiento 11).
     * Elimina un documento que ya está en la cola usando búsqueda O(1) con la Tabla Hash.
     * @param nombreUsuario El dueño del documento que deseamos cancelar.
     */
    public void eliminarDocumentoDeCola(String nombreUsuario) {
        NodoHash nodo = tablaUsuarios.buscar(nombreUsuario);

        if (nodo != null && nodo.getRegistro() != null) {
            RegistroCola registroAEliminar = nodo.getRegistro();
            
            // Se le pasa el objeto completo al Montículo
            colaPrioridad.eliminarDocumentoDeCola(registroAEliminar);
            
            // Limpiamos el registro del usuario
            nodo.setRegistro(null);
            
            System.out.println("✅ Documento de " + nombreUsuario + " extraído de la cola correctamente.");
        } else {
            System.err.println("❌ Error: No se encontró ningún documento en cola para: " + nombreUsuario);
        }
    }
    
    // --- Getters para la Interfaz Gráfica ---

    public TablaHash getTablaUsuarios() {
        return tablaUsuarios;
    }

    public MonticuloBinario getColaPrioridad() {
        return colaPrioridad;
    }

    public RelojSimulacion getReloj() {
        return reloj;
    }
    
    /**
     * Extrae todos los elementos para mostrar el orden final de salida.
     * Advertencia: Este método vacía la cola en el proceso.
     */
    public void mostrarOrdenSalida() {
        System.out.println("--- ORDEN DE IMPRESIÓN ACTUAL ---");
        while (!colaPrioridad.estaVacio()) {
            RegistroCola proximo = colaPrioridad.eliminarMin();
            System.out.println(proximo.getDocumento().getNombre() + " | Etiqueta: " + proximo.getEtiquetaTiempo());
        }
    }
}