package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

/**
 * @author Jeffrey Alexander Vargas, Kevin Valencia Romero
 */

class ExpresionRelacional(var exp1:ExpresionAritmetica?, var operador:Token?, var exp2:ExpresionAritmetica?): Expresion() {

    override fun getArbolVisual(): TreeItem<String> {
        return TreeItem("Expresion relacional")
    }

    override fun toString(): String {
        return "ExpresionRelacional(exp1=$exp1, operador=$operador, exp2=$exp2)"
    }
}