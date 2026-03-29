package rpjava.model;

import java.util.ArrayList;
import java.util.List;

public abstract class Player extends Personnage {
    protected int xp = 0;
    protected int niveau = 1;
    protected List<Objet> inventaire = new ArrayList<>();

    public Player(String nom, int hp, int atq, int def) {
        super(nom, hp, atq, def);
    }

    protected abstract void bonusNiveau();

    public void ajouterObjet(Objet objet) {
        inventaire.add(objet);
        System.out.println("Objet obtenu : " + objet);
    }

    public List<Objet> getInventaire() {
        return inventaire;
    }

    public boolean utiliserObjet(int index) {
        if (index < 0 || index >= inventaire.size()) return false;
        Objet objet = inventaire.remove(index);
        objet.appliquer(this);
        return true;
    }

    @Override
    public String toString() {
        return "[Nv." + niveau + "] " + super.toString() + "  XP:" + xp + "/100";
    }

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
