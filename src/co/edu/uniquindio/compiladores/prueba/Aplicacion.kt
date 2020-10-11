package co.edu.uniquindio.compiladores.prueba

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico

fun main() {
    val lexico = AnalizadorLexico("?¡¿...?¿\n%543.354%%5%%58.345-><--<545f.->8684.84\ncasa\n64646464+-*/654+6*5//")
    lexico.analizar()
    print(lexico.listaTokens)
}