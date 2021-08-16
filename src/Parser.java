import java.util.regex.Pattern;

public class Parser {

    private Scanner scanner;
    private String token;

    // the regex patterns for numbers and identifiers
    private Pattern number = Pattern.compile("[0-9]+");
    private Pattern identifier = Pattern.compile("[a-zA-Z]+");

    public Parser(String input) {
        scanner = new Scanner(input);
    }

    void start(){
        program();
    }

    //to output the proper error message and close the program
    void error(String error, String valid){
        int line = scanner.getLine();
        System.out.println("ERROR at line " + line + ": Found \"" + error + "\", \"" + valid + "\" expected.");
        System.exit(1);
    }

    void program(){
        token = scanner.nextToken();
        stmtSequence();
        if(token.isEmpty() && !scanner.isComment())
            System.out.println("Syntax analysis performed successfully and no errors were found!");
        else if (scanner.isComment())
            error(token, "}");
        else
            error(token, ";");
    }

    void stmtSequence(){
        statement();
        while(token.contentEquals(";")){
            token = scanner.nextToken();
            statement();
        }
    }

    void statement(){
        if(identifier.matcher(token).matches()) {
            switch (token) {
                case "if":
                    ifStatement();
                    break;
                case "repeat":
                    repeatStatement();
                    break;
                case "read":
                    readStatement();
                    break;
                case "write":
                    writeStatement();
                    break;
                default:
                    assignStatement();
                    break;
            }
        } else {
            error(token, "Statement");
        }
    }

    void ifStatement(){
        token = scanner.nextToken();
        exp();
        if(token.contentEquals("then")) {
            token = scanner.nextToken();
        } else
            error(token, "then");
        stmtSequence();

        if(token.contentEquals("else")){
            token = scanner.nextToken();
            stmtSequence();
        }
        if(token.contentEquals("end")) {
            token = scanner.nextToken();
        } else
            error(token, "end");
    }

    void repeatStatement(){
        token = scanner.nextToken();
        stmtSequence();
        if(token.contentEquals("until")) {
            token = scanner.nextToken();
        } else
            error(token, "until");
        exp();
    }

    void assignStatement(){
        token = scanner.nextToken();
        if(token.contentEquals(":")) {
            token = scanner.nextToken();
            if(token.contentEquals("=")) {
                token = scanner.nextToken();
            } else
                error(token, "=");
        } else
            error(token, ":=");
        exp();
    }

    void readStatement(){
        token = scanner.nextToken();
        if(identifier.matcher(token).matches()) {
            token = scanner.nextToken();
        } else
            error(token, "identifier");
    }

    void writeStatement(){
        token = scanner.nextToken();
        exp();
    }

    void exp(){
        simpleExp();
        if(comparisonOp()){
            token = scanner.nextToken();
            simpleExp();
        }
    }

    boolean comparisonOp(){
        switch (token){
            case "<":
            case "=":
                return true;
            default:
                return false;
        }
    }

    void simpleExp(){
        term();
        while(addop()) {
            token = scanner.nextToken();
            term();
        }
    }

    boolean addop(){
        switch (token){
            case "+":
            case "-":
                return true;
            default:
                return false;
        }
    }

    void term(){
        factor();
        while(mulop()) {
            token = scanner.nextToken();
            factor();
        }
    }

    boolean mulop(){
        switch (token){
            case "*":
            case "/":
                return true;
            default:
                return false;
        }
    }

    void factor() {
        if(identifier.matcher(token).matches()){
            token = scanner.nextToken();
        } else
            if(number.matcher(token).matches()){
                token = scanner.nextToken();
            } else
                if(token.contentEquals("(")){
                    token = scanner.nextToken();
                        exp();
                    if(token.contentEquals(")")){
                        token = scanner.nextToken();
                    } else
                        error(token, ")");
            } else error(token, "identifier, number or (");
    }
}