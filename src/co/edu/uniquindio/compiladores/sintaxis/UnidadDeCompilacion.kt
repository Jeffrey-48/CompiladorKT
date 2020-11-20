package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

class UnidadDeCompilacion(var listaFunciones:ArrayList<Funcion>, var listaVariable: ArrayList<DeclaracionVariable>?) {
    override fun toString(): String {
        return "UnidadDeCompilacion(listaFunciones=$listaFunciones, listaVariables=$listaVariable)"
    }

    fun getArbolVisual(): TreeItem<String> {

        var raiz = TreeItem("Unidad de compilación")

        for(f in listaFunciones) {
            raiz.children.add(f.getArbolVisual())
        }
        if (listaVariable!=null) {
            for (f in listaVariable!!) {
                raiz.children.add(f.getArbolVisual())
            }
        }
        return raiz
    }
}