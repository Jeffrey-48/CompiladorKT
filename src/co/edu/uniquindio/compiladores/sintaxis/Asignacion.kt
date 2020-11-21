package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token
import javafx.scene.control.TreeItem

/**
 * @author Jeffrey Alexander Vargas, Kevin Valencia Romero
 */

class Asignacion(): Sentencia() {
    var identificador: Token? = null
    var operadorAsignacion: Token? = null
    var invocacion: InvocacionFuncion? = null
    var invocacion2: Expresion? = null
    constructor(identificador: Token?, operadorAsignacion: Token?, invocacion: InvocacionFuncion?):this(){
        this.identificador=identificador
        this.operadorAsignacion=operadorAsignacion
        this.invocacion=invocacion
    }
    constructor(identificador: Token?, operadorAsignacion: Token?, invocacion: Expresion?):this(){
        this.identificador=identificador
        this.operadorAsignacion=operadorAsignacion
        this.invocacion2=invocacion
    }
    override fun toString(): String {
        return "Asignacion(identificador=$identificador, operadorAsignacion=$operadorAsignacion, invocacion=$invocacion)"
    }

    override fun getArbolVisual(): TreeItem<String> {
        var raiz = TreeItem("Asignacion")
        raiz.children.add( TreeItem("Nombre: ${identificador?.lexema}"))
        var condicion = TreeItem("Valor")
        condicion.children.add(invocacion?.getArbolVisual())
        raiz.children.add(condicion)
        return raiz
    }


}
