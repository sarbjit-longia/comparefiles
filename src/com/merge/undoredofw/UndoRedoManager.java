package com.merge.undoredofw;

import java.util.ArrayList;

import javax.swing.undo.UndoManager;

public class UndoRedoManager implements Action {

	ArrayList<Action> actions = new ArrayList<Action>();
	int index;
	public void addAction(Action act){
		if(actions.size() > index){
			for(int i = index; i < actions.size(); i++)
				actions.remove(i);
		}
		actions.add(act);
		index = actions.size() - 1;
	}
	@Override
	public void redo() {
		if(index < actions.size()){
			actions.get(index).redo();
			index++;
		}
	}
	@Override
	public void undo() {
		index--;
		if(index > 0){
			actions.get(index).undo();
		}	
	}
	
	public static void main(String args[]){
		
	}
}
