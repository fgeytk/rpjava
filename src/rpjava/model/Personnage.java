package rpjava.model;

public abstract class Personnage {
    protected String nom;
    protected int position = 0;
    protected int hp;
    protected int hpMax;
    protected int atq;
    protected int def;
    protected int defBase;
    protected boolean defenseActive = false;

    public Personnage(String nom, int hp, int atq, int def) {
        this.nom = nom;
        this.hp = hp;
        this.hpMax = hp;
        this.atq = atq;
        this.def = def;
        this.defBase = def;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean estVivant() {
        return hp > 0;
    }

    public String getNom() {
        return nom;
    }

    public int getHp() {
        return hp;
    }

    public int getHpMax() {
        return hpMax;
    }

    public int getAtq() {
        return atq;
    }

    public int getDef() {
        return def;
    }

    public boolean isBuffedDefense() {
        return defenseActive;
    }

    public void buffDefense() {
        if (!defenseActive) {
            this.def = (int)(this.defBase * 1.3); // Augmentation de 30% de la défense si on mets *2 on est invincible
            this.defenseActive = true;
            System.out.println(this.nom + " passe en defense. Defense : " + this.def);
        }
    }

    public void debuffDefense() {
        if (defenseActive) {
            this.def = this.defBase;
            this.defenseActive = false;
            System.out.println(this.nom + " n'est plus en defense. Defense : " + this.def);
        }
    }
    //attaque = (atq + random modificateur) - defense
    public void degats(int dmg) {
        int degatsSubis = Math.max(0, dmg - this.def);
        this.hp -= degatsSubis;
        System.out.println(this.nom + " subit " + degatsSubis + " points de degats !");
        System.out.println(this.nom + " a maintenant " + this.hp + " points de vie.");

        if (!estVivant()) {
            System.out.println(this.nom + " est mort !");
        }
    }
}
