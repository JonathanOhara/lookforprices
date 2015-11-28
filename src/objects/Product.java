package objects;
import org.jsoup.nodes.Node;

import comom.Keys;


public class Product {
	private String name;
	private String imageUrl;
	private String url;
	private String value;
	
	private Node productContainer;
	
	public Product() {}
		
	
	public Product(String name, String imageUrl, String url, Node productContainer, String value) {
		super();
		this.name = name;
		this.imageUrl = imageUrl;
		this.url = url;
		this.value = value;
		this.productContainer = productContainer;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public float getFloatValue(){
		float returnValue = 0;
		float valueModificator = 1;
		try{
			String value = getValue();
			
			if( value.startsWith("$") ){ //Nintendo eShop
				value = value.replace("$", "").trim();
				value = value.replace("*", "").trim();
			
				valueModificator = Keys.DOLAR_VALUE;
			}
			
			value = value.replace("por R$", "").trim();
			value = value.replace("R$", "").trim();
			
			if( value.contains(".") ){
				if( value.indexOf(".") == value.length() - 2 || value.indexOf(".") == value.length() - 3 ){
					
				}else{
					value = value.replace(".", "").trim();		
				}
			}
			
			if( value.contains(",") ){
				if( value.indexOf(",") == value.length() - 2 || value.indexOf(",") == value.length() - 3 ){
					value = value.replace(",", ".").trim();
				}else{
					value = value.replace(",", "").trim();		
				}
			}
			
			returnValue = Float.parseFloat( value ) * valueModificator;
		}catch(Exception e){
			returnValue = 9999.99f;
		}
		
		return returnValue;
	}
	/*
	public static void main(String[] args) {
		Product product = new Product();
		
		product.setValue("$19.99");
		System.out.println( product.getFloatValue() );
		
		product.setValue("$19.99*");
		System.out.println( product.getFloatValue() );
		
		product.setValue("por R$ 79,90");
		System.out.println( product.getFloatValue() );
		
		product.setValue("R$ 99,90 (33% de desconto)");
		System.out.println( product.getFloatValue() );
		
		product.setValue("R$ 219,90");
		System.out.println( product.getFloatValue() );
		
		product.setValue("R$ 219.90");
		System.out.println( product.getFloatValue() );
		
		product.setValue("219,9");
		System.out.println( product.getFloatValue() );

		product.setValue("188.9");
		System.out.println( product.getFloatValue() );
		
		product.setValue("1.000,01");
		System.out.println( product.getFloatValue() );
		
		product.setValue("1,000.01");
		System.out.println( product.getFloatValue() );

		product.setValue("Indisponível");
		System.out.println( product.getFloatValue() );
	}
	*/
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public Node getProductContainer() {
		return productContainer;
	}

	public void setProductContainer(Node productContainer) {
		this.productContainer = productContainer;
	}


	@Override
	public String toString() {
		return "Product [" + (name != null ? "name=" + name + "| " : "")
				+ (imageUrl != null ? "imageUrl=" + imageUrl + "| " : "")
				+ (url != null ? "url=" + url + "| " : "")
				+ (value != null ? "value=" + value : "") + "]";
	}

	
	
}
