package rpjava.model;

import java.util.Scanner;


public class Jeu {
    private Plateau plateau;
    private Guerrier guerrier;
    private Monstre monstre;

    public Jeu() {
        plateau = new Plateau();
        guerrier = new Guerrier("Arthur");
        monstre = new Monstre("Gobelin", 50, 15, 3);
        // Positionnement initial
        System.out.println("Vous vous trouvez dans un donjon sombre. Un " + monstre.getNom() + " apparaît devant vous !");
        plateau.afficher(guerrier, monstre);
    }

    public void tester() {
        System.out.println("Distance initiale : " + plateau.distance(guerrier, monstre));

        while (!plateau.estAPortee(guerrier, monstre, 1)) {
            plateau.deplacerVers(guerrier, monstre);
            System.out.println("Distance restante : " + plateau.distance(guerrier, monstre));
        }

        guerrier.coupEpee(monstre, plateau);
    }

    public void lancer() {
        Scanner scanner = new Scanner(System.in);


        while (guerrier.estVivant() && monstre.estVivant()) {
            System.out.println();
            System.out.println("HP Guerrier : " + guerrier.getHp());
            System.out.println("HP Monstre : " + monstre.getHp());
            plateau.afficher(guerrier, monstre);

            System.out.println("Choisis une action :");
            System.out.println("1. Avancer");
            System.out.println("2. Attaquer");
            System.out.println("3. Defendre");
            System.out.println("q. Quitter");

            String choix = scanner.nextLine();

            if (choix.equals("1")) {
                plateau.deplacerVers(guerrier, monstre);
            } else if (choix.equals("2")) {
                System.out.println("Choisis une attaque :");
                System.out.println("1. Coup d'epee");
                System.out.println("2. Arc");

                String choixAttaque = scanner.nextLine();

                if (choixAttaque.equals("1")) {
                    guerrier.coupEpee(monstre, plateau);
                } else if (choixAttaque.equals("2")) {
                    guerrier.arc(monstre, plateau);
                } else {
                    System.out.println("Attaque invalide");
                    continue;
                }
            } else if (choix.equals("3")) {
                guerrier.buffDefense((int)(1.3 * guerrier.getDef() - guerrier.getDef()));
                System.out.println(guerrier.getNom() + " se met en defense pour le prochain tour du monstre.");
            } else if (choix.equals("q")) {
                break;
            } else {
                System.out.println("Choix invalide");
                continue;
            }

            if (!monstre.estVivant()) {
                System.out.println(monstre.getNom() + " est vaincu !");
                break;
            }

            monstre.jouerTour(guerrier, plateau);

            if (!guerrier.estVivant()) {
                System.out.println(guerrier.getNom() + " est vaincu !");
                break;
            }
        }
    }
}
