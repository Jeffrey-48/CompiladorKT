package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Token

class InvocacionFuncion(var nombreFuncion: Token, var listaArgumentos:ArrayList<Variable>): Sentencia() {
    override fun toString(): String {
        return "InvocacionDeFuncion()"
    }
}