package rpjava.model;

public class Guerrier extends Player {
    private static final int PORTEE_EPEE = 1;
    private static final int PORTEE_ARC_MIN = 2;
    private static final int PORTEE_ARC_MAX = 3;

    public Guerrier(String nom) {
        super(nom, 100, 15, 10);
    }

    public void coupEpee(Personnage cible, Plateau plateau) {
        if (!plateau.estAPortee(this, cible, PORTEE_EPEE)) {
            System.out.println(cible.getNom() + " est trop loin pour une attaque a l'epee.");
            return;
        }

        System.out.println(getNom() + " utilise son coup d'epee sur " + cible.getNom() + " !");
        cible.degats(this.getAtq());
    }

    public void arc(Personnage cible, Plateau plateau) {
        int distance = plateau.distance(this, cible);

        if (distance < PORTEE_ARC_MIN || distance > PORTEE_ARC_MAX) {
            System.out.println(cible.getNom() + " est hors de portee pour l'arc.");
            return;
        }

        System.out.println(getNom() + " tire une fleche sur " + cible.getNom() + " !");
        cible.degats(this.getAtq());
    }
}
