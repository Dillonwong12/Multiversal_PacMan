package src.editor.matachi.mapeditor.editor;

public class EditStartingStrategy implements StartingStrategy{
    @Override
    public void startEditor(String[] argsEditor) {
        System.out.println("editstartingstrategy");
        Controller controller = Controller.getInstance();
    }
}
