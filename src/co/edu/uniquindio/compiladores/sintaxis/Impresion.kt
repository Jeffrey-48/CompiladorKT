package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

/**
 * @author Jeffrey Alexander Vargas, Kevin Valencia Romero
 */

class Impresion(var expresion: Expresion?): Sentencia() {
    override fun toString(): String {
        return "Impresion(expresion=$expresion)"
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Impresion")
        var raizExpresiones = TreeItem("Expresion")
        raizExpresiones.children.add(expresion?.getArbolVisual())
        raiz.children.add(raizExpresiones)
        return raiz
    }
}