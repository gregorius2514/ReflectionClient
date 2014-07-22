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

	/*
	 * Metoda wywolywana przy starcie programu (okna programu) Zaimplementowano
	 * w niej: wypelnianie elementow interfejsu uzytkownika takich jak combobxy,
	 * itp. tworzenie obiektu refkleksji i z niego wydobywanie potrzebnych
	 * danych do wypelniania komponentow obsluge zdarzen: klikniec, zmiany
	 * elementow
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		tfAttr.getStyleClass().add("custom-text-field");
		cbClassName.getStyleClass().add("custom-combobox");
		tfFirstParam.getStyleClass().add("custom-text-field");
		cbFields.getStyleClass().add("custom-combobox");
		cbMethods.getStyleClass().add("custom-combobox");
		textArea.getStyleClass().add("custom-textArea");

		// wypelnienie comboboxa z klasami do uzycia
		initializeCbClassName();

		try {
			/*
			 * inicjalizacja obiektu refkelskji: wywolanie konstruktora pobranie
			 * nazw metod pobranie nazw atrybutow
			 */
			refUtils = new ReflectUtilities();
			refUtils.invokeConstructor();
			addElement(refUtils.getMethods(), cbMethods);
			addElement(refUtils.getFields(), cbFields);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		/*
		 * inicjalizacja listenera dla comboboxa z metodami. Jego zadaniem jest
		 * wywolanie metody po zmianie elementu comboboxa. Metoda jest
		 * wywolywana w momecie klikniecia na element comboboxa.
		 */
		cbMethods.valueProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable,
					String oldValue, String newValue) {

				if (tfFirstParam.getText().length() > 0) {

					// wywolanie metody przy uzyciu obiektu refleksji
					String outputValue = refUtils.invokeMethod(newValue,
							tfFirstParam.getText().toString());
					// dodanie do pola tekstowego wyniku jaki zwrocila metoda
					textArea.setText(outputValue);
					// wyczysczenie pol tekstowych odpowiedzialnych za
					// wprowadzanie parametrow
					tfFirstParam.clear();

				} else {
					textArea.setText("UWAGA::WPROWADZ PARAMETR, KTÓRY ZOSTANIE PRZEKAZANY DO METODY!");
				}

			}
		}); // end Listener

		/*
		 * Listner - (nasluchiwacz) dla comboboxa z polami pelni ta sama funkcje
		 * co poprzedni
		 */
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

		/*
		 * Dodanie zdarzenia klikniecia dla przycisku start jego klikniecie
		 * spowoduje: wywolanie konstruktora nowej klasy odczytanie nowych metod
		 * i pol klasy wypelnienie comboboksow z tymi polami
		 */
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

	/*
	 * metoda do ktorej mozna dodac dodatkowe klasy do odczytania przez program
	 * (po wczesniejszym ich zaimplementowaniu)
	 */
	private void initializeCbClassName() {
		cbClassName.getItems().addAll("reflection.TestClass",
				"reflection.TestClass2");
	}

	/*
	 * metoda, ktora dodaje elementy z listy (lista metod lub pol klasy) do
	 * comboboksow
	 */
	private void addElement(ArrayList<String> list, ComboBox<String> element) {

		for (Object aList : list) {
			element.getItems().add(aList.toString());
		}
	}
}
