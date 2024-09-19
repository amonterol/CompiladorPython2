/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiladorpython;


import javax.swing.JOptionPane;
import utilitarios.Error;

/**
 *
 * @author abmon
 */
public class Lexico {

    private final Error listaDeErrores = new Error();

    private String[] args = null;
    //Se usa para construir el archivo de salida cuando se ha terminado el análisis léxico
    private static String archivoAdjunto = "";
  

    public static String nombreDePrograma = " ";
    public static boolean nombreDeProgramaEsIdentificadorValido = true;

    public Lexico(String[] args) {
        this.args = args;
    }

    public void analizarPrograma() {

        //Valida si se adjunta un único archivo con código fuente al programa
        if (validarExistenciaArchivoInicial(this.args)) {
            archivoAdjunto = args[0];
            System.out.println(" 38 BORRAR: Este es el archivo " + archivoAdjunto);

            //Valida que la extensión del archivo sea .vb
            if (!validarExtensionArchivoInicial(archivoAdjunto)) {
                JOptionPane.showMessageDialog(null, listaDeErrores.obtenerDescripcionDeError(102), "Código Fuente", JOptionPane.WARNING_MESSAGE);
                System.out.println(listaDeErrores.obtenerDescripcionDeError(102));
                System.exit(0);
            } else {
                String nombrePrograma = archivoAdjunto.substring(0, archivoAdjunto.indexOf('.'));
                System.out.println(" 47 BORRAR: ESTE ES EL NOMBRE DEL PROGRAM SI SU EXTENSION " + nombrePrograma + " " + archivoAdjunto);
            }
        } else {
            System.exit(0);
        }

    }

    public boolean validarExistenciaArchivoInicial(String[] args) {

        if (args.length == 0) {
            JOptionPane.showMessageDialog(null, listaDeErrores.obtenerDescripcionDeError(100), "Código Fuente", JOptionPane.WARNING_MESSAGE);
            System.out.println("59 BORRAR " + listaDeErrores.obtenerDescripcionDeError(100));
            return false;
        } else if (args.length > 1) {
            JOptionPane.showMessageDialog(null, listaDeErrores.obtenerDescripcionDeError(101), "Código Fuente", JOptionPane.WARNING_MESSAGE);
            System.out.println("63 Borrar " + listaDeErrores.obtenerDescripcionDeError(101));
            return false;
        } else {
            System.out.println("66 BORRAR: Este es el archivo " + args[0]);
            return true;

        }

    }

    public boolean validarExtensionArchivoInicial(String archivoAdjunto) {

        if (!archivoAdjunto.toLowerCase().endsWith(".py")) {

            return false;

        } else {
            return true;
        }

    }
}
