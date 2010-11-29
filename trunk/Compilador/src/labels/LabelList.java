/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package labels;

/**
 *
 * @author helionagamachi
 */
public class LabelList {

    private Element first;
    private int count;

    public LabelList() {
        count = 0;
    }

    public int getCount() {
        return count;
    }

    /**
     * Adds a label to the list
     * @param label the label to be added.
     */
    public void addLabel(Label label) {
        System.out.println("adding label to list "+label);
        if (count == 0) {
            first = new Element(label);
        } else {
            int index = 1;
            Element last = first;
            while (index < count) {
                last = last.getNext();
                index++;
            }
            last.setNext(new Element(label));
        }
        count++;
    }


    @Override
    public String toString(){
        String result = "";
        if(count == 0){
            result = "Label list is empty";
        }else{
            int index = 0;
            result = "Label List:\n";
            Element element = first;
            while(index < count){
                result += element.label.toString();
                result += "\n";
                index ++;
                element = element.next;
            }
        }
        return result;
    }
    /**
     * Clears the list.
     */
    public void clear() {
        System.out.println("Labels list cleared");
        count = 0;
        first = null;
    }

    /**
     * Gets the list as an array.
     * @return
     */
    public Label[] getArray() {
        Label[] result = new Label[count];
        int index = 0;
        Element element = first;
        while (index < count) {
            result[index] = element.getLabel();
            element = element.getNext();
            index++;
        }
        return result;
    }

    private class Element {

        private Element next;
        private Label label;

        public Element(Label label) {
            this.label = label;
        }

        public Element getNext() {
            return next;
        }

        public void setNext(Element next) {
            this.next = next;
        }

        public Label getLabel() {
            return label;
        }
    }
}
