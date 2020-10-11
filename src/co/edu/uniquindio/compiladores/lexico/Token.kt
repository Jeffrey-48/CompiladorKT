package co.edu.uniquindio.compiladores.lexico

import co.edu.uniquindio.compiladores.lexico.Categoria

class Token(var lexema:String, var categoria: Categoria, var fila:Int, var colimna:Int) {
    override fun toString(): String {
        return "Token(lexema='$lexema', categoria=$categoria, fila=$fila, colimna=$colimna)"
    }
}