package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

/**
 * @author Jeffrey Alexander Vargas, Kevin Valencia Romero
 */

open class Sentencia {

    open fun getArbolVisual(): TreeItem<String> {
        return TreeItem("Sentencia")
    }

    open fun llenarTablaSimbolos(tablaSimbolos:TablaSimbolos,erroresSemanticos:ArrayList<Error>, ambito:String){

    }

    open fun analizarSemantica(tablaSimbolos:TablaSimbolos,erroresSemanticos:ArrayList<Error>, ambito: String){

    }

    open fun getJavaCode(): String {
        return ""
    }
}