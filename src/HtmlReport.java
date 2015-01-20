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
	private StringBuilder htmlLog;

	public HtmlReport() {
		htmlFinal = new StringBuilder();
		
		
		htmlFinal.append("<!DOCTYPE>\n");
		htmlFinal.append("<html>\n");
		htmlFinal.append("<head>\n");
		htmlFinal.append("\t<script src='jquery.js'></script>\n");
		htmlFinal.append("\t<script src='scripts.js'></script>\n");
		htmlFinal.append("\t<link rel='stylesheet' type='text/css' href='css.css'>\n");
		htmlFinal.append("</head>\n");
		htmlFinal.append("<body>\n");
		
		htmlFinal.append("\t<section class='wrapper' >\n");
		htmlFinal.append("\t\t<h1>Navegue pelas abas para outras Visualizações do Resultado</h1>\n");
		htmlFinal.append("\t\t<ul class='tabs'>\n");
		htmlFinal.append("\t\t\t<li><a href='#tab1'>Relatório</a></li>\n");
		htmlFinal.append("\t\t\t\t<li><a href='#tab2'>Outros Buscadores</a></li>\n");
		htmlFinal.append("\t\t\t<li><a href='#tab3'>Meta Dados</a></li>\n");
		htmlFinal.append("\t\t\t<li><a href='#tab4'>Log</a></li>\n");
		htmlFinal.append("\t\t</ul>\n");
		htmlFinal.append("\t\t<div class='clr'></div>\n");
		htmlFinal.append("\t\t<section class='block'>\n");	
		
		htmlReport = new StringBuilder();
	}
	
	public void addReport( Shop shop, List<Product> products){
		htmlReport.append("\t<table width=100% border=1>\n");

		htmlReport.append("\t\t<thead>\n");
		htmlReport.append("\t\t\t<tr>\n");
		htmlReport.append("\t\t\t<th colspan=3>\n");
		htmlReport.append("\t\t\t\tLoja: ").append(shop.getNome()).append("\n");
		htmlReport.append("\t\t\t</th>\n");
		htmlReport.append("\t\t\t\t</tr>\n");	
		htmlReport.append("\t\t</thead>\n");
		
		htmlReport.append("\t\t<tbody>");
		if( products != null && products.size() > 0 ){
			for( Product product: products){
				htmlReport.append("\t\t\t<tr>\n");
				htmlReport.append("\t\t\t\t<td width=50%>\n");
				htmlReport.append("\t\t\t\t\t<a href='");
				htmlReport.append(product.getUrl());
				htmlReport.append("'>\n");
				htmlReport.append(product.getName());
				htmlReport.append("\t\t\t\t\t</a>\n");
				htmlReport.append("\t\t\t\t</td>\n");
				htmlReport.append("\t\t\t\t<td width=50%>\n");
				htmlReport.append(product.getValue()).append("\n");
				htmlReport.append("\t\t\t\t</td>\n");
				htmlReport.append("\t\t\t</tr>\n");
			}
		}else{

			htmlReport.append("\t\t\t<tr>\n");
			htmlReport.append("\t\t\t\t<td colspan=3>\n");
			htmlReport.append("\t\t\t\t\tSem resultados\n");
			htmlReport.append("\t\t\t\t</td>\n");
			htmlReport.append("\t\t\t</tr>\n");
		}
		htmlReport.append("\t\t</tbody>\n");
		
		htmlReport.append("\t</table>\n");
		htmlReport.append("<br>\n");
	}
	
	public void closeAndWriteFile(String nameToSearch) throws IOException{
		
		insertReportsInHtml();
		insertSeekersInHtml();
		insertMetaInHtml();
		insertLogInHtml();
		insertBottomJavascript();
		
		htmlFinal.append("\t\t</section>\n");
		htmlFinal.append("\t</section>\n");
		htmlFinal.append("</body>\n");
		htmlFinal.append("</html>\n");
		
		File dir = new File(Util.getReportsPath()); 
		if (!dir.exists()) {
			dir.mkdir();
		}
		
		Util.copyFolder( new File(Util.getProjectPath() + "/resources/imports"), dir);
				
		File file = new File(Util.getReportsPath() + "/" + nameToSearch + ".html");
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
		htmlFinal.append("<article id='tab1'>\n");
		htmlFinal.append("<br>\n");
		htmlFinal.append( htmlReport.toString() );
		htmlFinal.append("</article>\n");
	}
	
	private void insertSeekersInHtml() {
		htmlFinal.append("<article id='tab2'>\n");
		htmlFinal.append("<br>\n");
		htmlFinal.append( htmlSeekers.toString() );
		htmlFinal.append("</article>\n");
	}
	
	private void insertMetaInHtml() {
		htmlFinal.append("<article id='tab3'>\n");
		htmlFinal.append("<br>\n");
		htmlFinal.append( htmlMeta.toString() );
		htmlFinal.append("</article>\n");
	}
	
	private void insertLogInHtml() {
		htmlFinal.append("<article id='tab4'>\n");
		htmlFinal.append("<br>\n");
		htmlFinal.append( htmlLog.toString() );
		htmlFinal.append("</article>\n");
	}

	private void insertBottomJavascript() {
		htmlFinal.append("<script>\n");
		htmlFinal.append("startTabs();\n");
		htmlFinal.append("</script>\n");
		
	}

	public StringBuilder getHtmlFinal() {
		return htmlFinal;
	}

	public void setHtmlFinal(StringBuilder htmlFinal) {
		this.htmlFinal = htmlFinal;
	}

	public void addOtherSeekers(String nameToSearch) throws MalformedURLException, URISyntaxException {
		htmlSeekers = new StringBuilder();
		htmlSeekers.append("<table width=100% border=1>\n");
		htmlSeekers.append("\t<thead>\n");
		htmlSeekers.append("\t\t<tr>\n");
		htmlSeekers.append("\t\t\t<th colspan=3>\n");
		htmlSeekers.append("\t\t\t\t<b>Outros Buscadores</b>\n");
		htmlSeekers.append("\t\t\t</th>\n");
		htmlSeekers.append("\t\t</tr>\n");	
		htmlSeekers.append("\t</thead>\n");
		
		htmlSeekers.append("\t<tbody>\n");

		htmlSeekers.append("\t\t<tr>\n");
		htmlSeekers.append("\t\t\t<td width=50%>\n");
		htmlSeekers.append("\t\t\t\tBuscapé\n");
		htmlSeekers.append("\t\t\t</td>\n");
		htmlSeekers.append("\t\t\t<td width=50%>\n");
		htmlSeekers.append("\t\t\t\t<a href='http://www.buscape.com.br/cprocura?produto="+Util.stringToUrl(nameToSearch)+"&fromSearchBox=true'> Clique Aqui </a>\n");
		htmlSeekers.append("\t\t\t</td>\n");
		htmlSeekers.append("\t\t</tr>\n");
		
		htmlSeekers.append("\t\t<tr>\n");
		htmlSeekers.append("\t\t\t<td width=50%>\n");
		htmlSeekers.append("\t\t\t\tBondFaro\n");
		htmlSeekers.append("\t\t\t</td>\n");
		htmlSeekers.append("\t\t\t<td width=50%>\n");
		htmlSeekers.append("\t\t\t\t<a href='http://www.bondfaro.com.br/cprocura?produto="+Util.stringToUrl(nameToSearch)+"&fromSearchBox=true'> Clique Aqui </a>\n");
		htmlSeekers.append("\t\t\t</td>\n");
		htmlSeekers.append("\t\t</tr>\n");
		
		htmlSeekers.append("\t\t<tr>\n");
		htmlSeekers.append("\t\t\t<td width=50%>\n");
		htmlSeekers.append("\t\t\t\tShopping UOL\n");
		htmlSeekers.append("\t\t\t</td>\n");
		htmlSeekers.append("\t\t\t<td width=50%>\n");
		htmlSeekers.append("\t\t\t\t<a href='http://shopping.uol.com.br/busca.html?natural=sim&q="+Util.stringToUrl(nameToSearch)+"'> Clique Aqui </a>\n");
		htmlSeekers.append("\t\t\t</td>\n");
		htmlSeekers.append("\t\t</tr>\n");
		
		htmlSeekers.append("\t\t<tr>\n");
		htmlSeekers.append("\t\t\t<td width=50%>\n");
		htmlSeekers.append("\t\t\t\tGoogle Shopping\n");
		htmlSeekers.append("\t\t\t</td>\n");
		htmlSeekers.append("\t\t\t<td width=50%>\n");
		htmlSeekers.append("\t\t\t\t<a href='https://www.google.com.br/search?q=google&oq=google&aqs=chrome..69i57j69i60l3j69i65l2.679j0j7&sourceid=chrome&es_sm=93&ie=UTF-8#q="+Util.stringToUrl(nameToSearch)+"&tbm=shop'> Clique Aqui </a>\n");
		htmlSeekers.append("\t\t\t</td>\n");
		htmlSeekers.append("\t\t</tr>\n");
		
		htmlSeekers.append("\t\t<tr>\n");
		htmlSeekers.append("\t\t\t<td width=50%>\n");
		htmlSeekers.append("\t\t\t\tZOOM\n");
		htmlSeekers.append("\t\t\t</td>\n");
		htmlSeekers.append("\t\t\t<td width=50%>\n");
		htmlSeekers.append("\t\t\t\t<a href='http://www.zoom.com.br/search?q="+Util.stringToUrl(nameToSearch)+"'> Clique Aqui </a>\n");
		htmlSeekers.append("\t\t\t</td>\n");
		htmlSeekers.append("\t\t</tr>\n");
		
		htmlSeekers.append("\t\t<tr>\n");
		htmlSeekers.append("\t\t\t<td width=50%>\n");
		htmlSeekers.append("\t\t\t\tJá cotei\n");
		htmlSeekers.append("\t\t\t</td>\n");
		htmlSeekers.append("\t\t\t<td width=50%>\n");
		htmlSeekers.append("\t\t\t\t<a href='http://www.jacotei.com.br/busca/?texto="+Util.stringToUrl(nameToSearch)+"'> Clique Aqui </a>\n");
		htmlSeekers.append("\t\t\t</td>\n");
		htmlSeekers.append("\t\t</tr>\n");
		
		htmlSeekers.append("\t\t<tr>\n");
		htmlSeekers.append("\t\t\t<td width=50%>\n");
		htmlSeekers.append("\t\t\t\tTwenga\n");
		htmlSeekers.append("\t\t\t</td>\n");
		htmlSeekers.append("\t\t\t<td width=50%>\n");
		htmlSeekers.append("\t\t\t\t<a href='http://www.twenga.com.br/#q="+Util.stringToUrl(nameToSearch)+"&u=q'> Clique Aqui </a>\n");
		htmlSeekers.append("\t\t\t</td>\n");
		htmlSeekers.append("\t\t</tr>\n");

		htmlSeekers.append("\t\t<tr>\n");
		htmlSeekers.append("\t\t\t<td width=50%>\n");
		htmlSeekers.append("\t\t\t\tCompare games\n");
		htmlSeekers.append("\t\t\t</td>\n");
		htmlSeekers.append("\t\t\t<td width=50%>\n");
		htmlSeekers.append("\t\t\t\t<a href='http://www.comparegames.com.br/comprar/"+Util.stringToUrl(nameToSearch)+"'> Clique Aqui </a>\n");
		htmlSeekers.append("\t\t\t</td>\n");
		htmlSeekers.append("\t\t</tr>\n");
		
		

		htmlSeekers.append("\t</tbody>\n");
		
		htmlSeekers.append("</table>\n");
		htmlSeekers.append("<br>\n");
	}

	public void addMetaData(String nameToSearch, long l, String data, String hora) {
		htmlMeta = new StringBuilder();
		htmlMeta.append("<table width=100% border=1>\n");
		htmlMeta.append("\t<thead>\n");
		htmlMeta.append("\t\t<tr>\n");
		htmlMeta.append("\t\t\t<th colspan=3>\n");
		htmlMeta.append("\t\t\t\t<b>Meta Dados</b>\n");
		htmlMeta.append("\t\t\t</th>\n");
		htmlMeta.append("\t\t</tr>\n");	
		htmlMeta.append("\t</thead>\n");
		
		htmlMeta.append("\t<tbody>\n");

		htmlMeta.append("\t\t<tr>\n");
		htmlMeta.append("\t\t\t<td width=50%>\n");
		htmlMeta.append("\t\t\t\tTermo Buscado\n");
		htmlMeta.append("\t\t\t</td>\n");
		htmlMeta.append("\t\t\t<td width=50%>\n");
		htmlMeta.append(nameToSearch);
		htmlMeta.append("\t\t\t\t</td>\n");
		htmlMeta.append("\t\t</tr>\n");
		
		htmlMeta.append("\t\t<tr>\n");
		htmlMeta.append("\t\t\t<td width=50%>\n");
		htmlMeta.append("\t\t\t\tTempo Para buscar\n");
		htmlMeta.append("\t\t\t</td>\n");
		htmlMeta.append("\t\t\t<td width=50%>\n");
		htmlMeta.append(l);
		htmlMeta.append("\t\t\t</td>\n");
		htmlMeta.append("\t\t</tr>\n");
		
		htmlMeta.append("\t\t<tr>\n");
		htmlMeta.append("\t\t\t<td width=50%>\n");
		htmlMeta.append("\t\t\t\tData\n");
		htmlMeta.append("\t\t\t</td>\n");
		htmlMeta.append("\t\t\t<td width=50%>\n");
		htmlMeta.append(data);
		htmlMeta.append("\t\t\t</td>\n");
		htmlMeta.append("\t\t</tr>\n");
		
		htmlMeta.append("\t\t<tr>\n");
		htmlMeta.append("\t\t\t<td width=50%>\n");
		htmlMeta.append("\t\t\t\tHora\n");
		htmlMeta.append("\t\t\t</td>\n");
		htmlMeta.append("\t\t\t<td width=50%>\n");
		htmlMeta.append(hora);
		htmlMeta.append("\t\t\t</td>\n");
		htmlMeta.append("\t\t</tr>\n");
	
		htmlMeta.append("\t</tbody>\n");
		
		htmlMeta.append("</table>\n");
		
		htmlMeta.append("<br>\n");
	}

	public void addLogTab(String string) {
		htmlLog = new StringBuilder();
		
		htmlLog.append("<iframe src='./trace.log' style='width:100%; height:80%; border: 0px; none;'>\n");
		htmlLog.append("</iframe>\n");
		
	}
	
	
}
