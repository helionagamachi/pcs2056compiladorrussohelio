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
        result = new Label("Const", constCount, type);
        switch (type) {
            case CONST:
                constCount ++;
                break;
            case NORMAL:
                normalCount++;
                break;
            case VARIABLE:
                varCount++;
                break;
        }
        return result;
    }
}
