package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

/**
 * @author Jeffrey Alexander Vargas, Kevin Valencia Romero
 */

class InvocacionFuncion(var nombreFuncion: Token, var listaArgumentos:ArrayList<Expresion>): Sentencia() {
    override fun toString(): String {
        return "InvocacionDeFuncion()"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Invocacion de funcion")
        raiz.children.add(TreeItem("Nombre de funcion: ${nombreFuncion?.lexema}"))
        if (listaArgumentos.isNotEmpty()){
            val arg = TreeItem("Argumentos")
            for (expresion in listaArgumentos){
                arg.children.add(expresion.getArbolVisual())
            }
            raiz.children.add(arg)
        }
        return raiz
    }

    fun obtenerTiposArgumentos(tablaSimbolos: TablaSimbolos, ambito:String,erroresSemanticos:ArrayList<Error>):ArrayList<String>{
        var listaArgs = ArrayList<String>()
        for (a in listaArgumentos){
            listaArgs.add(a.obtenerTipo(tablaSimbolos,ambito,erroresSemanticos))
        }
        return listaArgs
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String) {
        var listaTiposArg = obtenerTiposArgumentos(tablaSimbolos,ambito,erroresSemanticos)
        var s = tablaSimbolos.buscarSimboloFuncion(nombreFuncion.lexema,listaTiposArg)
        if (s==null){
            erroresSemanticos.add(Error("La fucion ${nombreFuncion.lexema} ${listaTiposArg} no existe",nombreFuncion.fila, nombreFuncion.columna))
        }
    }

    override fun getJavaCode(): String {
        var codigo = nombreFuncion.getJavaCode() + "("
        if (listaArgumentos!=null) {
            for (a in listaArgumentos) {
                codigo += a.getJavaCode() + ","
            }
            codigo = codigo.substring(0, codigo.length - 1)
        }
        codigo += ");"
        return  codigo
    }

}