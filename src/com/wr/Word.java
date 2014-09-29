package com.wr;

/**
 * Created with IntelliJ IDEA.
 * User: alex
 * Date: 01.07.13
 * Time: 22:16
 * To change this template use File | Settings | File Templates.
 */
public class Word {
    private String rWord;
    private String eWord;

    public Word(String eWord, String rWord){
        this.rWord = rWord;
        this.eWord = eWord;
    }

    public void setRWord(String s){
        rWord = s;
    }
    public String getRWord(){
        return rWord;
    }
    public void setEWord(String s){
        eWord = s;
    }
    public String getEWord(){
        return eWord;
    }

    @Override
    public String toString() {
        return eWord +" - " + rWord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Word word = (Word) o;

        if (eWord != null ? !eWord.equals(word.eWord) : word.eWord != null) return false;
        if (rWord != null ? !rWord.equals(word.rWord) : word.rWord != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = rWord != null ? rWord.hashCode() : 0;
        result = 31 * result + (eWord != null ? eWord.hashCode() : 0);
        return result;
    }
}
