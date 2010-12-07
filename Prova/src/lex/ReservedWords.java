/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lex;

import java.util.ArrayList;

/**
 * Clase que abriga a lista de palavras reservadas.
 * @author helionagamachi
 */
public class ReservedWords {

    private static ArrayList<String> RW_WORDS = new ArrayList<String>();

    /**
     * Obtém a lista de palavras reservadas,
     * @return
     */
    public static ArrayList<String> getRW_WORDS() {
        if(RW_WORDS.isEmpty()){
            init();
        }
        return RW_WORDS;
    }

    /**
     * Retorna o indice da palavra reservada.
     * @param word
     * @return
     */
    public static int getIndex(String word){
        if(RW_WORDS.isEmpty()){
            init();
        }if(RW_WORDS.contains(word)){
            return RW_WORDS.indexOf(word);
        }
        return -1;
    }

    /**
     * Retorna a palavra reservada de acordo com index fornecido
     * @param index
     * @return
     */
    public static String getByIndex(int index){
        if(RW_WORDS.isEmpty()){
            init();
        }
        return RW_WORDS.get(index);
    }

    /**
     * Inicia a lista de palavras reservadas.
     */
    private static void init() {
        RW_WORDS.add("#");
        RW_WORDS.add("-");
        RW_WORDS.add("+");
        RW_WORDS.add("*");
        RW_WORDS.add("/");
        RW_WORDS.add("^");
        RW_WORDS.add("%");
        RW_WORDS.add(".");
        RW_WORDS.add("..");
        RW_WORDS.add("...");
        RW_WORDS.add("<");
        RW_WORDS.add("<=");
        RW_WORDS.add(">");
        RW_WORDS.add(">=");
        RW_WORDS.add("==");
        RW_WORDS.add("~=");
        RW_WORDS.add("and");
        RW_WORDS.add("or");
        RW_WORDS.add(",");
        RW_WORDS.add(";");
        RW_WORDS.add(":");
        RW_WORDS.add("[");
        RW_WORDS.add("]");
        RW_WORDS.add("=");
        RW_WORDS.add("{");
        RW_WORDS.add("}");
        RW_WORDS.add("(");
        RW_WORDS.add(")");
        RW_WORDS.add("end");
        RW_WORDS.add("nil");
        RW_WORDS.add("false");
        RW_WORDS.add("true");
        RW_WORDS.add("return");
        RW_WORDS.add("not");
        RW_WORDS.add("break");
        RW_WORDS.add("local");
        RW_WORDS.add("function");
        RW_WORDS.add("for");
        RW_WORDS.add("in");
        RW_WORDS.add("do");
        RW_WORDS.add("if");
        RW_WORDS.add("then");
        RW_WORDS.add("elseif");
        RW_WORDS.add("else");
        RW_WORDS.add("while");
        RW_WORDS.add("repeat");
        RW_WORDS.add("until");
    }
}
