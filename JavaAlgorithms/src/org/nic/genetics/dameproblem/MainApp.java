package org.nic.genetics.dameproblem;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import org.nic.genetics.dameproblem.controller.ChessController;

public class MainApp extends Application {

    private static final String TITLE = "Queen Problem";
    private static final int FIELDCOUNT = 8;

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
	
	primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {

	    @Override
	    public void handle(WindowEvent arg0)
	    {
		System.exit(0);
	    }
	    
	});

	cc.init(FIELDCOUNT, pane);

	Thread t = new Thread(new Runnable() {

	    @Override
	    public void run()
	    {
		cc.startSolve();
	    }
	});
	
	t.start();
	
    }

    public static void main(String[] args) {
	launch(args);
    }
}
