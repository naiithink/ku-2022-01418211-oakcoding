/**
 * @file ComplaintList.java
 * 
 * Reviews:
 *  - Naming
 *      1. (CASE) naiithink, 2022-10-05
 */

package ku.cs.oakcoding.app.models.complaints;

import java.util.ArrayList;

public class ComplaintList {
    private ArrayList<Complaint> complaints;

    public ComplaintList(){
        complaints = new ArrayList<Complaint>();
    }

    public void addComplaint(Complaint complaint){
        complaints.add(complaint);
    }

    public ArrayList<Complaint> getComplaints(){
        return complaints;
    }
}
