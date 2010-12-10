package semantic;

import lex.Token;

public class TokensList {

    private Element first;
    private int count;

    public TokensList() {
        count = 0;
    }

    public int getCount() {
        return count;
    }

    /**
     * Adds a token to the list
     * @param token the token to be added.
     */
    public void addToken(Token token) {
        if (count == 0) {
            first = new Element(token);
        } else {
            int index = 1;
            Element last = first;
            while (index < count) {
                last = last.getNext();
                index++;
            }
            last.setNext(new Element(token));
        }
        count++;
    }


    @Override
    public String toString(){
        String result = "";
        if(count == 0){
            result = "Token list is empty";
        }else{
            int index = 0;
            result = "Token List:\n";
            Element element = first;
            while(index < count){
                result += element.token.toString();
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
        System.out.println("Tokens list cleared");
        count = 0;
        first = null;
    }

    /**
     * Gets the list as an array.
     * @return
     */
    public Token[] getArray() {
        Token[] result = new Token[count];
        int index = 0;
        Element element = first;
        while (index < count) {
            result[index] = element.getToken();
            element = element.getNext();
            index++;
        }
        return result;
    }

    private class Element {

        private Element next;
        private Token token;

        public Element(Token token) {
            this.token = token;
        }

        public Element getNext() {
            return next;
        }

        public void setNext(Element next) {
            this.next = next;
        }

        public Token getToken() {
            return token;
        }
    }
}
