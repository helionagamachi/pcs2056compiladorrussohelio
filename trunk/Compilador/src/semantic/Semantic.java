/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package semantic;

import labels.LabelStack;
import labels.Label;
import codeGeneration.Coder;
import labels.LabelList;
import labels.LabelManager;
import labels.LabelType;
import utils.SymbolType;
import utils.SymbolLine;
import lex.Token;
import lex.TokenType;
import org.apache.log4j.Logger;
import utils.CompilerException;
import utils.SymbolTable;
import static utils.ArraysUtils.getReservedWordByIndex;
import static utils.ArraysUtils.getReservedWordIndex;

/**
 * This class will gives the support to all code that is related with
 * the semantics of the compiler
 * @author helionagamachi
 */
public class Semantic {

    protected static TokensList tokenList = new TokensList();
    private Token latestToken;
    public static SymbolTable rootTable = new SymbolTable();
    public static SymbolTable latestTable;
    private static final Logger LOGGER = Logger.getLogger(Semantic.class);
    private static Coder coder;
    private LabelManager labelMan = new LabelManager();
    private static LabelList constLabelList = new LabelList();
    private static LabelList varLabelList = new LabelList();
    //to expression control
    private TokenStack expressionOperators = new TokenStack();
    private LabelStack expressionOperands = new LabelStack();
    // this will be used to identify the situation with the labels of an expression,
    // so that i know how to react to them.

    enum expression_cases {

        TWO_CONSTANTS, VAR_AND_CONSTANT, CONSTANT_AND_VAR, TWO_VARS, ONE_VAR, ONE_CONSTANT
    };
    private String loadString;
    final Label zero = new Label("ZERO", 0, LabelType.CONST);
    final Label temp1 = new Label("TEMP", 1, LabelType.CONST);
    final Label temp2 = new Label("TEMP", 2, LabelType.CONST);
    final Label temp3 = new Label("TEMP", 3, LabelType.CONST);

    public static void initCoder() {
        coder = Coder.getInstance();
    }

    public Semantic() {
        System.out.println("Semantic Constructor called");
        if (latestTable == null) {
            latestTable = rootTable;
        }
    }

    /**
     * Adds a token to the list, this list will be used by the actions to do stuff.
     * @param token
     */
    public void addToken(Token token) {
        tokenList.addToken(token);
        latestToken = token;
    }

    public void runAction(String name) throws CompilerException {
        LOGGER.debug("Asked to run the action " + name);
        if (name.equals("fim_import")) {
            fimImport();
        } else if (name.equals("new_context")) {
            newContext();
        } else if (name.equals("new_func")) {
            newFunc();
        } else if (name.equals("new_var")) {
            newVar();
        } else if (name.endsWith("fim_vars")) {
            fimVar();
        } else if (name.equals("push_term")) {
            stackOperand();
        } else if (name.equals("push_op")) {
            stackOperator();
        } else if (name.equals("end_block_code")) {
            releaseVarsAndConstLabels();
        } else {
            LOGGER.warn("Action " + name + " not defined !");
        }

    }

    public void runFinalAction(int machineNumber) {
        //When the machine will return to another one, it can execute a final action;
        System.out.println("Running the final action to the machine " + machineNumber);

        Token RW_SUM = getReservedWordToken("+");
        Token RW_OPEN_BRACKETS = getReservedWordToken("(");
        Token RW_MINUS = getReservedWordToken("-");
        Token RW_DIV = getReservedWordToken("/");
        switch (machineNumber) {
            case 0:
                //PROGRAM;
                break;

            case 1:
                //Code block
                break;
            case 2:

                // <editor-fold defaultstate="collapsed" desc=" Expression ">

                if (!expressionOperators.isEmpty()) {
                    boolean stop = expressionOperators.isEmpty();
                    Token operator;
                    Label second, first;

                    System.out.println("when calling the final action" + expressionOperands);
                    System.out.println("when calling the final action" + expressionOperators);
                    operator = expressionOperators.pop();
                    second = expressionOperands.pop();
                    first = expressionOperands.pop();

                    while (!stop) {
                        expression_cases exp_case = getLoadString(first, second);
                        String result = loadString;
                        Label toUse = null;
                        switch (exp_case) {
                            case TWO_CONSTANTS:
                                toUse = second;
                                break;
                            case VAR_AND_CONSTANT:
                                toUse = second;
                                break;
                            case CONSTANT_AND_VAR:
                                toUse = first;
                                break;
                            case TWO_VARS:
                                toUse = temp2;
                                break;
                        }
                        Token otherTop = expressionOperators.peek();
                        if (operator.compatible(RW_MINUS) && RW_MINUS.compatible(otherTop)) {
                            result += "+" + toUse + "\n";
                        } else if (operator.compatible(RW_DIV) && RW_DIV.compatible(otherTop)) {
                            result += "*" + toUse + "\n";
                        } else if (isBooleanOperator(operator)) {
                            result += getBooleanOpExecutionCode(first, second, operator, exp_case);
                        } else if (!expressionOperators.isEmpty()) {
                            result += getReservedWordByIndex(operator.getValue()) + toUse + "\n";
                        }
                        if (expressionOperators.isEmpty()) {
                            // it is the case that got one var / const ?
                            if (exp_case == expression_cases.ONE_CONSTANT || exp_case == expression_cases.ONE_VAR) {
                                if (operator.compatible(RW_MINUS)) {
                                    result += "MM" + temp2 + "\n";
                                    result += "LD" + zero + "\n";
                                    result += "-" + temp2 + "\n";
                                }
                            } else {
                                //The ,the last operation should be written, if not a boolean operator
                                if (!isBooleanOperator(operator)) {
                                    result += getReservedWordByIndex(operator.getValue()) + toUse + "\n";
                                }
                            }
                            stop = true;
                            Label endLabel = labelMan.getLabel(LabelType.CONST);
                            expressionOperands.push(endLabel);
                            constLabelList.addLabel(endLabel);
                            result += "MM " + endLabel + "\n";
                        } else {
                            operator = expressionOperators.pop();
                            if (operator.compatible(RW_OPEN_BRACKETS)) {
                                stop = true;
                                Label endLabel = labelMan.getLabel(LabelType.CONST);
                                expressionOperands.push(endLabel);
                                constLabelList.addLabel(endLabel);
                                result += "MM " + endLabel + "\n";
                            } else {
                                result += "MM " + temp3 + "\n";
                                expressionOperands.push(temp3);
                                second = expressionOperands.pop();
                                first = expressionOperands.pop();
                            }
                        }
                        coder.putOnBuffer(result, false);
                    }

                    System.out.println("after all " + expressionOperands);
                    System.out.println("after all " + expressionOperators);
                } else {
                    // there is a var or constant hanging out..
                }

// </editor-fold>
                break;

        }
    }

    /**
     * Imports only causes the token list to be cleared
     */
    private void fimImport() {
        tokenList.clear();
    }

    private void newContext() {
        tokenList.clear();
        //TODO : SYMBOL TABLE.
    }

    private void newFunc() {
        tokenList.clear();
    }

    // <editor-fold desc=" Function, methods actions">
    /**
     * Put in the file the vars and Constants labels.
     * They should go before any code.
     * @throws CompilerException
     */
    private void releaseVarsAndConstLabels() throws CompilerException {
        String result = "";
        int index = 0;
        Label[] labels;
        System.out.println(constLabelList);
        labels = constLabelList.getArray();
        while (index < labels.length) {
            Label label = labels[index];
            result = result + label + " K =" + label.getNumericalData() + " \n";
            index++;
        }
        constLabelList.clear();
        index = 0;
        labels = varLabelList.getArray();
        int offSet = 0;
        while (index < labels.length) {
            Label label = labels[index];
            result = result + label + " K =" + offSet + " \n";
            index++;
            offSet = offSet + label.getNumericalData();
        }
        varLabelList.clear();
        coder.putOnBuffer(result, true);
    }
    // </editor-fold>

    // <editor-fold  desc=" Expression actions ">
    /**
     * The expression will have the operator stacked
     */
    private void stackOperator() {
        // only valid operators will be here...
        Token RW_SUM = getReservedWordToken("+");
        Token RW_MINUS = getReservedWordToken("-");
        Token RW_MUL = getReservedWordToken("*");
        Token RW_DIV = getReservedWordToken("/");
        Token RW_OPEN_BRACKET = getReservedWordToken("(");
        Token top = expressionOperators.peek();
        if (latestToken.compatible(RW_SUM)) {
            //SUM is less priotary than minus, mul and div.
            String code = "";
            while (!(latestToken.compatible(top) || RW_OPEN_BRACKET.compatible(top) || top == null)) {
                // it is a minus, or a mul , or a div...
                // pops the top
                top = expressionOperators.pop();
                Label first, second;
                Label toUse;
                second = expressionOperands.pop();
                first = expressionOperands.pop();
                expression_cases expCase = getLoadString(first, second);
                code = loadString;
                switch (expCase) {
                    case TWO_CONSTANTS:
                        toUse = second;
                        break;
                    case VAR_AND_CONSTANT:
                        toUse = second;
                        break;
                    case CONSTANT_AND_VAR:
                        toUse = first;
                        break;
                    case TWO_VARS:
                        toUse = temp2;
                        break;
                    case ONE_VAR:
                        toUse = temp2;
                        break;
                    case ONE_CONSTANT:
                        toUse = second;
                        break;
                    default:
                        toUse = second;
                        break;
                }
                if (top.compatible(RW_MINUS) && (expCase == expression_cases.ONE_CONSTANT || expCase == expression_cases.ONE_VAR)) {
                    code += "LD" + zero + "\n";
                    code += "-" + toUse + "\n";
                }
                if (top.compatible(RW_MINUS) && RW_MINUS.compatible(expressionOperators.peek())) {
                    // the top is a minus, check if before it there is a minus too..
                    code += "+" + toUse + "\n";
                } else {
                    code += getReservedWordByIndex(top.getValue()) + toUse + "\n";
                }
                Label temp = labelMan.getLabel(LabelType.CONST);
                code += "MM" + temp + "\n";
                constLabelList.addLabel(temp);
                expressionOperands.push(temp);
                top = expressionOperators.peek();
            }
            coder.putOnBuffer(code, false);

        }
        if (latestToken.compatible(RW_MINUS)) {
            String code = "";
            // minus is less prioritary than mul and div.
            while (!(latestToken.compatible(top) || top == null || RW_OPEN_BRACKET.compatible(top) || RW_SUM.compatible(top))) {
                // it is  a mul , or a div...
                // pops the top
                top = expressionOperators.pop();
                Label first, second;
                Label toUse;
                second = expressionOperands.pop();
                first = expressionOperands.pop();
                expression_cases expCase = getLoadString(first, second);
                code = loadString;
                switch (expCase) {
                    case TWO_CONSTANTS:
                        toUse = second;
                        break;
                    case VAR_AND_CONSTANT:
                        toUse = first;
                        break;
                    case CONSTANT_AND_VAR:
                        toUse = second;
                        break;
                    case TWO_VARS:
                        toUse = temp2;
                        break;
                    default:
                        toUse = second;
                        break;
                }
                code += getReservedWordByIndex(top.getValue()) + toUse + "\n";
                Label temp = labelMan.getLabel(LabelType.CONST);
                code += "MM" + temp + "\n";
                constLabelList.addLabel(temp);
                expressionOperands.push(temp);
                coder.putOnBuffer(code, false);
                top = expressionOperators.peek();

            }
        }
        // in the end the operator will be pushed into the stack.
        expressionOperators.push(latestToken);
    }

    /**
     * The expression will have the operand stacked
     */
    private void stackOperand() throws CompilerException {
        TokenType tokenType;
        tokenType = latestToken.getType();
        Label label;
        // it is a variable or an integer / char / bool ?
        if (tokenType != TokenType.IDENTIFIER) {
            // Creates a label for the constant, and adds it.
            // bool , char and int will get the value in a direct way.
            int value;
            if (tokenType == TokenType.RESERVED_WORD) {
                int tokenValue = latestToken.getValue();
                if (tokenValue == getReservedWordIndex("true")) {
                    value = 1;
                } else {
                    value = 0;
                }
            } else {
                value = latestToken.getValue();
            }
            label = labelMan.getLabel(LabelType.CONST);
            label.setNumericalData(value);
            constLabelList.addLabel(label);
        } else {
            // Checking if the variable has been declared.
            SymbolType symbolType;
            SymbolLine line = latestTable.getLine(latestToken.getValue());
            symbolType = line.getType();
            System.out.println(symbolType);
            if (symbolType == SymbolType.UNKOWN) {
                throw (new CompilerException("The variable " + line.getSymbol() + " hasn't been declared."));
            } // its ok , there is a label for the variable, right?
            label = line.getLabel();
        }
        expressionOperands.push(label);

    }

    /**
     * Gets a string that has the instructions for the load of two labels.
     * @param first the first label
     * @param second the second label
     * @return the type of the two labels, so i know how to use them after...
     */
    private expression_cases getLoadString(Label first, Label second) {
        System.out.println("load string for the labels " + first + second);
        LabelType firstType, secondType;
        // There are chances that the first is null, in case of  c =  ! true
        // or in case like a=  - b
        secondType = second.getType();
        if (first == null) {
            if (secondType == LabelType.CONST) {
                System.out.println("Nothing to add on the string...");
                loadString = "";
                return expression_cases.ONE_CONSTANT;
            } else {
                loadString = "LD " + second + "\n";
                loadString += "MM VAR" + "\n";
                loadString += "SC LDVAR" + "\n";
//                loadString += "MM " + temp2 + "\n";
                return expression_cases.ONE_VAR;
            }
        }
        firstType = first.getType();
        if (firstType == LabelType.CONST && secondType == LabelType.CONST) {
            loadString = "LD " + first + "\n";
            return expression_cases.TWO_CONSTANTS;
        } else if (firstType == LabelType.CONST && secondType == LabelType.VARIABLE) {
            loadString = "LD " + second + "\n";
            loadString += "MM VAR " + "\n";
            loadString += "SC LDVAR " + "\n";
            return expression_cases.CONSTANT_AND_VAR;

        } else if (firstType == LabelType.VARIABLE && secondType == LabelType.CONST) {
            loadString = "LD " + first + "\n";
            loadString += "MM VAR " + "\n";
            loadString += "SC LDVAR " + "\n";
            return expression_cases.VAR_AND_CONSTANT;

        } else {
            loadString = "LD " + first + "\n";
            loadString += "MM VAR" + "\n";
            loadString += "SC LDVAR" + "\n";
            loadString += "MM " + temp1 + "\n";
            loadString += "LD " + second + "\n";
            loadString += "MM VAR" + "\n";
            loadString += "SC LDVAR" + "\n";
            loadString += "MM " + temp2 + "\n";
            loadString += "LD " + temp1 + "\n";
            return expression_cases.TWO_VARS;
        }
    }
// </editor-fold>

    // <editor-fold  desc="Vars actions...">
    /**
     * Takes care when a new var is declared.
     * @throws CompilerException
     */
    private void newVar() throws CompilerException {
        Token[] array;
        array = Semantic.tokenList.getArray();
        SymbolType symbolType = SymbolType.UNKOWN;
        Token RW_INT = new Token(TokenType.RESERVED_WORD, getReservedWordIndex("int"));
        Token RW_STRING = new Token(TokenType.RESERVED_WORD, getReservedWordIndex("String"));
        Token RW_CHAR = new Token(TokenType.RESERVED_WORD, getReservedWordIndex("char"));
        Token RW_BOOL = new Token(TokenType.RESERVED_WORD, getReservedWordIndex("bool"));
        Token type = array[0];
        int numericalData = 0;
        if (type.getType() == TokenType.RESERVED_WORD) {
            System.out.println("new var is the type of " + type);
            // checks if it is one of the above, if not, error should happen...
            numericalData = 2;
            boolean ok = false;
            if (RW_INT.compatible(type)) {
                ok = true;
                symbolType = SymbolType.INT;
            } else if (RW_STRING.compatible(type)) {
                ok = true;
                symbolType = SymbolType.STRING;
                //Strings will have a maximum of 20 chars, the 2 last are the terminator char...
                numericalData = 22;
            } else if (RW_CHAR.compatible(type)) {
                ok = true;
                symbolType = SymbolType.CHAR;

            } else if (RW_BOOL.compatible(type)) {
                ok = true;
                symbolType = symbolType.BOOL;
            }
            if (!ok) {
                String errorMessage = "Unexpected reserved word , " + getReservedWordByIndex(type.getValue());
                errorMessage = errorMessage + " on line " + type.getLine();
                throw new CompilerException(errorMessage);
            }
        } else if (type.getType() == TokenType.IDENTIFIER) {
            // User type var...
            String errorMessage = "User types not supported yet.";
            errorMessage = errorMessage + " on line " + type.getLine();
            errorMessage = errorMessage + " the type was " + latestTable.getLine(type.getValue()).getSymbol();
            throw new CompilerException(errorMessage);
        }
        int index = latestToken.getValue();
        SymbolLine line;
        line = Semantic.latestTable.getLine(index);
        line.setType(symbolType);
        Label label = labelMan.getLabel(LabelType.VARIABLE);
        label.setNumericalData(numericalData);
        line.setLabel(label);
        varLabelList.addLabel(label);
    }

    /**
     * When the new Var is done...
     */
    private void fimVar() {
        tokenList.clear();
    }
    // </editor-fold>

// <editor-fold  desc=" SUPPORT , Boolean expressions ">
    private boolean isBooleanOperator(Token operator) {
        TokensList booleanOperators = new TokensList();
        booleanOperators.addToken(getReservedWordToken("=="));
        booleanOperators.addToken(getReservedWordToken("<="));
        booleanOperators.addToken(getReservedWordToken(">="));
        booleanOperators.addToken(getReservedWordToken(">"));
        booleanOperators.addToken(getReservedWordToken("<"));
        booleanOperators.addToken(getReservedWordToken("&&"));
        booleanOperators.addToken(getReservedWordToken("||"));
        booleanOperators.addToken(getReservedWordToken("!"));
        booleanOperators.addToken(getReservedWordToken("!="));
        boolean result = false;
        Token[] array = booleanOperators.getArray();
        int index = 0;
        while (index < array.length) {
            if (array[index].compatible(operator)) {
                result = true;
                index = array.length;
            }
            index++;
        }
        return result;
    }

    private String getBooleanOpExecutionCode(Label first, Label second, Token operator, expression_cases exp_case) {
        String result = "";
        Token RW_equals = getReservedWordToken("==");
        Token RW_dif = getReservedWordToken("!");
        Token RW_lesser = getReservedWordToken("<");
        Token RW_greater = getReservedWordToken(">");
        Token RW_not = getReservedWordToken("!");
        Token RW_lesser_equal = getReservedWordToken("<=");
        Token RW_greater_equal = getReservedWordToken(">=");
        Token RW_or = getReservedWordToken("||");
        Token RW_and = getReservedWordToken("&&");
        //TODO : continue from here
        switch (exp_case) {
            case TWO_CONSTANTS:
                result += "MM" + temp1 + "\n";
                result += "LD" + second + "\n";
                result += "MM" + temp2 + "\n";
                break;
            case CONSTANT_AND_VAR:
                result += "MM" + temp2 + "\n";
                result += "LD" + first + "\n";
                result += "MM" + temp1 + "\n";
                break;
            case VAR_AND_CONSTANT:
                result += "MM" + temp1 + "\n";
                result += "LD" + second + "\n";
                result += "MM" + temp2 + "\n";
                break;
            case ONE_CONSTANT:
                result += "LD" + second + "\n";
                result += "MM" + temp1 + "\n";
                break;
            case ONE_VAR:
                result += "MM" + temp1 + "\n";
                break;
        }
        if (RW_equals.compatible(operator)) {
            result += "SC IGUAL\n";
        } else if (RW_dif.compatible(operator)) {
            result += "SC DIF\n";
        } else if (RW_and.compatible(operator)) {
            result += "SC AND\n";
        } else if (RW_or.compatible(operator)) {
            result += "SC OU\n";
        } else if (RW_lesser.compatible(operator)) {
            result += "SC MENOR\n";
        } else if (RW_lesser_equal.compatible(operator)) {
            result += "SC MENORIG\n";
        } else if (RW_greater.compatible(operator)) {
            result += "SC MAIOR\n";
        } else if (RW_greater_equal.compatible(operator)) {
            result += "SC MAIORIG\n";
        } else if (RW_not.compatible(operator)) {
            result += "SC NOT\n";
        }

        return result;
    }

// </editor-fold>
    public Token getReservedWordToken(String reservedWord) {
        return new Token(TokenType.RESERVED_WORD, getReservedWordIndex(reservedWord));
    }
}
