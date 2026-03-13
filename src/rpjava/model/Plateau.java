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

        if (positionValide(nouvellePosition)) {
            personnage.setPosition(nouvellePosition);
        }

        System.out.println(
            personnage.getNom() + " avance vers " + cible.getNom()
            + ". Position actuelle : " + personnage.getPosition()
        );

        System.out.println("Plateau :");
        this.afficher(personnage, cible);
    }

    public void afficher(Personnage joueur, Personnage monstre) {

    for(int i = 0; i < taille; i++) {

        if(i == joueur.getPosition())
            System.out.print("J ");
        else if(i == monstre.getPosition())
            System.out.print("M ");
        else
            System.out.print("_ ");
    }

    System.out.println();
    }
}
