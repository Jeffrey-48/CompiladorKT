package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class ValorLogico(var valor:Token?, var exp:ExpresionRelacional?) {

    override fun toString(): String {
        return "ValorLogico(valor=$valor, exp=$exp)"
    }

    fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("valor: ${valor?.lexema}")
        raiz.children.add( TreeItem("Expresion : ${exp?.getArbolVisual()}"))
        return raiz
    }
}