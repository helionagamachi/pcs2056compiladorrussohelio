/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package labels;

/**
 *
 * @author helionagamachi
 */
public class Label {
    private String name;
    private int id;
    private LabelType type;
    // if the label is a label for a constant, it can hold some data...
    private int numericalData;
    private String StringData;

    public String getStringData() {
        return StringData;
    }

    public void setStringData(String StringData) {
        this.StringData = StringData;
    }

    public int getNumericalData() {
        return numericalData;
    }

    public void setNumericalData(int numericalData) {
        this.numericalData = numericalData;
    }

    public Label(String name, int id, LabelType type) {
        this.name = name;
        this.id = id;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LabelType getType() {
        return type;
    }

    public void setType(LabelType type) {
        this.type = type;
    }

    @Override
    public String toString(){
        return " " + name + id + " ";
    }

    
}
