package co.edu.uniquindio.compiladores.controladores

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico
import javafx.fxml.FXML
import java.awt.TextArea
import java.awt.TextField
import java.awt.event.ActionEvent

class InicioController {
    @FXML lateinit var codigoFuente : TextArea
    @FXML
    fun analizarCodigo(actionEvent: ActionEvent) {
        if (codigoFuente.text.length>0) {
            val lexico = AnalizadorLexico(codigoFuente.text)
            lexico.analizar()
            print(lexico.listaTokens)
        }
    }
}