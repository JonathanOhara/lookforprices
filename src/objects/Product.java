package objects;
import org.jsoup.nodes.Node;


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
		try{
			returnValue = Float.parseFloat( getValue() );
		}catch(Exception e){
			returnValue = 0;
		}
		
		return returnValue;
	}
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
