package reflection;

/**
 * Created by grzesiek on 24.06.14.
 */
public class TestClass2 {
    private String imie = "Tomasz";
    private String wiek = "19";

    public TestClass2() {
        System.out.println("Wywolanie konstruktora klasy " + this.getClass().getName());
    }

    public String oblicz(int i) {
        return "Wyliczenia " + i;
    }

    public String pobierzElement() {
        return "Siemka co tam";
    }
    public String ustawElement(String name) {
        return "Zmiana elementu na  " + name;
    }
    public String kasujElement(float id) {
        return "Usuniecie elementu z id:" + id;
    }
}
