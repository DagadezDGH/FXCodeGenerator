package dad.codegen.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class FXCodeGenApp extends Application {

	private static Stage primaryStage; 
	
	private MainController mainController;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		FXCodeGenApp.primaryStage = primaryStage;

		mainController = new MainController();
		
		Scene scene = new Scene(mainController.getView());
		
		primaryStage.setTitle("FX Code Generator");
		primaryStage.getIcons().add(new Image("/images/fx-64x64.png"));
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public static Stage getPrimaryStage() {
		return primaryStage;
	}
	
	public static boolean confirm(String title, String header, String content) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.initOwner(primaryStage);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		return alert.showAndWait().get().equals(ButtonType.OK);
	}
	
	public static void error(String header, String content) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.initOwner(primaryStage);
		alert.setTitle("Error");
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
	
	public static void info(String title, String header, String content) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.initOwner(primaryStage);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
