package rpjava.model;

public abstract class Player extends Personnage {
    protected int xp = 0;
    protected int niveau = 1;

    public Player(String nom, int hp, int atq, int def) {
        super(nom, hp, atq, def);
    }
}
