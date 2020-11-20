package co.edu.uniquindio.compiladores.lexico

/**
 * Clase que representa los errores de la fase de análisis de la compilación
 * @author Jeffrey Vargas, Kevin Valencia
 */
class Error (var error:String, var fila:Int, var columna:Int){
    override fun toString(): String {
        return "Error(error='$error', fila=$fila, columna=$columna)"
    }
}