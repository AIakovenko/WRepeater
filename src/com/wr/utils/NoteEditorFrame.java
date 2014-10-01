package com.wr.utils;



import com.wr.Main;

import javax.swing.*;
import java.awt.*;

/**
 * @autor Alex Iakovenko
 * Date: 9/29/14
 * Time: 9:13 AM
 */
public class NoteEditorFrame {

    private final static String FRAME_TITLE = "Note Editor";
    private static final ImageIcon MAIN_ICON = new ImageIcon(Main.icoPath + "calibre.png");

    /**
     * Dimension of the frame.
     */
    private static final Dimension WINDOW_SIZE = new Dimension(400,130);

    /**
     * Value of screen resolution where program will execute.
     */
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    private static NoteEditorFrame instance;
    private JFrame frame;
    private JTextField oldValue;
    private JTextField oldTranslation;
    private JTextField newValue;
    private JTextField newTranslation;

    private JButton buttonOK;
    private JButton buttonCancel;



    private NoteEditorFrame(){
        frame = new JFrame(FRAME_TITLE);
        initialize();
    }

    /**
     * Creates instance of a Note Editor's frame.
     * @return instance of NoteEditorFrame;
     */
    public static NoteEditorFrame getInstance(){
        if (instance == null){
            instance = new NoteEditorFrame();
        }
        return instance;
    }

    /**
     * Sets the state of Note Editor's frame as visible of invisible;
     * @param b if true the frame is visible;
     *          if false the frame is invisible;
     */
    public void setVisible(boolean b){

        frame.setVisible(b);

        //if false then frame should be disposed.
        if (!b){
            frame.dispose();
        }
    }

    /**
     * Writes the value of word into Old Value text field;
     * @param oldValue  text of word's value.
     */
    public void setOldValue(String oldValue){

        this.oldValue.setText(oldValue);
    }

    /**
     * Writes the value of word's translation into Old Translation text field;
     * @param oldTranslation  text of word's translation.
     */
    public void setOldTranslation(String oldTranslation){

        this.oldTranslation.setText(oldTranslation);
    }

    /**
     * Returns the value of word from Old Value text field;
     * @return text of word's value.
     */
    public String getOldValue(){

        return oldValue.getText();
    }

    /**
     * Returns the value of word's translation from Old Translation text field;
     * @return text of word's translation.
     */
    public String getOldTranslation(){

        return oldTranslation.getText();
    }

    /**
     * Writes the value of word into new Value text field;
     * @param newValue  text of word's value.
     */
    public void setNewValue(String newValue){

        this.newValue.setText(newValue);
    }

    /**
     * Writes the value of word's translation into New Translation text field;
     * @param newTranslation  text of word's translation.
     */
    public void setNewTranslation(String newTranslation){

        this.newTranslation.setText(newTranslation);
    }

    /**
     * Returns the value of word from New Value text field;
     * @return text of word's value.
     */
    public String getNewValue(){

        return newValue.getText();
    }

    /**
     * Returns the value of word's translation from New Translation text field;
     * @return text of word's translation.
     */
    public String getNewTranslation(){

        return newTranslation.getText();
    }


    /**
     * Returns the instance of button "OK";
     * @return the instance of button "OK".
     */
    public JButton getButtonOK(){

        return buttonOK;
    }

    /**
     * Returns the instance of button "Cancel";
     * @return the instance of button "Cancel".
     */
    public JButton getButtonCancel(){

        return buttonCancel;
    }


    private void initialize(){
        frame.setIconImage(MAIN_ICON.getImage());
        frame.setSize(WINDOW_SIZE);
        frame.setResizable(false);
        frame.setLocation((int) (SCREEN_SIZE.getWidth() / 2 - frame.getWidth() / 2),
                (int) (SCREEN_SIZE.getHeight() / 2 - frame.getHeight() / 2));

        frame.setDefaultCloseOperation(frame.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(new JPanel(), BorderLayout.NORTH);
        frame.add(new JPanel(), BorderLayout.EAST);
        frame.add(new JPanel(), BorderLayout.WEST);
        frame.add(new JPanel(), BorderLayout.SOUTH);
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.add(placeTextFields());
        contentPanel.add(placeButtons());
        frame.add(contentPanel, BorderLayout.CENTER);

    }

    private JPanel placeTextFields(){
        final int ROWS = 2;
        final int COLUMNS = 2;
        GridLayout layout = new GridLayout(ROWS, COLUMNS);
        layout.setHgap(10);
        layout.setVgap(10);
        JPanel panel = new JPanel(layout);
        oldValue = new JTextField("Old value");
        oldValue.setEditable(false);

        oldTranslation = new JTextField("Old translation");
        oldTranslation.setEditable(false);

        newValue = new JTextField();
        newTranslation = new JTextField();

        panel.add(oldValue);
        panel.add(oldTranslation);
        panel.add(newValue);
        panel.add(newTranslation);

        return panel;
    }

    private JPanel placeButtons(){
        JPanel panel = new JPanel(new BorderLayout());

        buttonOK = new JButton("Ok");
        buttonCancel = new JButton("Cancel");

        JPanel bPanel = new JPanel(new FlowLayout());
        bPanel.add(buttonOK);
        bPanel.add(buttonCancel);
        panel.add(bPanel, BorderLayout.EAST);

        return panel;
    }


}
