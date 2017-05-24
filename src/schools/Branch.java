package schools;

public class Branch {
	
	int regionalCode;
	Municipality municipality; 
	String address;
	int zipCode;
	School school;
	
	public Branch(int regionalCode, Municipality municipality, String address,
			int zipCode, School school) {
		this.regionalCode = regionalCode;
		this.municipality = municipality.addBranch(this);
		this.address = address;
		this.zipCode = zipCode;
		this.school = school.addBranch(this);
	}
	
	public int getCode() {
		return regionalCode;
	}
	
	public String getAddress() {
		return address;
	}
	
	public int getCAP() {
		return zipCode;
	}

	public Municipality getMunicipality(){
		return municipality;
	}

	public School getSchool(){
		return school;
	}

}
