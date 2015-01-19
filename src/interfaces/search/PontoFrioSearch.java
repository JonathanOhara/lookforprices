package interfaces.search;

import interfaces.Filter;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import objects.Product;
import objects.Shop;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import comom.Keys;
import comom.Util;

public class PontoFrioSearch implements Search{

	@Override
	public List<Product> search(Shop shop, String productName, Filter filter) {
		List<Product> products = null;
		
		Document document;
		
		try{
			document = readResults(shop, productName);
			
			System.out.println("\t\tDocumento Lido");
			
			Elements els = document.select(".vitrineProdutos");
			
			if( els.size() > 0 ){
				System.out.println("\t\tResultados: "+els.size());
				els = els.first().select("li");
				products = readEachProduct(shop, productName, els, filter);	
			}else{
				System.out.println("\t\tResultados: 1");
				products = new ArrayList<Product>();
				readProductPage(Util.prepareUrlMode1( shop.getSearchPattern(), productName ), document, null, products);
			}
			
			
			products = readEachProduct(shop, productName, els, filter);
			
		}catch(Exception e){
			e.printStackTrace();
		}
			
		return products;
	}

	private List<Product> readEachProduct(Shop shop, String productName, Elements els, Filter filter) throws IOException {
		String previewName;
		String individualUrl;
		Document document;
		Element productContainer;
		List<Product> products = null;
		
		if( els.size() > 0 ){
			products = new ArrayList<Product>(els.size());
			
			for( Element element : els ){
				productContainer = element;
				
				previewName = productContainer.select("h2.name").text();
				
				System.out.println("\t\tNome do Produto: "+previewName);
				
				if( filter.filter(previewName, productName) ){
					individualUrl = productContainer.select("a").first().attr("href");
					
					document = Util.readUrlDocument( individualUrl );
					
					readProductPage(individualUrl, document, productContainer, products);
				}else{
					System.out.println("\t\t\tIgorando Pelo Filtro de Nome...");
				}
			}

		}
		return products;
	}

	private void readProductPage(String individualUrl, Document document, Element productContainer, List<Product> products) {
		String gameCompleteName;
		String price;
		price = document.select(".sale").size() > 0 ? document.select(".sale").text().trim(): Keys.INDISPONIVEL;

		gameCompleteName = document.select("h1").first().child(0).text();
		
		products.add( new Product(gameCompleteName, "", individualUrl, productContainer, price ) );
	}

	private Document readResults(Shop shop, String productName)	throws IOException, MalformedURLException, URISyntaxException {
		Document document;
		document = Util.readUrlDocument( Util.prepareUrlMode1( shop.getSearchPattern(), productName ) );
		return document;
	}
	
}
