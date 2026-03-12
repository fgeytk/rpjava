package rpjava.model;

public abstract class Personnage {
    protected String nom;
    protected int position = 0;
    protected int hp;
    protected int atq;
    protected int def;

    public Personnage(String nom, int hp, int atq, int def) {
        this.nom = nom;
        this.hp = hp;
        this.atq = atq;
        this.def = def;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getNom() {
        return nom;
    }

    public int getHp() {
        return hp;
    }

    public int getAtq() {
        return atq;
    }

    public int getDef() {
        return def;
    }

    public void degats(int dmg) {
        int degatsSubis = Math.max(0, dmg - this.def);
        this.hp -= degatsSubis;
        System.out.println(this.nom + " subit " + degatsSubis + " points de degats !");
        System.out.println(this.nom + " a maintenant " + this.hp + " points de vie.");
    }
}
