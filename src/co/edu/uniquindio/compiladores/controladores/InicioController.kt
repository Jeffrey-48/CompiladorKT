package co.edu.uniquindio.compiladores.controladores

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Token
import javafx.beans.Observable
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.scene.control.TextArea
import javafx.scene.control.TableView
import javafx.event.ActionEvent
import javafx.scene.control.TableColumn
import javafx.collections.ObservableList
import javafx.scene.control.cell.PropertyValueFactory

class InicioController {
    @FXML lateinit var codigoFuente : TextArea
    @FXML lateinit var tablaTokens : TableView<Token>
    @FXML lateinit var columnaLexema : TableColumn<Token,String>
    @FXML lateinit var columnaCategoria : TableColumn<Token,Categoria>
    @FXML lateinit var ColumnaFila : TableColumn<Token,Int>
    @FXML lateinit var ColumnaColumna : TableColumn<Token,Int>

    @FXML
    fun analizarCodigo(actionEvent: ActionEvent) {
        if (codigoFuente.text.length > 0) {
            val lexico = AnalizadorLexico(codigoFuente.text)
            lexico.analizar()
            var lista : ObservableList<Token>
            lista = FXCollections.observableArrayList(lexico.listaTokens)
            columnaLexema.setCellValueFactory(PropertyValueFactory<Token,String>("Lexema"))
            columnaCategoria.setCellValueFactory(PropertyValueFactory<Token,Categoria>("Categoria"))
            ColumnaFila.setCellValueFactory(PropertyValueFactory<Token,Int>("Fila"))
            ColumnaColumna.setCellValueFactory(PropertyValueFactory<Token,Int>("Columna"))
            tablaTokens.setItems(lista)
        }
    }
}