package by.consoleapp.validator;

import by.entity.Store;

public class StoreValidator {

    public static boolean valid(Store sto) {
        return sto.getCity() != null && sto.getAddress() != null;
    }
}
