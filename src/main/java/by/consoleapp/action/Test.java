package by.consoleapp.action;

import java.io.Serializable;

public class Test implements Serializable {
    private transient String tName;
    private String name;

    public Test(String tName, String name) {
        this.tName = tName;
        this.name = name;
    }

    public Test() {
    }

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        tName = tName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

    public String toString() {
        return "Test{tName='" + tName + '\'' + ", name='" + name + '\'' + '}';
    }
}
