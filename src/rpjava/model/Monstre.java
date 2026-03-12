package rpjava.model;

public class Monstre extends Personnage {
    public Monstre(String nom, int hp, int atq, int def) {
        super(nom, hp, atq, def);
        this.position = 9; // Les monstres commencent à la position 9
    }

    public void attaquer(Personnage cible) {
        System.out.println(this.getNom() + " attaque " + cible.getNom() + " !");
    }
    
}
