package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

/**
 *  @author Jeffrey Alexander Vargas, Kevin Valencia Romero
 */

class BloqueComentario(var bloque:Token?):Sentencia() {
    override fun toString(): String {
        return "BloqueComentario(bloque=$bloque)"
    }
    override fun getArbolVisual(): TreeItem<String> {
        return TreeItem("BloqueComentario")
    }
}