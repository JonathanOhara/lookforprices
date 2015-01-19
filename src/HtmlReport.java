import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

import objects.Product;
import objects.Shop;

import comom.Util;


public class HtmlReport {

	private StringBuilder htmlFinal;
	private StringBuilder htmlReport;
	private StringBuilder htmlSeekers;
	private StringBuilder htmlMeta;

	public HtmlReport() {
		htmlFinal = new StringBuilder();
		
		htmlFinal.append("<!DOCTYPE>");
		htmlFinal.append("<html>");
		htmlFinal.append("<head>");
		htmlFinal.append("<script src='jquery.js'></script>");
		htmlFinal.append("<script src='scripts.js'></script>");
		htmlFinal.append("<link rel='stylesheet' type='text/css' href='css.css'>");
		htmlFinal.append("</head>");
		htmlFinal.append("<body>");
		
		htmlFinal.append("<section class='wrapper' >");
		htmlFinal.append("<h1>Navegue pelas abas para outras Visualizações do Resultado</h1>");
		htmlFinal.append("<ul class='tabs'>");
		htmlFinal.append("<li><a href='#tab1'>Relatório</a></li>");
		htmlFinal.append("<li><a href='#tab2'>Outros Buscadores</a></li>");
		htmlFinal.append("<li><a href='#tab3'>Meta Dados</a></li>");
		htmlFinal.append("</ul>");
		htmlFinal.append("<div class='clr'></div>");
		htmlFinal.append("<section class='block'>");	
		
		htmlReport = new StringBuilder();
	}
	
	public void addReport( Shop shop, List<Product> products){
		htmlReport.append("<table width=100% border=1>");

		htmlReport.append("<thead>");
		htmlReport.append("<tr>");
		htmlReport.append("<th colspan=3>");
		htmlReport.append("Loja: ").append(shop.getNome());
		htmlReport.append("</th>");
		htmlReport.append("</tr>");	
		htmlReport.append("</thead>");
		
		htmlReport.append("<tbody>");
		if( products != null && products.size() > 0 ){
			for( Product product: products){
				htmlReport.append("<tr>");
				htmlReport.append("<td width=50%>");
				htmlReport.append("<a href='");
				htmlReport.append(product.getUrl());
				htmlReport.append("'>");
				htmlReport.append(product.getName());
				htmlReport.append("</a>");
				htmlReport.append("</td>");
				htmlReport.append("<td width=50%>");
				htmlReport.append(product.getValue());
				htmlReport.append("</td>");
				htmlReport.append("</tr>");
			}
		}else{

			htmlReport.append("<tr>");
			htmlReport.append("<td colspan=3>");
			htmlReport.append("Sem resultados");
			htmlReport.append("</td>");
			htmlReport.append("</tr>");
		}
		htmlReport.append("</tbody>");
		
		htmlReport.append("</table>");
		htmlReport.append("<br>");
	}
	
	public void closeAndWriteFile(String nameToSearch) throws IOException{
		
		insertReportsInHtml();
		insertSeekersInHtml();
		insertMetaInHtml();
		insertBottomJavascript();
		
		htmlFinal.append("</section>");
		htmlFinal.append("</section>");
		htmlFinal.append("</body>");
		htmlFinal.append("</html>");
		
		File dir = new File(Util.getProjectPath() + "/reports/"); 
		if (!dir.exists()) {
			dir.mkdir();
		}
		
		Util.copyFolder( new File(Util.getProjectPath() + "/resources/imports"), dir);
				
		File file = new File(Util.getProjectPath() + "/reports/" +nameToSearch+ ".html");
		if (!file.exists()) {
			file.createNewFile();
		}

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		
		bw.write(htmlFinal.toString());
		
		bw.flush();
		bw.close();
	}
	
	private void insertReportsInHtml() {
		htmlFinal.append("<article id='tab1'>");
		htmlFinal.append("<br>");
		htmlFinal.append( htmlReport.toString() );
		htmlFinal.append("</article>");
	}
	
	private void insertSeekersInHtml() {
		htmlFinal.append("<article id='tab2'>");
		htmlFinal.append("<br>");
		htmlFinal.append( htmlSeekers.toString() );
		htmlFinal.append("</article>");
	}
	
	private void insertMetaInHtml() {
		htmlFinal.append("<article id='tab3'>");
		htmlFinal.append("<br>");
		htmlFinal.append( htmlMeta.toString() );
		htmlFinal.append("</article>");
	}

	private void insertBottomJavascript() {
		htmlFinal.append("<script>");
		htmlFinal.append("startTabs();");
		htmlFinal.append("</script>");
		
	}

	public StringBuilder getHtmlFinal() {
		return htmlFinal;
	}

	public void setHtmlFinal(StringBuilder htmlFinal) {
		this.htmlFinal = htmlFinal;
	}

	public void addOtherSeekers(String nameToSearch) throws MalformedURLException, URISyntaxException {
		htmlSeekers = new StringBuilder();
		htmlSeekers.append("<table width=100% border=1>");
		htmlSeekers.append("<thead>");
		htmlSeekers.append("<tr>");
		htmlSeekers.append("<th colspan=3>");
		htmlSeekers.append("<b>Outros Buscadores</b>");
		htmlSeekers.append("</th>");
		htmlSeekers.append("</tr>");	
		htmlSeekers.append("</thead>");
		
		htmlSeekers.append("<tbody>");

		htmlSeekers.append("<tr>");
		htmlSeekers.append("<td width=50%>");
		htmlSeekers.append("Buscapé");
		htmlSeekers.append("</td>");
		htmlSeekers.append("<td width=50%>");
		htmlSeekers.append("<a href='http://www.buscape.com.br/cprocura?produto="+Util.stringToUrl(nameToSearch)+"&fromSearchBox=true'> Clique Aqui </a>");
		htmlSeekers.append("</td>");
		htmlSeekers.append("</tr>");
		
		htmlSeekers.append("<tr>");
		htmlSeekers.append("<td width=50%>");
		htmlSeekers.append("BondFaro");
		htmlSeekers.append("</td>");
		htmlSeekers.append("<td width=50%>");
		htmlSeekers.append("<a href='http://www.bondfaro.com.br/cprocura?produto="+Util.stringToUrl(nameToSearch)+"&fromSearchBox=true'> Clique Aqui </a>");
		htmlSeekers.append("</td>");
		htmlSeekers.append("</tr>");
		
		htmlSeekers.append("<tr>");
		htmlSeekers.append("<td width=50%>");
		htmlSeekers.append("Shopping UOL");
		htmlSeekers.append("</td>");
		htmlSeekers.append("<td width=50%>");
		htmlSeekers.append("<a href='http://shopping.uol.com.br/busca.html?natural=sim&q="+Util.stringToUrl(nameToSearch)+"'> Clique Aqui </a>");
		htmlSeekers.append("</td>");
		htmlSeekers.append("</tr>");
		
		htmlSeekers.append("<tr>");
		htmlSeekers.append("<td width=50%>");
		htmlSeekers.append("Google Shopping");
		htmlSeekers.append("</td>");
		htmlSeekers.append("<td width=50%>");
		htmlSeekers.append("<a href='https://www.google.com.br/search?q=google&oq=google&aqs=chrome..69i57j69i60l3j69i65l2.679j0j7&sourceid=chrome&es_sm=93&ie=UTF-8#q="+Util.stringToUrl(nameToSearch)+"&tbm=shop'> Clique Aqui </a>");
		htmlSeekers.append("</td>");
		htmlSeekers.append("</tr>");
		
		htmlSeekers.append("<tr>");
		htmlSeekers.append("<td width=50%>");
		htmlSeekers.append("ZOOM");
		htmlSeekers.append("</td>");
		htmlSeekers.append("<td width=50%>");
		htmlSeekers.append("<a href='http://www.zoom.com.br/search?q="+Util.stringToUrl(nameToSearch)+"'> Clique Aqui </a>");
		htmlSeekers.append("</td>");
		htmlSeekers.append("</tr>");
		
		htmlSeekers.append("<tr>");
		htmlSeekers.append("<td width=50%>");
		htmlSeekers.append("Já cotei");
		htmlSeekers.append("</td>");
		htmlSeekers.append("<td width=50%>");
		htmlSeekers.append("<a href='http://www.jacotei.com.br/busca/?texto="+Util.stringToUrl(nameToSearch)+"'> Clique Aqui </a>");
		htmlSeekers.append("</td>");
		htmlSeekers.append("</tr>");
		
		htmlSeekers.append("<tr>");
		htmlSeekers.append("<td width=50%>");
		htmlSeekers.append("Twenga");
		htmlSeekers.append("</td>");
		htmlSeekers.append("<td width=50%>");
		htmlSeekers.append("<a href='http://www.twenga.com.br/#q="+Util.stringToUrl(nameToSearch)+"&u=q'> Clique Aqui </a>");
		htmlSeekers.append("</td>");
		htmlSeekers.append("</tr>");

		htmlSeekers.append("</tbody>");
		
		htmlSeekers.append("</table>");
		htmlSeekers.append("<br>");
	}

	public void addMetaData(String nameToSearch, long l, String data, String hora) {
		htmlMeta = new StringBuilder();
		htmlMeta.append("<table width=100% border=1>");
		htmlMeta.append("<thead>");
		htmlMeta.append("<tr>");
		htmlMeta.append("<th colspan=3>");
		htmlMeta.append("<b>Meta Dados</b>");
		htmlMeta.append("</th>");
		htmlMeta.append("</tr>");	
		htmlMeta.append("</thead>");
		
		htmlMeta.append("<tbody>");

		htmlMeta.append("<tr>");
		htmlMeta.append("<td width=50%>");
		htmlMeta.append("Termo Buscado");
		htmlMeta.append("</td>");
		htmlMeta.append("<td width=50%>");
		htmlMeta.append(nameToSearch);
		htmlMeta.append("</td>");
		htmlMeta.append("</tr>");
		
		htmlMeta.append("<tr>");
		htmlMeta.append("<td width=50%>");
		htmlMeta.append("Tempo Para buscar");
		htmlMeta.append("</td>");
		htmlMeta.append("<td width=50%>");
		htmlMeta.append(l);
		htmlMeta.append("</td>");
		htmlMeta.append("</tr>");
		
		htmlMeta.append("<tr>");
		htmlMeta.append("<td width=50%>");
		htmlMeta.append("Data");
		htmlMeta.append("</td>");
		htmlMeta.append("<td width=50%>");
		htmlMeta.append(data);
		htmlMeta.append("</td>");
		htmlMeta.append("</tr>");
		
		htmlMeta.append("<tr>");
		htmlMeta.append("<td width=50%>");
		htmlMeta.append("Hora");
		htmlMeta.append("</td>");
		htmlMeta.append("<td width=50%>");
		htmlMeta.append(hora);
		htmlMeta.append("</td>");
		htmlMeta.append("</tr>");
	
		htmlMeta.append("</tbody>");
		
		htmlMeta.append("</table>");
		
		htmlMeta.append("<br>");
	}
	
	
}
