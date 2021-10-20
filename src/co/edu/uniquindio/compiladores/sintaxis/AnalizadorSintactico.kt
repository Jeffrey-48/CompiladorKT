package co.edu.uniquindio.compiladores.sintaxis

import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import kotlin.math.exp

/**
 * Clase que representa el analsis sintactico del compilador
 * @author Jeffrey Alexander Vargas, Kevin Valencia Romero
 */

class AnalizadorSintactico ( var listaTokens:ArrayList<Token> ) {

    var posicionActual = 0
    var tokenActual = listaTokens[0]
    var listaErrores = ArrayList<Error>()


    fun obtenerSiguienteToken() {

        posicionActual++

        if (posicionActual < listaTokens.size) {
            tokenActual = listaTokens[posicionActual]
        }
    }

    fun reportarError(mensaje: String) {
        listaErrores.add(Error(mensaje, tokenActual.fila, tokenActual.columna))
    }

    fun hacerBacktracking(posInicial:Int) {
        posicionActual = posInicial
        tokenActual = listaTokens[posicionActual]
    }

    /**
     *  <UnidadDeCompilacion> ::= <ListaFunciones>
     */

    fun esUnidadDeCompilacion(): UnidadDeCompilacion? {
        val listaFunciones: ArrayList<Funcion> = esListaFunciones()
        val listaVariables: ArrayList<DeclaracionVariable> = esListaVariables2()
        return if (listaFunciones.size > 0 || listaVariables.size > 0) {
            if(listaVariables.size > 0){
                UnidadDeCompilacion(listaFunciones,listaVariables)
            }else {
                UnidadDeCompilacion(listaFunciones,null)
            }
        } else null
    }

    /**
     *  <ListaFunciones> ::= <Funcion>[<ListaFunciones>]
     */

    fun esListaFunciones(): ArrayList<Funcion> {

        var listaFunciones = ArrayList<Funcion>()
        var funcion = esFuncion()
        while (funcion != null) {
            listaFunciones.add(funcion)
            funcion = esFuncion()
        }
        return listaFunciones
    }

    /**
     *  <ListaVariables2> ::= <Variable> [“,” <ListaVariables>]
     */

    fun esListaVariables2():ArrayList<DeclaracionVariable> {

        var listaVariables = ArrayList<DeclaracionVariable>()
        var variable = esDeclaracionVariable()
        while (variable != null) {
            listaVariables.add(variable)
            variable = esDeclaracionVariable()
        }
        return listaVariables
    }

    /**
     * <Funcion> ::= function identificador "$"[<ListaParametros>]"$" [":"<TipoRetorno>] <BloqueSentencias>
     */
    fun esFuncion(): Funcion? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "function") {
            obtenerSiguienteToken()
            var tipoRetorno = esTipoRetorno()
            if (tipoRetorno != null) {
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                    var nombreFuncion = tokenActual
                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.SIMBOLO_DE_ABRIR) {
                        obtenerSiguienteToken()
                        var listaParametros = esListaParametros()
                        if (tokenActual.categoria == Categoria.SIMBOLO_DE_CERRAR) {
                            obtenerSiguienteToken()
                            var bloqueSentencias = esBloqueSentencias()
                            if (bloqueSentencias != null) {
                                return Funcion(nombreFuncion, tipoRetorno, listaParametros, bloqueSentencias)
                            } else {
                                reportarError("El bloque de sentencias esta vacio")
                            }
                        } else {
                            reportarError("Falta el simbolo de cerrar")
                        }
                    } else {
                        reportarError("Falta el simbolo abrir")
                    }
                } else {
                    reportarError("Falta el nombre de la funcion")
                }
            } else {
                reportarError("Falta el tipo de retorno en la funcion")
            }
        }
        return null
    }

    /**
     * <TipoRetorno> ::= _entero | _decimal | _real | _cadena | _caracter | void
     */
    fun esTipoRetorno(): Token? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA) {
            if (tokenActual.lexema == "_entero" || tokenActual.lexema == "_decimal" || tokenActual.lexema == "_real" || tokenActual.lexema == "_cadena" || tokenActual.lexema == "_caracter" || tokenActual.lexema == "_void") {
                return tokenActual
            }
        }
        return null
    }

    /**
     * <ListaParametros> ::= <Parametro> [","<ListaParametros>]
     */
    fun esListaParametros(): ArrayList<Parametro> {
        var listaParametros = ArrayList<Parametro>()
        var parametro = esParametro()

        while (parametro != null) {
            listaParametros.add(parametro)
            if (tokenActual.categoria == Categoria.COMA) {
                obtenerSiguienteToken()
                parametro = esParametro()
            } else {
                if (tokenActual.categoria != Categoria.SIMBOLO_DE_CERRAR) {
                    reportarError("Falta una coma en la lista de parametros")
                }
                break
            }
        }
        return listaParametros
    }

    /**
     * <Parametro> ::= identificador ":" <TipoDato>
     */
    fun esParametro(): Parametro? {
        if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
            val nombre = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.DOS_PUNTOS) {
                obtenerSiguienteToken()
                val tipoDato = esTipoDato()
                if (tipoDato != null) {
                    obtenerSiguienteToken()
                    return Parametro(nombre, tipoDato)
                } else {
                    reportarError("Falta el retorno en el parametro")
                }
            } else {
                reportarError("Falta el operador dos puntos")
            }
        }
        return null
    }

    /**
     * <TipoDato> ::= _entero | _decimal | _real | _cadena | _caracter
     */
    fun esTipoDato(): Token? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA) {
            if (tokenActual.lexema == "_entero" || tokenActual.lexema == "_decimal" || tokenActual.lexema == "_real" || tokenActual.lexema == "_cadena" || tokenActual.lexema == "_caracter" || tokenActual.lexema == "_booleano") {
                return tokenActual
            }
        }
        return null
    }

    /**
     * <BloqueSentencias> ::= "$" [<ListaSentencias>] "$"
     */
    fun esBloqueSentencias(): ArrayList<Sentencia>? {
        if (tokenActual.categoria == Categoria.TERMINAL_O_INICIAL) {
            obtenerSiguienteToken()
            var listaSentencias = esListaSentencias()
            if (tokenActual.categoria == Categoria.TERMINAL_O_INICIAL) {
                obtenerSiguienteToken()
                return listaSentencias
            } else {
                reportarError("Falta el simbolo de cerrar sentencia")
            }
        } else {
            reportarError("Falta el simbolo de abrir sentencia")
        }
        return null
    }

    /**
     *  <ListaSentencias> ::= <Sentencia>[<ListaSentencias>]
     */
    fun esListaSentencias(): ArrayList<Sentencia> {
        val lista = ArrayList<Sentencia>()
        var s = esSentencia()
        while (s != null) {
            lista.add(s)
            s = esSentencia()
        }
        return lista
    }

    /**
     *  <Sentencia> ::= <Decision> | <Ciclo> | <Impresion> | <Lectura> | <Asignacion> | <DeclaracionVariable> |
     *  <Retorno> | <InvocacionFuncion> | <Arreglo> | <Incremento> | <Decremento> | <Break> | <GoTo> | <LineaComentario> |
     *  <BloqueComentario>
     *
     */
    fun esSentencia(): Sentencia? {
        var s: Sentencia? = esArreglo()
        if (s != null) {
            return s
        }
        s = esAsignacion()
        if (s != null) {
            return s
        }
        s = esCiclo()
        if (s != null) {
            return s
        }
        s = esDecision()
        if (s != null) {
            return s
        }
        s = esDeclaracionVariable()
        if (s != null) {
            return s
        }
        s = esImpresion()
        if (s != null) {
            return s
        }
        s = esInvocacionFuncion()
        if (s != null) {
            return s
        }
        s = esLectura()
        if (s != null) {
            return s
        }
        s = esRetorno()
        if (s != null) {
            return s
        }
        s = esIncremento()
        if (s != null) {
            return s
        }
        s = esDecremento()
        if (s != null) {
            return s
        }
        s = esBreak()
        if (s != null) {
            return s
        }
        s = esGoTo()
        if (s != null) {
            return s
        }
        s = esLineaComentario()
        if (s != null) {
            return s
        }
        s = esBloqueComentario()
        if (s != null) {
            return s
        }
        return null
    }

    /**
     *  <LineaComentario> ::= ~~ <ExpresionCadena> ~ |
     */

    fun esLineaComentario(): Sentencia? {
        if (tokenActual.categoria == Categoria.LINEA_COMENTARIO) {
            var linea = tokenActual
            obtenerSiguienteToken()
            return LineaComentario(linea)
        }
        return null
    }

    fun esBloqueComentario(): Sentencia? {
        if (tokenActual.categoria == Categoria.BLOQUE_COMENTARIO) {
            var comentario = tokenActual
            obtenerSiguienteToken()
            return BloqueComentario(comentario)
        }
        return null
    }

    /**
     *  <Incremento> ::= ">" "+"
     */

    fun esIncremento(): Sentencia? {
        if (tokenActual.categoria == Categoria.INCREMENTO) {
            obtenerSiguienteToken()
            return Incremento()
        }
        return null
    }

    /**
     *  <Decremento> ::= ">" "-"
     */

    fun esDecremento(): Sentencia? {
        if (tokenActual.categoria == Categoria.DECREMENTO) {
            obtenerSiguienteToken()
            return Decremento()
        }
        return null
    }

    /**
     *  <Break> ::= "Break"
     */

    fun esBreak(): Sentencia? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "Break") {
            obtenerSiguienteToken()
            return Break()
        }
        return null
    }

    /**
     * <GoTo> ::= "GoTo"
     */

    fun esGoTo(): Sentencia? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "GoTo") {
            obtenerSiguienteToken()
            return GoTo()
        }
        return null
    }

    /**
     *  <Arreglo> ::=  array  identificador ":" <TipoDato> [OperadorDeAsignacion SimboloAbrir <ListaArgumentos> SimboloCerrar]
     */

    fun esArreglo(): Arreglo? {
        if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "array") {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                val nombre = tokenActual
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.DOS_PUNTOS) {
                    obtenerSiguienteToken()
                    val tipoDato = esTipoDato()
                    if (tipoDato != null) {
                        obtenerSiguienteToken()
                        var listaExpresiones = ArrayList<Expresion>()
                        if (tokenActual.categoria == Categoria.OPERADOR_DE_ASIGNACION && tokenActual.lexema == "->") {
                            obtenerSiguienteToken()
                            if (tokenActual.categoria == Categoria.SIMBOLO_DE_ABRIR) {
                                obtenerSiguienteToken()
                                listaExpresiones = esListaArgumentos()
                                if (tokenActual.categoria == Categoria.SIMBOLO_DE_CERRAR) {
                                    obtenerSiguienteToken()
                                    return Arreglo(nombre, tipoDato, listaExpresiones)
                                } else {
                                    reportarError("Falta el simbolo de cerrar")
                                }
                            } else {
                                reportarError("Falta el simbolo de abrir")
                            }
                        }
                        if (tokenActual.categoria == Categoria.TERMINAL_O_INICIAL) {
                            obtenerSiguienteToken()
                            return Arreglo(nombre, tipoDato, listaExpresiones)
                        } else {
                            reportarError("falta fin de sentencia")
                        }
                    } else {
                        reportarError("falta tipo de dato en el arreglo")
                    }
                } else {
                    reportarError("falta el indicador dos puntos")
                }
            } else {
                reportarError("falta definir el nombre del arreglo")
            }
        }
        return null
    }


    /**
     *  <Asignacion> ::= identificador operadorAsignacion <Expresion> | identificador operadorAsignacion <InvocarFuncion>
     */
    fun esAsignacion(): Asignacion? {
        val posicionInicial: Int = posicionActual
        if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
            val identificador = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.OPERADOR_DE_ASIGNACION) {
                val operadorAsignacion = tokenActual
                obtenerSiguienteToken()
                val invocacion = esInvocacionFuncion()
                if (invocacion != null) {
                    return Asignacion(identificador, operadorAsignacion, invocacion)
                } else {
                    val expresion = esExpresion()
                    if (expresion != null) {
                            return Asignacion(identificador, operadorAsignacion, expresion)
                    } else {
                        reportarError("Falta expresion en la asignacion")
                    }
                }
            } else {
                if (tokenActual.categoria == Categoria.SIMBOLO_DE_ABRIR) {
                    hacerBacktracking(posicionInicial)
                } else {
                    reportarError("Falta un operador de asignacion")
                }
            }
        }
        return null
    }

    /**
     *  <InvocacionFuncion> ::= Identificador "$" <ListaArgumentos> "$"
     */

    fun esInvocacionFuncion(): InvocacionFuncion? {
        var nombreFuncion: Token
        if (tokenActual.categoria == Categoria.INVOCACION) {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.IDENTIFICADOR) {
                var nombreFuncion = tokenActual
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.TERMINAL_O_INICIAL) {
                    obtenerSiguienteToken()
                    val expresiones = esListaArgumentos()
                    if (tokenActual.categoria == Categoria.TERMINAL_O_INICIAL) {
                        obtenerSiguienteToken()
                        return InvocacionFuncion(nombreFuncion, expresiones)
                    }else{
                        reportarError("Falta cerrar la sentencia")
                    }
                }else {
                    reportarError("Falta abrir la sentencia")
                }
            }else {
                reportarError("Falta nombre de la funcion")
            }
        }
        return null
    }

    /**
     *  <Ciclo> ::= &iterar& <ExpresionLogica>  <BloqueSentencias>
     */
    fun esCiclo(): Ciclo? {
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "&iterar&") {
            obtenerSiguienteToken()
            val expresion = esExpresionLogica()
            if(expresion != null) {
                val bloque = esBloqueSentencias()
                if(bloque != null) {
                    return Ciclo(expresion, bloque)
                }
            } else {
                reportarError("Hay un error en la condicion")
            }
        }
        return null
    }

    /**
     *  <Decision> ::= ¡SiES <ExpresionLogica> <BloqueSentencias> [!SiEs <Sentencias>]
     */
    fun esDecision(): Decision? {
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "¡SiEs") {
            obtenerSiguienteToken()
            val expresion =  esExpresionLogica()
            if(expresion != null) {
                    val bloqueV = esBloqueSentencias()
                    if(bloqueV != null){
                        if(tokenActual.lexema == "!SiEs"){
                            obtenerSiguienteToken()
                            val bloqueF = esBloqueSentencias()
                            if(bloqueF != null){
                                return Decision(expresion, bloqueV, bloqueF)
                            }
                        } else {
                            return Decision(expresion, bloqueV, listaSentenciaElse = null)
                        }
                    }
            } else {
                reportarError("Hay un error en condición")
            }
        }
        return null
    }

    /**
     *  <DeclaracionVariable> ::= var [Inmutable | Mutable] <ListaVariables>":"<TipoDato> | [<ListaVariables>":"<TipoDato>]
     */
    fun esDeclaracionVariable(): DeclaracionVariable? {
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "var") {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.PALABRA_RESERVADA && tokenActual.lexema == "Inmutable") {
                var mod = tokenActual.lexema
                obtenerSiguienteToken()
                val listaVariables = esListaVariables()
                if (listaVariables != null) {
                    if (tokenActual.categoria == Categoria.DOS_PUNTOS) {
                        obtenerSiguienteToken()
                        val tipoDato = esTipoDato()
                        if (tipoDato != null) {
                            obtenerSiguienteToken()
                            if (tokenActual.categoria == Categoria.OPERADOR_DE_ASIGNACION){
                                obtenerSiguienteToken()
                                var expresion = esExpresion()
                                if (expresion!=null){
                                    return DeclaracionVariable(listaVariables, tipoDato,mod)
                                }
                            }
                            return DeclaracionVariable(listaVariables, tipoDato, mod)
                        }else {
                            reportarError("Falta el tipo de dato")
                        }
                    }else {
                        reportarError("Faltan dos puntos")
                    }
                }else {
                    reportarError("Error en la lista de variables")
                }
            }else{
                val listaVariables = esListaVariables()
                if (listaVariables != null) {
                    if (tokenActual.categoria == Categoria.DOS_PUNTOS) {
                        obtenerSiguienteToken()
                        val tipoDato = esTipoDato()
                        if (tipoDato != null) {
                            obtenerSiguienteToken()
                            if (tokenActual.categoria == Categoria.OPERADOR_DE_ASIGNACION){
                                obtenerSiguienteToken()
                                var expresion = esExpresion()
                                if (expresion!=null){
                                    return DeclaracionVariable(listaVariables, tipoDato,"Mutable", expresion)
                                }
                            }
                            return DeclaracionVariable(listaVariables, tipoDato,"Mutable")
                        }else {
                            reportarError("Falta el tipo de dato")
                        }
                    }else {
                        reportarError("Faltan dos puntos")
                    }
                }else {
                    reportarError("Error en la lista de variables")
                }
            }
            val listaVariables = esListaVariables()
            if (listaVariables != null) {
                if (tokenActual.categoria == Categoria.DOS_PUNTOS) {
                    obtenerSiguienteToken()
                    val tipoDato = esTipoDato()
                    if (tipoDato != null) {
                        obtenerSiguienteToken()
                        if (tokenActual.categoria == Categoria.OPERADOR_DE_ASIGNACION){
                            obtenerSiguienteToken()
                            var expresion = esExpresion()
                            if (expresion!=null){
                                return DeclaracionVariable(listaVariables, tipoDato,"Mutable", expresion)
                            }
                        }
                        return DeclaracionVariable(listaVariables, tipoDato, "Mutable")
                    }else {
                        reportarError("Falta el tipo de dato")
                    }
                }else {
                    reportarError("Faltan dos puntos")
                }
            }else {
                reportarError("Error en la lista de variables")
            }
        }
        return null
    }


    /**
     * <ListaVariables> ::= <Variable> [“,” <ListaVariables>]
     */
    fun esListaVariables():ArrayList<Variable>{
        var listaVariable = ArrayList<Variable>()
        var variable = esVariable()
        while (variable != null) {
            listaVariable.add(variable)
            if (tokenActual.categoria == Categoria.COMA) {
                obtenerSiguienteToken()
                variable = esVariable()
            }else{
                break
            }
        }
        return listaVariable
    }

    /**
     * <Variable> ::=  Identificador [ operadorAsignacion <Expresion>]
     */
    fun esVariable():Variable?{
        if (tokenActual.categoria == Categoria.IDENTIFICADOR){
            var nombre = tokenActual
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.OPERADOR_DE_ASIGNACION){
                obtenerSiguienteToken()
                var expresion = esExpresion()
                if (expresion != null){
                    return Variable(nombre,expresion)
                }else {
                    reportarError("Un error en la expresión de asignación")
                }
            }else{
                return Variable(nombre)
            }
        }
        return null
    }

    /**
     *  <Expresion> ::= <ExpresionAritmetica> | <ExpresionRelacional> | <ExpresionLogica> | <ExpresionCadena>
     */

    fun esExpresion(): Expresion? {
        var e: Expresion? = esExpresionLogica()
        if(e != null) {
            return e
        }
        e = esExpresionAritmetica()
        if(e != null) {
            return e
        }
        e = esExpresionCadena()
        if(e != null) {
            return e
        }
        e = esExpresionCadena()
        return e
    }

    /**
     * < ExpresionArimetica > ::= "$"< ExpresionArimetica >"$" [operadorAritmetico <ExpresionArimetica>] | <valor numerico> | [operadorAritmetico <ExpresionArimetica>]
     */
    fun esExpresionAritmetica(): ExpresionAritmetica? {
        if (tokenActual.categoria == Categoria.SIMBOLO_DE_ABRIR){
            obtenerSiguienteToken()
            val exp1 = esExpresionAritmetica()
            if (exp1!=null){
                if (tokenActual.categoria == Categoria.SIMBOLO_DE_CERRAR){
                    obtenerSiguienteToken()
                    if (tokenActual.categoria == Categoria.OPERADOR_ARITMETICO){
                        val operador = tokenActual
                        obtenerSiguienteToken()
                        val exp2 = esExpresionAritmetica()
                        if (exp2!=null){
                            return ExpresionAritmetica(exp1,operador,exp2)
                        }else {
                            reportarError("Error en la segunda expresión")
                        }
                    }else{
                        return ExpresionAritmetica(exp1)
                    }
                }else {
                    reportarError("Falta el simbolo de cerrar")
                }
            }else {
                reportarError("Error en la primer expresión")
            }
        }else{
            val valor = esValorNumerico()
            if (valor!=null){
                obtenerSiguienteToken()
                if (tokenActual.categoria == Categoria.OPERADOR_ARITMETICO){
                    val operador = tokenActual
                    obtenerSiguienteToken()
                    val exp = esExpresionAritmetica()
                    if (exp!=null){
                        return ExpresionAritmetica(valor,operador,exp)
                    }else {
                        reportarError("Error en la expresión")
                    }
                }else{
                    return ExpresionAritmetica(valor)
                }
            }
        }
        return null
    }

    /**
     * <ExpresionLogica> ::= operadorNegacion <ExpresionLogica> [OperadorLogico <ExpresionLogica>] | <ValorLogico>[OperadorLogico <ExpresionLogica>]
     *
     * <ExpresionLogica> ::= operadorNegacion <ExpresionLogica> |
     *                      operadorNegacion <ExpresionLogica> OperadorLogico <ExpresionLogica> |
     *                       <ValorLogico> |
     *                        <ValorLogico> [OperadorLogico <ExpresionLogica>
     */
    fun esExpresionLogica(): ExpresionLogica? {
        var pos = posicionActual
        if (tokenActual.categoria == Categoria.OPERADOR_NEGACION){
            var operadorNegacion = tokenActual
            obtenerSiguienteToken()
            var expL1 = esExpresionLogica()
            if (expL1!=null){
                if (tokenActual.categoria==Categoria.OPERADOR_LOGICO){
                    var operadorLogico = tokenActual
                    obtenerSiguienteToken()
                    var expL2 = esExpresionLogica()
                    if (expL2!=null){
                        return ExpresionLogica(operadorNegacion, expL1, operadorLogico, expL2)
                    }else {
                        reportarError("Error en la expresión logíca")
                    }
                }else{
                    return ExpresionLogica(operadorNegacion,expL1)
                }
            }else {
                reportarError("Error en la expresión logíca")
            }
        }else{
            var valorLogico = esValorLogico()
            if (valorLogico!=null){
                if (tokenActual.categoria==Categoria.OPERADOR_LOGICO){
                    var operadorLogico = tokenActual
                    obtenerSiguienteToken()
                    var expL = esExpresionLogica()
                    if (expL!=null){
                        return ExpresionLogica(valorLogico,operadorLogico,expL)
                    }else {
                        reportarError("Error en la expresión logíca")
                    }
                }else{
                    return ExpresionLogica(valorLogico)
                }
            }
        }
        hacerBacktracking(pos)
        return null
    }

    /**
     *  <ValorLogico> ::= <ExpresionRelacional> | Booleano
     */

    fun esValorLogico():ValorLogico?{
        val exp = esExpresionRelacional()
        if (exp != null) {
            return ValorLogico(null, exp)
        }
        if (tokenActual?.categoria == Categoria.VERDADERO || tokenActual?.categoria == Categoria.FALSO) {
            val valor = tokenActual
            obtenerSiguienteToken()
            return ValorLogico(valor, null)
        }
        return null
    }

    /**
     *  <ValorNumerico> ::= [<OperadorAritmetico>] Entero | Decimal | Identificador
     */

    fun esValorNumerico(): ValorNumerico? {
        var signo: Token? = null
        if (tokenActual.categoria == Categoria.OPERADOR_ARITMETICO && (tokenActual.lexema == "+" || tokenActual.lexema == "-")) {
            signo = tokenActual
            obtenerSiguienteToken()
        }
        if (tokenActual.categoria == Categoria.ENTERO || tokenActual.categoria == Categoria.DECIMAL ||tokenActual.categoria == Categoria.IDENTIFICADOR){
            val termino = tokenActual
            return ValorNumerico(signo,termino)
        }
        return null
    }

    /**
     *  <ExpresionRelacional> ::= <ExpresionAritmetica> OperadorRelacional <ExpresionAritmetica>
     */

    fun esExpresionRelacional(): ExpresionRelacional? {
        var expA = esExpresionAritmetica()
        if (expA!=null) {
            if (tokenActual.categoria == Categoria.OPERADOR_RELACIONAL){
                var operador = tokenActual
                obtenerSiguienteToken()
                var expA2 = esExpresionAritmetica()
                if (expA2 != null){
                    return ExpresionRelacional(expA,operador,expA2)
                }else {
                    reportarError("Error en la expresión relacional")
                }
            }
        }
        return null
    }

    /**
     * <ExpresionCadena> ::= ° <Expresion> °
     */


    fun esExpresionCadena(): ExpresionCadena? {
        if (tokenActual.categoria==Categoria.ASIGNACION_PARA_CADENA){
            var cadena = tokenActual
            obtenerSiguienteToken()
            var expresion:Expresion?=null
            if (tokenActual.lexema == "+"){
                obtenerSiguienteToken()
                expresion = esExpresion()
                return ExpresionCadena(cadena, expresion!!)
            }
            if (expresion==null)
            return ExpresionCadena(cadena)
        }
        if (tokenActual.categoria == Categoria.IDENTIFICADOR){
            val variable = tokenActual
            obtenerSiguienteToken()
            return ExpresionCadena(variable)
        }
        return null
    }

    /**
     *  <ListaArgumentos> ::= <Expresion> ","
     */

    fun esListaArgumentos(): ArrayList<Expresion> {
        val lista = ArrayList<Expresion>()
        var param = esExpresion()
        while(param != null) {
            lista.add(param)
            if(tokenActual.categoria == Categoria.COMA) {
                obtenerSiguienteToken()
                param = esExpresion()
            } else {
                param = null
            }
        }
        return lista
    }

    /**
     * <Lectura> ::= toRead “$” Expresion “$”
     */
    fun esLectura(): Lectura? {
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA&&tokenActual.lexema == "toRead") {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.TERMINAL_O_INICIAL){
                obtenerSiguienteToken()
                var expresion = esVariable()!!.nombre
                if (expresion != null){
                    if (tokenActual.categoria == Categoria.TERMINAL_O_INICIAL){
                        obtenerSiguienteToken()
                        return Lectura(expresion)
                    }else {
                        reportarError("Falta cerrar sentencia")
                    }
                }else {
                    reportarError("Error en la expresión")
                }
            }else {
                reportarError("Falta abrir sentencia")
            }
        }
        return null
    }

    /**
     *  <Retorno> ::= "return" ":" <Expresion>
     */

    fun esRetorno(): Retorno? {
        if(tokenActual.categoria==Categoria.PALABRA_RESERVADA && tokenActual.lexema == "return") {
            obtenerSiguienteToken()
            if(tokenActual.categoria == Categoria.DOS_PUNTOS) {
                obtenerSiguienteToken()
                val expresion = esExpresion()
                if(expresion != null) {
                    if(tokenActual.categoria == Categoria.TERMINAL_O_INICIAL) {
                        obtenerSiguienteToken()
                        return Retorno(expresion)
                    } else {
                        reportarError("Falta fin de sentencia retorno")
                    }
                } else {
                    reportarError("Error en la expresion")
                }
            } else {
                reportarError("Faltan dos puntos en return")
            }
        }
        return null
    }

    /**
     * <Impresión> ::= toPrint “$” Expresion “$”
     */
    fun esImpresion(): Impresion?{
        if(tokenActual.categoria == Categoria.PALABRA_RESERVADA&&tokenActual.lexema == "toPrint") {
            obtenerSiguienteToken()
            if (tokenActual.categoria == Categoria.TERMINAL_O_INICIAL){
                obtenerSiguienteToken()
                var expresion = esExpresion()
                if (expresion != null){
                    if (tokenActual.categoria == Categoria.TERMINAL_O_INICIAL){
                        obtenerSiguienteToken()
                        return Impresion(expresion)
                    }else {
                        reportarError("Falta el simbolo de cerrar sentencia")
                    }
                }else {
                    reportarError("Error en la expresión")
                }
            }else {
                reportarError("Falta simbolo abrir sentencia")
            }
        }
        return null
    }
}