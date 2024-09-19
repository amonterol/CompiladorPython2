/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package compiladorpython;

/**
 *
 * @author abmon
 */
public class Compilador {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Lexico analizadorLexico = new Lexico(args);
        analizadorLexico.analizarPrograma();
       
    }
}
