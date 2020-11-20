package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

class Arreglo(var nombre: Token, var tipoDato: Token, var listaExpresiones: ArrayList<Expresion>): Sentencia() {

    override fun toString(): String {
        return "Arreglo(nombre='$nombre', tipoDato='$tipoDato', listaExpresiones=$listaExpresiones)"
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Arreglo")

        raiz.children.add( TreeItem("Nombre: ${nombre.lexema}"))
        raiz.children.add( TreeItem("TipoDato: ${tipoDato.lexema}"))

        var raizExpresiones = TreeItem("Expresion")

        for (p in listaExpresiones){
            raizExpresiones.children.add(p.getArbolVisual())
        }
        raiz.children.add(raizExpresiones)
        return raiz
    }
}