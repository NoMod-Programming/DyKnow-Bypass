import java.io.*;
import javax.swing.*;

class Echo extends FilterOutputStream {

    private final JTextArea text;

    public Echo(OutputStream out, JTextArea text) {
        super(out);
        if (text == null) throw new IllegalArgumentException("null text");
        this.text = text;
    }

    @Override
    public void write(int b) throws IOException {
        super.write(b);
        text.append(Character.toString((char) b));
        // scroll to end?
    }

    // overwrite the other write methods for better performance
}