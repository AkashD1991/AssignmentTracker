package AssginmentTracker;

public class Student
{
	int sid;
	String sname;
	int bid;
	String bname;
	
	public Student() {
		super();
	}

	public Student(int sid, String sname, int bid, String bname) {
		super();
		this.sid = sid;
		this.sname = sname;
		this.bid = bid;
		this.bname = bname;
	}

	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public int getBid() {
		return bid;
	}

	public void setBid(int bid) {
		this.bid = bid;
	}

	public String getBname() {
		return bname;
	}

	public void setBname(String bname) {
		this.bname = bname;
	}	
}
