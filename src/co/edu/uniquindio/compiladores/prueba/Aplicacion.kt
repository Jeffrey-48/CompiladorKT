package co.edu.uniquindio.compiladores.prueba

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.sintaxis.AnalizadorSintactico

/**
 * Clase para realizar pruebas y testeos
 * @author Jeffrey Alexander Vargas, Kevin Valencia Romero
 */

fun main() {
    val lexico = AnalizadorLexico("function _entero sumar _-numero:_decimal-_ $ ~~asdasdasd~ ~dasdasasas7d654654~ $")
    lexico.analizar()
    //print(lexico.listaTokens)
    val sintaxis = AnalizadorSintactico(lexico.listaTokens)
    print(sintaxis.esUnidadDeCompilacion())
    print(sintaxis.listaErrores)
}