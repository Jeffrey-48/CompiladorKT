package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.Simbolo
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

/**
 * @author Jeffrey Alexander Vargas, Kevin Valencia Romero
 */

class Lectura(private var nombre: Token?) : Sentencia() {
    private var simbolo: Simbolo?=null

    override fun toString(): String {
        return "Lectura(nombreVariable=$nombre)"
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Lectura")
        var raizTrue = TreeItem("Nombre variable: $nombre")
        raiz.children.add(raizTrue)
        return raiz
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String) {
        simbolo = tablaSimbolos.buscarSimboloVariable(nombre!!.lexema,ambito)
        if (simbolo==null){
            erroresSemanticos.add(Error("El campo (${nombre!!.lexema}) no existe dentro del ambito ($ambito)",nombre!!.fila,nombre!!.columna))
        }
    }

    override fun getJavaCode(): String {
        return when (simbolo!!.tipo){
            "_entero" -> {
                nombre!!.getJavaCode() + " = Integer.parseInt(JOptionPane.showInputDialog(null, \"Escribir:\"));"
            }
            "_decimal" -> {
                nombre!!.getJavaCode() + " = Double.parseDouble(JOptionPane.showInputDialog(null, \"Escribir:\"));"
            }
            "_boolean" -> {
                nombre!!.getJavaCode() + " = Boolean.parseBoolean(JOptionPane.showInputDialog(null, \"Escribir:\"));"
            }
            else -> {
                nombre!!.getJavaCode() + " = JOptionPane.showInputDialog(null, \"Escribir:\");"
            }
        }
    }
}