package interfaces.search;

import interfaces.Filter;

import java.util.List;

import objects.Product;
import objects.Shop;

public interface Search {
	
	public List<Product> search( Shop shop, String productName, Filter filter );
}
