package hepl.sysdys2020.stock.Jms;

import java.io.Serializable;

public class CustomMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private String studentId;
    private String name;
    private String rollNumber;



    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

}
