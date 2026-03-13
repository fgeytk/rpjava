package rpjava.model;

public class Monstre extends Personnage {
    public Monstre(String nom, int hp, int atq, int def) {
        super(nom, hp, atq, def);
        this.position = 9;
    }

    public void attaquer(Personnage cible) {
        System.out.println(this.getNom() + " attaque " + cible.getNom() + " !");
        cible.degats(this.getAtq());
    }

    public void jouerTour(Personnage cible, Plateau plateau) {
        if (plateau.estAPortee(this, cible, 1)) {
            attaquer(cible);
        } else {
            plateau.deplacerVers(this, cible);
        }
    }
}
