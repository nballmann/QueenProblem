package org.nic.genetics.dameproblem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import org.nic.genetics.dameproblem.controller.ChessController;

public class MainApp extends Application {

    private static final String TITLE = "TEMP TITLE";
    private ChessController cc;

    @Override
    public void start(Stage primaryStage) throws Exception {

	FXMLLoader loader = new FXMLLoader(getClass().getResource("view/ChessView.fxml"));

	Parent p = (Parent) loader.load();

	cc = loader.getController();
	
	AnchorPane pane = new AnchorPane();
	pane.getChildren().add(p);
	pane.setStyle("-fx-background-color: grey;");
	
	Scene scene = new Scene(pane);
	
	primaryStage.setScene(scene);	
	primaryStage.setTitle(TITLE);
	primaryStage.setHeight(388);
	primaryStage.setWidth(480);
	primaryStage.show();
	primaryStage.setResizable(false);

	cc.init(6);
	
//	cc.changeFieldStatus(1, 4);
//	cc.fieldMap.get(cc.chessBoard.getChessFields().get(5).get(4)).setVisible(false);
	cc.startSolve(0, 0);
    }

    public static void main(String[] args) {
	launch(args);
    }
}
