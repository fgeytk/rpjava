package rpjava.model;

public class Guerrier extends Player {
    private static final int PORTEE_EPEE = 1;

    public Guerrier(String nom) {
        super(nom, 100, 15, 10);
    }

    public void epee(Personnage cible, Plateau plateau) {
        if (!plateau.estAPortee(this, cible, PORTEE_EPEE)) {
            System.out.println(cible.getNom() + " est trop loin pour une attaque a l'epee.");
            return;
        }

        System.out.println(getNom() + " utilise son epee pour attaquer " + cible.getNom() + " !");
        cible.degats(this.getAtq());
    }
}
