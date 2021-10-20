package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
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
        if (ea1!=null && operador!=null && ea2!=null){
            raiz.children.add(ea1!!.getArbolVisual())
            raiz.children.add(TreeItem(operador!!.lexema))
            raiz.children.add(ea2!!.getArbolVisual())
        }else if (ea1!=null && operador==null && ea2==null&&vl==null){
            raiz.children.add(ea1!!.getArbolVisual())
        }else if (vl!=null&&operador!=null&&ea1!=null){
            raiz.children.add(vl!!.getArbolVisual())
            raiz.children.add(TreeItem(operador!!.lexema))
            raiz.children.add(ea1!!.getArbolVisual())
        }else{
            raiz.children.add(vl!!.getArbolVisual())
        }
        return raiz
    }

    override fun toString(): String {
        return "ExpresionAritmetica(ea1=$ea1, operador=$operador, ea2=$ea2, vl=$vl)"
    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito:String, erroresSemanticos: ArrayList<Error>): String {
        if (ea1!=null && ea2!=null){
            var tipo1 = ea1!!.obtenerTipo(tablaSimbolos,ambito,erroresSemanticos)
            var tipo2 = ea1!!.obtenerTipo(tablaSimbolos,ambito,erroresSemanticos)
            if (tipo1 == "_decimal" || tipo2 =="_decimal"){
                return "_decimal"
            }else{
                return "_entero"
            }
        }else if (ea1!=null){
            return ea1!!.obtenerTipo(tablaSimbolos,ambito,erroresSemanticos)
        }else if (vl!=null && ea1!=null){
            var tipo1 = ""
            if (vl!!.termino.categoria == Categoria.ENTERO){
                tipo1 = "_entero"
            }else if (vl!!.termino.categoria == Categoria.DECIMAL){
                tipo1 = "_decimal"
            }else if (vl!!.termino.categoria == Categoria.REAL){
                tipo1 = "_real"
            }else{
                var simbolo =tablaSimbolos.buscarSimboloVariable(vl!!.termino.lexema, ambito)
                if (simbolo!=null){
                    tipo1 = simbolo.tipo
                }else{
                    erroresSemanticos.add(Error("El campo ${vl!!.termino.lexema} no existe dentro del ámbito $ambito", vl!!.termino.fila, vl!!.termino.columna))
                }
            }
            var tipo2 = ea1!!.obtenerTipo(tablaSimbolos,ambito,erroresSemanticos)
            if (tipo1 == "_decimal" || tipo2 =="_decimal"){
                return "_decimal"
            }else{
                return "_entero"
            }
        }else if (vl!=null){
            if (vl!!.termino.categoria == Categoria.ENTERO){
                return "_entero"
            }else if (vl!!.termino.categoria == Categoria.DECIMAL){
                return "_decimal"
            }else if (vl!!.termino.categoria == Categoria.REAL){
                return "_real"
            }else{
                var simbolo =tablaSimbolos.buscarSimboloVariable(vl!!.termino.lexema, ambito)
                if (simbolo!=null){
                    return simbolo.tipo
                }else{
                    erroresSemanticos.add(Error("El campo ${vl!!.termino.lexema} no existe dentro del ámbito $ambito", vl!!.termino.fila, vl!!.termino.columna))
                }
            }
        }
        return ""
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String) {
        if (vl!=null){
            if (vl!!.termino.categoria == Categoria.IDENTIFICADOR){
                var simbolo = tablaSimbolos.buscarSimboloVariable(vl!!.termino.lexema, ambito)
                if (simbolo==null){
                    erroresSemanticos.add(Error("El campo (${vl!!.termino.lexema}) no existe dentro del ámbito ($ambito)",vl!!.termino.fila, vl!!.termino.columna))
                }else{
                    var tipo = simbolo!!.tipo
                    if (tipo!="_entero"&& tipo!="_decimal"&& tipo!="_real"){
                        erroresSemanticos.add(Error("El tipo de dato de (${vl!!.termino.lexema}) no es nummerico",vl!!.termino.fila, vl!!.termino.columna))
                    }
                }
            }
        }
        if (ea1!=null){
            ea1!!.analizarSemantica(tablaSimbolos,erroresSemanticos,ambito)
        }
        if (ea2!=null){
            ea2!!.analizarSemantica(tablaSimbolos,erroresSemanticos,ambito)
        }
    }

    override fun getJavaCode(): String {
        if (ea1!=null && operador!=null && ea2!=null){
            return "("+ea1!!.getJavaCode()+")" + operador!!.getJavaCode() + ea2!!.getJavaCode()
        }else if (ea1!=null && operador==null && ea2==null&&vl==null){
            return "("+ea1!!.getJavaCode()+")"
        }else if (vl!=null&&operador!=null&&ea1!=null){
            return vl!!.getJavaCode() + operador!!.getJavaCode() + ea1!!.getJavaCode()
        }else{
            return vl!!.getJavaCode()
        }
    }
}