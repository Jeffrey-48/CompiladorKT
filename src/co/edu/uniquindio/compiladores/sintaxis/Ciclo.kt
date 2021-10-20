package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

/**
 * @author Jeffrey Alexander Vargas, Kevin Valencia Romero
 */

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

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, erroresSemanticos:ArrayList<Error>, ambito:String){
        for (s in listaSentencia){
            s.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, ambito)
        }
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String) {
        expresionLogica.analizarSemantica(tablaSimbolos,erroresSemanticos,ambito)
        for (s in listaSentencia){
            s.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
        }
    }

    override fun getJavaCode(): String {
        var codigo = "while (" + expresionLogica.getJavaCode()+ "){"
        for (s in listaSentencia){
            codigo += s.getJavaCode()
        }
        codigo+="}"
        return codigo
    }
}