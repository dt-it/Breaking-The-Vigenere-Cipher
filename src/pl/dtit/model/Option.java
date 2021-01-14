package pl.dtit.model;

import pl.dtit.exceptions.NoSuchOptionException;

public enum Option {
    EXIT(0, "Exit"),
    DECRYPT_MESSAGE(1, "Choose file to decrypt message");


    private int value;
    private String description;

    Option(int value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String toString() {
        return value + " - " + description;
    }

    public static Option createFromInt(int option) throws NoSuchOptionException {
        try {
            return Option.values()[option];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new NoSuchOptionException("No such option as " + option);

        }

    }
}
