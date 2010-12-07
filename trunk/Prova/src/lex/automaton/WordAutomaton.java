/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lex.automaton;

import java.util.ArrayList;
import lex.ReservedWords;
import lex.Token;
import lex.TokenType;
import static utils.ArrayUtils.letters;
import static utils.ArrayUtils.numbers;
import static utils.ArrayUtils.charIsOnArray;
import static lex.ReservedWords.getIndex;

/**
 * Classe que reconhece identificadores e palavras reservadas
 * @author helionagamachi
 */
public class WordAutomaton extends Automaton {

    private String name;
    private ArrayList<String> possibleRWList;
    private boolean possibleRW;
    private boolean possibleID;
    private boolean finalState;
    private Token token;

    public WordAutomaton() {
        name = "";
        possibleRWList = new ArrayList<String>();
        finalState = false;
        possibleID = true;
        possibleRW = true;
        token = null;
    }

    @Override
    public boolean process(char c) {
//        System.out.println("Meu estado é :");
//            System.out.println("possibleID" + possibleID);
//            System.out.println("possibleRW" + possibleRW);
//            System.out.println("possibleLIST" + possibleRWList);
        if (name.equals("")) {
//            System.out.println("recebi " + c + " " + (int) c);
            // <editor-fold defaultstate="collapsed" desc=" primeiro caractere ">
            // nehhum caracterer foi processado ainda.
            // verificar se pode ser o primeiro caracter de uma palavra reservada.
            firstCheck(c);
            name += c;
            if (possibleID && possibleRWList.isEmpty()) {
                // é uma letra, e não houve coicidencia com uma palavra reservada.
                // descarta a possibilidade de ser uma palavra reservada.
                possibleRW = false;
                // está em um estado final, pode receber letras e numeros
                token = new Token(TokenType.IDENTIFIER, -1);
                finalState = true;
            } else if (!possibleID && possibleRWList.isEmpty()) {
                //não pode ser um id e nem tem possibilidade de ser uma palavra reservada
                //cai aqui no caso de numeros como primeiro caracter...
                return false;
            }
            // </editor-fold>
            // A menos de uma possibilidade remota....
            return true;
        } else if (name.equals(".")) {
            // caso especial
            // <editor-fold defaultstate="collapsed" desc=" Pode ser um float ">
            if (charIsOnArray(c, numbers)) {
                possibleRW = false;
                return false;
            } else {
                finalState = true;
                //se tem . , outras possibilidade são .. e ...
                if (c == '.') {
                    name += '.';
                    return true;
                } else {
                    token = new Token(TokenType.RESERVED_WORD, getIndex(name));
                    return false;
                }
            }
            // </editor-fold>
        } else if (possibleID && !possibleRW) {
            // <editor-fold defaultstate="collapsed" desc="apenas ID">
            // só pode ser um id
            if (charIsOnArray(c, letters) || charIsOnArray(c, numbers)) {
                name += c;
                return true;
            }
            // nao é letra , nem numero, então nao faz parte de um id.
            return false;
            // </editor-fold>
        } else if (possibleRWList.size() == 1) {
//            System.out.println("Caso de uma palavra reservada na mira.");
            // <editor-fold defaultstate="collapsed" desc="Estamos de olho em uma RW">
            // temos a possibilidade de uma unica palavra reservada.
            if (possibleRWList.get(0).startsWith(name + c)) {
                // ainda é prefixo da palavra reservada.
                name += c;
                return true;
            } else {
                // deixa de ser prefixo
                if (possibleID) {
                    // pode ser id ainda, ou não.....
                    if (charIsOnArray(c, letters) || charIsOnArray(c, numbers)) {
                        // se estiver nesse caso ok, se não..
                        name += c;
                        finalState = true;
                        possibleRW = false;
                        // O analisador léxico vai ter que definir o valor dele...
                        token = new Token(TokenType.IDENTIFIER, -1);
                        return true;
                    } else {
                        finalState = true;
                        //ainda pode ser id
                        if (getIndex(name) == -1) {
                            // é id
                            token = new Token(TokenType.IDENTIFIER, -1);
                        } else {
                            token = new Token(TokenType.RESERVED_WORD, getIndex(name));
                        }
                        return false;
                    }
                } else {
                    // pode ser apenas uma palavra reservada...
                    finalState = true;
                    token = new Token(TokenType.RESERVED_WORD, getIndex(name));
                    return false;
                }
            }
// </editor-fold>
        } else if (!possibleID && possibleRW) {
            // estamos em um caso como .. ou = , >
            // <editor-fold defaultstate="collapsed" desc=" Chega...">
            // verificar se o novo caracter leva a zero as possiveis palavras
            // reservadas.
            if (takesToZeroRWs(c)) {
                // achar a palavra reservada e retornar falso.
                for (String possible : possibleRWList) {
                    if (possible.equals(name)) {
                        finalState = true;
                        token = new Token(TokenType.RESERVED_WORD, ReservedWords.getRW_WORDS().indexOf(name));
                        return false;
                    }
                }
            } else {
                // remover as possibilidades que podem ser removidas
                removePossible(c);
                name += c;
                return true;
            }
            // </editor-fold>
        } else {
            // caso mais complicado , coisas como i , else...
            if (charIsOnArray(c, numbers)) {
                // é id.
                token = new Token(TokenType.IDENTIFIER, -1);
                name += c;
                possibleRW = false;
                finalState = true;
                return true;
            } else {
                // vai levar a zero ?
                if (takesToZeroRWs(c)) {
                    // mas pode ser que vai cair no caso elseh, então vira um identificador
                    if (charIsOnArray(c, letters)) {
                        name += c;
                        possibleRW = false;
                        finalState = true;
                        token = new Token(TokenType.IDENTIFIER, -1);
                        return true;
                    }else{
                        // sem o novo caractere é um palavra reservada?
                        if(getIndex(name) == -1){
                            token = new Token(TokenType.IDENTIFIER,-1);
                            finalState = true;
                            return false;
                        }
                    }
                    // não, é uma letra... , por exemplo : else(
                    for (String possible : possibleRWList) {
                        if (possible.equals(name)) {
                            finalState = true;
                            token = new Token(TokenType.RESERVED_WORD, ReservedWords.getRW_WORDS().indexOf(name));
                            return false;
                        }
                    }
                } else {
                    // remove o que der...
                    removePossible(c);
                    name += c;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean isOnFinalState() {
        return finalState;
    }

    @Override
    public void reset() {
        name = "";
        possibleRWList = new ArrayList<String>();
        finalState = false;
        possibleID = true;
        possibleRW = true;
        token = null;
    }

    /**
     * Checa se a primeira letra é igual a alguma primeira letra de uma palavra reservada.
     * @param c
     */
    private void firstCheck(char c) {
        if (!charIsOnArray(c, letters)) {
            possibleID = false;
        }
        for (String possible : ReservedWords.getRW_WORDS()) {
            if (possible.charAt(0) == c) {
                possibleRWList.add(possible);
            }
        }
    }

    /**
     * Verifica se a adição de c, irá fazer com que
     * a quantidade de palavras reservadas caia a zero.
     * @param c
     * @return
     */
    private boolean takesToZeroRWs(char c) {
        for (String possible : possibleRWList) {
            if (possible.startsWith(name + c)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Token getToken() {
        return token;
    }

    public String getName() {
        return name;
    }

    /**
     * Remove da lista de prováveis palavras reservadas
     * aquelas que não
     */
    private void removePossible(char c) {
        ArrayList<String> newPossibleList = new ArrayList<String>();
        for (String possible : possibleRWList) {
            if (possible.startsWith(name + c)) {
                newPossibleList.add(possible);
            }
        }
        possibleRWList = newPossibleList;
    }
}
