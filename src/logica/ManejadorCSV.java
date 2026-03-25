package logica;

import modelo.Usuario;
import estructuras.TablaHash;
import modelo.RegistroCola;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Se encarga de la lectura y procesamiento de archivos externos (CSV/TXT).
 * Contiene la lógica para extraer datos de usuarios y documentos, validarlos,
 * e introducirlos en el sistema y las estructuras de datos correspondientes.
 * * @author [Tu Nombre/Matrícula Aquí]
 */
public class ManejadorCSV {

    // Lista inmutable de prioridades válidas para el sistema
    private static final List<String> PRIORIDADES_VALIDAS = Arrays.asList(
        "prioridad_alta", "prioridad_media", "prioridad_baja"
    );

    /**
     * Lee un archivo CSV línea por línea y registra a los usuarios en la Tabla Hash.
     * Ignora las líneas con formatos incorrectos o prioridades no reconocidas.
     * * @param rutaArchivo La ubicación física del archivo en el sistema operativo.
     * @param tabla La instancia de la Tabla Hash donde se almacenarán los usuarios.
     * @return El número total de usuarios registrados con éxito.
     */
    public int cargarUsuarios(String rutaArchivo, TablaHash tabla) {
        int contadorUsuarios = 0;
        String linea;

        // Utilizamos try-with-resources para asegurar el cierre del archivo
        try (BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))) {
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                if (datos.length == 2) {
                    String nombre = datos[0].trim();
                    String prioridad = datos[1].trim().toLowerCase();

                    // Validación de integridad de datos
                    if (!PRIORIDADES_VALIDAS.contains(prioridad)) {
                        System.err.println("Prioridad inválida para usuario '" + nombre + "': " + prioridad);
                        continue; // Salta este registro y sigue con el próximo
                    }

                    Usuario nuevoUsuario = new Usuario(nombre, prioridad);
                    RegistroCola registroInicial = null; // Al crearse, el usuario no tiene documentos

                    // Inserta al usuario en la Tabla Hash con complejidad O(1)
                    tabla.insertar(nuevoUsuario, registroInicial);

                    contadorUsuarios++;
                    System.out.println("Cargado: " + nombre + " (" + prioridad + ")");
                } else {
                    System.err.println("Línea inválida (no tiene 2 campos separados por coma): " + linea);
                }
            }
        } catch (IOException e) {
            System.err.println("Error fatal al leer el archivo de usuarios: " + e.getMessage());
        }

        return contadorUsuarios;
    }

    /**
     * Procesa un archivo de peticiones de impresión y las envía a la cola del sistema.
     * El formato esperado por línea es: NombreDocumento,TipoDocumento,NombreUsuario
     * * @param ruta La ubicación física del archivo en el sistema.
     * @param sistema La instancia central del sistema que maneja la lógica de encolado.
     */
    public void procesarArchivoImpresion(String ruta, SistemaImpresion sistema) {
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.isEmpty()) continue; // Ignora líneas en blanco

                String[] datos = linea.split(",");
                
                // Traza de depuración en consola
                System.out.println("Línea leída: " + linea + " | Columnas encontradas: " + datos.length);

                if (datos.length >= 3) {
                    String nombreDoc = datos[0].trim();
                    String tipoDoc = datos[1].trim();
                    String nombreUsuario = datos[2].trim();

                    if (!nombreUsuario.isEmpty()) {
                        // Asignamos un tamaño por defecto (100) ya que el CSV no lo provee en este formato
                        modelo.Documento doc = new modelo.Documento(nombreDoc, 100, tipoDoc, nombreUsuario);
                        sistema.enviarAImprimir(doc);
                    }
                } else {
                    System.err.println("❌ Error de formato en línea: " + linea + ". ¿Usaste comas para separar los 3 campos?");
                }
            }
        } catch (IOException e) {
            System.err.println("Error al procesar el archivo de impresiones: " + e.getMessage());
        }
    }
}