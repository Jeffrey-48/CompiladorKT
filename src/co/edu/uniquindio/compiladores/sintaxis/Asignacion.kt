package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.TablaSimbolos
import javafx.scene.control.TreeItem

/**
 * @author Jeffrey Alexander Vargas, Kevin Valencia Romero
 */

class Asignacion(): Sentencia() {
    var identificador: Token? = null
    var operadorAsignacion: Token? = null
    var invocacion: InvocacionFuncion? = null
    var expresion: Expresion? = null
    constructor(identificador: Token?, operadorAsignacion: Token?, invocacion: InvocacionFuncion?):this(){
        this.identificador=identificador
        this.operadorAsignacion=operadorAsignacion
        this.invocacion=invocacion
    }
    constructor(identificador: Token?, operadorAsignacion: Token?, invocacion: Expresion?):this(){
        this.identificador=identificador
        this.operadorAsignacion=operadorAsignacion
        this.expresion=invocacion
    }
    override fun toString(): String {
        return if (expresion!=null){
            "Asignacion(identificador=$identificador, operadorAsignacion=$operadorAsignacion, expresion=$expresion)"
        }else {
            "Asignacion(identificador=$identificador, operadorAsignacion=$operadorAsignacion, invocacion=$invocacion)"
        }
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Asignacion")
        raiz.children.add( TreeItem("Nombre: ${identificador?.lexema}"))
        raiz.children.add( TreeItem("Operador: ${operadorAsignacion?.lexema}"))
        if (expresion!=null){
            raiz.children.add( expresion!!.getArbolVisual())
        }
        if (invocacion!=null){
            raiz.children.add( invocacion!!.getArbolVisual())
        }
        return raiz
    }

    override fun analizarSemantica(tablaSimbolos: TablaSimbolos, erroresSemanticos: ArrayList<Error>, ambito: String) {
        var s = tablaSimbolos.buscarSimboloVariable(identificador!!.lexema, ambito)
        if (s==null){
            erroresSemanticos.add(Error("El campo ${identificador!!.lexema} no existe dentro del ambito $ambito", identificador!!.fila, identificador!!.columna))
        }else {
            var tipo = s.tipo
            if (expresion != null) {
                var tipoExpresion = expresion!!.obtenerTipo(tablaSimbolos, ambito, erroresSemanticos)
                if (tipoExpresion!=tipo){
                    erroresSemanticos.add(Error("El tipo de dato de la expresios ${tipoExpresion} no coincide con el tipo de dato del campo ${identificador!!.lexema} que es $tipo", identificador!!.fila, identificador!!.columna))
                }
            }else if (invocacion!=null){
                invocacion!!.analizarSemantica(tablaSimbolos,erroresSemanticos,ambito)
                //comprobar tipos
            }
        }
    }

    override fun getJavaCode(): String {
        var codigo = identificador!!.getJavaCode() + " = " + expresion!!.getJavaCode() + ";"
        return codigo
    }
}
