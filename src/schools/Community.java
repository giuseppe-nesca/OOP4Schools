package schools;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.TreeMap;
import java.util.TreeSet;

public class Community {
	
	public enum Type { MONTANA, COLLINARE };
	private String name;
	private Type type;
	private Collection<Municipality> municipalities = new HashSet<>();
	
	public Community(String name, Community.Type type){
		this.type = type;
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public Type getType(){
		return type;
	}

	public Collection<Municipality> getMunicipalities() {
		return municipalities;
	}
	
	public Community addMunicipality(Municipality municipality){
		this.municipalities.add(municipality);
		return this;
	}
}
