package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

/**
 * @author Jeffrey Alexander Vargas, Kevin Valencia Romero
 */

class Decremento: Sentencia() {
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Decremento")
        return raiz
    }

    override fun toString(): String {
        return "Decremento()"
    }

}