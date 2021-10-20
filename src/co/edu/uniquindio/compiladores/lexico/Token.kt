package co.edu.uniquindio.compiladores.lexico

import co.edu.uniquindio.compiladores.lexico.Categoria

/**
 * Clase que representa un token
 * @author Jeffrey Alexander Vargas, Kevin Valencia Romero
 */

class Token(var lexema:String, var categoria: Categoria, var fila:Int, var columna:Int) {
    override fun toString(): String {
        return "Token(lexema='$lexema', categoria=$categoria, fila=$fila, columna=$columna)"
    }

    fun getJavaCode(): String {
        if (categoria == Categoria.PALABRA_RESERVADA){
            if (lexema == "_decimal"){
                return "double"
            }else if (lexema == "_cadena"){
                return "String"
            }else if (lexema == "_entero"){
                return "int"
            }else if (lexema == "_real"){
                return "float"
            }else if (lexema == "_booleano"){
                return "boolean"
            }else if (lexema == "_void"){
                return "void"
            }
        }else if (categoria == Categoria.ASIGNACION_PARA_CADENA){
            return lexema.replace("°","\"")
        }else if (categoria == Categoria.ENTERO){
            return lexema.substring(1,lexema.length-1)
        }else if (categoria == Categoria.OPERADOR_RELACIONAL){
            if (lexema == "?"){
                return ">"
            }else if (lexema == "¿"){
                return "<"
            }else if (lexema == "¡"){
                return "!="
            }else {
                return "=="
            }
        }
        return lexema
    }
}