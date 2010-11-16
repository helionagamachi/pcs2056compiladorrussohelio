/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package syntax;

/**
 * This class takes cares on analyzing
 * a code file syntax.
 * 
 * @author helionagamachi
 */
public class Analyzer {

    private lex.Analyzer lexical;
    private StructedAutomata structedAutomata;
    private final String[] files = {"programa" , "laco"};

    public Analyzer() {
        lexical = lex.Analyzer.getInstance();
        structedAutomata = new  StructedAutomata(17);
        structedAutomata.init(getFilePaths());
        
    }


    /**
     * Gets the paths to the files, using the files vector, the files vector should have the names of the files
     * @return array containing the paths
     */
    private String[] getFilePaths(){
        String[] result = new String[files.length];
        int index = 0;
        while(index < files.length){
            System.out.println("/syntax/config/" + files[index]);
            result[index] = Analyzer.class.getResource("/syntax/config/" + files[index]).getFile();
            index ++;
        }
        return result;
    }

    //TODO : CODE GENERATION....
}
