package by.consoleapp.validator;

import by.entity.Address;

public class AddressValidator {

    public static boolean valid(Address ad) {
        return ad.getAddress().length() > 2;
    }
}
