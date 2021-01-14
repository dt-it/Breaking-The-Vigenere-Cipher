package pl.dtit.app;

import pl.dtit.exceptions.NoSuchOptionException;
import pl.dtit.io.DataReader;
import pl.dtit.logic.VigenereBreaker;
import pl.dtit.model.Option;

import java.util.InputMismatchException;

public class MainLoop {
    VigenereBreaker vigenereBreaker = new VigenereBreaker();
    DataReader reader = new DataReader();
    public void controlLoop() throws InterruptedException {
        Option option;
        do {
            printOptions();
            option = getOption();
            switch (option) {
                case DECRYPT_MESSAGE:
                    vigenereBreaker.breakVigenere();
                    break;
                case EXIT:
                    exit();
                    break;
                default:
                    System.out.println("Selected option is invalid. Please try again.");

            }
        } while (option != Option.EXIT);
    }

    private void printOptions() {
        System.out.println("Choose an option: ");
        for (Option option : Option.values()) {
            System.out.println(option.toString());
        }
    }

    private Option getOption() {
        boolean noError = false;
        Option option = null;
        while (!noError) {
            try {
                option = Option.createFromInt(reader.getInt());
                noError = true;
            } catch (NoSuchOptionException e) {
                System.out.println(e.getMessage() + ", try again.");
            } catch (InputMismatchException e) {
                System.out.println("Provided input is not a number, try again.");
            }
        }
        return option;
    }

    private void exit() {
        System.out.println("Closing the program ... See you!");
        reader.close();
    }
}
