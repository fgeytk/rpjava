package rpjava;

import rpjava.model.Guerrier;
import rpjava.model.Mage;

public class Main {
    public static void main(String[] args) {
        Guerrier guerrier = new Guerrier("Arthur");
        Mage mage = new Mage("Merlin");

        guerrier.epee(mage);
    }
}
