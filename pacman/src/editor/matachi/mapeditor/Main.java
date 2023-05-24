package src.editor.matachi.mapeditor;

import src.editor.matachi.mapeditor.editor.Controller;
import src.game.*;

public class Main {

	private static String[] argsEditor;
	public static void main(String[] args) {
		Controller.getInstance();
		argsEditor = args;
	}

	public String[] getArgs() {
		return argsEditor;
	}
}
