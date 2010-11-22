/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package semantic;

/**
 *
 * @author helionagamachi
 */
public class ActionList {

    private int count ;
    private Element first;

    public ActionList() {
        count = 0;
    }


    /**
     * Gets an action by it's name.
     * @param name
     * @return
     */
    public Action getAction(String name){
        Element element = first;
        int index = 0;
        Action result = null;
        while(index < count){
            if(element.getName().equals(name)){
                result = element.getAction();
                index = count;
            }else{
                element = element.getNext();
            }
            index ++;
        }
        return result;
    }

    /**
     * Adds an action to the list.
     * @param name
     * @param action
     */
    public void addAction(String name, Action action){
        if(first == null){
            first = new Element(name, action);
        }else{
            int index = 1;
            Element last = first;
            while(index < count){
                last = last.getNext();
                index ++;
            }
            last.setNext(new Element(name, action));
        }
        count++;
    }

    
    private class Element{
        private Action action;
        private Element next;
        private String name;
        public Element(String name , Action action) {
            this.action = action;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public Element getNext() {
            return next;
        }

        public void setNext(Element next) {
            this.next = next;
        }

        public Action getAction() {
            return action;
        }


        
    }
}
