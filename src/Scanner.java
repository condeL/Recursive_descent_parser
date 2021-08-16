import java.util.Arrays;
import java.util.StringTokenizer;

public class Scanner{

    // the symbols that the scanner should stop at
    // "{}" are used for the comments and "\n\r" are used for newlines
    private String[] symbols = {"+", "-", "*", "/", "=", "<", "(", ")", ";", ":", "{", "}", "\n", "\r"};
    private StringTokenizer tokenizer;

    // to keep track of the lines
    private int line = 1;
    // to check whether it is in the middle of a comment
    private boolean comment = false;

    public Scanner(String input) {
        tokenizer = new StringTokenizer(input, Arrays.toString(symbols), true);
    }

    //the method to retrieve the next token in the scanner
    public String nextToken(){
        String token = "";
        if (tokenizer.hasMoreTokens()) {
            while (token.isBlank()) { // ignore all whitespace
                if (token.contentEquals("\n"))
                    line++;
                if (tokenizer.hasMoreTokens())
                    token = tokenizer.nextToken();
                else {
                    token = "";
                    break;
                }
            }
            if (token.contentEquals("{")) {
                comment = true;
                while (!token.contentEquals("}")) {// skip everything until closing bracket
                    if (token.contentEquals("\n"))
                        line++;
                    if (tokenizer.hasMoreTokens())
                        token = tokenizer.nextToken();
                    else {
                        token = "";
                        return token;
                    }
                }
                comment = false;
                return nextToken(); //recursively get next token after the comment
            }
            if (!token.isBlank()) {
                return token;
            }
        }
        return token;
    }

    public int getLine() {
        return line;
    }

    public boolean isComment() {
        return comment;
    }

}