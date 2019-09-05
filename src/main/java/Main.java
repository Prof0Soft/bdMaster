import convert.ConvertBase;
import convert.impl.ConvertBaseImpl;

/**
 * Main class.
 */
public final class Main {
    private Main() {
    }

    /**
     * Main point for start programm.
     *
     * @param args arguments for programm.
     */
    public static void main(final String[] args) {
        ConvertBase convertBase = new ConvertBaseImpl();
        convertBase.start();
    }
}
