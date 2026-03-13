package rpjava.model;

public class Monstre extends Personnage {
    private int niveauMin;
    private int niveauMax;

    public Monstre(String nom, int niveauMin, int niveauMax, int hp, int atq, int def) {
        super(nom, hp, atq, def);
        this.niveauMin = niveauMin;
        this.niveauMax = niveauMax;
        this.position = 9;
    }

    public int getNiveauMin() {
        return niveauMin;
    }

    public int getNiveauMax() {
        return niveauMax;
    }

    public boolean correspondAuNiveau(int niveauJoueur) {
        return niveauMin <= niveauJoueur && niveauJoueur <= niveauMax;
    }

    public Monstre copie() {
        return new Monstre(this.nom, this.niveauMin, this.niveauMax, this.hp, this.atq, this.defBase);
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
