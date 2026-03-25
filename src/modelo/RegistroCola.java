package modelo;

/**
 * Representa un registro individual dentro de la cola de impresión (Montículo Binario).
 * Cumple con la restricción de NO almacenar información directa sobre el propietario
 * del documento, basándose únicamente en la etiqueta de tiempo para determinar su prioridad.
 * * @author [Tu Nombre/Matrícula Aquí]
 */
public class RegistroCola {
    private Documento documento;
    private int etiquetaTiempo;
    private int indiceHeap;

    /**
     * Constructor del registro para la cola de impresión.
     * * @param documento El documento que se va a encolar.
     * @param etiquetaTiempo El valor numérico de prioridad (tiempo real modificado por el bono del usuario).
     */
    public RegistroCola(Documento documento, int etiquetaTiempo) {
        this.documento = documento;
        this.etiquetaTiempo = etiquetaTiempo;
    }

    /**
     * Obtiene el documento asociado a este registro.
     * * @return El objeto Documento.
     */
    public Documento getDocumento() {
        return documento;
    }
    
    /**
     * Devuelve el valor numérico utilizado por el Montículo Binario para ordenar los nodos.
     * * @return El valor de la etiqueta de tiempo.
     */
    public int getValorPrioridad() {
        return this.etiquetaTiempo; 
    }

    /**
     * Reemplaza el documento del registro.
     * * @param documento El nuevo documento a asignar.
     */
    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    /**
     * Obtiene la etiqueta de tiempo actual del registro.
     * * @return El valor entero de la etiqueta de tiempo.
     */
    public int getEtiquetaTiempo() {
        return etiquetaTiempo;
    }

    /**
     * Modifica la etiqueta de tiempo del registro.
     * Útil para la operación de eliminación (Req 10.2) donde se debe cambiar la prioridad
     * a un valor muy bajo para que el nodo suba a la raíz.
     * * @param etiquetaTiempo El nuevo valor de tiempo/prioridad.
     */
    public void setEtiquetaTiempo(int etiquetaTiempo) {
        this.etiquetaTiempo = etiquetaTiempo;
    }

    /**
     * Compara este registro con otro basándose en su etiqueta de tiempo.
     * Útil para mantener la propiedad de orden del Montículo Binario.
     * * @param otro El registro con el cual se va a comparar.
     * @return Un valor negativo si este registro es menor, 0 si son iguales, o positivo si es mayor.
     */
    public int compararPorTiempo(RegistroCola otro) {
        return Integer.compare(this.etiquetaTiempo, otro.etiquetaTiempo);
    }

    /**
     * Devuelve una descripción detallada del registro para auditoría.
     * * @return Cadena con el documento y su etiqueta de tiempo.
     */
    public String descripcionCompleta() {
        return "Documento: " + documento.toString() + " | Etiqueta de tiempo: " + etiquetaTiempo;
    }
    
    public int getIndiceHeap() {
        return indiceHeap;
    }

    public void setIndiceHeap(int indiceHeap) {
        this.indiceHeap = indiceHeap;
    }
    /**
     * Representación simplificada del registro.
     * * @return Formato corto del nombre del documento y su tiempo.
     */
    @Override
    public String toString() {
        return documento.getNombre() + "." + documento.getTipo() + " [Tiempo: " + etiquetaTiempo + "]";
    }
}