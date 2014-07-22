package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    // Obiekt sceny, ktory sluzy do rysowania roznych komponentow interfejsu w ramce okna
    private Scene scene;

    /*
        przeslonieta metoda z klasy abstrakcyjnej biblioteki JAVA-FX
        jest uruchamiana podczas startu programu i inicjalizuje wszystkie komponenty
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        /*
            Tworzone jest drzewo elementow interfejsu uzytkownika.
            Elementy te sa przechowywane w pliku xml, ktory byl stworzony wczesniej
            przy pomocy programu do projektowania interfejsow Java-Fx
         */
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Klient Refleksji");
        // wymiary sceny (okna)
        scene = new Scene(root, 600, 375);
        primaryStage.setScene(scene);
        // dodanie pliku css sluzacego do zmiany wygladu aplikacji
        scene.getStylesheets().add(this.getClass().getResource("style.css").toExternalForm());
        // wywolanie okna (ramki)
        primaryStage.show();
    }

    public static void main(String[] args) {
        //start aplikacji
        launch(args);
    }
}
