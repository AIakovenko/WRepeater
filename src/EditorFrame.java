import javax.swing.*;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 08.09.13
 * Time: 17:24
 * To change this template use File | Settings | File Templates.
 */
public class EditorFrame extends JFrame {

    private static final Dimension WINDOW_SIZE = new Dimension(600,400);
    private static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    private static final Dimension F_OPEN_SIZE = new Dimension(30,30);
    private static final Dimension FP_TFIELD_SIZE = new Dimension(WINDOW_SIZE.width - F_OPEN_SIZE.width - 15, F_OPEN_SIZE.height);
    private static final String DICTIONARY_PATH = "";
    private JTextField directoryPath;
    private JScrollPane treeView;
    private JTree tree;
    private JTable table;
    private JScrollPane tableView;
    private JSplitPane splitPane;
    private File destinationDir;

    public EditorFrame(){
        new JFrame("Database editor");

        setSize(WINDOW_SIZE);
        setLocation((int)(SCREEN_SIZE.getWidth()/2-getWidth()/2),
                (int)(SCREEN_SIZE.getHeight()/2-getHeight()/2));

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getContentPane().setLayout(null);
        destinationDir = new File(DICTIONARY_PATH);
        initPathField();
        initTreeView();
        initTable();
        initSplitPane();

    }
    private void initPathField(){
        /*
           Initialize text field to form which sets the path to dictionaries;
         */

        directoryPath = new JTextField(destinationDir.getAbsolutePath());
        directoryPath.setSize(FP_TFIELD_SIZE);
        directoryPath.setLocation(5, 10);

        /*
           Initialize button which lets to choose the path to dictionary;
         */
        ImageIcon iconOpenPath = new ImageIcon(Main.icoPath + "folder.png");
        JButton openPath = new JButton(iconOpenPath);
        openPath.setSize(F_OPEN_SIZE);
        openPath.setLocation(directoryPath.getX()+directoryPath.getWidth()+5, directoryPath.getY());
        openPath.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDirOpenDialog();

            }
        });


        add(directoryPath);
        add(openPath);

    }
    private void initTreeView(){

        tree = new JTree(loadTreeViewNodes());
        treeView = new JScrollPane(tree);
        treeView.setSize(WINDOW_SIZE.width/3, WINDOW_SIZE.height-20);
        treeView.setLocation(10, 10);

        add(treeView);
    }
    private DefaultMutableTreeNode loadTreeViewNodes(){
        DefaultMutableTreeNode dbFiles = new DefaultMutableTreeNode("...");
        File pa = new File(destinationDir.getAbsolutePath());
        File [] f = pa.listFiles();
//        String list[] = new File("/home/alex/").list(new FilenameFilter() {
//            @Override
//            public boolean accept(File dir, String name) {
//                if (name.contains(".txt"))
//                return true;
//                  else return false;//To change body of implemented methods use File | Settings | File Templates.
//            }
//        });
//        Collection<String> listFiles = new ArrayList<String>();
//        listFiles.addAll(Arrays.asList(new File("/home/alex/").list()));
        for(int i = 0; i < f.length; i++){
//            System.out.println(list[i]);
//            dbFiles.add(new DefaultMutableTreeNode(list[i]));
            dbFiles.add(new DefaultMutableTreeNode(f[i].getName()));
        }

        return dbFiles;
    }
    private void initTable(){

        Object[] columns = {"English", "Russian"};
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.setColumnIdentifiers(columns);

        table = new JTable(tableModel);
        tableView = new JScrollPane(table);



    }
    private void initSplitPane(){
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, treeView, tableView);
        splitPane.setDividerLocation(WINDOW_SIZE.width/3);
        splitPane.setDividerSize(5);
        splitPane.setSize(WINDOW_SIZE.width-10, WINDOW_SIZE.height-50);
        splitPane.setLocation(5,45);
        add(splitPane);
    }
    private void showDirOpenDialog(){
        JFileChooser fileOpen = new JFileChooser(directoryPath.getText());
        fileOpen.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//        FileNameExtensionFilter filter = new FileNameExtensionFilter(
//                "Algorythm workspace files .wks", "wks");
//        fileOpen.setFileFilter(filter);
        int ret = fileOpen.showOpenDialog(null);
        if (ret == JFileChooser.APPROVE_OPTION) {
            destinationDir = fileOpen.getSelectedFile();
            directoryPath.setText(destinationDir.getAbsolutePath());
            updateTree();
//            if(Main.setListAllWords(importingFile))
//                JOptionPane.showMessageDialog(this, "Import success!", "Import file",JOptionPane.INFORMATION_MESSAGE);
//            else
//                JOptionPane.showMessageDialog(this, "Import filed!", "Import file",JOptionPane.ERROR_MESSAGE);

        }
//

    }
    private void updateTree(){
        tree = new JTree(loadTreeViewNodes());
//        treeView.set;
        splitPane.setLeftComponent(treeView);
    }



}
