package src.editor.matachi.mapeditor.editor;

//  [Tue 09:00] Team 03
//  1173104 Erick Wong (erickw@student.unimelb.edu.au)
//  1236449 Dillon Han Ren Wong (dillonhanren@student.unimelb.edu.au)
//  1272545 Jonathan Linardi (linardij@student.unimelb.edu.au)

import java.util.ArrayList;

/**
 * Composite game check class comprsising of checks A and B.
 */
public class CompositeABGameCheck extends CompositeGameCheck{
    public CompositeABGameCheck() {
        addGameCheck(new GameCheckA());
        addGameCheck(new GameCheckB());
    }

}
