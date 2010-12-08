/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

/**
 *
 * @author helionagamachi
 */
public class StringStack {
 private Element top;

    public StringStack() {
    }

    public boolean isEmpty(){
        return top == null;
    }

    /**
     * Gets the top of the stack.
     * @return null if the stack is empty.
     */
    public String pop(){
        if(top == null){
            return null;
        }else{
            Element result = top;
            top = result.getPrevious();
            return result.getString();
        }
    }

    /**
     * Adds a String to the stack.
     * @param String
     */
    public void push(String String){
        System.out.println("Stacking a String " + String);
        Element element = new Element(String);
        element.setPrevious(top);
        top = element;
    }

    /**
     * Like pop, but doesn't remove the top from the stack
     * @return
     */
    public String peek(){
       return top.getString();
    }


    @Override
    public String toString(){
        System.out.println("Called to string from the String stack");
        String result = "";
        Element element;
        element = top;
        while(element != null){
            result = element.getString() + " \n" + result;
            element = element.getPrevious();
        }
        return result;
    }
    private class Element {

        private Element previous;
        private String String;

        public Element(String String) {
            this.String = String;
        }

        public Element getPrevious() {
            return previous;
        }

        public void setPrevious(Element previous) {
            this.previous = previous;
        }

        public String getString() {
            return String;
        }


    }
}

