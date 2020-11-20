package co.edu.uniquindio.compiladores.sintaxis

import javafx.scene.control.TreeItem

class Variable() {
    var nombre:String?=null
    var expresion:Expresion?=null
    override fun toString(): String {
        return "Variable(nombre='$nombre', expresion=$expresion)"
    }
    constructor( nombre:String?,expresion:Expresion?):this(){
        this.nombre=nombre
        this.expresion=expresion
    }
    constructor(nombre:String):this(){
        this.nombre=nombre
    }
    fun getArbolVisual(): TreeItem<String> {
        return TreeItem("${nombre} : ${expresion?.getArbolVisual()}")
    }
}