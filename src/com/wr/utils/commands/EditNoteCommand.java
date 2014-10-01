package com.wr.utils.commands;

import com.wr.EditorFrame;
import com.wr.MainFrame;
import com.wr.Word;
import com.wr.utils.NoteEditorFrame;

import java.util.List;

/**
 * @autor Alex Iakovenko
 * Date: 9/29/14
 * Time: 1:27 PM
 */
public class EditNoteCommand implements ICommand {

    @Override
    public void execute() {
        MainFrame mainFrame = MainFrame.getInstance();
        NoteEditorFrame noteEditorFrame = NoteEditorFrame.getInstance();
        List<Word> list = mainFrame.getList();

        String newEWord = noteEditorFrame.getNewValue();
        String newRWord = noteEditorFrame.getNewTranslation();
        Word newWord = new Word(newEWord, newRWord);

        int index = mainFrame.getIndex();
        Word oldWord = list.get(index);
        if (!newWord.equals(oldWord)) {
            mainFrame.setWord(newWord, index);
            mainFrame.setQuestion(newRWord);
        }

    }
}
