package rpjava.model;

public class Guerrier extends Player {
    public Guerrier(String nom) {
        super(nom, 100, 15, 10);
    }

    public void epee(Personnage cible) {
        degats(this.getAtq());
        System.out.println(getNom() + " utilise son epee pour attaquer " + cible.getNom() + " !");
    }
}
