package rpjava.model;

import java.util.Random;

public class Objet {

    public enum Rarete { COMMUN, RARE, EPIQUE, LEGENDAIRE }

    private static final Random rng = new Random();

    private static final String[][] NOMS = {
        { "Bandage use", "Herbe seche", "Pierre de soin" },
        { "Cape de soie", "Dague affutee", "Anneau de protection" },
        { "Bouclier en acier", "Baton de foudre", "Amulette de guerre" },
        { "Epee de la destruction", "Toge du Chaos", "Orbe Maudit" }
    };

    private final String nom;
    private final Rarete rarete;
    private final int bonusHp;
    private final int bonusAtq;
    private final int bonusDef;

    private Objet(String nom, Rarete rarete, int bonusHp, int bonusAtq, int bonusDef) {
        this.nom = nom;
        this.rarete = rarete;
        this.bonusHp = bonusHp;
        this.bonusAtq = bonusAtq;
        this.bonusDef = bonusDef;
    }

    public static Objet genererAleatoire() {
        int roll = rng.nextInt(100);
        Rarete rarete;
        int bonusHp, bonusAtq, bonusDef;

        if (roll < 5) {                        // 5% — LEGENDAIRE
            rarete = Rarete.LEGENDAIRE;
            bonusHp  = 50 + rng.nextInt(51);   // 50–100
            bonusAtq = 15 + rng.nextInt(11);   // 15–25
            bonusDef = 10 + rng.nextInt(11);   // 10–20
        } else if (roll < 20) {                // 15% — EPIQUE
            rarete = Rarete.EPIQUE;
            bonusHp  = 20 + rng.nextInt(21);   // 20–40
            bonusAtq = 8  + rng.nextInt(7);    // 8–14
            bonusDef = 5  + rng.nextInt(6);    // 5–10
        } else if (roll < 50) {                // 30% — RARE
            rarete = Rarete.RARE;
            bonusHp  = 8 + rng.nextInt(11);    // 8–18
            bonusAtq = 3 + rng.nextInt(5);     // 3–7
            bonusDef = 2 + rng.nextInt(4);     // 2–5
        } else {                               // 50% — COMMUN
            rarete = Rarete.COMMUN;
            bonusHp  = 3 + rng.nextInt(6);     // 3–8
            bonusAtq = 1 + rng.nextInt(3);     // 1–3
            bonusDef = rng.nextInt(3);         // 0–2
        }

        String[] noms = NOMS[rarete.ordinal()];
        String nom = noms[rng.nextInt(noms.length)] + " [" + rarete.name() + "]";
        return new Objet(nom, rarete, bonusHp, bonusAtq, bonusDef);
    }

    public void appliquer(Player joueur) {
        joueur.hpMax += bonusHp;
        joueur.hp = Math.min(joueur.hp + bonusHp, joueur.hpMax);
        joueur.atq += bonusAtq;
        joueur.def += bonusDef;
        joueur.defBase += bonusDef;
        System.out.println(joueur.getNom() + " utilise " + nom
            + " ! +HP:" + bonusHp + " +ATQ:" + bonusAtq + " +DEF:" + bonusDef);
    }

    public Rarete getRarete() {
        return rarete;
    }

    @Override
    public String toString() {
        return nom + "  (+HP:" + bonusHp + " +ATQ:" + bonusAtq + " +DEF:" + bonusDef + ")";
    }
}
