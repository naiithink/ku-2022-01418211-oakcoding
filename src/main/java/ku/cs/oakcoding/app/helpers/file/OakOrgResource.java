package ku.cs.oakcoding.app.helpers.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;

import ku.cs.oakcoding.app.helpers.configurations.OakAppDefaults;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;

public class OakOrgResource {
    private OakOrgResource() {}

    private static final Path ORG_DIR_PREFIX = OakResourcePrefix.getDataDirPrefix().resolve(OakAppDefaults.APP_ORG_DIR.value());

    private static final Path DEFAULT_ORG_DIR_TEMPLATE = OakResourcePrefix.getPrefix().resolve(OakAppDefaults.APP_DEFAULT_ORG_INFO_TEMPLATE.value());

    public static Path getOrgDirOf(String orgName) {
        return ORG_DIR_PREFIX.resolve(orgName);
    }

    public static boolean newOrgDirectory(String orgName) {
        if (Files.exists(ORG_DIR_PREFIX.resolve(orgName))) {
            OakLogger.log(Level.SEVERE, "Organization directory is already exist");
            return false;
        }

        try {
            Files.createDirectories(ORG_DIR_PREFIX.resolve(orgName));
            // OakResource.recursiveCopyDirectory(DEFAULT_ORG_DIR_TEMPLATE, ORG_DIR_PREFIX.resolve(orgName));
            Files.copy(DEFAULT_ORG_DIR_TEMPLATE, ORG_DIR_PREFIX.resolve(orgName).resolve(OakAppDefaults.APP_USER_INFO.value()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            OakLogger.log(Level.SEVERE, "Got 'IOException' while attempting to create new organization directory");
            return false;
        }

        return true;
    }

    public static boolean removeOrgDirectory(String orgName) {
        if (!Files.exists(ORG_DIR_PREFIX.resolve(orgName))) {
            OakLogger.log(Level.SEVERE, "Organization directory does not exist");
            return false;
        }

        try {
            OakDirEditor.deleteDirectory(ORG_DIR_PREFIX.resolve(orgName));
        } catch (IOException e) {
            OakLogger.log(Level.SEVERE, "Got 'IOException' while attempting to remove organization directory");
            return false;
        }

        return true;
    }

    public static String renameOrgDirectory(String oldName, String newName) {
        Path orgDir = ORG_DIR_PREFIX.resolve(oldName);

        try {
            Files.move(orgDir, orgDir.resolveSibling(newName));
        } catch (IOException e) {
            OakLogger.log(Level.SEVERE, "Got 'IOException' while attempting to rename directory");
            return null;
        }

        return orgDir.toString();
    }

    public static void main(String[] args) {
        System.out.println(OakOrgResource.newOrgDirectory("ACME"));
        // System.out.println(OakOrgResource.removeOrgDirectory("ACME"));
    }
}
