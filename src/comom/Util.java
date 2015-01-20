package comom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class Util {
	
	private static String projectPath = null;
	private static String reportsPath = null;
	
	static{
		try {
			projectPath = new File(".").getCanonicalPath();
			reportsPath = new File("./reports/").getCanonicalPath();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static List<String> ler(File file) throws IOException { 
		BufferedReader buffRead = new BufferedReader(new FileReader(file));
		String linha = "";
		List<String> stringList = new ArrayList<String>();
		
		while (true) {
			if (linha == null)
				break;
			linha = buffRead.readLine();
			stringList.add(linha);
			
		}
		
		buffRead.close();
		
		return stringList;
	}
	
	public static String getProjectPath() throws IOException{
		return projectPath;
	}
	
	public static String getReportsPath() throws IOException{
		return reportsPath;
	}
	

	public static String stringToUrl(String urlStr) throws MalformedURLException, URISyntaxException{
		return URLEncoder.encode(urlStr);
	}
	
	public static Document readUrlDocument(String url) throws IOException{
		Document doc = null;
		try{
			doc = Jsoup.connect(url)
					.userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
					.referrer("http://www.google.com")
					.timeout(20000)
					.get();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Tentando conectar Na Url de outro modo");
			doc = parseDocument( readUrl(url, null) );
		}
		return doc;
	}
	
	public static String readUrl(String url, String charset){
		StringBuilder builder = new StringBuilder();
		try {
			BufferedReader in = getPageBuffer(url, charset);

			if( in != null){
				String inputLine;
		        while ((inputLine = in.readLine()) != null){
		            builder.append(inputLine);
		        }
		        in.close();
			}
		}catch (MalformedURLException e) { 
			e.printStackTrace();
		}catch (IOException e) {   
			e.printStackTrace();
		}
		
		if( builder.toString().contains("") ){
    		System.out.println("Possivelmente Ocorreu Erro ao ler a pagina");
    	}
		
		return builder.toString();
	}
	
	private static BufferedReader getPageBuffer(String url, String charset)throws IOException {
		URL oracle = null;
		URLConnection yc = null;
		BufferedReader in = null;
		
		for( int i = 0; i < 10; i++ ){
			try{
				oracle = new URL( url );
				yc = oracle.openConnection();
				if( charset == null || charset.isEmpty() ){
					in = new BufferedReader(new InputStreamReader( yc.getInputStream() ));
				}else{
					in = new BufferedReader(new InputStreamReader( yc.getInputStream(), charset ));
				}
				
				break;
			}catch( ConnectException e){
				System.out.println("ConnectException - Tentando novamente["+i+"]: "+url);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e2) {
					e2.printStackTrace();
				}
			}catch( UnknownHostException e){
				System.out.println("UnknownHostException - Tentando novamente["+i+"]: "+url);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e2) {
					e2.printStackTrace();
				}
			}
			
		}
		
		return in;
	}
	
	public static Document parseDocument(String html){
		Document document = null;
		try{
			document = Jsoup.parse(html);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return document;
	}

	public static String prepareUrlMode1(String searchPattern,	String productName) throws MalformedURLException, URISyntaxException {
		return searchPattern.replace(Keys.BUSCA, stringToUrl( productName ) );
	}

	public static String prepareUrlMode2(String searchPattern, String productName) {
		return searchPattern.replace(Keys.BUSCA, productName.replaceAll(" ", "%20") );
	}
	
	public static void copyFolder(File src, File dest) throws IOException{
	 
    	if(src.isDirectory()){
 
    		//if directory not exists, create it
    		if(!dest.exists()){
    		   dest.mkdir();
    		   System.out.println("Directory copied from " 
                              + src + "  to " + dest);
    		}
 
    		//list all the directory contents
    		String files[] = src.list();
 
    		for (String file : files) {
    		   //construct the src and dest file structure
    		   File srcFile = new File(src, file);
    		   File destFile = new File(dest, file);
    		   //recursive copy
    		   copyFolder(srcFile,destFile);
    		}
 
    	}else{
    		//if file, then copy it
    		//Use bytes stream to support all file types
    		InputStream in = new FileInputStream(src);
    		OutputStream out = new FileOutputStream(dest); 
 
    	    byte[] buffer = new byte[1024];
 
    	    int length;
    	    //copy the file content in bytes 
    	    while ((length = in.read(buffer)) > 0){
    	    	out.write(buffer, 0, length);
    	    }
 
    	    in.close();
    	    out.close();
    	    System.out.println("File copied from " + src + " to " + dest);
    	}
    }
}
