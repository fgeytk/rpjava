package rpjava.model;

public abstract class Player extends Personnage {
    protected int xp = 0;
    protected int niveau = 1;

    public Player(String nom, int hp, int atq, int def) {
        super(nom, hp, atq, def);
    }

    protected abstract void bonusNiveau();

    public int getXp() {
        return xp;
    }

    public int getNiveau() {
        return niveau;
    }

    public void gagnerXp(int montant) {
        xp += montant;
        if (xp >= 100) {
            xp -= 100;
            niveau++;
            bonusNiveau();
            System.out.println("Félicitations ! " + getNom() + " a atteint le niveau " + niveau + " !");
        }
    }
}
