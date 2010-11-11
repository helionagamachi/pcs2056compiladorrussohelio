/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import syntax.Transition;

/**
 *
 * @author helionagamachi
 */
public class TransitionList {

    private Element firstElement;
    private Element lastElement;
    private int amount;

    public TransitionList() {
        this.amount = 0;
    }

    /**
     * Gets an array from the Elements of the List.
     * @return the array with the elements.
     */
    public Transition[] getArray() {
        Transition[] result = new Transition[this.amount];
        int index = 0;
        Element element;
        element = this.firstElement;
        while (index < this.amount) {
            result[index] = element.getTransition();
            element = element.getNext();
            index++;
        }
        return result;
    }

    /**
     * Adds a String on the list.
     * @param string the string to be added.
     */
    public void addTransition(Transition transition) {
        if (this.firstElement == null) {
            this.firstElement = new Element(transition);
            this.lastElement = this.firstElement;
            this.amount++;
        } else {
            Element newElement = new Element(transition);
            this.lastElement.setNext(newElement);
            this.lastElement = newElement;
            this.amount++;
        }
    }

    private class Element {

        private Transition transition;
        private Element next;

        public Element(Transition transition) {
            this.transition = transition;
        }

        public Element getNext() {
            return next;
        }

        public Transition getTransition() {
            return transition;
        }

        public void setNext(Element next) {
            this.next = next;
        }
    }
}
