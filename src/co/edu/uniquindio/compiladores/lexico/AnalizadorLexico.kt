package co.edu.uniquindio.compiladores.lexico

class AnalizadorLexico(var codigoFuente: String){
    var caracterActual=codigoFuente[0]
    var listaTokens = ArrayList<Token>()
    var posicionActual = 0
    var finCodigo = 0.toChar()
    var filaActual = 0
    var columnaActual = 0

    fun almacenarToken(lexema: String, categoria: Categoria, fila: Int, columna: Int) = listaTokens.add(Token(lexema, categoria, fila, columna))

    fun hacerBT(posicionInicial: Int, filaInicial: Int, columnaInicial: Int){
        posicionActual = posicionInicial
        filaActual = filaInicial
        columnaActual = columnaInicial
        caracterActual = codigoFuente[posicionActual]
    }

    fun analizar(){
        while (caracterActual != finCodigo){
            if (caracterActual == ' ' || caracterActual == '\t' || caracterActual == '\n'){
                obtenerSiguienteCaracter()
                continue
            }
            if (esEntero()) continue
            if (esDecimal()) continue
            if (esReal()) continue
            if (esIdentificador()) continue
            if (esOperadorAritmetico ()) continue
            if(esOperadorAsignacion()) continue
            if (operadoresRelacionales ()) continue
            almacenarToken("" + caracterActual, Categoria.DESCONOCIDO, filaActual, columnaActual)
            obtenerSiguienteCaracter()
        }
    }

    fun esEntero(): Boolean{
        if (caracterActual.isDigit()){
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            while (caracterActual.isDigit()){
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }
            if (caracterActual == '.'){
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
            almacenarToken(lexema, Categoria.ENTERO, filaInicial, columnaInicial)
            return true
        }
        return false
    }
    fun obtenerSiguienteCaracter(){
        if (posicionActual == codigoFuente.length-1){
            caracterActual =  finCodigo
        }else{
            if (caracterActual == '\n'){
                filaActual++
                columnaActual = 0
            }else{
                columnaActual++
            }
            posicionActual++
            caracterActual = codigoFuente[posicionActual]
        }
    }

    fun esIdentificador(): Boolean{
        if (caracterActual.isLetter() || caracterActual == '$' || caracterActual == '_'){
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var lexema = ""
            lexema += caracterActual
            obtenerSiguienteCaracter()
            while (caracterActual.isLetter() || caracterActual == '$' || caracterActual == '_' || caracterActual.isDigit()){
                lexema += caracterActual
                obtenerSiguienteCaracter()
            }
            almacenarToken(lexema, Categoria.IDENTIFICADOR, filaInicial, columnaInicial)
            return true
        }
        return false
    }

    fun esDecimal(): Boolean{
        if (caracterActual.isDigit() || caracterActual == '.') {
            var filaInicial = filaActual
            var columnaInicial = columnaActual
            var posicionInicial = posicionActual
            var lexema = ""
            if (caracterActual == '.') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual.isDigit()){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                }else
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
    fun esOperadorAritmetico (): Boolean {
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var lexema = ""
        if (caracterActual == '+' || caracterActual == '*' || caracterActual == '/') {
            if (caracterActual == '-'){
                lexema += caracterActual
                obtenerSiguienteCaracter()
                if (caracterActual != '>'){
                    lexema += caracterActual
                    obtenerSiguienteCaracter()
                    almacenarToken(lexema, Categoria.OPERADOR_ARITMETICO, filaInicial, columnaInicial)
                    return true
                }
            }

        }
        return false
    }
    fun esOperadorAsignacion(): Boolean{
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual
        var lexema = ""
        if (caracterActual == '-') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if ( caracterActual=='>') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.OPERADOR_DE_ASIGNACION, filaInicial, columnaInicial)
                return true
            }else{
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }
        return false
    }
    fun esReal(): Boolean{
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
                hacerBT(posicionInicial,filaInicial,columnaInicial)
                return false
            }
        }
        return false
    }
    fun operadoresRelacionales (): Boolean{
        var filaInicial = filaActual
        var columnaInicial = columnaActual
        var posicionInicial = posicionActual;
        var lexema = ""
        if (caracterActual == '.') {
            lexema += caracterActual
            obtenerSiguienteCaracter()
            if ( caracterActual=='.') {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial)
                return true
            }else{
                hacerBT(posicionInicial, filaInicial, columnaInicial)
                return false
            }
        }else{
            if (caracterActual == '?' || caracterActual == '¿' || caracterActual == '¡' ) {
                lexema += caracterActual
                obtenerSiguienteCaracter()
                almacenarToken(lexema, Categoria.OPERADOR_RELACIONAL, filaInicial, columnaInicial)
                return true
            }
        }
        return false
    }
}