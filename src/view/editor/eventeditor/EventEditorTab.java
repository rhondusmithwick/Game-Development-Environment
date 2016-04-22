package view.editor.eventeditor;

import java.util.List;

import api.ILevel;
import view.editor.Editor;

public abstract class EventEditorTab extends Editor
{
	public abstract void choseLevels(List<ILevel> levels);
}
