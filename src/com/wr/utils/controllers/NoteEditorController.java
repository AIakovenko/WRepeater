package com.wr.utils.controllers;

import com.wr.MainFrame;
import com.wr.Word;
import com.wr.utils.NoteEditorFrame;
import com.wr.utils.commands.EditNoteCommand;
import com.wr.utils.commands.ICommand;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @autor Alex Iakovenko
 * Date: 9/29/14
 * Time: 11:02 AM
 */
public class NoteEditorController {
    private static NoteEditorController instance;
    private static NoteEditorFrame noteEditorFrame;
    private List<Word> words;

    private NoteEditorController() {
        noteEditorFrame = NoteEditorFrame.getInstance();
        addActions();
        fillTextFields();

    }

    public static NoteEditorController getInstance(){
        if (instance == null || noteEditorFrame == null){
            instance = new NoteEditorController();
        }
        return instance;
    }

    private void addActions(){
        JButton buttonOK = noteEditorFrame.getButtonOK();
        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ICommand command = new EditNoteCommand();
                command.execute();
                noteEditorFrame.setVisible(false);
                instance = null;
            }
        });

        JButton buttonCancel = noteEditorFrame.getButtonCancel();
        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                noteEditorFrame.setVisible(false);
                noteEditorFrame = null;
            }
        });
    }

    private void fillTextFields(){
        MainFrame mainFrame = MainFrame.getInstance();

        words = mainFrame.getList();
        int index = mainFrame.getIndex();
        if (words != null) {
            Word currentWord = words.get(index);
            noteEditorFrame.setOldValue(currentWord.getEWord());
            noteEditorFrame.setOldTranslation(currentWord.getRWord());
            noteEditorFrame.setNewValue(currentWord.getEWord());
            noteEditorFrame.setNewTranslation(currentWord.getRWord());
            noteEditorFrame.setVisible(true);
        } else {
            noteEditorFrame = null;
            mainFrame.showMessage("No any dictionary has been loaded!", "Edit note",JOptionPane.ERROR_MESSAGE);

        }
    }
}
