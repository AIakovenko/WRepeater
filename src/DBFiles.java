/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 11.09.13
 * Time: 12:03
 * To change this template use File | Settings | File Templates.
 */
import java.util.*;
import java.io.*;
public class DBFiles {
    private Collection<File> dbFile;
    public DBFiles (Collection<File> collection ){
        this.dbFile = collection;
    }
    public Collection<File> getDbFile(){
        return dbFile;
    }
    public void addFile (File file){
        this.dbFile.add(file);
    }

}
