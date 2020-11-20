package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class ExpresionCadena(): Expresion() {
    var identificador:String? = null

    constructor(identificador: String):this(){
        this.identificador = identificador
    }

    override open fun getArbolVisual(): TreeItem<String> {
        return TreeItem("Expresion Cadena")
    }
}