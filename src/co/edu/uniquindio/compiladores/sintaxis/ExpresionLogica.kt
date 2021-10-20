package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
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

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito:String,erroresSemanticos: ArrayList<Error>): String {
        return "_booleano"
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String) {
        if (valorLogico!=null){
            if (valorLogico!!.valor!=null) {
                if (valorLogico!!.valor!!.categoria == Categoria.IDENTIFICADOR) {
                    var simbolo = tablaSimbolos.buscarSimboloVariable(valorLogico!!.valor!!.lexema, ambito)
                    if (simbolo == null) {
                        erroresSemanticos.add(Error("El campo (${valorLogico!!.valor!!.lexema}) no existe dentro del ámbito ($ambito)", valorLogico!!.valor!!.fila, valorLogico!!.valor!!.columna))
                    } else {
                        var tipo = simbolo!!.tipo
                        if (tipo != "_entero" && tipo != "_decimal" && tipo != "_real") {
                            erroresSemanticos.add(Error("El tipo de dato de (${valorLogico!!.valor!!.lexema}) no es nummerico", valorLogico!!.valor!!.fila, valorLogico!!.valor!!.columna))
                        }
                    }
                }
            }
        }
        if (expL1!=null){
            expL1!!.analizarSemantica(tablaSimbolos,erroresSemanticos,ambito)
        }
        if (expL2!=null){
            expL2!!.analizarSemantica(tablaSimbolos,erroresSemanticos,ambito)
        }
    }

    override fun getJavaCode(): String {
        if (expL1!=null && expL2!=null){
            if (operadorNegacion!=null){
                return operadorNegacion!!.getJavaCode() + expL1!!.getJavaCode() + operadorLogico!!.getJavaCode() + expL2!!.getJavaCode()
            }else {
                return expL1!!.getJavaCode() + operadorLogico!!.getJavaCode() + expL2!!.getJavaCode()
            }
        }
        if (expL1!=null){
            if (operadorNegacion!=null){
                return operadorNegacion!!.getJavaCode() + expL1!!.getJavaCode()
            }else{
                return expL1!!.getJavaCode()
            }
        }
        if (expL2!=null){
            return valorLogico!!.getJavaCode() + operadorLogico!!.getJavaCode() + expL2!!.getJavaCode()
        }else{
            return valorLogico!!.getJavaCode()
        }
        return super.getJavaCode()
    }

}