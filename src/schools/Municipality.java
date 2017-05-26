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
	
	@Override
	public boolean equals(Object object){
		//return this.nome.compareTo(((Municipality) object).getName()) == 0;
		if ( 
				this.nome.compareTo(((Municipality) object).getName()) == 0 &&
				this.provincia.compareTo(((Municipality) object).getProvince()) == 0
				)
			return true;
		else 
			return false;
	}
	
	@Override
	public int hashCode(){
		return this.getName().hashCode();
	}
	
	public Municipality getReference(){
		return this;
	}
}
