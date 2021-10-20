package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

/**
 * @author Jeffrey Alexander Vargas, Kevin Valencia Romero
 */

class Variable() {
    var nombre:Token?=null
    var expresion:Expresion?=null
    override fun toString(): String {
        return "Variable(nombre='$nombre', expresion=$expresion)"
    }
    constructor( nombre:Token,expresion:Expresion?):this(){
        this.nombre=nombre
        this.expresion=expresion
    }
    constructor(nombre:Token):this(){
        this.nombre=nombre
    }
    fun getArbolVisual(): TreeItem<String> {
        return TreeItem("${nombre!!.lexema} : ${expresion?.getArbolVisual()}")
    }

    fun getJavaCode(): String {
        return if (expresion!=null){
            nombre!!.getJavaCode()+" = "+expresion!!.getJavaCode()
        }else{
            nombre!!.getJavaCode()
        }
    }
}