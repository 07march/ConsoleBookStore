package by.consoleapp.validator;

import by.entity.City;

public class CityValidator {
     public static boolean valid(City ci) {
        return ci.getName().length() > 1;
    }
}
