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
    
    private AutomataStackElement top;
    private static AutomataStack instance;


    /**
     * Puts an element on the top of the stack.
     * @param element the element to be pushed
     */
    public void push(AutomataStackElement element){
        if(top != null){
            top.setNextElement(element);
            element.setPreviousElement(top);
        }
        top = element;
    }

    /**
     * Pops the element from the stack. Updates the stack so the top has
     * the previous element indicated by the present top.
     * @return the element on the top of stack
     */
    public AutomataStackElement pop(){
        if(top == null){
            return null;
        }
        top = top.getPreviousElement();
        return top.getNextElement();
    }

    private AutomataStack() {
        LOGGER.debug("AutomataStack instance created");
        top = null;
    }

    public AutomataStack getInstance(){
       LOGGER.debug("AutomataStack instance required");
       if(instance == null){
           instance = new AutomataStack();
       }
       return instance;
    }

    /**
     * Indicates if the stack is empty or not.
     * @return true if empty
     */
    public boolean isEmpty(){
        return top == null;
    }
}
