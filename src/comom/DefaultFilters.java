package comom;

import interfaces.Filter;

import java.util.Arrays;
import java.util.List;

public class DefaultFilters {
	
	private static boolean useDenyWords = true;
	private static String[] denyWords = {"PS3", "PS4", "XBOX", "Xone", "X360", "Wii", "WiiU", "Guide", "USADO", "Fricção", "Capa", "Capinha", "Carregador", "Bateria", "Película", "Pelicula"};

	public static Filter noFilter(){
		return new Filter(){
			@Override
			public boolean filter(String productName, String searchedName) {
				return true;
			}
		};
	}
	
	public static Filter contains(){
		return new Filter(){
			@Override
			public boolean filter(String productName, String searchedName) {
				return productName.toLowerCase().contains( searchedName.toLowerCase() );
			}
		};
	}
	
	public static Filter containAllWords(){
		return new Filter(){
			@Override
			public boolean filter(String productName, String searchedName) {
				List<String> productNameWords = Arrays.asList( productName.toLowerCase().split(" ") );
				String[] searchedNameWords = searchedName.toLowerCase().split(" ");
				boolean matches = true;
				
				for( String word: searchedNameWords ){
					
					if( !wordContainsInList( productNameWords, word) ){
						matches = false;
						break;
					}
				}
				
				if( useDenyWords ){
					for( String word: denyWords ){
						
						if( wordContainsInList( productNameWords, word) ){
							matches = false;
							break;
						}
					}	
				}
				return matches;
			}

			private boolean wordContainsInList(List<String> productNameWords, String word) {
				boolean matches = false;
				
				
				for(String s : productNameWords){
					if( replaceAllAccent(s).contains(word.toLowerCase()) ){
						matches = true;
						break;
					}
				}
				return matches;
			}
		};
	}
	
	public static String replaceAllAccent(String replacement) {
		String retorno = replacement;
		if (retorno == null) {
			return null;
		}
		retorno = retorno.toLowerCase();
		retorno = retorno.replaceAll("[ãáàâäª]", "a");
		retorno = retorno.replaceAll("[ãáàâä]".toUpperCase(), "a".toUpperCase());
		retorno = retorno.replaceAll("[éèêë]", "e");
		retorno = retorno.replaceAll("[éèêë]".toUpperCase(), "e".toUpperCase());
		retorno = retorno.replaceAll("[íìîï]", "i");
		retorno = retorno.replaceAll("[íìîï]".toUpperCase(), "i".toUpperCase());
		retorno = retorno.replaceAll("[õóòôöº]", "o");
		retorno = retorno.replaceAll("[õóòôö]".toUpperCase(), "o".toUpperCase());
		retorno = retorno.replaceAll("[úùûü]", "u");
		retorno = retorno.replaceAll("[úùûü]".toUpperCase(), "u".toUpperCase());
		retorno = retorno.replaceAll("[ç]", "c");
		retorno = retorno.replaceAll("[ç]".toUpperCase(), "c".toUpperCase());
		retorno = retorno.replaceAll("[ñ]", "n");
		retorno = retorno.replaceAll("[ñ]".toUpperCase(), "n".toUpperCase());
		retorno = retorno.replaceAll("[&]", "");

		return retorno;
	}	
}
