package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

/**
 * @author Jeffrey Alexander Vargas, Kevin Valencia Romero
 */

class Parametro(var nombre:Token, var tipoDato: Token) {
    override fun toString(): String {
        return "Parametro(nombre=$nombre, tipoDato=$tipoDato)"
    }

    fun getArbolVisual(): TreeItem<String> {
        return TreeItem("${nombre.lexema} : ${tipoDato.lexema}")
    }
}