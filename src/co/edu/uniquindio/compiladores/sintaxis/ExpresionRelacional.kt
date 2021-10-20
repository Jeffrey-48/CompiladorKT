package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

/**
 * @author Jeffrey Alexander Vargas, Kevin Valencia Romero
 */

class ExpresionRelacional(var exp1:ExpresionAritmetica?, var operador:Token?, var exp2:ExpresionAritmetica?): Expresion() {

    override fun getArbolVisual(): TreeItem<String> {
        return TreeItem("Expresion relacional")
    }

    override fun toString(): String {
        return "ExpresionRelacional(exp1=$exp1, operador=$operador, exp2=$exp2)"
    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito:String, erroresSemanticos: ArrayList<Error>): String {
        return "_booleano"
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String) {
        if (exp1!=null && exp2!=null){
            exp1!!.analizarSemantica(tablaSimbolos,erroresSemanticos,ambito)
            exp2!!.analizarSemantica(tablaSimbolos,erroresSemanticos,ambito)
        }
    }

    override fun getJavaCode(): String {
        if (exp1!=null && exp2!=null){
            return exp1!!.getJavaCode() + operador!!.getJavaCode() + exp2!!.getJavaCode()
        }
        return super.getJavaCode()
    }
}