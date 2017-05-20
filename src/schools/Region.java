package schools;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

// Hint: to write compact stream expressions
// you can include the following
//import static java.util.stream.Collectors.*;
//import static java.util.Comparator.*;


public class Region {
	
	public Region(String name){
	}
	
	public String getName(){
		return null;
	}
	
	public Collection<School> getSchools() {
		return null;
	}
	
	public Collection<Community> getCommunities() {
		return null;
	}
	
	public Collection<Municipality> getMunicipalies() {
		return null;
	}
	
	
	// factory methods
	
	public Community newCommunity(String name, Community.Type type){
		return null;
	}

	public Municipality newMunicipality(String nome, String provincia){
		return null;
	}
	public Municipality newMunicipality(String nome, String provincia, Community comunita){
		return null;
	}
	
	public School newSchool(String name, String code, int grade, String description){
		return null;
	}
	
	public Branch newBranch(int regionalCode, Municipality municipality, 
							String address, int zipCode, School school)	{
		return null;
	}
	
	public void readData(String url) throws IOException{
		// Hint: use LineUtils.loadLinesUrl(url) to load the CSV lines from a URL
	}

	public Map<String,Long>countSchoolsPerDescription(){
		return null;
	}

	public Map<String,Long>countBranchesPerMunicipality(){
		return null;
	}

	public Map<String,Double>averageBranchesPerMunicipality(){
		return null;
	}


	public Collection<String> countSchoolsPerMunicipality(){
		return null;
	}
	

	public List<String> countSchoolsPerCommunity(){
		return null;
	}

	
}
