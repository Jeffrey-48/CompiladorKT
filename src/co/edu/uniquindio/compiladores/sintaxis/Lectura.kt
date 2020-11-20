package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Lectura(var expresion: Expresion?) : Sentencia() {
    override fun toString(): String {
        return "Lectura(nombreVariable=$expresion)"
    }
    override fun getArbolVisual(): TreeItem<String> {
        return TreeItem("Lectura")
    }
}