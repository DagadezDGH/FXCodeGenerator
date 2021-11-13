package dad.codegen.ui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.codegen.model.javafx.Bean;
import dad.codegen.model.javafx.Property;
import dad.codegen.model.javafx.Type;
import javafx.fxml.Initializable;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.layout.GridPane;

public class BeanController implements Initializable {

	// model externo

	private ObjectProperty<Bean> bean = new SimpleObjectProperty<>();
	private ListProperty<Bean> beans = new SimpleListProperty<>(FXCollections.observableArrayList());

	// model interno

	private StringProperty name = new SimpleStringProperty();
	private ObjectProperty<Bean> parent = new SimpleObjectProperty<>();
	private ListProperty<Property> properties = new SimpleListProperty<>(FXCollections.observableArrayList());

	// view

	@FXML
	private GridPane view;

	@FXML
	private TextField nombreText;

	@FXML
	private ComboBox<Bean> padreCombo;

	@FXML
	private Button quitarPadreButton;

	@FXML
	private TableView<Property> propiedadesList;

	@FXML
	private TableColumn<Property, String> nombreColumn;

	@FXML
	private TableColumn<Property, Boolean> soloLecturaColumn;

	@FXML
	private TableColumn<Property, Type> tipoColumn;

	@FXML
	private TableColumn<Property, Bean> genericoColumn;

	@FXML
	private Button nuevaPropiedadButton;

	@FXML
	private Button eliminarPropiedadButton;

	@FXML
	private Button editarPropiedadButton;

	public BeanController() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/BeanView.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// bindings

		nombreText.textProperty().bindBidirectional(name);
		padreCombo.valueProperty().bindBidirectional(parent);
		padreCombo.itemsProperty().bind(beans);
		propiedadesList.itemsProperty().bind(properties);
		
		quitarPadreButton.disableProperty().bind(parent.isNull());

		// cell factories
		
		nombreColumn.setCellValueFactory(v -> v.getValue().nameProperty());
		soloLecturaColumn.setCellValueFactory(v -> v.getValue().readOnlyProperty());
		tipoColumn.setCellValueFactory(v -> v.getValue().typeProperty());
		genericoColumn.setCellValueFactory(v -> v.getValue().genericProperty());
		
		soloLecturaColumn.setCellFactory(CheckBoxTableCell.forTableColumn(soloLecturaColumn));

		// listeners

		bean.addListener((o, ov, nv) -> onBeanChanged(o, ov, nv));

	}

	private void onBeanChanged(ObservableValue<? extends Bean> o, Bean ov, Bean nv) {

		if (ov != null) {
			// desbindeo el bean viejo
			name.unbindBidirectional(ov.nameProperty());
			parent.unbindBidirectional(ov.parentProperty());
			properties.unbind();
		}

		if (nv != null) {
			// bindeo el bean nuevo
			name.bindBidirectional(nv.nameProperty());
			parent.bindBidirectional(nv.parentProperty());
			properties.bind(nv.propertiesProperty());
		}

	}

	public GridPane getView() {
		return view;
	}

	@FXML
	void onEditarPropiedadAction(ActionEvent event) {
		// TODO editar la propiedad seleccionada
		Property seleccion = propiedadesList.getSelectionModel().getSelectedItem();
		EditBeanController controller = new EditBeanController();
		controller.bind(seleccion, beans);
		controller.showOnStage(FXCodeGenApp.getPrimaryStage());
	}

	@FXML
	void onEliminarPropiedadAction(ActionEvent event) {
		// TODO eliminar la propiedad seleccionada
		
		Property seleccion = propiedadesList.getSelectionModel().getSelectedItem();
		if (FXCodeGenApp.confirm("Eliminar propiedad",
								 "Se va a eliminar la propiedad '"
						         + seleccion.getName() + "'.", 
								 "¿Está seguro?")) {
			
			getBean().getProperties().remove(seleccion);
			
		}
	}

	@FXML
	void onNuevaPropiedadAction(ActionEvent event) {
		// TODO añadir una nueva propiedad
		Property property = new Property();
		property.setName("nuevaPropiedad");
		property.setType(Type.STRING);
		properties.add(property);
		propiedadesList.getSelectionModel().select(property);
		onEditarPropiedadAction(event);
	}

	@FXML
	void onQuitarPadreAction(ActionEvent event) {

		bean.get().setParent(null);
	
	}

	// getters y setters

	public ObjectProperty<Bean> beanProperty() {
		return this.bean;
	}

	public Bean getBean() {
		return this.beanProperty().get();
	}

	public void setBean(final Bean bean) {
		this.beanProperty().set(bean);
	}

	public ListProperty<Bean> beansProperty() {
		return this.beans;
	}

	public ObservableList<Bean> getBeans() {
		return this.beansProperty().get();
	}

	public void setBeans(final ObservableList<Bean> beans) {
		this.beansProperty().set(beans);
	}

}
