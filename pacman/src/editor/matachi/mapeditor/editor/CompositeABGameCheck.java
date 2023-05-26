package src.editor.matachi.mapeditor.editor;
/**
 * Composite `GameCheck` comprising checks A and B.
 *   [Tue 09:00] Team 03
 *   1173104 Erick Wong (erickw@student.unimelb.edu.au)
 *   1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
 *   1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)
 */

public class CompositeABGameCheck extends CompositeGameCheck{
    public CompositeABGameCheck() {
        addGameCheck(new GameCheckA());
        addGameCheck(new GameCheckB());
    }

}
