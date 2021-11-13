package dad.codegen.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.codegen.model.javafx.Bean;
import dad.codegen.model.javafx.Property;
import dad.codegen.model.javafx.Type;
import javafx.beans.property.ListProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public class EditBeanController implements Initializable {
	Stage stage;
	Property property;
    @FXML
    private GridPane editPropertyView;

    @FXML
    private TextField nombrePropertyText;

    @FXML
    private CheckBox checkBoxLectura;

    @FXML
    private ComboBox<Type> comboBoxTipo;

    @FXML
    private ComboBox<Bean> comboBoxGenerico;

    @FXML
    private Button volverButton;

   
  
    public EditBeanController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EditPropertyView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		this.comboBoxTipo.setItems(FXCollections.observableArrayList(Type.values()));
	}
	
	public void showOnStage(Window ventana) {
		// TODO Auto-generated method stub
		stage = new Stage();
		stage.getIcons();
		stage.setTitle("Editar Propriedad");
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.initOwner(ventana);
		stage.setScene(new Scene(editPropertyView, 400,200));
		stage.showAndWait();
		
		
	}

	public void bind(Property seleccion, ListProperty<Bean> beans) {
		// TODO Auto-generated method stub
		this.unbind(seleccion);
		
		this.nombrePropertyText.textProperty().bindBidirectional(seleccion.nameProperty());
		this.checkBoxLectura.selectedProperty().bindBidirectional(seleccion.readOnlyProperty());
		this.comboBoxTipo.valueProperty().bindBidirectional(seleccion.typeProperty());
		this.comboBoxGenerico.itemsProperty().bind(beans);
		this.comboBoxGenerico.valueProperty().bindBidirectional(seleccion.genericProperty());
	}

	public void unbind (Property seleccion) {
		if (seleccion == null) return;
		this.property = null;
		this.nombrePropertyText.textProperty().unbindBidirectional(seleccion.nameProperty());
		this.checkBoxLectura.selectedProperty().unbindBidirectional(seleccion.readOnlyProperty());
		this.comboBoxTipo.valueProperty().unbindBidirectional(seleccion.typeProperty());
		this.comboBoxGenerico.itemsProperty().unbind();
		this.comboBoxGenerico.valueProperty().unbindBidirectional(seleccion.genericProperty());
	}
	public GridPane getView() {
		return editPropertyView;
	}
	 @FXML
	  void onVolverAction(ActionEvent event) {
	    	unbind(property);
			stage.close();
	    }
}
