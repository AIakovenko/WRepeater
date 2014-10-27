package com.wr.utils.commands;

import com.wr.utils.NoteEditorFrame;

import javax.swing.*;
import java.io.*;


/**
 * @autor Alex Iakovenko
 * Date: 10/2/14
 * Time: 11:19 PM
 */
public class EditFileCommand implements ICommand {
    private NoteEditorFrame noteEditorFrame = NoteEditorFrame.getInstance();
    private final String DEFAULT_EDITOR = "kate";
    @Override
    public void execute() {
//        NoteEditorFrame noteEditorFrame = NoteEditorFrame.getInstance();
        File file = noteEditorFrame.showOpenFileDialog("Open file");
        if (file != null){
             editFile(file);
        }
    }

    private void editFile(File file) {
        try {
            Runtime.getRuntime().exec(DEFAULT_EDITOR + " " + file.getAbsolutePath());
        } catch (IOException e){
            noteEditorFrame.showMessage("I/O Error", "Open editor error", JOptionPane.ERROR_MESSAGE);
        }

    }


}
