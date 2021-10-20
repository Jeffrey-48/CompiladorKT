package co.edu.uniquindio.compiladores.prueba

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.sintaxis.AnalizadorSintactico

/**
 * Clase para realizar pruebas y testeos
 * @author Jeffrey Alexander Vargas, Kevin Valencia Romero
 */

fun main() {
    val lexico = AnalizadorLexico("function _entero sumar _-numero:_decimal-_ $ \n" +
            "~~asdasdasd~ \n" +
            "~dasdasasas7d654654~ \n" +
            "GoTo \n" +
            "Break \n" +
            "$ \n" +
            "function _entero sumar2 _- numero1:_entero, numero2:_decimal, numero3:_cadena-_ $ \n" +
            "array arreglo:_cadena -> _-/654/-_ \n" +
            "&iterar& 5?6 $ \n" +
            "var asfas:_decimal\n" +
            "$ \n" +
            "$ \n" +
            "function _entero sumar2 _- numero1:_entero, numero2:_decimal, numero3:_cadena-_ $ \n" +
            "var asd:_entero \n" +
            "¡SiEs 6¿5 $ \n" +
            ">+ \n" +
            "$ !SiEs $ \n" +
            ">- \n" +
            "$ \n" +
            "toPrint$ /5646/ + /654/ $ \n" +
            "toRead $ dfsdf $\n" +
            "<dasdasd $ nuevo $ \n" +
            "return: casde\n" +
            "$ \n")
    lexico.analizar()
    //print(lexico.listaTokens)
    val sintaxis = AnalizadorSintactico(lexico.listaTokens)
    print(sintaxis.esUnidadDeCompilacion())
    print(sintaxis.listaErrores)
}