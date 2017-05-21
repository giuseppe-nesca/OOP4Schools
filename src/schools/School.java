package schools;

import java.util.ArrayList;
import java.util.Collection;

public class School {
	
	private String name;
	private String code; 
	private int grade; 
	private String description;
	private Collection<Branch> branches = new ArrayList<Branch>();

	public School(String name, String code, int grade, String description) {
		this.name = name;
		this.code = code;
		this.grade = grade;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

	public int getGrade() {
		return grade;
	}

	public String getDescription() {
		return description;
	}

	public Collection<Branch> getBranches() {
		return branches;
	}
	
	public School addBranch(Branch branch){
		branches.add(branch);
		return this;
	}

}
