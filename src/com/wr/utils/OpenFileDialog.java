package com.wr.utils;

import com.wr.Main;

import javax.swing.*;
import java.awt.*;
import java.io.File;

/**
 * @autor Alex Iakovenko
 * Date: 10/2/14
 * Time: 11:43 PM
 */
public class OpenFileDialog {

    private static final ImageIcon MAIN_ICON = new ImageIcon(Main.icoPath + "calibre.png");
    private static final String HOME_DIRECTORY = "./Dictionary";

    public static File showOpenFileDialog(Frame parentFrame, String title){
        FileDialog fileDialog = new FileDialog(parentFrame, title);
        fileDialog.setIconImage(MAIN_ICON.getImage());
        fileDialog.setDirectory(HOME_DIRECTORY);
        fileDialog.setVisible(true);
        String directoryName = fileDialog.getDirectory();
        String fileName = fileDialog.getFile();

        File file = null;
        if (directoryName != null && fileName != null) {
            file = new File(directoryName + fileName);
        }
        return file;
    }

}
