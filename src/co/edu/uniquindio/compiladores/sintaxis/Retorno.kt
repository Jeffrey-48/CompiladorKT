package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

/**
 * @author Jeffrey Alexander Vargas, Kevin Valencia Romero
 */

class Retorno(var expresion: Expresion) : Sentencia() {
    override fun toString(): String {
        return "Retorno(expresion=$expresion)"
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Retorno")
        var raizTrue = TreeItem("Expresion: ${expresion?.getArbolVisual()}")
        raiz.children.add(raizTrue)
        return raiz
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String) {
        expresion!!.analizarSemantica(tablaSimbolos,erroresSemanticos,ambito)
        // comprobar el tipo de dato de la funcion con el tipo de dato del retorno
    }

    override fun getJavaCode(): String {
        return "return " + expresion.getJavaCode() + ";"
    }
}