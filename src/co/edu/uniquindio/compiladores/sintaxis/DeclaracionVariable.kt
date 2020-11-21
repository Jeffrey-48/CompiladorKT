package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

/**
 * @author Jeffrey Alexander Vargas, Kevin Valencia Romero
 */

class DeclaracionVariable(var listaVariables:ArrayList<Variable>, var tipoDato:Token, var modo: String): Sentencia() {
    override fun toString(): String {
        return "DeclaracionVariable(listaVariables=$listaVariables, tipoDato=$tipoDato, tipoVariable=$modo)"
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Declaracion de variable")

        var raizTrue = TreeItem("Variables")
        for(s in listaVariables) {
            raizTrue.children.add(s.getArbolVisual())
        }
        raiz.children.add(raizTrue)
        raiz.children.add( TreeItem("Tipo de Dato: ${tipoDato?.lexema}"))
        raiz.children.add( TreeItem("Tipo de Variable: ${modo}"))
        return raiz
    }
}