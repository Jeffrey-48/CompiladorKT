package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class ValorNumerico(var signo: Token?, var termino: Token) {
    override fun toString(): String {
        return "ValorNumerico(signo=$signo, termino=$termino)"
    }

    fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Signo: ${signo?.lexema}")
        raiz.children.add( TreeItem("Termino : ${termino?.lexema}"))
        return raiz
    }
}