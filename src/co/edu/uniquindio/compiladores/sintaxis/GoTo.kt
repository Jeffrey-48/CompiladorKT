package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

class GoTo: Sentencia() {
    override fun getArbolVisual(): TreeItem<String> {
        return TreeItem("GoTo")
    }

    override fun toString(): String {
        return "GoTo()"
    }

}