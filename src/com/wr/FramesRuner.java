package com.wr;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 09.07.13
 * Time: 18:18
 * To change this template use File | Settings | File Templates.
 */
public class FramesRuner {
    private  MainFrame mainFrame;

    public void runMainFrame(){

            mainFrame = MainFrame.getInstance();
        mainFrame.setVisible(true);
    }

}
