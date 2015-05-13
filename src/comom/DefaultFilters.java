package comom;

import interfaces.Filter;

import java.util.Arrays;
import java.util.List;

public class DefaultFilters {

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
		retorno = retorno.replaceAll("[דבאגה×]", "a");
		retorno = retorno.replaceAll("[דבאגה]".toUpperCase(), "a".toUpperCase());
		retorno = retorno.replaceAll("[יטךכ]", "e");
		retorno = retorno.replaceAll("[יטךכ]".toUpperCase(), "e".toUpperCase());
		retorno = retorno.replaceAll("[םלמן]", "i");
		retorno = retorno.replaceAll("[םלמן]".toUpperCase(), "i".toUpperCase());
		retorno = retorno.replaceAll("[ץףעפצ÷]", "o");
		retorno = retorno.replaceAll("[ץףעפצ]".toUpperCase(), "o".toUpperCase());
		retorno = retorno.replaceAll("[תשûü]", "u");
		retorno = retorno.replaceAll("[תשûü]".toUpperCase(), "u".toUpperCase());
		retorno = retorno.replaceAll("[ח]", "c");
		retorno = retorno.replaceAll("[ח]".toUpperCase(), "c".toUpperCase());
		retorno = retorno.replaceAll("[ס]", "n");
		retorno = retorno.replaceAll("[ס]".toUpperCase(), "n".toUpperCase());
		retorno = retorno.replaceAll("[&]", "");

		return retorno;
	}	
}
