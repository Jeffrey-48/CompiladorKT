package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

class Ciclo(var expresionLogica:ExpresionLogica, var listaSentencia:ArrayList<Sentencia>): Sentencia() {

    override fun toString(): String {
        return "Ciclo(ExpresionLogica=$expresionLogica, listaSentencia=$listaSentencia"
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Ciclo")
        var condicion = TreeItem("Condición")
        condicion.children.add(expresionLogica.getArbolVisual())
        raiz.children.add(condicion)
        var raizSentencia = TreeItem("Sentencia")
        for(s in listaSentencia) {
            raizSentencia.children.add(s.getArbolVisual())
        }
        raiz.children.add(raizSentencia)

        return raiz
    }
}