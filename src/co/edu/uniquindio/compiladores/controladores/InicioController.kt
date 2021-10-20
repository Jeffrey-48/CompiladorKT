package co.edu.uniquindio.compiladores.controladores

import co.edu.uniquindio.compiladores.lexico.AnalizadorLexico
import co.edu.uniquindio.compiladores.lexico.Categoria
import co.edu.uniquindio.compiladores.lexico.Error
import co.edu.uniquindio.compiladores.lexico.Token
import co.edu.uniquindio.compiladores.semantica.AnalizadorSemantico
import co.edu.uniquindio.compiladores.sintaxis.AnalizadorSintactico
import co.edu.uniquindio.compiladores.sintaxis.UnidadDeCompilacion
import javafx.beans.Observable
import javafx.collections.FXCollections
import javafx.fxml.FXML
import javafx.event.ActionEvent
import javafx.collections.ObservableList
import javafx.scene.control.*
import javafx.scene.control.cell.PropertyValueFactory
import java.io.File
import java.net.URL
import java.util.*

/**
 *  Clase que controla la interfaz del proyecto
 *  @author Jeffrey Alexander Vargas, Kevin Valencia Romero
 */

class InicioController {
    @FXML lateinit var codigoFuente : TextArea
    @FXML lateinit var tablaTokens : TableView<Token>
    @FXML lateinit var tablaErrorSintactico : TableView<Error>
    @FXML lateinit var tablaErrorSemantico : TableView<Error>
    @FXML lateinit var columnaLexema : TableColumn<Token,String>
    @FXML lateinit var columnaCategoria : TableColumn<Token,Categoria>
    @FXML lateinit var ColumnaFila : TableColumn<Token,Int>
    @FXML lateinit var ColumnaColumna : TableColumn<Token,Int>
    @FXML lateinit var tablaErrores : TableView<Error>
    @FXML lateinit var mensajeError: TableColumn<Error, String>
    @FXML lateinit var filaError : TableColumn<Error, Int>
    @FXML lateinit var columnaError : TableColumn<Error, Int>
    @FXML lateinit var arbolVisual : TreeView<String>
    @FXML lateinit var columnae1 : TableColumn<Error,String>
    @FXML lateinit var ColumnaFila1 : TableColumn<Error,Int>
    @FXML lateinit var ColumnaColumna1 : TableColumn<Error,Int>
    @FXML lateinit var columnaErrorSeman: TableColumn<Error, String>
    @FXML lateinit var ColumnaFilaSeman : TableColumn<Error,Int>
    @FXML lateinit var ColumnaColumnaseman : TableColumn<Error,Int>
    var uc:UnidadDeCompilacion?=null
    var lexico:AnalizadorLexico?= null
    var sintaxis:AnalizadorSintactico?=null
    var semantica:AnalizadorSemantico?=null

    @FXML
    fun analizarCodigo(e:ActionEvent) {
        if (codigoFuente.text.length > 0) {
            lexico = AnalizadorLexico(codigoFuente.text)
            lexico!!.analizar()
            tablaTokens.items = FXCollections.observableArrayList(lexico!!.listaTokens)
            //tablaErrores.items = FXCollections.observableArrayList(lexico.listaErrores)

            //if(lexico.listaErrores.isEmpty()) {

            sintaxis = AnalizadorSintactico(lexico!!.listaTokens)
            uc = sintaxis!!.esUnidadDeCompilacion()
            semantica = AnalizadorSemantico(uc!!)
            if(uc != null){
                arbolVisual.root = uc!!.getArbolVisual()
                semantica!!.llenarTablaSimbolos()
                semantica!!.analizarSemantica()
            }
            var lista : ObservableList<Token>
            lista = FXCollections.observableArrayList(lexico!!.listaTokens)
            var listaErrorSintactico : ObservableList<Error>
            listaErrorSintactico = FXCollections.observableArrayList(sintaxis!!.listaErrores)
            var listaErrorSemantico : ObservableList<Error>
            listaErrorSemantico = FXCollections.observableArrayList(semantica!!.erroresSemanticos)
            columnaLexema.setCellValueFactory(PropertyValueFactory<Token,String>("Lexema"))
            columnaCategoria.setCellValueFactory(PropertyValueFactory<Token,Categoria>("Categoria"))
            ColumnaFila.setCellValueFactory(PropertyValueFactory<Token,Int>("Fila"))
            ColumnaColumna.setCellValueFactory(PropertyValueFactory<Token,Int>("Columna"))
            columnae1.setCellValueFactory(PropertyValueFactory<Error,String>("Error"))
            ColumnaFila1.setCellValueFactory(PropertyValueFactory<Error,Int>("Fila"))
            ColumnaColumna1.setCellValueFactory(PropertyValueFactory<Error,Int>("Columna"))
            tablaTokens.setItems(lista)
            tablaErrorSintactico.setItems(listaErrorSintactico)
            columnaErrorSeman.setCellValueFactory(PropertyValueFactory<Error,String>("Error"))
            ColumnaFilaSeman.setCellValueFactory(PropertyValueFactory<Error,Int>("Fila"))
            ColumnaColumnaseman.setCellValueFactory(PropertyValueFactory<Error,Int>("Columna"))
            tablaErrorSemantico.setItems(listaErrorSemantico)
        }
    }
    @FXML
    fun traducirCodigo(e: ActionEvent){
        if (lexico!!.listaErrores.isEmpty() && sintaxis!!.listaErrores.isEmpty() && semantica!!.erroresSemanticos.isEmpty()){
            val codigo = uc!!.getJavaCode()
            File("src/Principal.java").writeText( codigo )
            try {
                val p = Runtime.getRuntime().exec("javac src/Principal.java")
                p.waitFor()
                Runtime.getRuntime().exec("java Principal", null, File("src"))
            } catch (ea: Exception) {
                ea.printStackTrace()
            }
        }else{
            val alerta = Alert(Alert.AlertType.ERROR)
            alerta.headerText = null
            alerta.contentText = "El codigo no se puede traducir por que hay errores"
            alerta.show()
        }
    }
}