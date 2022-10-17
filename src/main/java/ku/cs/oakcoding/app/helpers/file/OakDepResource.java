package ku.cs.oakcoding.app.helpers.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;

import ku.cs.oakcoding.app.helpers.configurations.OakAppDefaults;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;

public class OakDepResource {

    // private static final Path DEP_DIR_PREFIX = OakResourcePrefix.getDataDirPrefix().resolve(OakAppDefaults.APP_DEP_DIR.value());

    private static final Path ORG_DIR_PREFIX = OakResourcePrefix.getDataDirPrefix().resolve(OakAppDefaults.APP_ORG_DIR.value());

    private static final Path DEFAULT_DEP_DIR_TEMPLATE = OakResourcePrefix.getPrefix().resolve(OakAppDefaults.APP_DEFAULT_DEP_INFO_TEMPLATE.value());

    public static Path getOrgDirOf(String orgName, String depName) {
        return ORG_DIR_PREFIX.resolve(orgName).resolve(depName);
    }

    public static boolean newOrgDirectory(String orgName, String depName) {
        if (Files.exists(ORG_DIR_PREFIX.resolve(orgName).resolve(depName))) {
            OakLogger.log(Level.SEVERE, "Department directory is already exist");
            return false;
        }

        try {
            Files.createDirectories(ORG_DIR_PREFIX.resolve(orgName).resolve(depName));
            // OakResource.recursiveCopyDirectory(DEFAULT_DEP_DIR_TEMPLATE, DEP_DIR_PREFIX.resolve(depName));
            Files.copy(DEFAULT_DEP_DIR_TEMPLATE, ORG_DIR_PREFIX.resolve(orgName).resolve(depName).resolve(OakAppDefaults.APP_USER_INFO.value()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            OakLogger.log(Level.SEVERE, "Got 'IOException' while attempting to create new department directory");
            return false;
        }

        return true;
    }

    public static boolean removeOrgDirectory(String orgName, String depName) {
        if (!Files.exists(ORG_DIR_PREFIX.resolve(orgName).resolve(depName))) {
            OakLogger.log(Level.SEVERE, "Department directory does not exist");
            return false;
        }

        try {
            OakDirEditor.deleteDirectory(ORG_DIR_PREFIX.resolve(orgName));
        } catch (IOException e) {
            OakLogger.log(Level.SEVERE, "Got 'IOException' while attempting to remove department directory");
            return false;
        }

        return true;
    }

    public static String renameDepDirectory(String oldName, String newName) {
        Path depDir = ORG_DIR_PREFIX.resolve(oldName);

        try {
            Files.move(depDir, depDir.resolveSibling(newName));
        } catch (IOException e) {
            OakLogger.log(Level.SEVERE, "Got 'IOException' while attempting to rename directory");
            return null;
        }

        return depDir.toString();
    }

    public static void main(String[] args) {
        OakDepResource.newOrgDirectory("ACME", "ahiahi");
    }
}
