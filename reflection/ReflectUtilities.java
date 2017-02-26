package reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class ReflectUtilities {

    private String className;
    private Class cl;
    private Object instance = null;

    public ReflectUtilities() throws ClassNotFoundException {
        className = "reflection.TestClass";
        cl = Class.forName(className);
    }

    public void setClassName(String className) {
        if(className.length() > 0) {
            this.className = className;
            try {
                cl = Class.forName(className);
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<String> getMethods() throws ClassNotFoundException {
        ArrayList<String> methods = new ArrayList<String>();
        Method[] meth = cl.getDeclaredMethods();

        for(Method i : meth) {
            methods.add(i.getName().toString());
        }
        return methods;
    }

    public ArrayList<String> getFields() throws ClassNotFoundException {
        ArrayList<String> fields = new ArrayList<String>();

        Field[] fld = cl.getDeclaredFields();
        for(Field i : fld) {
            fields.add(i.getName().toString());
        }
        return fields;
    }

    public void invokeConstructor() {
        try {
            Constructor constructor = cl.getDeclaredConstructor();
            this.instance = cl.newInstance();
        }
        catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public String invokeMethod(String name, String firstParam) {
        String output = null;
        Method method = null;

        Class[][] parametersTypes = {
            {Integer.TYPE}, // method oblicz
            {}, // method getter
            {String.class}, // method update
            {Float.TYPE} // method delete
        };

        if (name.equalsIgnoreCase("oblicz")) {
            try {
                method = cl.getDeclaredMethod(name, parametersTypes[0]);
                output = (String) method.invoke(instance, Integer.parseInt(firstParam));
            }
            catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        } 
        else if(name.startsWith("ustaw")) {
            try {
                // ten sam algorytm jest dla wszystkich metod
                method = cl.getDeclaredMethod(name, parametersTypes[2]);
                output = (String) method.invoke(instance, firstParam);
            }
            catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        else if(name.startsWith("kasuj")) {
            try {
                method = cl.getDeclaredMethod(name, parametersTypes[3]);
                output = (String) method.invoke(instance, Float.parseFloat(firstParam));
            }
            catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        else if(name.startsWith("pobierz")) {
            try {
                method = cl.getDeclaredMethod(name, parametersTypes[1]);
                output = (String) method.invoke(instance, null);
            } 
            catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return output;
    }

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
        }
        catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return output;
    }
}
