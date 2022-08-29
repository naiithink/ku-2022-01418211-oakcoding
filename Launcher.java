/**
 * OakCoding's Application Launcher
 * 
 * References:
 *  -# ClassLoader
 *      - <https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/ClassLoader.html>
 *  -# Names
 *      2.1 Any class name provided as a String parameter to methods in ClassLoader must be a binary name
 *          as defined by The Java Language Specification.
 *      2.2 Any package name provided as a String parameter to methods in ClassLoader must be either the empty string
 *          (denoting an unnamed package) or a fully qualified name as defined by The Java Language Specification.
 *      - Fully Qualified Names and Canonical Names <https://docs.oracle.com/javase/specs/jls/se17/html/jls-6.html#jls-6.7>
 *      - The Form of a Binary <https://docs.oracle.com/javase/specs/jls/se17/html/jls-13.html#jls-13.1>
 */

package oakcoding;

import java.io.File;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.List;

class Launcher {

    static final String PROGRAM_NAME;

    static final String USAGE_MSG;

    static {
        PROGRAM_NAME = Launcher.class.getSimpleName();

        USAGE_MSG = String.format("""
            Usage:
                java %s.java <option [option arg]>

            Options:
                help                print this help text
                launch              launch the OakCoding application
                path <PATH>         launch OakCoding application located at PATH""", PROGRAM_NAME);
    }

    public static void main(String[] args) {
        /**
         * getopt -- command line arguments
         */
        if (args.length < 1) {
            System.err.format("""
                ERROR: This program requires exactly one argument
                %s
                """, USAGE_MSG);
                            System.exit(1);
        }

        final String OPT_OPT = args[0];

        final String OPT_ARG = args[1];

        final File APP_PATH;

        switch (OPT_OPT) {
            case "path":
                if (OPT_ARG == null) {
                    System.err.println("ERROR: path is null");
                    System.exit(1);
                }

                APP_PATH = File(OPT_ARG);
                if (APP_PATH.isFile() == false) {
                    System.err.println("ERROR: file does not exist");
                    System.exit(1);
                }

                

                break;
            case "launch":
                System.out.println("Launching...");
                System.exit(0);
                break;
            case "help":
                System.out.println(USAGE_MSG);
                System.exit(0);
                break;
            default:
                System.err.format("%s: unrecognized option '%s'\n", PROGRAM_NAME, OPT_OPT);
                System.err.println(USAGE_MSG);
                System.exit(1);
        }

        /**
         * Get and validate JRE version
         * 
         * Terminate if JRE version != 17.*.*
         */
        final Runtime.Version RUNTIME_VERSION_INSTANCE  = Runtime.version();
        final List<Integer> RUNTIME_VERSION             = RUNTIME_VERSION_INSTANCE.version();
        final String RUNTIME_VERSION_STRING             = RUNTIME_VERSION.get(0)
                                                          + "." + RUNTIME_VERSION.get(1)
                                                          + "." + RUNTIME_VERSION.get(2);

        if (RUNTIME_VERSION.get(0) != 17) {
            System.err.format("ERROR: Unsupported runtime version (%s)\n", RUNTIME_VERSION_STRING);
            System.exit(1);
        } else {
            System.out.format("INFO: Runtime version %s\n", RUNTIME_VERSION_STRING);
        }

        /**
         * Get current file system info
         */
        // final Path START_PATH = java.
        FileSystem fs = FileSystems.getDefault();
        Path cwd = fs.getPath(".");

        System.out.println(cwd.toString());
    }
}

/**
 * Exception generator
 */
class RunException extends Exception {

    private static RunException instance;

    private RunException() {}

    static RunException getInstance() {
        if (instance == null) {
            synchronized (RunException.class) {
                if (instance == null) {
                    instance = new RunException();
                }
            }
        }

        return instance;
    }
}

/**
 * OakCoding's module loader
 * System ClassLoader
 */
class OakLoader extends ClassLoader {


}
