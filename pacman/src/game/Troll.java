/**
 * Subclass of Monster. Troll randomly turns left or right. If it hits a wall, it will return to face the `oldDirection`
 * and go forward, or turn the other side. Otherwise, goes backwards.
 */
package src.game;

/**
 *   1173104 Erick Wong (erickw@student.unimelb.edu.au)
 *   1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
 *   1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)
 */

public class Troll extends Monster {
    private static final String TROLL_TYPE = "Troll";

    public Troll(Game game) {
        super("sprites/" + MonsterType.Troll.getImageName(), game, MonsterType.Troll);
    }

    // Walks randomly
    @Override
    public void walkApproach() {
        super.walkApproach();
    }

    public String getType(){
        return TROLL_TYPE;
    }
}
