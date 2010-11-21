package syntax;

import org.apache.log4j.Logger;

/**
 * This class represents the syntax analyzer 's stack.
 * should have only one instance, hence the single ton
 * pattern is used.
 * @author helionagamachi
 */
public class AutomataStack {

    private static Logger LOGGER = Logger.getLogger(AutomataStack.class);
    private Element top;
    private static AutomataStack instance;

    /**
     * Pushes data into the stack
     * @param automata the number of the automata
     * @param state the number of the state.
     */
    public void push(int automata, int state) {
        LOGGER.debug("Pushing into the stack : automata  " + automata + " state  " + state );
        Element element = new Element(this.top, automata, state);
        top = element;
    }

    /**
     * Pops the element from the stack.
     * @return an int array with 2 elements, the first one is the automata and
     * the second one is the state, returns an zero element array if there is no element
     * on the stack
     */
    public int[] pop() {
        int[] result;
        if (top == null) {
            result = new int[0];
        }else{
            result = new int[2];
            result[0] = top.getAutomata();
            result[1] = top.getState();
            top = top.getPrevious();
        }
        return result;
    }

    /**
     * Private constructor, for the single ton pattern
     */
    private AutomataStack() {
        LOGGER.debug("AutomataStack instance created");
        top = null;
    }

    /**
     * Here we get the instance of the stack.
     * @return the stack of the instance.
     */
    public static AutomataStack getInstance() {
        LOGGER.debug("AutomataStack instance required");
        if (instance == null) {
            instance = new AutomataStack();
        }
        return instance;
    }

    /**
     * Indicates if the stack is empty or not.
     * @return true if empty
     */
    public boolean isEmpty() {
        return top == null;
    }

    /**
     * The element of the stack is done by this class
     * holds the info on the automata number and state number
     */
    private class Element {

        private Element previous;
        private int automata;
        private int state;

        /**
         * Creates an Element
         * @param previous the previous element
         * @param Automata the number of the automata
         * @param State the number of the state.
         */
        public Element(Element previous, int Automata, int State) {
            this.previous = previous;
            this.automata = Automata;
            this.state = State;
        }

        public Element getPrevious() {
            return previous;
        }

        public int getAutomata() {
            return automata;
        }

        public int getState() {
            return state;
        }


    }
}
