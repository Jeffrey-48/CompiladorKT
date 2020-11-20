package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

class Incremento: Sentencia() {
    override fun toString(): String {
        return "Incremento()"
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Incremento")
        return raiz
    }
}