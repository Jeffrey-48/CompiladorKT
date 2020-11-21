package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

/**
 * @author Jeffrey Alexander Vargas, Kevin Valencia Romero
 */

class ExpresionCadena(): Expresion() {
    var identificador:String? = null

    constructor(identificador: String):this(){
        this.identificador = identificador
    }

    override open fun getArbolVisual(): TreeItem<String> {
        return TreeItem("Expresion Cadena")
    }
}