package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

/**
 * @author Jeffrey Alexander Vargas, Kevin Valencia Romero
 */

class Lectura(var expresion: Expresion?) : Sentencia() {
    override fun toString(): String {
        return "Lectura(nombreVariable=$expresion)"
    }
    override fun getArbolVisual(): TreeItem<String> {
        return TreeItem("Lectura")
    }
}