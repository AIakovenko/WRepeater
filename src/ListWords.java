import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 02.07.13
 * Time: 19:28
 * To change this template use File | Settings | File Templates.
 */
public class ListWords {
    private Collection<Word> list;
    public ListWords(){
        list = new HashSet<Word>();
    }
    public void addWord(Word w){
        list.add(w);
    }
    public Collection<Word> getList(){
        return list;
    }

    public void clearList(){
        list.clear();
    }
    public Word getAnswer(String s){
        Iterator<Word> iterator = list.iterator();
        Word w = null;
        boolean flag = false;
        while (iterator.hasNext()){
            w = iterator.next();
            if (w.getEWord().equals(s)){
                System.out.println(w);
                flag = true;
                break;
            }
        }
        if (!flag){
//            System.out.println("Nothing was find!");
            return null;
        }
        else
            return w;
    }



}
