/**
 * This class describes GUI the main window of the program.
 *
 * User: Alex Iakovenko
 * Date: 02.07.13
 * Time: 19:44
 */
import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.*;

public class MainFrame extends JFrame{
    /**
     * Dimension of the frame.
     */
    private static final Dimension WINDOW_SIZE = new Dimension(700,400);

    /**
     * Min value of dimension if frame size have been changed manually by user.
     */
    private static final Dimension MIN_WINDOW_SIZE = new Dimension(WINDOW_SIZE.width,WINDOW_SIZE.height-30);

    /**
     * Value of screen resolution where program will execute.
     */
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * Sets height of status bar component.
     */
    private final int STATUS_HEIGHT = 25;

    /**
     * Main icon of application
     */
    private static final ImageIcon MAIN_ICON = new ImageIcon(Main.icoPath + "calibre.png");
    private JTabbedPane mainPane;
    private JPanel mainPanel;
    private JPanel repetPanel;
    private JPanel statusBar;
    private JButton bCancel;
    private JButton bNext;
    private JButton bAnswer;
    private JButton bAddToRepetition;
    private JButton bRemoveFromRepetition;

    private JMenuBar menu;
    private JTextArea question;
    private JScrollPane scrollPaneQuestion;
    private JTextField answer;
    private JProgressBar progressBar;

    private JLabel totalQuestions;
    private JLabel numberTotQuestions;
    private JLabel numberOfCurQuastion;
    private JLabel curentQuestions;
    private JLabel totalRepetitions;
    private JLabel numberTotRepetitions;

    /*Labels on the status bar*/
    private JLabel subject;
    private JLabel action;

    /**
     * Default value of status bar's label <Code>Subject</Code>
     */
    private String nameCurrentSubject = "";

    private File importingFile;
    private final Dimension dimButt = new Dimension(120,25);
    private ArrayList<Word> words;
    private ArrayList<Word> repetitionWords;
    private int index = 0;
    private int indRepet = 0;
    private int tabIndex = 0; /* Sets index of the tab is chosen*/

    private final String TAB_MAIN = "Main list";
    private final String TAB_REPETITION = "Repetition";

    public MainFrame(){
        new JFrame();
        setTitle("Learn-Words");
        setIconImage(MAIN_ICON.getImage());
        setSize(WINDOW_SIZE);
//        setResizable(false);
        setMinimumSize(MIN_WINDOW_SIZE);
        setLocation((int)(SCREEN_SIZE.getWidth()/2-getWidth()/2),
               (int)(SCREEN_SIZE.getHeight()/2-getHeight()/2));

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        getContentPane().setLayout(null);
        LayoutManager layout = getContentPane().getLayout();
        BorderLayout bl = new BorderLayout();
        bl.setVgap(1);
        getContentPane().setLayout(bl);


        mainPanel = new JPanel(layout);
        repetPanel = new JPanel(layout);

        repetitionWords = new ArrayList<Word>();


        initStatusBar();
        initButton();
        initMenuBar();
        initTextArea();
        initTextField();
        initProgressBar();
        initLabel();

        mainPane = new JTabbedPane();
        mainPane.addTab(TAB_MAIN, mainPanel);
        mainPane.addTab(TAB_REPETITION, repetPanel);
        this.add(mainPane, BorderLayout.CENTER);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);

                bCancel.setLocation(getWidth() - bCancel.getWidth() - 20, getHeight() - 90);
                menu.setBounds(0, 0, getWidth(), 25);
                scrollPaneQuestion.setBounds(10, 55, getWidth() - 30, 50);
                answer.setBounds(scrollPaneQuestion.getX(),
                        scrollPaneQuestion.getHeight() + scrollPaneQuestion.getY() + 10,
                        scrollPaneQuestion.getWidth(), 25);
                progressBar.setLocation(answer.getX(), answer.getY() + answer.getHeight() + 20);

//                bAddToRepetition.setLocation(progressBar.getX() + progressBar.getWidth() + 20, progressBar.getY());

                bNext.setLocation(getWidth() - bCancel.getWidth() - 20, progressBar.getY());
                bAnswer.setLocation(bNext.getX(), bNext.getY() + 30);

                totalQuestions.setLocation(answer.getX(), progressBar.getY() + progressBar.getHeight() + 20);
                numberTotQuestions.setLocation(totalQuestions.getX() + totalQuestions.getWidth(),
                        totalQuestions.getY());
                curentQuestions.setLocation(totalQuestions.getX(), totalQuestions.getY() + totalQuestions.getHeight() + 10);
                numberOfCurQuastion.setLocation(curentQuestions.getX() + curentQuestions.getWidth(),
                        curentQuestions.getY());
                totalRepetitions.setLocation(curentQuestions.getX(), curentQuestions.getY() + curentQuestions.getHeight() + 10);
                numberTotRepetitions.setLocation(totalRepetitions.getX() + totalRepetitions.getWidth(),
                        totalRepetitions.getY());


            }
        });

        mainPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try{
                    JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
                    tabIndex = sourceTabbedPane.getSelectedIndex();
                    setQuestion();
                }catch(RuntimeException ex){
                    /* Does nothing */
                }
            }
        });
    }
    private void initStatusBar(){

        statusBar = new JPanel();
        statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
        statusBar.setSize(getWidth() - 10, STATUS_HEIGHT);

        subject = new JLabel(nameCurrentSubject);
        subject.setMaximumSize(new Dimension(50, 25));
        action = new JLabel(":");

        statusBar.setLayout(new BorderLayout());
        statusBar.add(subject, BorderLayout.EAST);
        statusBar.add(action, BorderLayout.WEST);

        this.add(statusBar, BorderLayout.SOUTH);
    }

    private void initMenuBar(){
        menu = new JMenuBar();
        menu.setSize(this.getWidth(), 25);
        JMenu menuFile = new JMenu("File");
        ImageIcon openIcon = new ImageIcon(Main.icoPath + "folder_open_icon.png");
        JMenuItem itemOpen = new JMenuItem("Open new dictionary    ",openIcon);
        itemOpen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                words = null;
                Main.clearList();
                File temp = importingFile;
                showFileOpenDialog();
                if((importingFile != null) && (importingFile != temp)){
                    setWords();
                    setQuestion();
                    bAddToRepetition.setEnabled(true);
                    bNext.setEnabled(true);
                    bAnswer.setEnabled(true);
                }

            }
        });
        ImageIcon importIcon = new ImageIcon(Main.icoPath+"import_icon.png");
        JMenuItem itemImport = new JMenuItem("Add dictionary  ", importIcon);
        itemImport.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File temp = importingFile;
                showFileOpenDialog();
                if((importingFile != null) && (importingFile != temp)){
                    setWords();
                    setQuestion();
                    bNext.setEnabled(true);
                    bAnswer.setEnabled(true);
                }
            }
        });

        menuFile.add(itemOpen);
        menuFile.add(itemImport);

        JMenu menuEdit = new JMenu("Edit");
        ImageIcon editorIcon = new ImageIcon(Main.icoPath+"folder_open_icon.png");
        JMenuItem itemEditor = new JMenuItem("Database editor",editorIcon);
        itemEditor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 Main.runEditorFrame();
            }
        });

        menuEdit.add(itemEditor);

        menu.add(menuFile);
        menu.add(menuEdit);
        add(menu,BorderLayout.NORTH);
    }

    private void initTextField(){
        answer = new JTextField();
        add(answer);

        answer.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                /* Press Enter-key - accept an answer */
                if(e.getKeyCode() == KeyEvent.VK_ENTER ){
                   checkAnswer();
                   showMessage(action, "");
                }
                /* Press F1-key - insert a correct answer */
                if(e.getKeyCode() == KeyEvent.VK_F1){
                    if(tabIndex == 0 && index>-1)//Tab <code> TAB_MAIN </code> is chosen
                        answer.setText(words.get(index).getEWord());
                    else if(tabIndex == 1 && indRepet >-1)//Tab <code>TAB_REPETITION</code> is chosen
                        answer.setText(repetitionWords.get(indRepet).getEWord());
                }

                /* Press F5-key - add current word to repetition list */
                if(e.getKeyCode() == KeyEvent.VK_F5){
                    repetitionWords.add(words.get(index));
                    numberTotRepetitions.setText(new Integer(repetitionWords.size()).toString());
                    showMessage(action, "< " + words.get(index).getEWord() + " > was added");
                }

                /* Press F8-key - delete current answer from repetition list */
                if(e.getKeyCode() == KeyEvent.VK_F8){

                    showMessage(action, "< " + repetitionWords.get(indRepet).getEWord() + " > was removed");
                    repetitionWords.remove(indRepet);
                    numberTotRepetitions.setText(new Integer(repetitionWords.size()).toString());
                    setQuestion();
                    if(repetitionWords.isEmpty())
                        bRemoveFromRepetition.setEnabled(false);
                }
                /* Press F12-key - skip current word  */
                if(e.getKeyCode() == KeyEvent.VK_F12){
                        if(tabIndex == 0)//Tab <code> TAB_MAIN </code> is chosen
                        index++;
                    else if(tabIndex == 1)//Tab <code>TAB_REPETITION</code> is chosen
                        indRepet++;
                    setQuestion();
                }
            }
        });

    }
    private void initButton(){

        bCancel = new JButton("Cancel");
        bCancel.setSize(dimButt);

        bCancel.setToolTipText("Exit program");
        bCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        this.add(bCancel);

        bNext = new JButton("Next");
        bNext.setToolTipText("Next word <F12>");
        bNext.setSize(dimButt);
        bNext.setEnabled(false);
        bNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tabIndex == 0)//Tab <code> TAB_MAIN </code> is chosen
                    index++;
                else if(tabIndex == 1)//Tab <code>TAB_REPETITION</code> is chosen
                    indRepet++;

                setQuestion();
            }
        });

        bAnswer = new JButton("Answer");
        bAnswer.setToolTipText("Insert an answer <F1>");
        bAnswer.setSize(dimButt);
        bAnswer.setEnabled(false);
        bAnswer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(tabIndex == 0 && index>-1)//Tab <code> TAB_MAIN </code> is chosen
                    answer.setText(words.get(index).getEWord());
                else if(tabIndex == 1 && indRepet >-1)//Tab <code>TAB_REPETITION</code> is chosen
                    answer.setText(repetitionWords.get(indRepet).getEWord());
            }
        });

        bAddToRepetition = new JButton("Add to repetition");
        bAddToRepetition.setToolTipText("Add current word to repetition list <F5>");
        bAddToRepetition.setSize(dimButt.width + 50, dimButt.height);
        bAddToRepetition.setLocation(378, 114);
        bAddToRepetition.setEnabled(false);
        bAddToRepetition.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                repetitionWords.add(words.get(index));
                numberTotRepetitions.setText(new Integer(repetitionWords.size()).toString());
                showMessage(action, "< " + words.get(index).getEWord() + " > was added");
            }
        });

        bRemoveFromRepetition = new JButton("Remove");
        bRemoveFromRepetition.setToolTipText("Remove current word from repetition list <F8>");
        bRemoveFromRepetition.setSize(dimButt.width+50, dimButt.height);
        bRemoveFromRepetition.setLocation(378, 114);
        bRemoveFromRepetition.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMessage(action, "< " + repetitionWords.get(indRepet).getEWord() + " > was removed");
                repetitionWords.remove(indRepet);
                numberTotRepetitions.setText(new Integer(repetitionWords.size()).toString());
                setQuestion();
                if(repetitionWords.isEmpty())
                    bRemoveFromRepetition.setEnabled(false);
            }
        });
        ButtonGroup groupRepetition = new ButtonGroup();
        groupRepetition.add(bAddToRepetition);
        groupRepetition.add(bRemoveFromRepetition);


        this.add(bNext);
        this.add(bAnswer);
        mainPanel.add(bAddToRepetition);
        repetPanel.add(bRemoveFromRepetition);
    }
    private void initTextArea(){
        question = new JTextArea();
        question.setSize(100, 50);
        question.setLineWrap(true);
        question.setEditable(false);
        scrollPaneQuestion = new JScrollPane(question);

        this.add(scrollPaneQuestion);
    }


    private void initProgressBar(){

        progressBar = new JProgressBar();
        progressBar.setSize(getWidth()/2,25);
        progressBar.setStringPainted(true);

        progressBar.setMaximum(100);
        progressBar.setMinimum(0);
        progressBar.setValue(0);

        this.add(progressBar);
    }
    private void initLabel(){
        final Dimension labelDim = new Dimension(150, 20);
        totalQuestions = new JLabel("Total questions: ");
        totalQuestions.setSize(labelDim);

        numberTotQuestions = new JLabel("0");
        numberTotQuestions.setSize(labelDim);

        curentQuestions = new JLabel("Current question: ");
        curentQuestions.setSize(labelDim);

        numberOfCurQuastion = new JLabel ("0");
        numberOfCurQuastion.setSize(labelDim);

        totalRepetitions = new JLabel("Total repetition");
        totalRepetitions.setSize(labelDim);

        numberTotRepetitions = new JLabel("0");
        numberTotRepetitions.setSize(labelDim);

        this.add(totalQuestions);
        this.add(numberTotQuestions);
        this.add(curentQuestions);
        this.add(numberOfCurQuastion);
        this.add(totalRepetitions);
        this.add(numberTotRepetitions);
    }

    private void showFileOpenDialog(){
        JFileChooser fileOpen = new JFileChooser("./");
//        FileNameExtensionFilter filter = new FileNameExtensionFilter(
//                "Algorythm workspace files .wks", "wks");
//        fileOpen.setFileFilter(filter);
        int ret = fileOpen.showDialog(null, "Open file");
        if (ret == JFileChooser.APPROVE_OPTION) {
            importingFile = fileOpen.getSelectedFile();
            if(Main.setListAllWords(importingFile)){
                JOptionPane.showMessageDialog(this, "Import success!", "Import file",JOptionPane.INFORMATION_MESSAGE);
                nameCurrentSubject = importingFile.getName();
                showMessage(subject,nameCurrentSubject);
            }
            else{
                JOptionPane.showMessageDialog(this, "Import filed!", "Import file",JOptionPane.ERROR_MESSAGE);
            }

        }
//        FileDialog fileDialog = new FileDialog(this, "Open file",FileDialog.LOAD);
//        fileDialog.setLocation((int)(SCREEN_SIZE.getWidth()/2-getWidth()/2),
//                (int)(SCREEN_SIZE.getHeight()/2-getHeight()/2));
//        fileDialog.setDirectory("./");
//        fileDialog.setVisible(true);
//
//        importingFile = new File(fileDialog.getDirectory()+fileDialog.getFile());
//        if(importingFile.getPath().equals("nullnull"))
//            return;
//        if(Main.setListAllWords(importingFile))
//                JOptionPane.showMessageDialog(this, "Import success!", "Import file",JOptionPane.INFORMATION_MESSAGE);
//            else
//                JOptionPane.showMessageDialog(this, "Import filed!", "Import file",JOptionPane.ERROR_MESSAGE);

    }
    private void setWords(){
        words = new ArrayList<Word>();
        Iterator<Word> iterator = Main.getListAllWords().getList().iterator();
        while(iterator.hasNext()){
            words.add(iterator.next());
        }
        progressBar.setMaximum(words.size());
        Collections.shuffle(words);
        numberTotQuestions.setText(new Integer(words.size()).toString());




    }
    private void setQuestion(){
        if(tabIndex == 0){ //Tab <code> TAB_MAIN </code> is chosen
            numberTotQuestions.setText(new Integer(words.size()).toString());
            progressBar.setMaximum(words.size());
            progressBar.setValue(index);
            answer.setText("");
               if (index<words.size()){
                    question.setText(words.get(index).getRWord());
                    numberOfCurQuastion.setText(new Integer(index+1).toString());
               }
               else{
                    question.setText("End of file!!!");
                    index = -1;
                    Collections.shuffle(words);
               }
        }else
            if(tabIndex == 1){ //Tab <code>TAB_REPETITION</code> is chosen
                numberTotRepetitions.setText(new Integer(repetitionWords.size()).toString());
                progressBar.setMaximum(repetitionWords.size());
                progressBar.setValue(indRepet);
                answer.setText("");
                if (indRepet<repetitionWords.size()){
                    question.setText(repetitionWords.get(indRepet).getRWord());
                    numberOfCurQuastion.setText(new Integer(indRepet+1).toString());
                }
                else{
                    question.setText("End of file!!!");
                    indRepet = -1;
                    Collections.shuffle(repetitionWords);
                }
            }

    }

    private void checkAnswer(){
        if(tabIndex == 0){//Tab <code> TAB_MAIN </code> is chosen
            if(index == -1){
                index++;
                setQuestion();
            }

            String trueAnswer = words.get(index).getEWord().toLowerCase().trim();
            String thisAnswer = answer.getText().toLowerCase().trim();

            if (thisAnswer.equals(trueAnswer)&&(index<words.size())){
                index++;
                setQuestion();
            }
            progressBar.setValue(index);
        }else
            if(tabIndex == 1){//Tab <code>TAB_REPETITION</code> is chosen
                if(indRepet == -1){
                    indRepet++;
                    setQuestion();
                }

                String trueAnswer = repetitionWords.get(indRepet).getEWord().toLowerCase().trim();
                String thisAnswer = answer.getText().toLowerCase().trim();

                if (thisAnswer.equals(trueAnswer)&&(indRepet<repetitionWords.size())){
                    indRepet++;
                    setQuestion();
                }
                progressBar.setValue(indRepet);
            }
    }

    private void showMessage(JLabel label, String message){
        StringBuilder mess = new StringBuilder(label.getText());
        mess.setLength(20);
        mess.append(message);
        label.setText(mess.toString());
    }

}
