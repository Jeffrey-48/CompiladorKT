package co.edu.uniquindio.compiladores.lexico

class AnalizadorLexico(var codigoFuente: String) {
    var caracterActual = codigoFuente[0]
    var listaTokens = ArrayList<Token>()
    var posicionActual = 0
    var finCodigo = 0.toChar()
    var filaActual = 0
    var columnaActual = 0
    var palabrasReservadas = ArrayList<String>()
    var listaErrores = ArrayList<Error>()

    fun almacenarToken(lexema: String, categoria: Categoria, fila: Int, columna: Int) = listaTokens.add(Token(lexema, categoria, fila, columna))

    fun hacerBT(posicionInicial: Int, filaInicial: Int, columnaInicial: Int) {
        posicionActual = posicionInicial
        filaActual = filaInicial
        columnaActual = columnaInicial
        caracterActual = codigoFuente[posicionActual]
    }

    fun analizar() {
        while (caracterActual != finCodigo) {
            if (caracterActual == ' ' || caracterActual == '\t' || caracterActual == '\n') {
                obtenerSiguienteCaracter()
                continue
            }
            if (esReservadaFuncion()) continue
            if (esReservadaArreglo()) continue
            if (esReservadaBooleano()) continue
            if (esReservadaLectura()) continue
            if (esReservadaRetorno()) continue
            if (esReservadaGoTo()) continue
            if (esReservadaBreak()) continue
            if (esSimboloIncremento()) continue
            if (esSimboloDecremento()) continue
            if (esReservadaInmutable()) continue
            if (esSimboloInvocacion()) continue
            if (esBooleano()) continue
            if (esEntero()) continue
            if (esDecimal()) continue
            if (esReal()) continue
            if (esComa()) continue
            if (esDosPuntos()) continue
            if (esLineaComentario()) continue
            if (esBloqueComentario()) continue
            if (esReservadaVariable()) continue
            if (esReservadaDecision()) continue
            if (esReservadaImpresion()) continue
            if (esReservadaDeLoContrario()) continue
            if (esSimboloDeAbrir()) continue
            if (esSimboloDeCerrar()) continue
            if (esOperadorAritmetico()) continue
            if (esOperadorAsignacion()) continue
            if (operadoresRelacionales()) continue
            if (esOperadorLogico()) continue
            if (esOperadorNegacion()) continue
            if (Caracter()) continue
            if (esCadena()) continue
            if (esReservadaIterador()) continue
            if (esReservadaDecimal()) continue
            if (esReservadaCadena()) continue
            if (esReservadaCaracter()) continue
            if (esReservadaEntero()) continue
            if (esReservadaMain()) continue
            if (esReservadaNombresVariables()) continue
            if (esReservadaNombresMetodos()) continue
            if (esReservadaNombresClase()) continue
            if (esTerminalOInicial()) continue
            if (esIdentificador()) continue

            almacenarToken("" + caracterActual, Categoria.DESCONOCIDO, filaActual, columnaActual)
            obtenerSiguienteCaracter()
        }
    }

    fun esEntero(): Boolean{
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var lexema = ""
        if(caracterActual == '/') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual.isDigit()) {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                while (caracterActual.isDigit()) {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
                if (caracterActual == '.') {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
                if (caracterActual == '/'){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.ENTERO, filaInicial, columnaInicial)
                    return true
                }else{
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            }else{
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        return false
    }

    fun esSimboloInvocacion(): Boolean{
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var lexema = ""

        if(caracterActual == '<' ) {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.INVOCACION, filaInicial, columnaInicial)
            return true

        }else{
            hacerBT(posicionInicial,filaInicial,columnaInicial)
            return false
        }
        return false
    }

    fun obtenerSiguienteCaracter() {
        if (posicionActual == codigoFuente.length - 1) {
            caracterActual = finCodigo
        } else {
            if (caracterActual == '\n') {
                filaActual++
                columnaActual = 0
            } else {
                columnaActual++
            }
            posicionActual++
            caracterActual = codigoFuente[posicionActual]
        }
    }

    fun esIdentificador(): Boolean {
        if (caracterActual.isLetter() || caracterActual == '$' || caracterActual == '_') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            while (caracterActual.isLetter() || caracterActual == '$' || caracterActual == '_' || caracterActual.isDigit()) {
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }
            almacenarToken(lexema, Categoria.IDENTIFICADOR, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    fun esComa(): Boolean {
        if (caracterActual == ',') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.COMA, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    fun esDosPuntos(): Boolean {
        if (caracterActual == ':') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()

            almacenarToken(lexema, Categoria.DOS_PUNTOS, filaInicial, columnaInicial)
            return true
        }
        return false
    }



    fun esDecimal(): Boolean {
        if (caracterActual.isDigit() || caracterActual == '.') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            var lexema = ""
            if (caracterActual == '.') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual.isDigit()) {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                } else
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            } else {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                while (caracterActual.isDigit()) {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
                if (caracterActual == '.') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()

                }
            }
            while (caracterActual.isDigit()) {
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }
            almacenarToken(lexema, Categoria.DECIMAL, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    fun esOperadorAritmetico(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var lexema = ""
        if (caracterActual == '+' || caracterActual == '*' || caracterActual == '/' || caracterActual == '-') {
            if (caracterActual == '-') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual != '>') {
                    almacenarToken(lexema, Categoria.OPERADOR_ARITMETICO, filaInicial, columnaInicial)
                    return true
                } else {
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            }
            if (caracterActual != '-') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.OPERADOR_ARITMETICO, filaInicial, columnaInicial)
                return true
            }
        }
        return false
    }

    fun esOperadorAsignacion(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var lexema = ""
        if (caracterActual == '-') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == '>') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.OPERADOR_DE_ASIGNACION, filaInicial, columnaInicial)
                return true
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }else{
            hacerBT(posicionInicial, filaInicial, columnaInicial)
            return false
        }
        return false
    }

    fun esReal(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var lexema = ""
        if (caracterActual == '%') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual.isDigit() || caracterActual == '.') {

                if (caracterActual == '.') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    if (caracterActual.isDigit()) {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                    }
                } else {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    while (caracterActual.isDigit()) {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()
                    }
                    if (caracterActual == '.') {
                        lexema += caracterActual
                        obtenerSiguienteCaracter()

                    }
                }
                while (caracterActual.isDigit()) {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
                if (caracterActual == '%') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.REAL, filaInicial, columnaInicial)
                    return true
                }
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        return false
    }

    fun operadoresRelacionales(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual;
        var lexema = ""
        if (caracterActual == '.') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == '.') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial)
                return true
            } else {
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        } else {
            if (caracterActual == '?' || caracterActual == '¿' || caracterActual == '¡') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial)
                return true
            }
        }
        return false
    }


    fun esOperadorLogico(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var lexema = ""
        if (caracterActual == 'Y' || caracterActual == 'O') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.OPERADOR_LOGICO, filaInicial, columnaInicial)
            return true
        }
        if (caracterActual == ',') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == ',') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.OPERADOR_LOGICO, filaInicial, columnaInicial)
                return true
            }else{
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        return false
    }
    fun esOperadorNegacion(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var lexema = ""
        if (caracterActual == '¬') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.OPERADOR_NEGACION, filaInicial, columnaInicial)
            return true
        }
        return false
    }
    fun esSimboloDeAbrir(): Boolean{
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var lexema = ""
        if (caracterActual == '_') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if ( caracterActual=='-') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.SIMBOLO_DE_ABRIR, filaInicial, columnaInicial)
                return true
            }else{
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        return false
    }
    fun esSimboloDeCerrar(): Boolean{
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var lexema = ""
        if (caracterActual == '-') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if ( caracterActual=='_') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.SIMBOLO_DE_CERRAR, filaInicial, columnaInicial)
                return true
            }else{
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        return false
    }
    fun esSimboloIncremento(): Boolean{
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var lexema = ""
        if (caracterActual == '>') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if ( caracterActual=='+') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.INCREMENTO, filaInicial, columnaInicial)
                return true
            }else{
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        return false
    }
    fun esSimboloDecremento(): Boolean{
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var lexema = ""
        if (caracterActual == '>') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if ( caracterActual=='-') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.DECREMENTO, filaInicial, columnaInicial)
                return true
            }else{
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        return false
    }
    fun esTerminalOInicial(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var lexema = ""
        if (caracterActual == '$') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            almacenarToken(lexema, Categoria.TERMINAL_O_INICIAL, filaInicial, columnaInicial)
            return true
        } else {
            hacerBT(posicionInicial, filaInicial, columnaInicial)
            return false
        }
        return false
    }
    fun Caracter(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var lexema = ""
        if (caracterActual == '#') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            lexema += caracterActual
            almacenarToken(lexema, Categoria.CARACTER, filaInicial, columnaInicial)
            return true
        } else {
            hacerBT(posicionInicial, filaInicial, columnaInicial)
            return false
        }
        return false
    }
    fun esCadena(): Boolean{
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var contador : Int = 0
        var lexema = ""
        if (caracterActual == '°') {
            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (caracterActual != '°' && contador <= codigoFuente.length) {
                contador++
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }
            if (caracterActual == '°') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.ASIGNACION_PARA_CADENA, filaInicial, columnaInicial)
                return true
            }
            hacerBT(posicionInicial,filaInicial,columnaInicial)
            return false
        }
        return false
    }
    fun esReservadaEntero(): Boolean{
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var contador : Int = 0
        var palabra : String = ""
        var lexema = ""
        if (caracterActual == '_') {
            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (contador <= 5) {
                palabra+= caracterActual
                lexema += caracterActual
                obtenerSiguienteCaracter()
                contador++
            }
            if (palabra.equals("entero")) {
                almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                return true
            }
            hacerBT(posicionInicial,filaInicial,columnaInicial)
            return false
        }
        return false
    }

    fun esReservadaDecimal(): Boolean{
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var contador : Int = 0
        var palabra : String = ""
        var lexema = ""
        if (caracterActual == '_') {
            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (contador <= 6) {
                palabra+= caracterActual
                lexema += caracterActual
                obtenerSiguienteCaracter()
                contador++
            }
            if (palabra.equals("decimal")) {
                almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                return true
            }
            hacerBT(posicionInicial,filaInicial,columnaInicial)
            return false
        }
        return false
    }

    fun esReservadaCadena(): Boolean{
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var contador : Int = 0
        var palabra : String = ""
        var lexema = ""
        if (caracterActual == '_') {
            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (contador <= 5) {
                palabra+= caracterActual
                lexema += caracterActual
                obtenerSiguienteCaracter()
                contador++
            }
            if (palabra.equals("cadena")) {
                almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                return true
            }
            hacerBT(posicionInicial,filaInicial,columnaInicial)
            return false
        }
        return false
    }

    fun esReservadaCaracter(): Boolean{
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var contador : Int = 0
        var palabra : String = ""
        var lexema = ""
        if (caracterActual == '_') {
            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (contador <= 7) {
                palabra+= caracterActual
                lexema += caracterActual
                obtenerSiguienteCaracter()
                contador++
            }
            if (palabra.equals("caracter")) {
                almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                return true
            }
            hacerBT(posicionInicial,filaInicial,columnaInicial)
            return false
        }
        return false
    }
    fun esReservadaIterador(): Boolean{
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var contador : Int = 0
        var palabra : String = ""
        var lexema = ""
        if (caracterActual == '&') {
            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (contador <= 5) {
                palabra += caracterActual
                lexema += caracterActual
                obtenerSiguienteCaracter()
                contador++
            }
            if (palabra.equals("iterar")) {
                if (caracterActual == '&') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                    return true
                } else{
                    hacerBT(posicionInicial, filaInicial, columnaInicial)
                    return false
                }
            }
        }

        return false
    }
    fun esReservadaDecision(): Boolean{
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var contador : Int = 0
        var palabra : String = ""
        var lexema = ""
        if (caracterActual == '¡') {
            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (contador <= 3) {
                palabra+= caracterActual
                lexema += caracterActual
                obtenerSiguienteCaracter()
                contador++
            }
            if (palabra.equals("SiEs")) {
                almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                return true
            }
            hacerBT(posicionInicial,filaInicial,columnaInicial)
            return false
        }
        return false
    }
    fun esReservadaDeLoContrario(): Boolean{
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var contador : Int = 0
        var palabra : String = ""
        var lexema = ""
        if (caracterActual == '!') {
            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (contador <= 3) {
                palabra+= caracterActual
                lexema += caracterActual
                obtenerSiguienteCaracter()
                contador++
            }
            if (palabra.equals("SiEs")) {
                almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                return true
            }
            hacerBT(posicionInicial,filaInicial,columnaInicial)
            return false
        }
        return false
    }
    fun esReservadaMain(): Boolean{
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var contador : Int = 0
        var palabra : String = ""
        var lexema = ""

            while (contador <= 8) {
                palabra+= caracterActual
                lexema += caracterActual
                obtenerSiguienteCaracter()
                contador++
            }
            if (palabra.equals("Principal")) {
                almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                return true
            }else{
            hacerBT(posicionInicial,filaInicial,columnaInicial)
            return false
        }
        return false
    }

    fun esReservadaFuncion(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var contador: Int = 0
        var palabra: String = ""
        var lexema = ""

        while (contador <= 7) {
            palabra += caracterActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            contador++
        }
        if (palabra.equals("function")) {
            almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
            return true
        } else {
            hacerBT(posicionInicial, filaInicial, columnaInicial)
            return false
        }
        return false
    }

    fun esBooleano(): Boolean{
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var contador : Int = 0
        var palabra : String = ""
        var lexema = ""
        if (caracterActual == '+') {
            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (contador <= 8) {
                palabra+= caracterActual
                lexema += caracterActual
                obtenerSiguienteCaracter()
                contador++
            }
            if (palabra.equals("verdadero")) {
                almacenarToken(lexema, Categoria.VERDADERO, filaInicial, columnaInicial)
                return true
            }
            hacerBT(posicionInicial,filaInicial,columnaInicial)
            return false
        }
        if (caracterActual == '-') {
            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (contador <= 4) {
                palabra+= caracterActual
                lexema += caracterActual
                obtenerSiguienteCaracter()
                contador++
            }
            if (palabra.equals("falso")) {
                almacenarToken(lexema, Categoria.FALSO, filaInicial, columnaInicial)
                return true
            }
            hacerBT(posicionInicial,filaInicial,columnaInicial)
            return false
        }
        return false
    }

    fun esReservadaBreak(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var contador: Int = 0
        var palabra: String = ""
        var lexema = ""

        while (contador <= 4) {
            palabra += caracterActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            contador++
        }
        if (palabra.equals("Break")) {
            almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
            return true
        } else {
            hacerBT(posicionInicial, filaInicial, columnaInicial)
            return false
        }
        return false
    }

    fun esReservadaGoTo(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var contador: Int = 0
        var palabra: String = ""
        var lexema = ""

        while (contador <= 3) {
            palabra += caracterActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            contador++
        }
        if (palabra.equals("GoTo")) {
            almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
            return true
        } else {
            hacerBT(posicionInicial, filaInicial, columnaInicial)
            return false
        }
        return false
    }

    fun esReservadaBooleano(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var contador : Int = 0
        var palabra : String = ""
        var lexema = ""
        if (caracterActual == '_') {
            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (contador <= 7) {
                palabra+= caracterActual
                lexema += caracterActual
                obtenerSiguienteCaracter()
                contador++
            }
            if (palabra.equals("booleano")) {
                almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
                return true
            }
            hacerBT(posicionInicial,filaInicial,columnaInicial)
            return false
        }
        return false
    }

    fun esReservadaArreglo(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var contador: Int = 0
        var palabra: String = ""
        var lexema = ""

        while (contador <= 4) {
            palabra += caracterActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            contador++
        }
        if (palabra.equals("array")) {
            almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
            return true
        } else {
            hacerBT(posicionInicial, filaInicial, columnaInicial)
            return false
        }
        return false
    }

    fun esReservadaVariable(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var contador: Int = 0
        var palabra: String = ""
        var lexema = ""

        while (contador <= 2) {
            palabra += caracterActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            contador++
        }
        if (palabra.equals("var")) {
            almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
            return true
        } else {
            hacerBT(posicionInicial, filaInicial, columnaInicial)
            return false
        }
        return false
    }

    fun esReservadaImpresion(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var contador: Int = 0
        var palabra: String = ""
        var lexema = ""

        while (contador <= 6) {
            palabra += caracterActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            contador++
        }
        if (palabra.equals("toPrint")) {
            almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
            return true
        } else {
            hacerBT(posicionInicial, filaInicial, columnaInicial)
            return false
        }
        return false
    }
    fun esReservadaLectura(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var contador: Int = 0
        var palabra: String = ""
        var lexema = ""

        while (contador <= 5) {
            palabra += caracterActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            contador++
        }
        if (palabra.equals("toRead")) {
            almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
            return true
        } else {
            hacerBT(posicionInicial, filaInicial, columnaInicial)
            return false
        }
        return false
    }

    fun esReservadaRetorno(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var contador: Int = 0
        var palabra: String = ""
        var lexema = ""

        while (contador <= 5) {
            palabra += caracterActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            contador++
        }
        if (palabra.equals("return")) {
            almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
            return true
        } else {
            hacerBT(posicionInicial, filaInicial, columnaInicial)
            return false
        }
        return false
    }

    fun esReservadaInmutable(): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var contador: Int = 0
        var palabra: String = ""
        var lexema = ""

        while (contador <= 8) {
            palabra += caracterActual
            lexema += caracterActual
            obtenerSiguienteCaracter()
            contador++
        }
        if (palabra.equals("Inmutable")) {
            almacenarToken(lexema, Categoria.PALABRA_RESERVADA, filaInicial, columnaInicial)
            return true
        } else {
            hacerBT(posicionInicial, filaInicial, columnaInicial)
            return false
        }
        return false
    }

    fun esReservadaNombresVariables(): Boolean{
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var lexema = ""

        if(caracterActual >= 65.toChar() && caracterActual<= 90.toChar()) {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual.isLetter()) {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                while (caracterActual.isLetter() || caracterActual.isDigit()) {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
                almacenarToken(lexema, Categoria.IDENTIFICADOR, filaInicial, columnaInicial)
                return true
            }
        }else{
                hacerBT(posicionInicial,filaInicial,columnaInicial)
            return false
        }
        return false
    }
    fun esReservadaNombresMetodos(): Boolean{
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var lexema = ""

        if(caracterActual >= 97.toChar() && caracterActual<= 122.toChar()) {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual.isLetter()) {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                while (caracterActual.isLetter() || caracterActual.isDigit()) {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
                almacenarToken(lexema, Categoria.IDENTIFICADOR, filaInicial, columnaInicial)
                return true
            }
        }else{
            hacerBT(posicionInicial,filaInicial,columnaInicial)
            return false
        }
        return false
    }
    fun esReservadaNombresClase(): Boolean{
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var lexema = ""

        if(caracterActual == '^' ) {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == '^') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                while (caracterActual.isLetter() || caracterActual.isDigit()) {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
                almacenarToken(lexema, Categoria.IDENTIFICADOR, filaInicial, columnaInicial)
                return true
            }
        }else{
            hacerBT(posicionInicial,filaInicial,columnaInicial)
            return false
        }
        return false
    }

    fun esBloqueComentario(): Boolean{
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var contador : Int = 0
        var lexema = ""
        if (caracterActual == '~') {
            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (caracterActual != '~' && contador <= codigoFuente.length) {
                contador++
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }
            if (caracterActual == '~') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.BLOQUE_COMENTARIO, filaInicial, columnaInicial)
                return true
            }
            hacerBT(posicionInicial,filaInicial,columnaInicial)
            return false
        }
        return false
    }
    fun esLineaComentario(): Boolean{
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var lexema = ""

        if(caracterActual == '~' ) {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if (caracterActual == '~') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                var contador = 0
                while (caracterActual != '~' && contador <= codigoFuente.length) {
                    contador++
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }
                if (caracterActual == '~') {
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.LINEA_COMENTARIO, filaInicial, columnaInicial)
                    return true
                }
            }else{
                hacerBT(posicionInicial,filaInicial,columnaInicial)
                return false
            }
        }else{
            hacerBT(posicionInicial,filaInicial,columnaInicial)
            return false
        }
        return false
    }
}



