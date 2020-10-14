package co.edu.uniquindio.compiladores.lexico

class AnalizadorLexico(var codigoFuente: String) {
    var caracterActual = codigoFuente[0]
    var listaTokens = ArrayList<Token>()
    var posicionActual = 0
    var finCodigo = 0.toChar()
    var filaActual = 0
    var columnaActual = 0

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
            if (esEntero()) continue
            if (esDecimal()) continue
            if (esReal()) continue
            if (esSimboloDeAbrir()) continue
            if (esSimboloDeCerrar()) continue
            if (esOperadorAritmetico()) continue
            if (esOperadorAsignacion()) continue
            if (operadoresRelacionales()) continue
            if (esOperadorLogico()) continue
            if (Caracter()) continue
            if (esCadena()) continue
            if (esReservadaIterador()) continue
            if (esReservadaDecimal()) continue
            if (esReservadaCadena()) continue
            if (esReservadaCaracter()) continue
            if (esReservadaEntero()) continue
            if (esReservadaDecision()) continue
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
                almacenarToken(lexema, Categoria.PALABRA_RESERVADA_PARA_ENTERO, filaInicial, columnaInicial)
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
                almacenarToken(lexema, Categoria.PALABRA_RESERVADA_PARA_DECIMAL, filaInicial, columnaInicial)
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
                almacenarToken(lexema, Categoria.PALABRA_RESERVADA_PARA_CADENA, filaInicial, columnaInicial)
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
                almacenarToken(lexema, Categoria.PALABRA_RESERVADA_PARA_CARACTER, filaInicial, columnaInicial)
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
                    almacenarToken(lexema, Categoria.PALABRA_RESERVADA_PARA_ITERADOR, filaInicial, columnaInicial)
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
        if (caracterActual == '$') {
            lexema += caracterActual
            obtenerSiguienteCaracter()

            while (contador <= 3) {
                palabra+= caracterActual
                lexema += caracterActual
                obtenerSiguienteCaracter()
                contador++
            }
            if (palabra.equals("SiEs")) {
                almacenarToken(lexema, Categoria.PALABRA_RESERVADA_PARA_DECISION, filaInicial, columnaInicial)
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
                almacenarToken(lexema, Categoria.PALABRA_RESERVADA_PARA_MAIN, filaInicial, columnaInicial)
                return true
            }else{
            hacerBT(posicionInicial,filaInicial,columnaInicial)
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
                almacenarToken(lexema, Categoria.PALABRA_RESERVADA_PARA_NOMBRES_VARIABLES, filaInicial, columnaInicial)
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
                almacenarToken(lexema, Categoria.PALABRA_RESERVADA_PARA_NOMBRES_METODOS, filaInicial, columnaInicial)
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
                almacenarToken(lexema, Categoria.PALABRA_RESERVADA_PARA_NOMBRES_CLASES, filaInicial, columnaInicial)
                return true
            }
        }else{
            hacerBT(posicionInicial,filaInicial,columnaInicial)
            return false
        }
        return false
    }
}



