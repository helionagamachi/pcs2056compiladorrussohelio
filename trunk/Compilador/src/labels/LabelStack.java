/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package labels;

/**
 *
 * @author helionagamachi
 */
public class LabelStack {
 private Element top;

    public LabelStack() {
    }

    public boolean isEmpty(){
        return top == null;
    }

    /**
     * Gets the top of the stack.
     * @return null if the stack is empty.
     */
    public Label pop(){
        if(top == null){
            return null;
        }else{
            Element result = top;
            top = result.getPrevious();
            return result.getLabel();
        }
    }

    /**
     * Adds a label to the stack.
     * @param label
     */
    public void push(Label label){
        System.out.println("Stacking a label " + label);
        Element element = new Element(label);
        element.setPrevious(top);
        top = element;
    }

    /**
     * Like pop, but doesn't remove the top from the stack
     * @return
     */
    public Label peek(){
       return top.getLabel();
    }


    @Override
    public String toString(){
        System.out.println("Called to string from the label stack");
        String result = "";
        Element element;
        element = top;
        while(element != null){
            result = element.getLabel() + " \n" + result;
            element = element.getPrevious();
        }
        return result;
    }
    private class Element {

        private Element previous;
        private Label label;

        public Element(Label label) {
            this.label = label;
        }

        public Element getPrevious() {
            return previous;
        }

        public void setPrevious(Element previous) {
            this.previous = previous;
        }

        public Label getLabel() {
            return label;
        }


    }
}
