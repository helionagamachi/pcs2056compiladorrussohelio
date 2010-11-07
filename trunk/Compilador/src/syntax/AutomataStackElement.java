package syntax;

/**
 *
 * @author helionagamachi
 */
public class AutomataStackElement {

    private AutomataStackElement nextElement;
    private AutomataStackElement previousElement;
    private int automataNumber;
    private int stateNumber;

    /**
     * 
     * @param automataNumber
     * @param stateNumber
     */
    public AutomataStackElement(int automataNumber, int stateNumber) {
        this.automataNumber = automataNumber;
        this.stateNumber = stateNumber;
    }

    public int getAutomataNumber() {
        return automataNumber;
    }

    public int getStateNumber() {
        return stateNumber;
    }


    public AutomataStackElement getNextElement() {
        return nextElement;
    }

    public void setNextElement(AutomataStackElement nextElement) {
        this.nextElement = nextElement;
    }

    public AutomataStackElement getPreviousElement() {
        return previousElement;
    }

    public void setPreviousElement(AutomataStackElement previousElement) {
        this.previousElement = previousElement;
    }


}
