package schools;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

public class Municipality {
	
	private String nome;
	private String provincia;
	private Community comunita; //non sempre presente --> potrebbe generare NullPointerExeption
	private Collection<Branch>branches = new HashSet<Branch>();
	
	/*
	 * 2 costrutori per comunità optionale
	 */
	public Municipality(String nome, String provincia){
		this.nome = nome;
		this.provincia = provincia;
	}
	public Municipality(String nome, String provincia, Community comunita){
		this.nome = nome;
		this.provincia = provincia;
		this.comunita = comunita.addMunicipality(this); //aggiorna l'elenco dei comuni della comunità
	}

	public String getName() {
		return nome;
}
	public String getProvince() {
		return provincia;
	}

	public Collection<Branch> getBranches() {
		return branches;
	}

	public Optional<Community> getCommunity() {
		return Optional.ofNullable(comunita); //gestisco il caso in cui non è definita
	}	
	
	public Municipality addBranch(Branch branch){
		branches.add(branch);
		return this;
	}
	
}
