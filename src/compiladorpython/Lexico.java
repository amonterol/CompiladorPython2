/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compiladorpython;

import utilitarios.Token;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import utilitarios.Archivo;
import utilitarios.Error;
import utilitarios.TipoDeToken;
import utilitarios.Token;

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

    public static int numeroLineaActual = 1;

    private static List<Token> listaDeTokens;
    private static List<String> listaCodigoAnalizado; //Incluye los errores detectados

    public Lexico(String[] args) {
        this.args = args;
    }

    public void analizarPrograma() throws IOException {

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

                Archivo archivo = new Archivo();
                List<String> contenidoArchivo = archivo.leerArchivo(archivoAdjunto);
                System.out.println("\n" + "64 BORRAR: ESTE ES EL CONTENIDO DEL ARCHIVO GATO.PY" + "\n");
                imprimirListas(contenidoArchivo);
                String nombreArchivo = "salida.txt";
                archivo.escribirArchivo(contenidoArchivo, nombreArchivo);
                System.out.println("\n" + " 72 BORRAR: ESTE ES EL CONTENIDO DEL ARCHIVO SALIDA.TXT" + "\n");
                archivo.imprimirArchivo(nombreArchivo);

                try {
                    if (!contenidoArchivo.isEmpty()) {
                        System.out.println("\n 70 BORRAR.- codigo fuente no esta vacio");

                        //System.out.println("\n 72 BORRAR.- INICIO codigo fuente"); R
                        //imprimirContenidoArchivoTexto(contenidoArchivo); //BORRAR
                        //System.out.println("\n1.- FIN codigo fuente"); //BORRAR
                        //Lee el contenido del programa linea por linea
                        listaDeTokens = new ArrayList<>();
                        for (String lineaDeCodigo : contenidoArchivo) {

                            //System.out.println("\n2.- INICIO LINEA DE CODIGO"); //BORRAR
                            //System.out.println(lineaDeCodigo); //BORRAR
                            //System.out.println("\n2.- FIN LINEA DE CODIGO"); //BORRAR
                            
                            if (lineaDeCodigo.isBlank() || lineaDeCodigo.isEmpty()) {
                                System.out.println("\n 90 BORRAR ES UN NUEVA LINEA DE CODIGO ESTA EN BLANCO " + lineaDeCodigo); 
                                agregarNuevoToken("linea en blanco", TipoDeToken.LINEA_EN_BLANC0.toString(), null, numeroLineaActual);
                                System.out.println("\n 90 BORRAR ES UN NUEVA LINEA DE CODIGO ESTA EN BLANCO " + numeroLineaActual); 
                            } else {
                                // System.out.println("\n4.-.- BORRAR> INICIO LINEA DE CODIGO CONVERTIDA A CARACTERES  " + lineaDeCodigo);
                                char[] arregloCaracteres = lineaDeCodigo.toCharArray();
                                // iterar sobre la array `char[]` usando for-loop mejorado
                                for (char ch : arregloCaracteres) {
                                    System.out.print(ch);
                                    System.out.print(" ");
                                }
                                System.out.println(" ");

                                analisisLexico(arregloCaracteres, numeroLineaActual);

                                // numeroDeLineaActual++;
                                // System.out.println("\n4.-.- BORRAR> FIN LINEA DE CODIGO CONVERTIDA A CARACTERES  " + lineaDeCodigo);
                            }
                            ++numeroLineaActual; //Aumenta con cada linea que es analizada
                        }

                       
                    }
                } catch (NullPointerException ex) {
                    System.out.println("No hay lineas que leer en el archivo de codigo fuente" + ex);
                }

            }
        } else {
            System.exit(0);
        }
         System.out.println("\n\n<<< 218 Lexico> CANTIDAD DE TOKENS EN EL LEXICO>>> " + listaDeTokens.size());
                        System.out.println("\n\n<<<CANTIDAD DE TOKENS>>> " + listaDeTokens.size());
                        listaDeTokens.forEach((item) -> {
                            System.out.println(item.getLexema() + " " + item.getTipoDeToken() + " " + item.getValor() + " " + item.getLinea());
                        });

    }

    //Verifica que exista un solo archivo a analizar 
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

    //Verifica que la extension del archivo a analizar tenga extension .py
    public boolean validarExtensionArchivoInicial(String archivoAdjunto) {

        return archivoAdjunto.toLowerCase().endsWith(".py");

    }

    public void imprimirListas(List<String> contenidoArchivo) {
        /*
        Iterator iter = lista.iterator();

        while (iter.hasNext()) {
            System.out.print(iter.next());
        }
         */
        for (String linea : contenidoArchivo) {
            System.out.println(linea);
        }
    }

    public void analisisLexico(char[] arregloCaracteres, int numeroLinea) {
        char caracterActual = ' ';
        char caracterSiguiente = ' ';
        String identificador = "";
        String str = "";
        String comentario = "";
        String numero = "";

        int[] estadisticas = new int[10];

        int cantidadComentarios = 0;

        for (int i = 0; i < arregloCaracteres.length; i++) {

            caracterActual = arregloCaracteres[i];
            switch (caracterActual) {
                //Comentarios de una línea
                case '#':

                    while (!esFinalLinea(arregloCaracteres, i)) {
                        caracterActual = arregloCaracteres[i];
                        comentario = comentario + caracterActual;
                        ++i;
                    }
                    System.out.println("138 BORRAR>  El comentario es: " + comentario + "\n");//BORRAR
                   
                    ++cantidadComentarios;
                    comentario = " ";
                    --i;
                    break;

                // SE IGNORAN LOS CARACTERES EN BLANCO
                case ' ':
                case '\r':
                case '\t':
                    break;

                default:
                    break;

            }

        }
    }

    //FUNCIONES AUXILIARES
    public static boolean esFinalLinea(char[] arregloCaracteres, int contador) {
        return contador >= arregloCaracteres.length;
    }

    public static void agregarNuevoToken(String nombreToken, String tipoDeToken, String valor, int numeroLinea) {
        Token nuevoToken = new Token(nombreToken, tipoDeToken, valor, numeroLinea);

        listaDeTokens.add(nuevoToken);
    }

}
