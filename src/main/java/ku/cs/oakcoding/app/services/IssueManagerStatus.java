package ku.cs.oakcoding.app.services;

public enum IssueManagerStatus {
    SUCCESS,
    INVALID_ACCESS,
    CATEGORY_NOT_FOUND,
    CATEGORY_ALREADY_EXIST,
    COMPLAINT_NOT_FOUND,
    REPORT_NOT_FOUND,
    TARGET_NOT_FOUND,
    EVIDENCE_PATH_DOES_NOT_EXIST,
    /* when a user report themself */   SELF_REPORTING_ERROR,
    WRONG_REPORT_TYPE,
    NO_REPORT_TYPE_SPECIFIED,
    FAILURE
}
