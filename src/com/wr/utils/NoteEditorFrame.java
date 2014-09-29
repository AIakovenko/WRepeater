package com.wr.utils;



import javax.swing.*;

/**
 * @autor Alex Iakovenko
 * Date: 9/29/14
 * Time: 9:13 AM
 */
public class NoteEditorFrame {

    private final static String FRAME_TITLE = "Note Editor";
    private static final ImageIcon MAIN_ICON = new ImageIcon(Main.icoPath + "calibre.png");
    private static NoteEditorFrame instance;
    private JFrame frame;

    private NoteEditorFrame(){
        frame = new JFrame(FRAME_TITLE);
        initialize();
    }

    public static NoteEditorFrame getInstance(){
        if (instance == null){
            instance = new NoteEditorFrame();
        }
        return instance;
    }
    private void initialize(){

    }

}
