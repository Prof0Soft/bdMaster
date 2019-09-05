package utils;

/**
 * Work with JVM memory (only read).
 * Project: importBaseFoxPro
 *
 * @author Sergey B. (Prof0Soft@gmail.com) on 05.04.2019
 */
public final class MemoryUtils {
    /**
     * Default constructor.
     */
    private MemoryUtils() {
    }

    /**
     * Get description of using memory.
     *
     * @return string about using memory.
     */
    public static String getMemory() {
        return "Memory total/use/free >>> "
                + Runtime.getRuntime().totalMemory() / 1048576 + "M / "
                + (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576 + "M / "
                + Runtime.getRuntime().freeMemory() / 1048576 + "M";
    }
}
