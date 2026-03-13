package rpjava.model;

import java.util.Scanner;

import rpjava.model.exceptions.ActionInvalideException;
import rpjava.model.exceptions.AttaqueInvalideException;
import rpjava.model.exceptions.ClasseInvalideException;

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
        while (true) {
            try {
                System.out.println("Choisis ta classe :");
                System.out.println("1. Guerrier");
                System.out.println("2. Mage");

                String choixClasse = scanner.nextLine();

                System.out.println("Entre le nom du personnage :");
                String nom = scanner.nextLine().trim();

                if (nom.isEmpty()) {
                    nom = "Arthur";
                }

                return creerJoueurDepuisChoix(choixClasse, nom);
            } catch (ClasseInvalideException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private Player creerJoueurDepuisChoix(String choixClasse, String nom) throws ClasseInvalideException {
        if (choixClasse.equals("1")) {
            return new Guerrier(nom);
        }

        if (choixClasse.equals("2")) {
            return new Mage(nom);
        }

        throw new ClasseInvalideException("Classe invalide. Tape 1 pour Guerrier ou 2 pour Mage.");
    }

    public void lancer() {
        while (joueur.estVivant() && monstre.estVivant()) {
            
            System.out.println();
            System.out.println("HP " + joueur.getNom() + " : " + joueur.getHp());
            System.out.println("Statistique de " + joueur.getNom() + " : Atq = " + joueur.getAtq() + ", Def = " + joueur.getDef());
            System.out.println("HP " + monstre.getNom() + " : " + monstre.getHp());
            plateau.afficher(joueur, monstre);

            System.out.println("Choisis une action :");
            System.out.println("1. Avancer");
            System.out.println("2. Attaquer");
            System.out.println("3. Defendre");
            System.out.println("q. Quitter");

            String choix = scanner.nextLine();

            if (choix.equals("q")) {
                break;
            }

            try {
                executerAction(choix);
            } catch (ActionInvalideException | AttaqueInvalideException e) {
                System.out.println(e.getMessage());
                continue;
            }

            if (!monstre.estVivant()) {
                System.out.println(monstre.getNom() + " est vaincu !");
                monstre = new Monstre("Gobelin", 50, 15, 3);
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

    private void executerAction(String choix) throws ActionInvalideException, AttaqueInvalideException {
        if (choix.equals("1")) {
            plateau.deplacerVers(joueur, monstre);
            return;
        }

        if (choix.equals("2")) {
            attaquer();
            return;
        }

        if (choix.equals("3")) {
            joueur.buffDefense();
            System.out.println(joueur.getNom() + " se met en defense pour le prochain tour du monstre.");
            return;
        }

        throw new ActionInvalideException("Action invalide. Choisis 1, 2, 3 ou q.");
    }

    private void attaquer() throws AttaqueInvalideException {
        if (joueur instanceof Guerrier) {
            System.out.println("Choisis une attaque :");
            System.out.println("1. Coup d'epee");
            System.out.println("2. Arc");

            String choixAttaque = scanner.nextLine();

            if (choixAttaque.equals("1")) {
                ((Guerrier) joueur).coupEpee(monstre, plateau);
                return;
            }

            if (choixAttaque.equals("2")) {
                ((Guerrier) joueur).arc(monstre, plateau);
                return;
            }

            throw new AttaqueInvalideException("Attaque invalide. Choisis 1 pour Coup d'epee ou 2 pour Arc.");
        }

        if (joueur instanceof Mage) {
            System.out.println("Choisis une attaque :");
            System.out.println("1. Projectile magique");
            System.out.println("2. Boule de feu");

            String choixAttaque = scanner.nextLine();

            if (choixAttaque.equals("1")) {
                ((Mage) joueur).projectileMagique(monstre, plateau);
                return;
            }

            if (choixAttaque.equals("2")) {
                ((Mage) joueur).bouleDeFeu(monstre, plateau);
                return;
            }

            throw new AttaqueInvalideException("Attaque invalide. Choisis 1 pour Projectile magique ou 2 pour Boule de feu.");
        }

        throw new AttaqueInvalideException("Impossible d'attaquer avec cette classe.");
    }
}
