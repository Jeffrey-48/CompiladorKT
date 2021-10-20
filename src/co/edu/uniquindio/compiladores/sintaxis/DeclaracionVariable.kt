package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem
import kotlin.math.exp

/**
 * @author Jeffrey Alexander Vargas, Kevin Valencia Romero
 */

class DeclaracionVariable(): Sentencia() {
    var listaVariables:ArrayList<Variable>?=null
    var tipoDato:Token?=null
    var modo: String?=null
    var expresion:Expresion?=null

    constructor(listaVariables:ArrayList<Variable>?, tipoDato:Token?, modo: String?):this(){
        this.listaVariables=listaVariables
        this.tipoDato=tipoDato
        this.modo=modo
    }

    constructor(listaVariables:ArrayList<Variable>?, tipoDato:Token?, modo: String?, expresion: Expresion?):this(){
        this.listaVariables=listaVariables
        this.tipoDato=tipoDato
        this.modo=modo
        this.expresion= expresion
    }
    override fun toString(): String {
        return "DeclaracionVariable(listaVariables=$listaVariables, tipoDato=$tipoDato, tipoVariable=$modo)"
    }
    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Declaracion de variable")

        var raizTrue = TreeItem("Variables")
        for(s in listaVariables!!) {
            raizTrue.children.add(s.getArbolVisual())
        }
        raiz.children.add(raizTrue)
        raiz.children.add( TreeItem("Tipo de Dato: ${tipoDato?.lexema}"))
        raiz.children.add( TreeItem("Tipo de Variable: ${modo}"))
        return raiz
    }

    override fun llenarTablaSimbolos(tablaSimbolos: TablaSimbolos, erroresSemanticos:ArrayList<Error>, ambito:String){
        for (v in listaVariables!!){
            tablaSimbolos.guardarSimboloValor(v.nombre!!.lexema, tipoDato!!.lexema, true, ambito, v.nombre!!.fila, v.nombre!!.columna)
        }
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String) {
        for (v in listaVariables!!){
            if (v!=null&&v.expresion!=null){
                v.expresion!!.analizarSemantica(tablaSimbolos,erroresSemanticos,ambito)
            }
        }
    }

    override fun getJavaCode(): String {
        var codigo = tipoDato!!.getJavaCode() + " "
        for (v in listaVariables!!){
            codigo += v.getJavaCode()+","
        }
        codigo = codigo.substring(0,codigo.length-1)
        codigo += ";"
        return codigo
    }

}