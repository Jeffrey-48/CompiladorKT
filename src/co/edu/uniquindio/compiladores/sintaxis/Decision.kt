package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

/**
 * @author Jeffrey Alexander Vargas, Kevin Valencia Romero
 */

class Decision(var expresionLogica:ExpresionLogica, var listaSentencia:ArrayList<Sentencia>, var listaSentenciaElse:ArrayList<Sentencia>?): Sentencia() {
    override fun toString(): String {
        return "Decision(ExpresionLogica=$expresionLogica, listaSentencia=$listaSentencia, listaSentenciaElse=$listaSentenciaElse)"
    }
    override fun getArbolVisual():TreeItem<String> {
        var raiz = TreeItem("Decisión")
        var condicion = TreeItem("Condición")
        condicion.children.add(expresionLogica.getArbolVisual())
        raiz.children.add(condicion)
        var raizTrue = TreeItem("Sentencias Verdaderas")
        for(s in listaSentencia) {
            raizTrue.children.add(s.getArbolVisual())
        }
        raiz.children.add(raizTrue)
        if(listaSentenciaElse!=null){
            var raizFalse = TreeItem("Sentencias Falsas")
            for(s in listaSentenciaElse!!) {
                raizFalse.children.add(s.getArbolVisual())
            }
            raiz.children.add(raizFalse)

        }
        return raiz
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, erroresSemanticos:ArrayList<Error>, ambito:String){
        for (s in listaSentencia){
            s.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, ambito)
        }
        if (listaSentenciaElse!=null){
            for (s in listaSentenciaElse!!){
                s.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, ambito)
            }
        }
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String) {
        if (expresionLogica!=null){
            expresionLogica.analizarSemantica(tablaSimbolos,erroresSemanticos,ambito)
        }
        for (s in listaSentencia){
            s.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
        }
        if (listaSentenciaElse!=null){
            for (s in listaSentenciaElse!!){
                s.analizarSemantica(tablaSimbolos, erroresSemanticos, ambito)
            }
        }
    }

    override fun getJavaCode(): String {
        var codigo = "if (" + expresionLogica.getJavaCode()+ "){"
        for (s in listaSentencia){
            codigo += s.getJavaCode()
        }
        codigo+="}"
        if (listaSentenciaElse!=null){
           codigo+= "else {"
            for (s in listaSentenciaElse!!){
                codigo+=s.getJavaCode()
            }
            codigo+="}"
        }
        return codigo
    }

}