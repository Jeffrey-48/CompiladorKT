package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

/**
 * @author Jeffrey Alexander Vargas, Kevin Valencia Romero
 */

class ExpresionLogica(): Expresion() {
    var operadorNegacion:Token?=null
    var expL1: ExpresionLogica?=null
    var operadorLogico:Token?=null
    var expL2:ExpresionLogica?=null
    var valorLogico:ValorLogico?=null

    constructor(operadorNegacion:Token?, expL1:ExpresionLogica?, operadorLogico:Token?, expL2:ExpresionLogica?):this(){
        this.operadorNegacion=operadorNegacion
        this.expL1=expL1
        this.operadorLogico=operadorLogico
        this.expL2=expL2
    }
    constructor(operadorNegacion:Token?, expL1:ExpresionLogica?):this(){
        this.operadorNegacion=operadorNegacion
        this.expL1=expL1
    }

    constructor(valorLogico:ValorLogico?, operadorLogico:Token?, expL2:ExpresionLogica?):this(){
        this.valorLogico=valorLogico
        this.operadorLogico=operadorLogico
        this.expL2=expL2
    }
    constructor(valorLogico:ValorLogico?):this(){
        this.valorLogico=valorLogico
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Expresion Logica")
        var expresion = TreeItem("Expresion")
        expresion.children.add(expL1?.getArbolVisual())
        raiz.children.add(expresion)
        var condicion = TreeItem("Valor Logico")
        condicion.children.add(valorLogico?.getArbolVisual())
        raiz.children.add(condicion)
        var raizTrue = TreeItem("Operador: ${operadorLogico?.lexema}")
        raiz.children.add(raizTrue)
        var expresion2 = TreeItem("Expresion")
        expresion2.children.add(expL2?.getArbolVisual())
        raiz.children.add(expresion2)
        return raiz    }

    override fun toString(): String {
        return "ExpresionLogica(operadorNegacion=$operadorNegacion, expL1=$expL1, operadorLogico=$operadorLogico, expL2=$expL2, valorLogico=$valorLogico)"
    }

}