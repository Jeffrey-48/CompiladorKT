package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

/**
 *@author Jeffrey Alexander Vargas, Kevin Valencia Romero
 */

class ExpresionAritmetica(): Expresion() {
    var ea1: ExpresionAritmetica? = null
    var operador: Token? = null
    var ea2: ExpresionAritmetica? = null
    var vl:ValorNumerico? = null

    constructor(ea1: ExpresionAritmetica?, operador: Token?, ea2: ExpresionAritmetica?):this(){
        this.ea1 = ea1
        this.operador = operador
        this.ea2 = ea2
    }
    constructor(ea1: ExpresionAritmetica?):this(){
        this.ea1 = ea1
    }
    constructor(vl:ValorNumerico?):this(){
        this.vl=vl
    }
    constructor(valor: ValorNumerico, operador: Token,ea1: ExpresionAritmetica):this(){
        this.vl=valor
        this.operador=operador
        this.ea1=ea1
    }
    override fun getArbolVisual():TreeItem<String> {
        var raiz = TreeItem("Expresion aritmetica")
        var expresion = TreeItem("Expresion")
        expresion.children.add(ea1?.getArbolVisual())
        raiz.children.add(expresion)
        var condicion = TreeItem("Valor numerico")
        condicion.children.add(vl?.getArbolVisual())
        raiz.children.add(condicion)
        var raizTrue = TreeItem("Operador: ${operador?.lexema}")
        raiz.children.add(raizTrue)
        var expresion2 = TreeItem("Expresion")
        expresion2.children.add(ea2?.getArbolVisual())
        raiz.children.add(expresion2)
        return raiz
    }

    override fun toString(): String {
        return "ExpresionAritmetica(ea1=$ea1, operador=$operador, ea2=$ea2, vl=$vl)"
    }

}