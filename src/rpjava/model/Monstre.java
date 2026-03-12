package rpjava.model;

public class Monstre extends Personnage {
    public Monstre(String nom, int hp, int atq, int def) {
        super(nom, hp, atq, def);
    }

    public void attaquer(Personnage cible) {
        System.out.println(this.getNom() + " attaque " + cible.getNom() + " !");
    }
    
}
