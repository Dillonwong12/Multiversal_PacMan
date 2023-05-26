package src.editor.matachi.mapeditor.editor;

//  [Tue 09:00] Team 03
//  1173104 Erick Wong (erickw@student.unimelb.edu.au)
//  1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
//  1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)

/**
 * Composite level check comprising of checks A,B, C and D.
 */
public class CompositeABCDLevelCheck extends CompositeLevelCheck {

    public CompositeABCDLevelCheck() {
        // add neeeded level Checks
        addLevelCheck(new LevelCheckA());
        addLevelCheck(new LevelCheckB());
        addLevelCheck(new LevelCheckC());
        addLevelCheck(new LevelCheckD());
    }
}
