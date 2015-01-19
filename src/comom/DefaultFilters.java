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
					if( s.contains(word) ){
						matches = true;
						break;
					}
				}
				return matches;
			}
		};
	}
}
