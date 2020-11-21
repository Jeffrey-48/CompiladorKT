package co.edu.uniquindio.compiladores.app

import co.edu.uniquindio.compiladores.controladores.InicioController
import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

/**
 *  Clase que lanza el aplicativo principal del proyecto
 *  @author Jeffrey Alexander Vargas, Kevin Valencia Romero
 *
 */

class Aplicacion : Application() {
    override fun start(primaryStage: Stage?) {
        val loader = FXMLLoader(Aplicacion::class.java.getResource("/inicio.fxml"))
        val parent: Parent = loader.load()
        val scene = Scene(parent)

        if (primaryStage != null) {
            primaryStage.scene = scene
        }


        if (primaryStage != null) {
            primaryStage.title = "Mi compilador"
        }

        if (primaryStage != null) {
            primaryStage.show()
        }
    }


    companion object{
        @JvmStatic
        fun main(args: Array<String>){
            launch(Aplicacion::class.java)
        }
    }
}


