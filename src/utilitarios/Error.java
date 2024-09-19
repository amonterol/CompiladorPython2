/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilitarios;

import java.util.HashMap;

/**
 *
 * @author abmon
 */
public class Error {
    
     HashMap<Integer, String> tiposDeErrores = new HashMap();

    public Error() {

        tiposDeErrores.put(100, "No existe el archivo con el programa a analizar.");
        tiposDeErrores.put(101, "Solo se permite analizar un archivo con el código fuente.");
        tiposDeErrores.put(102, "La extensión del archivo para analizar debe ser .py ");
        tiposDeErrores.put(103, "El archivo adjunto no contiene un programa para analizar.");
     
    }

    public String obtenerDescripcionDeError(Integer key) {
        return tiposDeErrores.get(key);
    }
}
