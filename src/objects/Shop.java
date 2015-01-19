package objects;
import interfaces.Filter;
import interfaces.search.Search;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;



public class Shop {
	
	private String nome;
	private String mainUrl;
	private String searchPattern;
	private Search searcher;
	
	
	public Shop(String nome, String mainUrl, String searchPattern, Search searcher) {
		super();
		this.nome = nome;
		this.mainUrl = mainUrl;
		this.searchPattern = searchPattern;
		this.searcher = searcher;
	}
	
	public List<Product> searchProduct(String productName, Filter filter){
		System.out.println("Loja: "+nome);
		System.out.println("\tURL Principal: "+mainUrl);
		System.out.println("\tURL Busca: "+searchPattern);
		
		long time = System.currentTimeMillis();
		List<Product> products = searcher.search(this, productName, filter);
		System.out.println("\tTempo Para buscar: "+(System.currentTimeMillis() - time));
		
		if( products != null ){
			
			Collections.sort(products, new Comparator<Product>() {
				@Override
				public int compare(Product o1, Product o2) {
					int valor1, valor2;
					
					valor1 = valor2 = 0;
					try{
						valor1 = Integer.parseInt(o1.getValue());
					}catch(Exception e){
						valor1 = 0;
					}
					
					try{
						valor2 = Integer.parseInt(o2.getValue());
					}catch(Exception e){
						valor2 = 0;
					}
					return Integer.compare(valor1, valor2);
				}
			});
			
		}
		return products;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getMainUrl() {
		return mainUrl;
	}
	public void setMainUrl(String mainUrl) {
		this.mainUrl = mainUrl;
	}
	public String getSearchPattern() {
		return searchPattern;
	}
	public void setSearchPattern(String searchPattern) {
		this.searchPattern = searchPattern;
	}

	
	
}
