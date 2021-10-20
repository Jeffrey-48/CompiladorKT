package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

/**
 * @author Jeffrey Alexander Vargas, Kevin Valencia Romero
 */

class Arreglo(var nombre: Token, var tipoDato: Token, var listaExpresiones: ArrayList<Expresion>): Sentencia() {

    override fun toString(): String {
        return "Arreglo(nombre='$nombre', tipoDato='$tipoDato', listaExpresiones=$listaExpresiones)"
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Arreglo")

        raiz.children.add( TreeItem("Nombre: ${nombre.lexema}"))
        raiz.children.add( TreeItem("TipoDato: ${tipoDato.lexema}"))

        var raizExpresiones = TreeItem("Expresion")

        for (p in listaExpresiones){
            raizExpresiones.children.add(p.getArbolVisual())
        }
        raiz.children.add(raizExpresiones)
        return raiz
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, erroresSemanticos:ArrayList<Error>, ambito:String){
        tablaSimbolos.guardarSimboloValor(nombre.lexema, tipoDato.lexema, true, ambito, nombre.fila, nombre.columna)
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String) {

        for (e in listaExpresiones){
            e!!.analizarSemantica(tablaSimbolos,erroresSemanticos,ambito)
            var tipo = e.obtenerTipo(tablaSimbolos, ambito, erroresSemanticos)
           if( tipo != tipoDato.lexema){
               erroresSemanticos.add(Error("El tipo de dato de la expresion ($tipo ) no coincide con el de el arreglo (${tipoDato.lexema})",nombre.fila, nombre.columna))
           }
        }
    }

    override fun getJavaCode(): String {
        var codigo = tipoDato.getJavaCode() + "[] " + nombre.getJavaCode()
        if (listaExpresiones!=null){
            codigo+="= {"
            for (e in listaExpresiones){
                codigo+= e.getJavaCode()+","
            }
            codigo = codigo.substring(0,codigo.length-1)
            codigo+="}"
        }
        codigo+=";"
        return codigo
    }

}