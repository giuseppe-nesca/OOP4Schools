package schools;

import it.polito.utility.LineUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.HashSet;
import java.util.StringTokenizer;

import javax.xml.bind.JAXBException;

import org.xml.sax.SAXException;

import com.sun.javafx.scene.control.skin.FXVK.Type;
import com.sun.org.apache.xerces.internal.impl.dtd.models.CMAny;
import com.sun.xml.internal.bind.v2.runtime.reflect.ListIterator;

import jdk.nashorn.internal.runtime.Scope;

// Hint: to write compact stream expressions
// you can include the following
//import static java.util.stream.Collectors.*;
//import static java.util.Comparator.*;


public class Region {
	
	private String name;
	private Collection<Community> communities = new HashSet<Community>();
	private Collection<Municipality> municipalities = new HashSet<Municipality>();
	private Collection<School> schools = new HashSet<School>();
	
	public Region(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public Collection<School> getSchools() {
		return schools;
	}
	
	public Collection<Community> getCommunities() {
		return communities;
	}
	
	public Collection<Municipality> getMunicipalies() {
		return municipalities;
	}
	
	
	// factory methods
	
	// definisce una nuova comunità è la ritorna
	public Community newCommunity(String name, Community.Type type){
		Community community = new Community(name, type);
		communities.add(community);
		return community;
	}

	/*
	 * Definizione di un nuovo comune. due versioni: 
	 * con comunità
	 * senza comunità
	 */
	public Municipality newMunicipality(String nome, String provincia){
		Municipality municipality = new Municipality(nome, provincia);
		municipalities.add(municipality);
		return municipality;
	}
	public Municipality newMunicipality(String nome, String provincia, Community comunita){
		Municipality municipality = new Municipality(nome, provincia, comunita);
		municipalities.add(municipality);
		return municipality;
	}
	
	//
	public School newSchool(String name, String code, int grade, String description){
		School school = new School(name, code, grade, description);
		schools.add(school);
		return school;
	}
	
	public Branch newBranch(int regionalCode, Municipality municipality, 
							String address, int zipCode, School school)	{
		return new Branch(regionalCode, municipality, address, zipCode, school);
	}
	
	//popola la classe regione 
	public void readData(String url) throws IOException{
		// Hint: use LineUtils.loadLinesUrl(url) to load the CSV lines from a URL
		List<String> lines = LineUtils.loadLinesUrl(url);
		
		//debug only
/*		for(Iterator<String> l=lines.iterator(); l.hasNext();){
			String s = l.next();
			System.out.println(s);
		} 
*/
		Iterator<String>iter=lines.iterator(); 
		assert iter.next() == "Provincia,Comune,Grado Scolastico,Descrizione,Cod Sede,Cod Scuola,Denominazione,Indirizzo,C.A.P.,Comunita Collinare,Comunita Montana";
		iter.next();
		while(iter.hasNext()){
			/*String[] infos = iter.next().split(",");
			//String[] infoschool = infos[2].split("-");
			String provincia = infos[0], comune = infos[1]; //municipality
			//String gradoScolastico = infoschool[0], Descrizione = infoschool[1]; //school
			String gradoScolastico = infos[2] , descrizione = infos[3];
			String codSede = infos[4]; //branch
			String codScuola = infos[5], denominazione = infos[6]; //school
			String indirizzo = infos[7], zipCode = infos[8]; //branch
			Optional<String> comunitaCollinare = Optional.ofNullable(infos[9]) , comunitaMontana = Optional.ofNullable(infos[10]); //community
			
			Community community;
			Municipality municipality;
			School school;
			Branch branch;
			if(comunitaCollinare.isPresent()){
				community = newCommunity(comunitaCollinare.get(), Community.Type.COLLINARE);
				municipality = newMunicipality(comune, provincia,community);
				branch = newBranch(new Integer(codSede), municipality, indirizzo, new Integer(zipCode), 
						newSchool(denominazione, codScuola, new Integer(gradoScolastico), descrizione));
			}else if(comunitaMontana.isPresent()){
				community = newCommunity(comunitaMontana.get(), Community.Type.MONTANA);
				branch = newBranch(new Integer(codSede), newMunicipality(comune, provincia,community), indirizzo, new Integer(zipCode), 
						newSchool(denominazione, codScuola, new Integer(gradoScolastico), descrizione));
			}else{
				branch = newBranch(new Integer(codSede), newMunicipality(comune, provincia), indirizzo, new Integer(zipCode), 
						newSchool(denominazione, codScuola, new Integer(gradoScolastico), descrizione));
			}
			*/
			String string = iter.next();
			StringTokenizer stringTokenizer = new StringTokenizer(string, ",");
			String Provincia = stringTokenizer.nextToken();
			String comune = stringTokenizer.nextToken();
			String gradoScolastico = stringTokenizer.nextToken();
			String descrizione = stringTokenizer.nextToken();
			String codSede = stringTokenizer.nextToken();
			String codScuola = stringTokenizer.nextToken();
			String denominazione = stringTokenizer.nextToken();
			String indirizzo = stringTokenizer.nextToken();
			String zipCode = stringTokenizer.nextToken();
			
			assert stringTokenizer.hasMoreElements();
			String comunitaCollinare = stringTokenizer.nextToken("");
			
			String comunitaMontana;  
			try{
				comunitaMontana = stringTokenizer.nextToken("");
				System.out.println("CM added!");
			}catch(NoSuchElementException e){
				comunitaMontana = null;
			}
		}
		
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
