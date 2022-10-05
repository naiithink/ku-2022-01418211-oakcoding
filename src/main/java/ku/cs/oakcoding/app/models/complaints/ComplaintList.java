package ku.cs.oakcoding.app.models.complaints;

import java.util.ArrayList;

import ku.cs.oakcoding.app.models.data.User;

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
