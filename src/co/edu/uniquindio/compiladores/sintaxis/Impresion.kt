package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem
import javax.swing.JOptionPane

/**
 * @author Jeffrey Alexander Vargas, Kevin Valencia Romero
 */

class Impresion(var expresion: Expresion?): Sentencia() {
    override fun toString(): String {
        return "Impresion(expresion=$expresion)"
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Impresion")
        var raizExpresiones = TreeItem("Expresion")
        raizExpresiones.children.add(expresion?.getArbolVisual())
        raiz.children.add(raizExpresiones)
        return raiz
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String) {
        expresion!!.analizarSemantica(tablaSimbolos,erroresSemanticos,ambito)
    }

    override fun getJavaCode(): String {
        return "JOptionPane.showMessageDialog(null, " + expresion!!.getJavaCode()+ ");"

    }
}