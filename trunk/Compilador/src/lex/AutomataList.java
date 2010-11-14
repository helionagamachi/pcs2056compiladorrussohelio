/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package lex;

import automaton.Automata;


/**
 *
 * @author helionagamachi
 */
public class AutomataList {

    int availableAutomatas = 0;
    int totalAutomatas = 0;
    Element first;
    //The last automata to be possible to return false will be stored here.
    Automata finalAutomata;



    /**
     * Makes the automatas process the char c
     * @param c the char c
     * @return true if at least one machine has returned true
     * after processing the char.
     */
    public boolean process(char c){
        // I wil make in two diffent cases, only one automata
        // and more than one.
        if(availableAutomatas > 1){
            Element element;
            element = first;
            int index = 0;
            while(index < totalAutomatas){
                if(element.isUsable()){
                    boolean automataResult;
                    Automata automata;
                    automata = element.getAutomata();
                    automataResult = automata.processChar(c);
                    if(!automataResult){
                        element.setUsable(false);
                        this.availableAutomatas--;
                        if(availableAutomatas == 0){
                            this.finalAutomata = element.getAutomata();
                            return false;
                        }
                    }
                }
                element = element.getNext();
                index ++;
            }
            if(availableAutomatas == 1){
                // find the last automata;
                element = first;
                index = 0;
                while(index<totalAutomatas){
                    if(element.isUsable()){
                        finalAutomata = element.getAutomata();
                    }
                    element =element.getNext();
                    index ++;
                }
            }
            return true;
        }else{
            return finalAutomata.processChar(c);
        }
    }

    public Automata getFinalAutomata() {
        return finalAutomata;
    }


    

    /**
     * Resets all the automatas on the list.
     */
    public void resetAutomatas(){
        Element element;
        element = first;
        int index = 0;
        while(index < totalAutomatas){
            element.reset();
            element = element.getNext();
            index++;
        }
        finalAutomata = null;
        availableAutomatas = totalAutomatas;
    }
    /**
     * Adds an automata to the list.
     * @param automata.
     */
    public void addAutomata(Automata automata) {
        if (totalAutomatas == 0) {
            first = new Element(automata);
        } else {
            Element last;
            last = first;
            int count = 1;
            while (count < totalAutomatas) {
                last = last.getNext();
                count++;
            }
            last.setNext(new Element(automata));
        }
        totalAutomatas++;
        availableAutomatas++;
    }

    private class Element {

        private boolean usable;
        private Automata automata;
        private Element next;

        public Element(Automata automata) {
            this.automata = automata;
            this.usable = true;
        }

        public void setUsable(boolean usable) {
            this.usable = usable;
        }

        public void setNext(Element next) {
            this.next = next;
        }

        public Automata getAutomata() {
            return automata;
        }

        public boolean isUsable() {
            return usable;
        }

        public Element getNext() {
            return next;
        }

        public void reset(){
            this.usable = true;
            this.automata.resetAutomata();
        }
    }
}
