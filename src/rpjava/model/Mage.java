package rpjava.model;

public class Mage extends Player {
    private static final int PORTEE_PROJECTILE_MIN = 1;
    private static final int PORTEE_PROJECTILE_MAX = 2;
    private static final int PORTEE_BOULE_FEU_MIN = 2;
    private static final int PORTEE_BOULE_FEU_MAX = 5;

    public Mage(String nom) {
        super(nom, 100, 20, 5);
    }

    public void projectileMagique(Personnage cible, Plateau plateau) {
        int distance = plateau.distance(this, cible);

        if (distance < PORTEE_PROJECTILE_MIN || distance > PORTEE_PROJECTILE_MAX) {
            System.out.println(cible.getNom() + " est hors de portee pour le projectile magique.");
            return;
        }

        System.out.println(getNom() + " lance un projectile magique sur " + cible.getNom() + " !");
        cible.degats(this.getAtq() + (int)(Math.random() * 6)); // Bonus de 0 a 5 points de degats
    }

    public void bouleDeFeu(Personnage cible, Plateau plateau) {
        int distance = plateau.distance(this, cible);

        if (distance < PORTEE_BOULE_FEU_MIN || distance > PORTEE_BOULE_FEU_MAX) {
            System.out.println(cible.getNom() + " est hors de portee pour la boule de feu.");
            return;
        }

        System.out.println(getNom() + " lance une boule de feu sur " + cible.getNom() + " !");
        cible.degats(this.getAtq() + (int)(Math.random() * 11)); // Bonus de 0 a 10 points de degats
    }
    // utilise la classe abstraite Player pour implementer le bonus de niveau de mage et guerrier 
    @Override
    protected void bonusNiveau() {
        this.hp += 10;
        this.hpMax += 10;
        this.atq += 5;
        this.def += 30;
        this.defBase += 30; 
    }
}
