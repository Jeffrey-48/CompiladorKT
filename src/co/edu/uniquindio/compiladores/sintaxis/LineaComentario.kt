package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class LineaComentario(var linea: Token?):Sentencia() {
    override fun toString(): String {
        return "LineaComentario(Linea=$linea)"
    }
    override fun getArbolVisual(): TreeItem<String> {
        return TreeItem("LineaComentario")
    }
}