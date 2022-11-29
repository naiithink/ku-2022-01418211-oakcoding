package ku.cs.oakcoding.app.services;

import java.io.FileNotFoundException;
import java.time.Instant;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import ku.cs.oakcoding.app.helpers.configurations.OakAppDefaults;
import ku.cs.oakcoding.app.helpers.file.OakResourcePrefix;
import ku.cs.oakcoding.app.helpers.file.OakWorkspaceResource;
import ku.cs.oakcoding.app.helpers.logging.OakLogger;
import ku.cs.oakcoding.app.models.org.Department;
import ku.cs.oakcoding.app.models.org.Departments;
import ku.cs.oakcoding.app.models.users.FullUserEntry;
import ku.cs.oakcoding.app.services.data_source.AutoUpdateCSV;

public class WorkspaceManager {

    private final ObservableMap<String, Department> departmentTable;

    private final AutoUpdateCSV departmentIndexFile;

    private final Map<String, AutoUpdateCSV> departmentInfoFiles;

    private final Map<String, AutoUpdateCSV> departmentMemberFiles;

    private final Map<String, AutoUpdateCSV> departmentTagFiles;

    public WorkspaceManager(AutoUpdateCSV departmentIndexFile) {
        this.departmentTable = FXCollections.observableHashMap();
        this.departmentIndexFile = departmentIndexFile;
        this.departmentInfoFiles = new HashMap<>();
        this.departmentMemberFiles = new HashMap<>();
        this.departmentTagFiles = new HashMap<>();

        Set<String> depKeys = this.departmentIndexFile.getPrimaryKeySet();

        for (String depKey : depKeys) {
            try {
                this.departmentInfoFiles.put(depKey, new AutoUpdateCSV("depInfo", "DEP_ID", "depInfo", OakResourcePrefix.getDataDirPrefix().resolve(OakAppDefaults.APP_DEP_DIR.value()).resolve(this.departmentIndexFile.getDataWhere(depKey, "DEP_NAME")).resolve(OakAppDefaults.APP_USER_INFO.value())));
            } catch (FileNotFoundException e) {
                OakLogger.log(Level.SEVERE, "Department info file not found for '" + this.departmentIndexFile.getDataWhere(depKey, "DEP_NAME") + "'");
                System.exit(1);
            }

            try {
                this.departmentMemberFiles.put(depKey, new AutoUpdateCSV("depMembers", "UID", "depMembers", OakResourcePrefix.getDataDirPrefix().resolve(OakAppDefaults.APP_DEP_DIR.value()).resolve(this.departmentIndexFile.getDataWhere(depKey, "DEP_NAME")).resolve(OakAppDefaults.APP_DEP_MEMBERS_FILE.value())));
            } catch (FileNotFoundException e) {
                OakLogger.log(Level.SEVERE, "Department member file not found for '" + this.departmentIndexFile.getDataWhere(depKey, "DEP_NAME") + "'");
                System.exit(1);
            }

            try {
                this.departmentTagFiles.put(depKey, new AutoUpdateCSV("depTags", "TAG", "depTags", OakResourcePrefix.getDataDirPrefix().resolve(OakAppDefaults.APP_DEP_DIR.value()).resolve(this.departmentIndexFile.getDataWhere(depKey, "DEP_NAME")).resolve(OakAppDefaults.APP_DEP_TAGS_FILE.value())));
            } catch (FileNotFoundException e) {
                OakLogger.log(Level.SEVERE, "Department tags file not found for '" + this.departmentIndexFile.getDataWhere(depKey, "DEP_NAME") + "'");
                System.exit(1);
            }

            Set<String> staffMembers = this.departmentMemberFiles.get(depKey).getPrimaryKeySet();
            Set<String> tags = this.departmentTagFiles.get(depKey).getPrimaryKeySet();

            this.departmentTable.put(depKey, Departments.restoreDepartment(depKey, this.departmentIndexFile.getDataWhere(depKey, "DEP_NAME"), this.departmentIndexFile.getDataWhere(depKey, "LEADER_STAFF"), staffMembers, tags));
        }
    }


    /**
     * @section Department
     */

    public ObservableSet<Department> getAllDepartmentsSet() {
        ObservableSet<Department> deps = FXCollections.observableSet();

        Set<String> depKeys = this.departmentTable.keySet();

        for (String depKey : depKeys) {
            deps.add(this.departmentTable.get(depKey));
        }

        return deps;
    }

    public Department newDepartment(String departmentName) {
        if (containsDepartmentWithName(departmentName)) {
            OakLogger.log(Level.SEVERE, "Department name already in use '" + departmentName + "'");
            return null;
        }

        Department dep = Departments.newDepartment(departmentName);
        this.departmentTable.put(dep.getDepartmentID(), dep);
        this.departmentIndexFile.addRecord(new String[] { dep.getDepartmentID(), dep.getDepartmentName(), "NIL" });
        OakWorkspaceResource.newDepartmentDirectory(departmentName);

        try {
            this.departmentInfoFiles.put(dep.getDepartmentID(), new AutoUpdateCSV("depInfo", "DEP_ID", "depInfo", OakResourcePrefix.getDataDirPrefix().resolve(OakAppDefaults.APP_DEP_DIR.value()).resolve(departmentName).resolve(OakAppDefaults.APP_USER_INFO.value())));
        } catch (FileNotFoundException e) {
            OakLogger.log(Level.SEVERE, "Department info file not found for '" + this.departmentIndexFile.getDataWhere(dep.getDepartmentID(), "DEP_NAME") + "'");
            System.exit(1);
        }

        this.departmentInfoFiles.get(dep.getDepartmentID()).addRecord(new String[] { dep.getDepartmentID(), dep.getDepartmentName() });

        try {
            this.departmentMemberFiles.put(dep.getDepartmentID(), new AutoUpdateCSV("depInfo", "UID", "depInfo", OakResourcePrefix.getDataDirPrefix().resolve(OakAppDefaults.APP_DEP_DIR.value()).resolve(departmentName).resolve(OakAppDefaults.APP_DEP_MEMBERS_FILE.value())));
        } catch (FileNotFoundException e) {
            OakLogger.log(Level.SEVERE, "Department member file not found for '" + this.departmentIndexFile.getDataWhere(dep.getDepartmentID(), "DEP_NAME") + "'");
            System.exit(1);
        }

        try {
            this.departmentTagFiles.put(dep.getDepartmentID(), new AutoUpdateCSV("depTags", "TAG", "depTags", OakResourcePrefix.getDataDirPrefix().resolve(OakAppDefaults.APP_DEP_DIR.value()).resolve(departmentName).resolve(OakAppDefaults.APP_DEP_TAGS_FILE.value())));
        } catch (FileNotFoundException e) {
            OakLogger.log(Level.SEVERE, "Department tags file not found for '" + this.departmentIndexFile.getDataWhere(dep.getDepartmentID(), "DEP_NAME") + "'");
            System.exit(1);
        }

        return dep;
    }

    public WorkspaceManagerStatus deleteDepartment(String departmentID) {
        if (!this.departmentTable.containsKey(departmentID))
            return WorkspaceManagerStatus.DEPARTMENT_NOT_FOUND;

        OakWorkspaceResource.removeDepartmentDirectory(getDepartmentNameOfID(departmentID));
        this.departmentIndexFile.removeRecordWhere(departmentID);
        this.departmentTable.remove(departmentID);

        return WorkspaceManagerStatus.SUCCESS;
    }

    public String getDepartmentNameOfID(String departmentID) {
        if (!this.departmentTable.containsKey(departmentID)) {
            OakLogger.log(Level.SEVERE, "Department not found ID '" + departmentID + "', null is returned");
            return null;
        }

        return this.departmentTable.get(departmentID).getDepartmentName();
    }

    public String getDepartmentIDOfName(String depName) {
        if (!isUniqueDepartmentName(depName)) {
            OakLogger.log(Level.SEVERE, "Not a unique department name, null is returned");
            return null;
        }

        Set<String> depKeys = this.departmentTable.keySet();

        for (String depKey : depKeys)
            if (this.departmentTable.get(depKey).getDepartmentName().equals(depName))
                return depKey;

        return null;
    }

    public boolean containsDepartment(String departmentID) {
        return this.departmentTable.containsKey(departmentID);
    }

    public boolean containsDepartmentWithName(String depName) {
        return countDepartmentWithName(depName) > 0;
    }

    public long countDepartmentWithName(String depName) {
        long res = 0;
        Set<String> depKeys = this.departmentTable.keySet();

        for (String depKey : depKeys)
            if (this.departmentTable.get(depKey).getDepartmentName().equals(depName))
                res++;

        return res;
    }

    public boolean isUniqueDepartmentName(String depName) {
        return countDepartmentWithName(depName) < 2 ? true
                                                    : false;
    }

    public String getDepartmentOfStaff(String staffUID) {
        String res = null;
        Iterator<Entry<String, AutoUpdateCSV>> departmentEntries = this.departmentMemberFiles.entrySet().iterator();

        while (departmentEntries.hasNext()) {
            Entry<String, AutoUpdateCSV> departmentEntry = departmentEntries.next();

            if (departmentEntry.getValue().getDataWhere(staffUID, "DATE").isEmpty())
                continue;
            else
                res = departmentEntry.getKey();
        }

        return res;
    }

    public Department getDepartment(String departmentID) {
        if (Objects.isNull(departmentID) || !this.departmentTable.containsKey(departmentID))
            return null;

        return this.departmentTable.get(departmentID);
    }

    public Department getDepartmentWithName(String depName) {
        if (!isUniqueDepartmentName(depName)) {
            OakLogger.log(Level.SEVERE, "Not a unique organization name, null is returned");
            return null;
        }

        return this.departmentTable.get(getDepartmentIDOfName(depName));
    }

    public WorkspaceManagerStatus renameDepartment(String departmentID, String oldName, String newName) {
        if (!this.departmentTable.containsKey(departmentID)) {
            OakLogger.log(Level.SEVERE, "Department not found ID '" + departmentID + "'");
            return WorkspaceManagerStatus.DEPARTMENT_NOT_FOUND;
        } else if (containsDepartmentWithName(newName)) {
            OakLogger.log(Level.SEVERE, "Department name already in use'" + newName + "'");
            return WorkspaceManagerStatus.DEPARTMENT_NAME_IN_USE;
        }

        Department dep = this.departmentTable.get(departmentID);

        if (dep.getDepartmentName().equals(oldName)) {
            dep.renameDepartmentTo(newName);
            this.departmentIndexFile.editRecord(departmentID, "DEP_NAME", newName, false);
            this.departmentInfoFiles.get(departmentID).editRecord(departmentID, "DEP_NAME", newName, false);
            OakWorkspaceResource.renameDepartmentDirectory(oldName, newName);
            return WorkspaceManagerStatus.SUCCESS;
        }

        return WorkspaceManagerStatus.FAILURE;
    }

    public String getLeaderStaffMemberIDOf(String departmentID) {
        return getDepartment(departmentID).getLeaderStaffMemberID();
    }

    public boolean hasLeaderStaffMember(String departmentID) {
        return getDepartment(departmentID).hasLeaderStaffMember();
    }

    public ObservableSet<FullUserEntry> getAllStaffMemberSetProperty(String departmentID){
        ObservableSet<FullUserEntry> departmentStaffMembers = FXCollections.observableSet();
        for (String staffUID : getDepartment(departmentID).getStaffMemberSetProperty()){
            departmentStaffMembers.add(AccountService.getUserManager().getFullUserEntryFromUID(staffUID));
        }

        return departmentStaffMembers;
    }

    public boolean assignLeaderStaffMember(String departmentID, String staffUID) {
        if (!this.departmentTable.containsKey(departmentID)) {
            OakLogger.log(Level.SEVERE, "Department not found ID '" + departmentID + "'");
            return false;
        }

        if (!getDepartment(departmentID).containsStaffMember(staffUID)) {
            addStaffMember(departmentID, staffUID);
        }

        if (getDepartment(departmentID).assignLeaderStaffMemberTo(staffUID)) {
            this.departmentIndexFile.editRecord(departmentID, "LEADER_STAFF", staffUID, false);
            return true;
        }

        return false;
    }

    public boolean changeLeaderStaffMember(String departmentID, String staffUID) {
        if (!getDepartment(departmentID).containsStaffMember(staffUID)) {
            addStaffMember(departmentID, staffUID);
        }

        if (getDepartment(departmentID).changeLeaderStaffMemberTo(staffUID)) {
            this.departmentIndexFile.editRecord(departmentID, "LEADER_STAFF", staffUID, false);
            return true;
        }

        return false;
    }

    public void addStaffMember(String departmentID, String staffUID) {
        if (getDepartment(departmentID).containsStaffMember(staffUID)) {
            return;
        }

        getDepartment(departmentID).addStaffMember(staffUID);
        this.departmentMemberFiles.get(departmentID).addRecord(new String[] { staffUID, Long.toString(Instant.now().toEpochMilli()) });
    }

    public void removeStaffMember(String departmentID, String staffUID) {
        if (getDepartment(departmentID).getLeaderStaffMemberID().equals(staffUID))
            this.departmentIndexFile.editRecord(departmentID, "LEADER_STAFF", "NIL", false);

        this.departmentMemberFiles.get(departmentID).removeRecordWhere(staffUID);
        getDepartment(departmentID).removeStaffMember(staffUID);
    }

    public void assignCategory(String departmentID, String categoryName) {
        getDepartment(departmentID).assign(categoryName);
        this.departmentTagFiles.get(departmentID).addRecord(new String[] { categoryName, Long.toString(Instant.now().toEpochMilli()) });
    }

    public void removeCategory(String departmentID, String categoryName) {
        getDepartment(departmentID).removeAssignment(categoryName);
        this.departmentTagFiles.get(departmentID).removeRecordWhere(categoryName);
    }
}
