package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

/**
 * @author Jeffrey Alexander Vargas, Kevin Valencia Romero
 */

class ExpresionCadena(): Expresion() {
    var cadena:Token? = null
    var expresion:Expresion?=null
    var identificador:Token?=null

    constructor(cadena:Token, expresion:Expresion):this(){
        this.cadena=cadena
        this.expresion=expresion
    }

    constructor(identificador:Token):this(){
        this.identificador=identificador
    }

    override open fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Expresion Cadena")
        if (identificador!=null){
            raiz.children.add(TreeItem(identificador!!.lexema))
        }else{
            raiz.children.add(TreeItem(cadena!!.lexema))
            if (expresion!=null){
                raiz.children.add(expresion!!.getArbolVisual())
            }
        }
        return raiz
    }

    override fun obtenerTipo(tablaSimbolos: TablaSimbolos, ambito:String,erroresSemanticos: ArrayList<Error>): String {
        return "_cadena"
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String) {
        if (expresion != null){
            expresion!!.analizarSemantica(tablaSimbolos,erroresSemanticos,ambito)
        }
    }

    override fun toString(): String {
        return "ExpresionCadena(cadena=$cadena, expresion=$expresion, identificador=$identificador)"
    }

    override fun getJavaCode(): String {
        var codigo =""
        if (cadena!=null) {
            codigo = cadena!!.getJavaCode()
            if (expresion != null) {
                codigo += "+" + expresion!!.getJavaCode()
            }
        }
        if (identificador!=null) {
            codigo = identificador!!.getJavaCode()
        }
        return codigo
    }
}