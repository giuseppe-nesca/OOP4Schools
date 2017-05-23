package schools;

import it.polito.utility.LineUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.HashSet;
import java.util.StringTokenizer;
import java.util.function.Supplier;

import java.util.stream.*;

import javax.xml.bind.JAXBException;

import org.xml.sax.SAXException;

import sun.swing.text.CountingPrintable;

import com.sun.javafx.scene.control.skin.FXVK.Type;
import com.sun.org.apache.xerces.internal.impl.dtd.models.CMAny;
import com.sun.org.glassfish.gmbal.Description;
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
		
		Iterator<String>iter=lines.iterator(); 
		assert iter.next() == "Provincia,Comune,Grado Scolastico,Descrizione,Cod Sede,Cod Scuola,Denominazione,Indirizzo,C.A.P.,Comunita Collinare,Comunita Montana";
		iter.next();
		while(iter.hasNext()){
			String string = iter.next();
			StringTokenizer stringTokenizer = new StringTokenizer(string, ",");
			String provincia = stringTokenizer.nextToken();
			String comune = stringTokenizer.nextToken();
			String gradoScolastico = stringTokenizer.nextToken();
			Integer grade = new Integer(new StringBuilder(gradoScolastico).charAt(0));
			String descrizione = stringTokenizer.nextToken();
			String codSede = stringTokenizer.nextToken();
			String codScuola = stringTokenizer.nextToken();
			String denominazione = stringTokenizer.nextToken();
			String indirizzo = stringTokenizer.nextToken();
			String zipCode = stringTokenizer.nextToken();
			
			//questi dati sono opzionali ovvero la liena importata potrebbe non contenerli
			assert stringTokenizer.hasMoreElements();
			Community community;
			Municipality municipality;
			
			String comunitaCollinare;
			String comunitaMontana;  
			String s = stringTokenizer.nextToken("");
			if(!s.equals(",,")){
				if(s.startsWith(",,")){
					comunitaMontana = new StringBuilder(s).delete(0, 2).toString();
					//System.out.println("comunita montana =  "+ comunitaMontana);
					community = newCommunity(comunitaMontana, Community.Type.MONTANA);
				}else{
					comunitaCollinare = new StringBuilder(s).deleteCharAt(0).reverse().deleteCharAt(0).reverse().toString();
					//System.out.println("comunita collinare = "+ comunitaCollinare);
					community = newCommunity(comunitaCollinare, Community.Type.COLLINARE);
				}
				municipality = newMunicipality(comune, provincia, community);
			}else{
				municipality = newMunicipality(comune, provincia);
			}
			School school = newSchool(denominazione, codScuola, grade, descrizione);
			Branch branch = newBranch(new Integer(codSede), municipality, indirizzo, new Integer(zipCode), school);
			}
		
	}

	// return map: key==school description, int: num school with the same description
	public Map<String,Long>countSchoolsPerDescription(){
		Map<String, Long> schoolTypes = schools.stream()
				.collect(Collectors.groupingBy(School::getDescription, Collectors.counting()));
		return schoolTypes;
	}
	// return map: key==municipalityName,  value: branchesNumber
	public Map<String,Long>countBranchesPerMunicipality(){
		Map<String, Long> municipalityBranches = municipalities.stream()
				.collect(Collectors.groupingBy(Municipality::getName, Collectors.
						mapping(Municipality::getBranches, Collectors.counting()))); //conto ibranches dentro la municipality
		return municipalityBranches;
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
