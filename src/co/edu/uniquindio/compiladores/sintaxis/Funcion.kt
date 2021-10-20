package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

/**
 * @author Jeffrey Alexander Vargas, Kevin Valencia Romero
 */

class Funcion(var nombreFuncion:Token, var tipoRetorno:Token, var listaParametros:ArrayList<Parametro>, var listaSentencias:ArrayList<Sentencia>) {
    override fun toString(): String {
        return "Funcion(nombreFuncion=$nombreFuncion, tipoRetorno=$tipoRetorno, listaParametros=$listaParametros, listaSentencias=$listaSentencias)"
    }

    fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Función")
        raiz.children.add(TreeItem("Nombre: ${nombreFuncion.lexema}"))
        raiz.children.add(TreeItem("Tipo Retorno: ${tipoRetorno.lexema}"))

        var raizParametros = TreeItem("Parámetros")

        for(p in listaParametros){
            raizParametros.children.add(p.getArbolVisual())
        }
        raiz.children.add(raizParametros)

        var raizSentencias = TreeItem("Sentencias")

        for(p in listaSentencias){
            raizSentencias.children.add(p.getArbolVisual())
        }
        raiz.children.add(raizSentencias)

        return raiz
    }

    fun obtenerTiposParametros():ArrayList<String>{
        var lista = ArrayList<String>()
        for (p in listaParametros){
            lista.add(p.tipoDato.lexema)
        }
        return lista
    }

    fun llenarTablaSimbolos(tablaSimbolos:TablaSimbolos, erroresSemanticos:ArrayList<Error>, ambito:String){
        tablaSimbolos.guardarSimboloFuncion(nombreFuncion.lexema, tipoRetorno.lexema, obtenerTiposParametros(), ambito, nombreFuncion.fila, nombreFuncion.columna)
        for (p in listaParametros){
            tablaSimbolos.guardarSimboloValor(p.nombre.lexema, p.tipoDato.lexema, true, nombreFuncion.lexema, p.nombre.fila, p.nombre.columna)
        }
        for (s in listaSentencias){
            s.llenarTablaSimbolos(tablaSimbolos,erroresSemanticos, nombreFuncion.lexema)
        }
    }

    fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos:ArrayList<Error>){
        for (s in listaSentencias){
            s.analizarSemantica(tablaSimbolos,erroresSemanticos, nombreFuncion.lexema)
        }
    }

    fun getJavaCode(): String {
        var codigo = ""
        if (nombreFuncion.lexema == "principal"){
            codigo = "public static void main (String[] args){"
        }else {
            codigo = "static "+tipoRetorno.getJavaCode() + " " + nombreFuncion.getJavaCode() + "("
            if (listaParametros.isNotEmpty()) {
                for (p in listaParametros) {
                    codigo += p.getJavaCode() + ","
                }
                codigo = codigo.substring(0, codigo.length - 1)
            }
            codigo += "){"
        }
        for (s in listaSentencias) {
            codigo += s.getJavaCode()
        }
        codigo += "}"
        return codigo
    }

}