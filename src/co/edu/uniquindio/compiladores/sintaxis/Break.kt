package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

class Break: Sentencia() {
    override fun getArbolVisual(): TreeItem<String> {
        return TreeItem("Break")
    }

    override fun toString(): String {
        return "Break()"
    }

}