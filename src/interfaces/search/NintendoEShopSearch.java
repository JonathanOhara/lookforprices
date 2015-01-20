package interfaces.search;

import interfaces.Filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import objects.Product;
import objects.Shop;

import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import comom.JsonReader;
import comom.Keys;
import comom.Util;

public class NintendoEShopSearch implements Search{

	private String urlGame = "http://www.nintendo.com/games/detail/";
	
	@Override
	public List<Product> search(Shop shop, String productName, Filter filter) {
		List<Product> products = null;
		JSONArray games = null;

		try{

			System.out.println("NintendoEShopSearch.search() "+Util.prepareUrlMode1( shop.getSearchPattern(), productName ));
			JSONObject json = JsonReader.readJsonFromUrl( Util.prepareUrlMode1( shop.getSearchPattern(), productName ) );
			
			System.out.println("\t\tDocumento Lido");
			
			if( json.get("game") instanceof JSONArray ){
				games = json.getJSONArray("game");	
			}else{
				games = new JSONArray();
				games.put(json.getJSONObject("game"));
			}
			
			System.out.println("\t\tResultados: "+games.length());
			
			products = readEachProduct(shop, productName, games, filter);
			
		}catch(Exception e){
			e.printStackTrace();
		}
			
		return products;
	}

	private List<Product> readEachProduct(Shop shop, String productName, JSONArray games, Filter filter) throws IOException {
		String previewName;
		String gameId;
		String individualUrl = "";
		String price;
		Document document;
		Element productContainer = null;
		List<Product> products = null;
		
		JSONObject jsonObject;
		if( games.length() > 0 ){
			products = new ArrayList<Product>(games.length());
			
			for( int i = 0; i < games.length(); i++ ){
				jsonObject = games.getJSONObject(i);
				
				previewName = jsonObject.getString("title");
				gameId = jsonObject.getString("id");
				
				System.out.println("\t\tNome do Produto: "+previewName);
				
				if( filter.filter(previewName, productName) ){
					individualUrl = urlGame + gameId;
					document = Util.readUrlDocument( individualUrl );
					System.out.println("\t\tAcessando URL do produto.");
					System.out.println("URL = "+individualUrl);
					price = document.select(".price_display").size() > 0 ? document.select(".price_display").text().trim(): Keys.INDISPONIVEL;
	
					
					products.add( new Product(previewName, "", individualUrl, productContainer, price ) );
				}else{
					System.out.println("\t\t\tIgorando Pelo Filtro de Nome...");
				}
			}

		}
		return products;
	}

}