package reflection;

/**
 * Created by grzesiek on 13.06.14.
 */
public class TestClass {
    private String imie = "Archael";
    private String wiek = "2000";

    public TestClass() {
        System.out.println("Wywolanie konstruktora klasy " + this.getClass().getName());
    }

    public String oblicz(int i) {
        return "Odliczam: " + i;
    }

    public String pobierzElement() {
        return "Hello World";
    }
    public String ustawElement(String name) {
        return "Change Element to " + name;
    }
    public String kasujElement(float id) {
        return "Delete Element with id:" + id;
    }
}
