# Proyecto 2 – Sistema de Gestión de Impresiones (PriorityPrint)

**Materia:** Estructura de Datos
**Grupo:** 2
**Integrantes:**
* Santaella William
* Yanez Eduardo 
**Lenguaje:** Java
**Entorno:** NetBeans IDE / JDK 
**Fecha:** 25/03/2026
**Repositorio:** [Enlace a tu GitHub]

---

## Descripción General

Este proyecto implementa un simulador de cola de impresión inteligente, donde los documentos se organizan automáticamente según el tiempo de llegada y el nivel de prioridad del usuario (Alta, Media, Baja).
El sistema está respaldado por un Montículo Binario (Min-Heap) para la gestión de la cola y una Tabla de Dispersión (Hash Table) para el acceso inmediato a los datos.
La aplicación incluye una interfaz gráfica (Java Swing) que permite:
* Cargar usuarios e impresiones desde archivos `.csv`.
* Enviar documentos a la cola asignando etiquetas.
* Simular la liberación de la impresora (imprimir el documento más prioritario).
* Cancelar impresiones encoladas en tiempo constante $O(1)$ sin recorrer la cola.
* Visualizar la cola de impresión en formato de lista y en estructura de árbol.

---

## 🗂️ Estructura del Proyecto

```text
proyecto2/
└── src/
    ├── Usuario.java              # TDA para los datos del usuario
    ├── Documento.java            # TDA para los datos del documento
    ├── RegistroCola.java         # Nodo que encapsula el documento y su etiqueta de tiempo
    ├── TablaHash.java            # Diccionario para búsqueda de usuarios en O(1)
    ├── MonticuloBinario.java     # Cola de prioridad (Min-Heap) en O(log n)
    ├── VentanaPrincipal.java     # Interfaz gráfica principal (JFrame)
    ├── ManejoArchivos.java       # Lógica de JFileChooser y lectura de CSV
    ├── usuarios.csv              # Archivo de prueba de usuarios
    └── impresiones.csv           # Archivo de prueba de documentos

## ⚙️ Requerimientos del Proyecto

### Requerimientos Funcionales

| Código | Descripción | Estado |
| :---: | :--- | :---: |
| **A** | **Cargar archivos:** Permitir la carga masiva de usuarios y sus prioridades desde un archivo `.csv` utilizando el componente `JFileChooser`. | ✅ |
| **B** | **Gestión de Usuarios:** Agregar usuarios con un nivel de prioridad, y poder eliminarlos (eliminando sus documentos creados que aún no estén en cola). | ✅ |
| **C** | **Gestión de Documentos:** Crear documentos (nombre, tamaño, tipo) asociados a un usuario. Permitir eliminar un documento si este aún no ha sido enviado a imprimir. | ✅ |
| **D** | **Enviar a Imprimir (Encolar):** Generar un registro con los datos del documento y una etiqueta de tiempo. Si es prioritario, la etiqueta se altera matemáticamente según el tipo de usuario antes de insertarse en el Montículo Binario. | ✅ |
| **E** | **Visualización del Sistema:** Observar en todo momento a los usuarios, sus documentos (diferenciando cuáles están en cola) y tener dos vistas de la cola de impresión: secuencia de registros y estructura de árbol. | ✅ |
| **F** | **Liberar Impresora:** Simular el avance de la cola extrayendo el documento con la etiqueta de tiempo más pequeña (equivalente a la primitiva `eliminar_min` del Montículo). | ✅ |
| **G** | **Cancelar Impresión en Cola:** Dado un usuario, ubicar su documento mediante la **Tabla de Dispersión en $O(1)$**, alterar su etiqueta de tiempo al valor mínimo para subirlo al inicio del Montículo y aplicar `eliminar_min` sin imprimirlo. | ✅ |
| **H** | **Reloj del Sistema:** Contar con un reloj/contador que mida el tiempo (o el orden de llegada) desde la inicialización de la simulación para generar las etiquetas base. | ✅ |

### Requerimientos Técnicos y Criterios de Evaluación

| # | Descripción | Estado |
| :---: | :--- | :---: |
| **1** | **Estructuras Híbridas (Sin librerías):** Implementación propia del TDA Montículo Binario y el TDA Tabla de Dispersión (Hash Table) desde cero. | ✅ |
| **2** | **Complejidad Algorítmica:** La inserción y liberación en la cola operan en $O(\log n)$, mientras que la búsqueda de usuarios para cancelación opera en tiempo cercano a $O(1)$. | ✅ |
| **3** | **Interfaz Gráfica Obligatoria:** Toda la interacción del usuario se realiza a través de una GUI en Java Swing (no por consola). | ✅ |
| **4** | **Representación Visual:** Capacidad de representar el Montículo Binario de manera gráfica (estructura de árbol). | ✅ |
| **5** | **Robustez y Tolerancia a Fallos:** El sistema no colapsa ante datos erróneos, conversiones de tipos (incompatible types) o manipulaciones inadecuadas de la interfaz. | ✅ |
| **6** | **Documentación:** Código fuente documentado utilizando el estándar Javadoc. | ✅ |
| **7** | **Arquitectura:** Inclusión de un Diagrama de Clases detallado que explica la solución híbrida (Hash + Heap). | ✅ |
| **8** | **Control de Versiones:** Repositorio en GitHub público válido, reflejando commits consistentes de todos los integrantes del equipo. | ✅ |

### Conclusión

Todos los requerimientos funcionales y técnicos establecidos en el enunciado del proyecto han sido implementados y verificados con éxito. 
El simulador ofrece una experiencia visual completa mediante una interfaz gráfica desarrollada en Java Swing. A nivel arquitectónico, el sistema destaca por la integración híbrida de dos Estructuras de Datos Avanzadas: 
1. Un **Montículo Binario (Min-Heap)** construido desde cero, que garantiza el manejo de la cola de impresión y la extracción del elemento mínimo en tiempo $O(\log n)$.
2. Una **Tabla de Dispersión (Hash Table)**, que rompe la limitación tradicional de búsqueda en los árboles, permitiendo localizar a los usuarios y sus documentos en un tiempo cercano a $O(1)$ para su cancelación eficiente.

El programa es tolerante a fallos, maneja correctamente las excepciones de lectura de archivos y valida la entrada de datos, cumpliendo estrictamente con las restricciones académicas (cero uso de librerías externas para los TDA lógicos).

---

## 🖥️ Instrucciones de Uso

**1. Ejecutar el programa**
* Abre el proyecto en NetBeans IDE.
* Ejecuta el archivo de la clase principal (ej. `VentanaPrincipal.java`).

**2. Cargar datos iniciales (Archivos CSV)**
* En la interfaz principal, utiliza el botón designado para **"Cargar Usuarios"**. Se abrirá un `JFileChooser` donde deberás seleccionar el archivo `.csv`, donde primero se debe escoger el archivo "usuario" y luego el archivo "impresiones"  con el formato requerido (`usuario, tipo`).
* *(Opcional)* Carga los documentos de prueba si tienes un archivo configurado para ello.
** Si desea registrar usuario, en el cuadro de arriba deberá colocar un nombre (ej. Carlos), y en el cuadro de abajo la prioridad (prioridad_baja, prioridad_media, prioridad_alta)


**3. Interacción con la Cola de Impresión**
* **Enviar a imprimir:** Selecciona un usuario y un documento creado, e indícale al sistema que lo envíe a la cola. El programa le asignará automáticamente una etiqueta de tiempo basada en el reloj del sistema y su nivel de prioridad.
* **Liberar Impresora (Imprimir siguiente):** Haz clic en el botón de imprimir. El sistema extraerá automáticamente el documento con la etiqueta de tiempo más baja (mayor prioridad) utilizando la primitiva `eliminar_min` del Montículo Binario, y actualizará la tabla visual.

**4. Cancelar un documento en cola**
* Ubica el campo de texto inferior etiquetado como **"usuario a eliminar:"**.
* Escribe el nombre exacto del usuario cuyo documento deseas cancelar (tal como aparece en la tabla).
* Haz clic en **"Cancelar"**. El sistema ubicará el documento mediante la Tabla Hash, forzará su subida a la cima del montículo y lo eliminará sin imprimirlo.

**5. Visualización del Montículo**
* El sistema ofrece la vista de la cola en formato de tabla (secuencia de registros).
* Para ver la estructura interna, navega a la pestaña o botón de **"Ver Árbol"** para visualizar el Montículo Binario representado gráficamente, comprobando que se cumplen las propiedades de orden y forma.
