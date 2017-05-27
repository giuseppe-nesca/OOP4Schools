package schools;

import it.polito.utility.LineUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.function.Supplier;
import java.util.stream.*;
import java.util.stream.Collectors.*;
import java.util.stream.Collector.*;

import javafx.css.PseudoClass;

import javax.xml.bind.JAXBException;

import org.xml.sax.SAXException;

import sun.swing.text.CountingPrintable;

import com.sun.javafx.scene.control.skin.FXVK.Type;
import com.sun.jmx.remote.util.OrderClassLoaders;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import com.sun.org.apache.xerces.internal.impl.dtd.models.CMAny;
import com.sun.org.glassfish.gmbal.Description;
import com.sun.swing.internal.plaf.basic.resources.basic;
import com.sun.xml.internal.bind.v2.runtime.reflect.ListIterator;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;
//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Collections;


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
	private Collection<Provincia> provinces = new HashSet<Provincia>();
	
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
	
	public Provincia newProvincia(String name){
		Provincia provincia = new Provincia(name);
		boolean flag = provinces.add(provincia);
		if(flag)
			return provincia;
		else {
			for(Provincia iter : provinces){
				if (iter.equals(provincia)) return iter;
			}
		}
		System.err.println("error con newProvinces: return null");
		return null;
	}
	
	// definisce una nuova comunità è la ritorna
	public Community newCommunity(String name, Community.Type type){
		Community community = new Community(name, type);
		boolean flag = communities.add(community);
		if(flag)
			return community;
		else {
			for(Community iter : communities){
				if (iter.equals(community)) return iter;
			}
		}
		System.err.println("error con newCommunity: return null");
		return null;
	}

	/*
	 * Definizione di un nuovo comune. due versioni: 
	 * con comunità
	 * senza comunità
	 */
	public Municipality newMunicipality(String nome, String provincia){
		Municipality municipality = new Municipality(nome, provincia);
		boolean flag = municipalities.add(municipality);
		if(flag)
			return municipality;
		else {
			for(Municipality iter : municipalities){
				if (iter.equals(municipality)) return iter;
			}
		}
		System.err.println("error con newMunicipality: return null");
		return null;
	}	
	public Municipality newMunicipality(String nome, String provincia, Community comunita){
		Municipality municipality = new Municipality(nome, provincia, comunita);
		boolean flag = municipalities.add(municipality);
		if(flag)
			return municipality;
		else {
			for(Municipality iter : municipalities){
				if (iter.equals(municipality)) return iter;
			}
		}
		System.err.println("error con newMunicipality: return null");
		return null;
	}
	
	//
	public School newSchool(String name, String code, int grade, String description){
		School school = new School(name, code, grade, description);
		boolean flag = schools.add(school);
		if (flag)
			return school;
		else {
			for(School iter : schools){
				if (iter.equals(school)) return iter;
			}
		}
		System.err.println("error con newSchool: return null");
		return null;
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
			String gradoScolastico = stringTokenizer.nextToken(); //comodo trasformarlo in Integer da subito
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
				//.collect(Collectors.groupingBy(School::getDescription, Collectors.counting()));
				.collect(Collectors.groupingBy((School s) -> s.getDescription(), Collectors.counting() ));
		return schoolTypes;
	}
	// return map: key==municipalityName,  value: branchesNumber
	public Map<String,Long>countBranchesPerMunicipality(){
		/*Map<String, Long> municipalityBranches = municipalities.stream()
				.collect(Collectors.groupingBy(Municipality::getName, 
						Collectors.mapping(Municipality::getBranches, Collectors.counting()))); //conto ibranches dentro la municipality
		return municipalityBranches;*/
		Map<String,Long> municipalityBranches = new HashMap<String, Long>();
		municipalities.forEach(s-> {
			municipalityBranches.put(s.getName(),(long) s.getBranches().size());
		});
		return municipalityBranches;
	}

	public Map<String,Double>averageBranchesPerMunicipality(){
		
		//provinceName list
		Set<String> provincesStrings = municipalities.stream()
			.map(Municipality::getProvince)
			.distinct()
			.collect(Collectors.toSet());
		
		Set<Province> provinceSet = new HashSet<Region.Province>();
		
		provincesStrings.forEach(s -> {
			provinceSet.add(new Province(s,municipalities));
		});
		
		Map<String,Double>averageProv = new HashMap<String, Double>();
		
		provinceSet.forEach(s -> {
			averageProv.put(s.name, s.average);
		});
		return averageProv;
	}


	public Collection<String> countSchoolsPerMunicipality(){
		
		Set<String> s = new HashSet<String>();
		
		Map<Municipality,Long> schoolNumSet = new HashMap<>();
		
		for (Municipality iter : municipalities) {
			 long n = 
				iter.getBranches().stream()
					.map(Branch::getSchool)
					.distinct()
					.count();
			schoolNumSet.put(iter, n);
			String tmp = n + " - " + iter.getName();
			s.add(tmp);
		}		
		return s;
	}
	

	public List<String> countSchoolsPerCommunity(){
		
		Set<String> s = new TreeSet<String>(new Comparator<String>() {
			@Override
			public int compare (String s1, String s2){
				Integer i1 = new Integer(new StringTokenizer(s1," - ").nextToken().toString());
				Integer i2 = new Integer(new StringTokenizer(s2," - ").nextToken().toString());
				return i1 - i2;
			}
		});
		Map<Long,Community> schoolNumMap = new TreeMap<>(java.util.Collections.reverseOrder());
		Map<Community, Set<School>> goodSchools = new HashMap<>();
		Map<Community, List<Branch>> goodBranches;
		
		goodBranches = 
		communities.stream()
			.flatMap( c -> c.getMunicipalities().stream())
			.distinct()
			.flatMap( m -> m.getBranches().stream() )
			.distinct()
			.collect(Collectors.groupingBy( Branch::getCommunity))
			/*.collect(Collectors.groupingBy(
					 b -> {b.getMunicipality().getCommunity(); }
					))*/
			;
		for (Community community : communities) {
			List<School> listSchools = goodBranches.get(community).stream()
											.map(Branch::getSchool)
											.distinct()
											.collect(Collectors.toList());
			int sizeListSchools = listSchools.size();
			String tmp = sizeListSchools + " - " + community.getName();
			s.add(tmp);
			
		}
		
		/*while(!goodBranches.isEmpty()){
			int maxL=0;
			goodBranches.values().forEach(b -> {
				if(b.size()>maxL){
					maxL = b.size();
				}
			});
			for (String string : s) {
				
			}
		}*/
		
		/*fo r (Community community : communities) {
			Set<School> schools = new HashSet<School>();
			for (Municipality municipatiy : community.getMunicipalities()) {
				Set<School> tmp = municipatiy.getBranches().stream()
										.map(Branch::getSchool)
										.distinct()
										.collect(Collectors.toSet())
										;
				for (School school : tmp) {
					schools.add(school);
				};
			}*/
			
			//goodSchools.put(community, schools);
			//schoolNumMap.put(community, (long) schools.size());
			//String tmpS = schools.size() + " " + community.getName();
			//s.add(tmpS);
		//}
		
		
		
		// 
		
	/*	while (!schoolNumMap.isEmpty()) {
			long max = Stream.of(schoolNumMap)
						.flatMap(m -> m.values().stream())
						.max(Comparator.naturalOrder()).get();
			for (Community community : communities) {
				
				
			}
		}
		*/
		/*communities.stream()
			.flatMap(c -> c.getMunicipalities().stream())
			.flatMap(m -> m.getBranches().stream())
			
			.collect(Collectors.groupingBy())
			;*/
		
		
		
		/*Set<Municipality> goodMuni = 
				communities.stream()
					.flatMap(c -> c.getMunicipalities().stream())
					.distinct()
					.collect(Collectors.toSet())
			;
		for (Municipality municipality : goodMuni) {
			long n = 
				municipality.getBranches().stream()
					.map(Branch::getSchool)
					.distinct()
					.count();
			schoolNumSet.put(municipality, n);
			String tmp = n + " " + municipality.getName();
			s.add(tmp);
		}*/
		
		
		return s.stream().collect(Collectors.toList());
	}

	class Province {
		String name;
		Set<Municipality> municipalities = new HashSet<>();
		int municipalitiesNum = 0;
		double average = 0;
		
		public Province( String name, Collection<Municipality> m ){
			this.name = name;
						
			m.forEach(s -> {
				if(s.getProvince().equals(name)){
					municipalities.add(s);	
				}
			});
			average = municipalities.stream()
				.mapToInt(s-> s.getBranches().size())
				.average().getAsDouble();
			
		}
	}
	
	
}
