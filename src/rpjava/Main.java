package rpjava;

import rpjava.model.Guerrier;
import rpjava.model.Monstre;
import rpjava.model.Plateau;

public class Main {
    public static void main(String[] args) {
        Plateau plateau = new Plateau();
        Guerrier guerrier = new Guerrier("Arthur");
        Monstre monstre = new Monstre("Gobelin", 50, 15, 3);

        System.out.println("Distance initiale : " + plateau.distance(guerrier, monstre));

        while (!plateau.estAPortee(guerrier, monstre, 1)) {
            plateau.deplacerVers(guerrier, monstre);
            System.out.println("Distance restante : " + plateau.distance(guerrier, monstre));
        }

        guerrier.epee(monstre, plateau);
    }
}
