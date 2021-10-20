package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

/**
 * @author Jeffrey Alexander Vargas, Kevin Valencia Romero
 */

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
    fun llenarTablaSimbolos(tablaSimbolos:TablaSimbolos, erroresSemanticos:ArrayList<Error>){
        for (f in listaFunciones) {
            f.llenarTablaSimbolos(tablaSimbolos, erroresSemanticos, "UnidadDeCompilacion")
        }
    }
    fun analizarSemantica(tablaSimbolos:TablaSimbolos, erroresSemanticos:ArrayList<Error>){
        for (f in listaFunciones) {
            f.analizarSemantica(tablaSimbolos, erroresSemanticos)
        }
    }

    fun getJavaCode(): String {
        var codigo = "import javax.swing.JOptionPane; public class Principal{"
        for (funcion in listaFunciones) {
            codigo += funcion.getJavaCode()
        }
        codigo += "}"
        return codigo
    }
}