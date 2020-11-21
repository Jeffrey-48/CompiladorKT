package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

/**
 * @author Jeffrey Alexander Vargas, Kevin Valencia Romero
 */

open class Sentencia {

    open fun getArbolVisual(): TreeItem<String> {
        return TreeItem("Sentencia")
    }
}