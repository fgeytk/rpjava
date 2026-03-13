package rpjava.model;

import java.util.Scanner;

public class Jeu {
    private Plateau plateau;
    private Player joueur;
    private Monstre monstre;
    private Scanner scanner;

    public Jeu() {
        scanner = new Scanner(System.in);
        plateau = new Plateau();
        joueur = creerJoueur();
        monstre = new Monstre("Gobelin", 50, 15, 3);

        System.out.println("Vous vous trouvez dans un donjon sombre. Un " + monstre.getNom() + " apparait devant vous !");
        plateau.afficher(joueur, monstre);
    }

    private Player creerJoueur() {
        System.out.println("Choisis ta classe :");
        System.out.println("1. Guerrier");
        System.out.println("2. Mage");

        String choixClasse = scanner.nextLine();

        System.out.println("Entre le nom du personnage :");
        String nom = scanner.nextLine().trim();
        // nom de base
        if (nom.isEmpty()) {
            nom = "Arthur";
        }
        // mage
        if (choixClasse.equals("2")) {
            return new Mage(nom);
        }
        // Par défaut, on crée un Guerrier
        return new Guerrier(nom);
    }

    public void lancer() {
        while (joueur.estVivant() && monstre.estVivant()) {
            System.out.println();
            System.out.println("HP " + joueur.getNom() + " : " + joueur.getHp());
            System.out.println("Statistique de " + joueur.getNom() + " : Atq = " + joueur.getAtq() + ", Def = " + joueur.getDef());
            plateau.afficher(joueur, monstre);

            System.out.println("Choisis une action :");
            System.out.println("1. Avancer");
            System.out.println("2. Attaquer");
            System.out.println("3. Defendre");
            System.out.println("q. Quitter");

            String choix = scanner.nextLine();

            if (choix.equals("1")) {
                plateau.deplacerVers(joueur, monstre);
            } else if (choix.equals("2")) {
                attaquer();
            } else if (choix.equals("3")) {
                joueur.buffDefense();
                System.out.println(joueur.getNom() + " se met en defense pour le prochain tour du monstre.");
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

            monstre.jouerTour(joueur, plateau);

            if (!joueur.estVivant()) {
                System.out.println(joueur.getNom() + " est vaincu !");
                break;
            }

            if (joueur.isBuffedDefense()) {
                joueur.debuffDefense();
            }
        }
    }

    private void attaquer() {
        if (joueur instanceof Guerrier) {
            System.out.println("Choisis une attaque :");
            System.out.println("1. Coup d'epee");
            System.out.println("2. Arc");

            String choixAttaque = scanner.nextLine();

            if (choixAttaque.equals("1")) {
                ((Guerrier) joueur).coupEpee(monstre, plateau);
            } else if (choixAttaque.equals("2")) {
                ((Guerrier) joueur).arc(monstre, plateau);
            } else {
                System.out.println("Attaque invalide");
            }
            return;
        }

        if (joueur instanceof Mage) {
            System.out.println("Choisis une attaque :");
            System.out.println("1. Projectile magique");
            System.out.println("2. Boule de feu");

            String choixAttaque = scanner.nextLine();

            if (choixAttaque.equals("1")) {
                ((Mage) joueur).projectileMagique(monstre, plateau);
            } else if (choixAttaque.equals("2")) {
                ((Mage) joueur).bouleDeFeu(monstre, plateau);
            } else {
                System.out.println("Attaque invalide");
            }
        }
    }
}
