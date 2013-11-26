import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 01.07.13
 * Time: 22:14
 * To change this template use File | Settings | File Templates.
 */
public class Main {
    public static final String icoPath = "ico/";
    private static ListWords listAllWords = new ListWords();
    private static FramesRuner mainWindow;
    private static EditorFrame editorFrame;

    public static void main(String[] args){

        mainWindow = new FramesRuner();
        mainWindow.runMainFrame();
        mainWindow.getMainFrame().setVisible(true);


    }

    public static boolean setListAllWords(File file){
        try{
            Collection<Word> lWord = readWords(file);
            for(Word w : lWord)
                listAllWords.addWord(w);
            return true;
        }catch (IOException ex){
            System.out.println(ex.getMessage());
            return false;
        }

    }
    public static void clearList(){
        listAllWords.clearList();
    }
    public static ListWords getListAllWords(){
        return listAllWords;
    }
    private static Collection<Word> readWords(File file) throws IOException{
        String ruWord = "";
        String engWord = "";
        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
        Collection<Word> lWord = new ArrayList<Word>();

        while((line = reader.readLine())!=null){
            int i = 0;
//            for(int i = 0; i<line.length(); i++){
                if(line.contains(":"))
                    while(line.charAt(i)!=':'){
                        engWord = engWord+line.charAt(i);
                        i++;
                    }
                    i++;
                    while(i<line.length()){
                        ruWord = ruWord+line.charAt(i);
                        i++;
                    }
//                if( (line.charAt(i)>=65)&&(line.charAt(i)<=122))
//                    engWord = engWord+line.charAt(i);
//                if((line.charAt(i)>=1040)&&(line.charAt(i)<=1102))
//                    ruWord = ruWord+line.charAt(i);
//            }
            lWord.add(new Word(engWord, ruWord));
            ruWord = "";
            engWord = "";

        }
        reader.close();
        return lWord;
    }
    public static void runEditorFrame(){
        editorFrame = new EditorFrame();
        editorFrame.setVisible(true);
    }



}
