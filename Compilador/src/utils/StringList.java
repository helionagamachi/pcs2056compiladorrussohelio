/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

/**
 * Implements a List to hold strings.
 * @author helionagamachi
 */
public class StringList {

    private Element firstElement;
    private Element lastElement;
    private int amount;
    public StringList() {
        this.amount = 0;
    }



    /**
     * Gets an array from the Elements of the List.
     * @return the array with the elements.
     */
    public String[] getArray(){
        String[] result = new String[this.amount];
        int index = 0;
        Element element;
        element = firstElement;
        while(index < amount){
            result[index] = element.getString();
            element = element.getNext();
        }
        return result;
    }



    /**
     * Returns the id given to a String.
     * @param string the string
     * @return the id , -1 if the string is not present.
     */
    public int getId(String string){
        int trys = 0;
        Element element;
        element = this.firstElement;
        while(trys < this.amount){
            if(string.equals(element.getString())){
                return trys;
            }else{
                element = element.getNext();
                trys++;
            }
        }
        return -1;
    }

    /**
     * Gets an string by  it's id
     * @param id
     * @return the String that has that id.
     */
    public String getByNumber(int id){
        if(id > this.amount){
            return null;
        }
        int nowIndex = 0;
        Element element;
        element = this.firstElement;
        while(nowIndex < id){
            element = element.getNext();
            nowIndex++;
        }
        return element.getString();
    }
    
    /**
     * Adds a String on the list.
     * @param string the string to be added.
     */
    public void addString(String string){
        if(this.firstElement == null){
            this.firstElement = new Element(this.amount, string);
            this.lastElement = this.firstElement;
            this.amount++;
        }else{
            Element newElement = new Element(this.amount, string);
            this.lastElement.setNext(newElement);
            this.lastElement = newElement;
            this.amount++;
        }
    }

    /**
     * This class represents each one of the elements
     * on the String list.
     */
    private class Element{
        private int id;
        private String string;
        private Element next;

        public Element(int id, String string){
            this.id = id;
            this.string = string;
        }

        public Element getNext() {
            return next;
        }

        public void setNext(Element next) {
            this.next = next;
        }

        public int getId() {
            return id;
        }

        public String getString() {
            return string;
        }
        
        
    }
}
