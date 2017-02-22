package comom;

import interfaces.Filter;

import java.util.Arrays;
import java.util.List;

public class DefaultFilters {
	
	private static boolean useDenyWords = true;
	private static String[] denyWords = {"PS3", "PS4", "XBOX", "Xone", "X360", "Wii", "WiiU", "Guide", "PSV", "USADO", "Fricзгo"};

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
		retorno = retorno.replaceAll("[гбавдЄ]", "a");
		retorno = retorno.replaceAll("[гбавд]".toUpperCase(), "a".toUpperCase());
		retorno = retorno.replaceAll("[йикл]", "e");
		retorno = retorno.replaceAll("[йикл]".toUpperCase(), "e".toUpperCase());
		retorno = retorno.replaceAll("[нмоп]", "i");
		retorno = retorno.replaceAll("[нмоп]".toUpperCase(), "i".toUpperCase());
		retorno = retorno.replaceAll("[хутфцє]", "o");
		retorno = retorno.replaceAll("[хутфц]".toUpperCase(), "o".toUpperCase());
		retorno = retorno.replaceAll("[ъщыь]", "u");
		retorno = retorno.replaceAll("[ъщыь]".toUpperCase(), "u".toUpperCase());
		retorno = retorno.replaceAll("[з]", "c");
		retorno = retorno.replaceAll("[з]".toUpperCase(), "c".toUpperCase());
		retorno = retorno.replaceAll("[с]", "n");
		retorno = retorno.replaceAll("[с]".toUpperCase(), "n".toUpperCase());
		retorno = retorno.replaceAll("[&]", "");

		return retorno;
	}	
}
