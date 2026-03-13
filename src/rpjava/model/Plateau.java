package rpjava.model;

public class Plateau {
    private int taille = 10;

    public int getTaille() {
        return taille;
    }

    public boolean positionValide(int pos) {
        return pos >= 0 && pos < taille;
    }

    public int distance(Personnage a, Personnage b) {
        return Math.abs(a.getPosition() - b.getPosition());
    }

    public boolean estAPortee(Personnage attaquant, Personnage cible, int portee) {
        return distance(attaquant, cible) <= portee;
    }

    public void deplacerVers(Personnage personnage, Personnage cible) {
        int nouvellePosition = personnage.getPosition();

        if (personnage.getPosition() < cible.getPosition()) {
            nouvellePosition++;
        } else if (personnage.getPosition() > cible.getPosition()) {
            nouvellePosition--;
        }

        if (nouvellePosition == cible.getPosition()) {
            System.out.println(personnage.getNom() + " est deja au contact de " + cible.getNom() + ".");
        } else if (positionValide(nouvellePosition)) {
            personnage.setPosition(nouvellePosition);
        }

        System.out.println(
            personnage.getNom() + " avance vers " + cible.getNom()
            + ". Position actuelle : " + personnage.getPosition()
        );

        System.out.println("Plateau :");
        this.afficher(personnage, cible);
    }

    public void afficher(Personnage premier, Personnage second) {

    for(int i = 0; i < taille; i++) {

        if(i == premier.getPosition())
            System.out.print(symbole(premier) + " ");
        else if(i == second.getPosition())
            System.out.print(symbole(second) + " ");
        else
            System.out.print("_ ");
    }

    System.out.println();
    }

    private String symbole(Personnage personnage) {
        if (personnage instanceof Player) {
            return "J";
        }

        if (personnage instanceof Monstre) {
            return "M";
        }

        return "?";
    }
}
