/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package labels;

/**
 *
 * @author helionagamachi
 */
public class LabelManager {

    static int normalCount;
    static int varCount;
    static int constCount;

    public Label getLabel(LabelType type) {
        Label result;
        int counter = 0;
        String name = "";
        switch (type) {
            case CONST:
                counter = constCount;
                name = "CONST";
                constCount ++;
                break;
            case NORMAL:
                counter = normalCount;
                name = "LB";
                normalCount++;
                break;
            case VARIABLE:
                counter = varCount;
                name = "VAR";
                varCount++;
                break;
        }
        result = new Label(name, counter, type);
        return result;
    }
}
