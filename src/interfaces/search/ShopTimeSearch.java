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

public class ShopTimeSearch implements Search{

	@Override
	public List<Product> search(Shop shop, String productName, Filter filter) {
		List<Product> products = null;
		
		Document document;

		try{
			document = readResults(shop, productName);
			
			System.out.println("\t\tDocumento Lido");
			
			Elements els = document.select(".pList li");
			
			System.out.println("\t\tResultados: "+els.size());
			
			products = readEachProduct(shop, productName, els, filter);
			
		}catch(Exception e){
			e.printStackTrace();
		}
			
		return products;
	}

	private List<Product> readEachProduct(Shop shop, String productName, Elements els, Filter filter) throws IOException {
		String previewName;
		String gameCompleteName;
		String individualUrl;
		String price;
		Document document;
		Element productContainer;
		List<Product> products = null;
		
		if( els.size() > 0 ){
			products = new ArrayList<Product>(els.size());
			
			for( Element element : els ){
				productContainer = element;

				previewName = productContainer.parent().select(".name").text();
				
				System.out.println("\t\tNome do Produto: "+previewName);
				
				if( filter.filter(previewName, productName) ){
					
					individualUrl = productContainer.select("a").first().attr("href");
					
					document = Util.readUrlDocument( individualUrl );
					System.out.println("\t\tAcessando URL do produto.");
					
					price = document.select(".sales-price").size() > 0 ? document.select(".sales-price").first().nextSibling().toString().trim(): Keys.INDISPONIVEL;
	
					gameCompleteName = document.select(".mp-tit-name").
							first().text();
					
					products.add( new Product(gameCompleteName, "", individualUrl, productContainer, price ) );
				}else{
					System.out.println("\t\t\tIgorando Pelo Filtro de Nome...");	
				}
			}
		}
		return products;
	}

	private Document readResults(Shop shop, String productName)	throws IOException, MalformedURLException, URISyntaxException {
		Document document;
		document = Util.readUrlDocument( Util.prepareUrlMode1( shop.getSearchPattern(), productName ) );
		return document;
	}
	
}
