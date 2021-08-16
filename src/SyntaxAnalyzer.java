import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class SyntaxAnalyzer {
    public static void main(String[] args) {

        if(args.length > 0) {
            String input;

            try {
                Path path = FileSystems.getDefault().getPath(args[0]);

                // Java 11+ convenience method
                input = Files.readString(path);

                Parser parser = new Parser(input);
                parser.start();

            } catch (IOException e) {
                System.out.println("FILE ERROR: File not found \"" + e.getMessage() + "\".");
            }
        } else
            System.out.println("FILE ERROR: No filename provided.");
    }
}
