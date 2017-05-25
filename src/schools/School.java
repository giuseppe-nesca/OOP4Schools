package schools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

public class School {
	
	private String name;
	private String code; 
	private int grade; 
	private String description;
	private Collection<Branch> branches = new HashSet<Branch>();

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
	
	@Override
	public boolean equals(Object object){
		return this.code.compareTo(((School) object).getCode()) == 0;
	}
	
	@Override
	public int hashCode(){ 
		return this.getCode().hashCode();
		}

}
