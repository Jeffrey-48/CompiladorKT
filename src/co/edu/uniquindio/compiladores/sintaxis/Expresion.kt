package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

/**
 * @author Jeffrey Alexander Vargas, Kevin Valencia Romero
 */

open class Expresion {

    open fun getArbolVisual():TreeItem<String>{
        return TreeItem("Expresion")
    }

    open fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito:String, erroresSemanticos: ArrayList<Error>):String{
        return ""
    }

    open fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String) {

    }

    open fun getJavaCode(): String {
        return ""
    }
}