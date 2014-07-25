package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import reflection.ReflectUtilities;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {

	// ------------ ATTRIBUTES
	private ReflectUtilities refUtils;

	// ------------ JavaFX gui fields
	@FXML
	private ComboBox<String> cbMethods;

	@FXML
	private ComboBox<String> cbFields;

	@FXML
	private TextArea textArea;

	@FXML
	private TextField tfAttr;

	@FXML
	private ComboBox<String> cbClassName;

	@FXML
	private TextField tfFirstParam;

	@FXML
	private Button btStart;

	// ----------- Methods

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		tfAttr.getStyleClass().add("custom-text-field");
		cbClassName.getStyleClass().add("custom-combobox");
		tfFirstParam.getStyleClass().add("custom-text-field");
		cbFields.getStyleClass().add("custom-combobox");
		cbMethods.getStyleClass().add("custom-combobox");
		textArea.getStyleClass().add("custom-textArea");

		// fill comboboxes
		initializeCbClassName();

		try {

            refUtils = new ReflectUtilities();
			refUtils.invokeConstructor();
			addElement(refUtils.getMethods(), cbMethods);
			addElement(refUtils.getFields(), cbFields);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		cbMethods.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {

				if (tfFirstParam.getText().length() > 0) {

					String outputValue = refUtils.invokeMethod(newValue,
							tfFirstParam.getText().toString());
					textArea.setText(outputValue);
					tfFirstParam.clear();

				} else {
					textArea.setText("UWAGA::WPROWADZ PARAMETR, KTÓRY ZOSTANIE PRZEKAZANY DO METODY!");
				}

			}
		}); // end Listener

		cbFields.valueProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {

				if (tfAttr.getText().length() > 0) {

					String outputValue = refUtils.getFieldsValue(newValue,
							tfAttr.getText());
					textArea.setText(outputValue);

				} else {
					textArea.setText("UWAGA::WPROWADZ NOWĄ WARTOŚĆ ATRYBUTU!");
				}
			}
		});

		btStart.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				cbMethods = new ComboBox<String>();
				cbFields = new ComboBox<String>();

				refUtils.setClassName(cbClassName.getSelectionModel()
						.getSelectedItem().toString());
				refUtils.invokeConstructor();

				try {
					addElement(refUtils.getMethods(), cbMethods);
					addElement(refUtils.getFields(), cbFields);

				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void initializeCbClassName() {
		cbClassName.getItems().addAll("reflection.TestClass",
				"reflection.TestClass2");
	}

	private void addElement(ArrayList<String> list, ComboBox<String> element) {

		for (Object aList : list) {
			element.getItems().add(aList.toString());
		}
	}
}
