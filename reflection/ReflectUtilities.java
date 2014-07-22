package reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * Created by grzesiek on 13.06.14.
 */
public class ReflectUtilities {

    /*
        pola prywatne przechowujace:
        * nazwe klasy sluzacej do uzycia na niej mechanizmow refleksji
        * obiekt klasy sluzacy do odczytu elementow klasy takich jak pola, metody, konstruktory
        * instancja klasy czyli fizyczny obiekt przez, ktory wywolujemy konstruktory, metody, odczytujemy wartosci pol
        *
        * !! instancja jest tworzona tylko jeden raz podczas wywolywania konstruktora (podczas klikniecia na przycisk "START")
     */
    private String className;
    private Class cl;
    private Object instance = null;

    /*
            konstruktor klasy refleksji
            posiada zainicjalizowana przykladowa klase
            oraz utworzony jest obiekt klasy, przez ktora odczytujemy jej wlasciwosci
     */
    public ReflectUtilities() throws ClassNotFoundException {
        className = "reflection.TestClass";
        cl = Class.forName(className);
    }

    /*
        metoda sluzaca do ustawienia nazwy klasy.
        Sprawdzana jest dlugosc zmiennej przekazanej do metody by
        upewnic sie czy nie przkazywany jest pusty znak
     */

    public void setClassName(String className) {

        if(className.length() > 0) {

            this.className = className;

            try {

                cl = Class.forName(className);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /*
            metoda odczytujaca pola metod zawartych w klasie.
            Pola te nastepnie sa dodawane do listy (listy tablic) i lista ta jest nastepnie zwracana.
            Uzyto tutaj listy poniewaz jest duzo wygodniejsza w uzyciu od tablicy.
     */
    public ArrayList<String> getMethods() throws ClassNotFoundException {
        ArrayList<String> methods = new ArrayList<String>();
        Method[] meth = cl.getDeclaredMethods();

        for(Method i : meth) {
            methods.add(i.getName().toString());
        }

        return methods;
    }

    /*
        Metoda pobierajaca pola dziala na tej samej zasadzie co metoda pobierajaca metody
     */
    public ArrayList<String> getFields() throws ClassNotFoundException {
        ArrayList<String> fields = new ArrayList<String>();

        Field[] fld = cl.getDeclaredFields();
        for(Field i : fld) {
            fields.add(i.getName().toString());
        }

        return fields;
    }

    /*
        Metoda wywolujaca konstruktor klasy.
        Tworzona jest tutaj instancja (fizyczny obiekt) klasy, z ktorej
        wyciagane sa potrzebne informacje.
     */
    // execute constructor
    public void invokeConstructor() {

        try {

            Constructor constructor = cl.getDeclaredConstructor();
            this.instance = cl.newInstance();

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

     /*
            Metoda wywolujaca konkretna metode, przekazujaca do niej parametry oraz zwracajaca
            dane z metody.
      */
    // execute specyfic method
    public String invokeMethod(String name, String firstParam) {
        // atrybut (zmienna), ktora sluzy do zwrocenia wartosci
        String output = null;
        // atrybut method slyzy do przechowywania obiektu metody, ktora ma byc wywolana (w polaczeniu z instancja)
        Method method = null;

        // typy parametrow przekazywanych do metody
        Class[][] parametersTypes = {
                {Integer.TYPE}, // method oblicz
                {}, // method getter
                {String.class}, // method update
                {Float.TYPE} // method delete
        };
        // algorytm sprawdzajacy jaka metode wykonyjemy
        if(name.equalsIgnoreCase("oblicz")) {

            try {
                // pobranie obiektu metody po nazwie oraz przekazanie jej parametrow metody (z zdefiniowanej wczesniej tablicy parametrow)
                method = cl.getDeclaredMethod(name, parametersTypes[0]);
                // metoda zwraca dane
                output = (String) method.invoke(instance, Integer.parseInt(firstParam));

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        } else if(name.startsWith("ustaw")) {

                try {
                    // ten sam algorytm jest dla wszystkich metod
                    method = cl.getDeclaredMethod(name, parametersTypes[2]);
                    output = (String) method.invoke(instance, firstParam);

                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

        } else if(name.startsWith("kasuj")) {

                try {

                    method = cl.getDeclaredMethod(name, parametersTypes[3]);
                    output = (String) method.invoke(instance, Float.parseFloat(firstParam));

                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

        } else if(name.startsWith("pobierz")) {

                try {

                    method = cl.getDeclaredMethod(name, parametersTypes[1]);
                    output = (String) method.invoke(instance, null);

                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
        }

        return output;
    }

    /*
        Metoda, ktora sluzy do odczytu wartosci pol oraz zapisu pol
     */
    // getFields values
    public String getFieldsValue(String fieldName, String newValue) {
        String output = "Old value: ";

        try {
            // pobranie obiektu pola
            Field fieldValue = cl.getDeclaredField(fieldName);
            // ustawienie modyfikatora dostepu z private na public
            fieldValue.setAccessible(true);
            // pobranie wartosci pola
            output +=  fieldValue.get(instance).toString();
            // ustawienie nowej wartosci
            fieldValue.set(instance, newValue);
            // przypisanie do zmiennej nowej wartosci
            output += " new value: " + fieldValue.get(instance);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return output;
    }
}
