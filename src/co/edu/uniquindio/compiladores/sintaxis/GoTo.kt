package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

/**
 * @author Jeffrey Alexander Vargas, Kevin Valencia Romero
 */

class GoTo: Sentencia() {
    override fun getArbolVisual(): TreeItem<String> {
        return TreeItem("GoTo")
    }

    override fun toString(): String {
        return "GoTo()"
    }

}