import interfaces.search.AmericanasSearch;
import interfaces.search.BalaoDaInformaticaSearch;
import interfaces.search.BigBoyGamesSearch;
import interfaces.search.CasasBahiaSearch;
import interfaces.search.EVirtuaSearch;
import interfaces.search.ExtraSearch;
import interfaces.search.FNACSearch;
import interfaces.search.FastGamesSearch;
import interfaces.search.FastShopSearch;
import interfaces.search.KabumSearch;
import interfaces.search.LivrariaCulturaSearch;
import interfaces.search.MagazineLuizaSearch;
import interfaces.search.MulaGamesSearch;
import interfaces.search.NetShoesSearch;
import interfaces.search.NintendoEShopSearch;
import interfaces.search.PSStoreSearch;
import interfaces.search.PontoFrioSearch;
import interfaces.search.RiHappySearch;
import interfaces.search.RicardoEletroSearch;
import interfaces.search.SaraivaSearch;
import interfaces.search.ShopBSearch;
import interfaces.search.ShopFacilSearch;
import interfaces.search.ShopTimeSearch;
import interfaces.search.SubmarinoSearch;
import interfaces.search.WalmartSearch;
import interfaces.search.ZigStoreSearch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import objects.Shop;
import comom.DefaultFilters;
import comom.MyPrintStream;
import comom.Util;


public class Main {

	//private final static String gameListAdress = "C:/java/rep/LookForPrices/resources/gamelist.txt";
	private static String gameListAdress = "";
	private static String logAdress = "";
	private static TotalsReport totalsReport;
	
	public static void main(String[] args) throws IOException, URISyntaxException {

		List<Shop> shops = getAllShopsConfig();
		
		gameListAdress = Util.getProjectPath() + "/resources/finalgamelist.txt";
		File gameList = readFile();
		
		totalsReport = new TotalsReport( shops );
		totalsReport.generateHeaders();
		
		for( String gameName: Util.ler(gameList) ){
			configurarSaida(gameName);
			System.out.println("Game: "+gameName);
			generateHtmlReport(gameName, shops);
		}
		
		totalsReport.closeAndWriteFile();
		
		/*
		String gameName = "GameName";
		configurarSaida(gameName);
		generateHtmlReport(gameName, shops);
		*/
	} 
		
	private static void configurarSaida(String productName) throws FileNotFoundException, IOException {
		logAdress = Util.getReportsPath() + "/" + productName + ".log";
		PrintStream fileStream = new MyPrintStream(new FileOutputStream( logAdress, true ), System.out);
		
		System.setOut(fileStream);
		System.setErr(fileStream);
		
	}

	public static void generateHtmlReport(String nameToSearch, List<Shop> shops) throws URISyntaxException, IOException{ 
		long time = System.currentTimeMillis();

		System.out.println("Product Name: "+nameToSearch);
		
		GamesReport htmlReport = new GamesReport(nameToSearch);

		String data = new SimpleDateFormat("dd/MM/yyyy").format(new Date());

		DateFormat df = new SimpleDateFormat("HH:mm");
		Date myDate = new Date(System.currentTimeMillis());
		String hora = df.format(myDate);

		for(Shop shop: shops){
			htmlReport.addReport(shop, shop.searchProduct(nameToSearch, DefaultFilters.containAllWords() ) );
		}

		htmlReport.addOtherSeekers(nameToSearch);
		
		htmlReport.addMetaData(nameToSearch, (System.currentTimeMillis() - time), data, hora);
		
		htmlReport.addLogTab( nameToSearch );
		
		htmlReport.closeAndWriteFile(nameToSearch);
		
		totalsReport.generateContent(shops, nameToSearch);
		
		System.out.println("Tempo total de execu��o: "+(System.currentTimeMillis() - time));
	}
	
	private static List<Shop> getAllShopsConfig() {
		List<Shop> shops = new ArrayList<Shop>();
		
		shops.add( new Shop( "Nintendo eShop", "http://www.nintendo.com/3ds/eshop", "http://www.nintendo.com/json/content/get/game/filter?&qterm=<BUSCA>", new NintendoEShopSearch() ) );
		shops.add( new Shop( "PlayStation Store", "https://store.playstation.com/#!/pt-br/home/main", "https://store.playstation.com/#!/pt-br/pesquisar/q=<BUSCA>", new PSStoreSearch() ) );

		shops.add( new Shop( "Americanas", "http://www.americanas.com.br/", "http://busca.americanas.com.br/busca.php?q=<BUSCA>", new AmericanasSearch() ) );
		shops.add( new Shop( "Big Boy Games", "http://www.bigboygames.com.br/", "http://www.bigboygames.com.br/pesquisa/?p=<BUSCA>", new BigBoyGamesSearch() ) );
		shops.add( new Shop( "Bal�o da Inform�tica", "https://www.balaodainformatica.com.br/", "http://www.balaodainformatica.com.br/digimon?&utmi_p=_Produtos_Buscar_&utmi_pc=BuscaFullText&utmi_cp=<BUSCA>", new BalaoDaInformaticaSearch() ) );
		shops.add( new Shop( "Casas Bahia", "http://www.casasbahia.com.br/", "http://buscas.casasbahia.com.br/search?w=<BUSCA>", new CasasBahiaSearch() ) );
		shops.add( new Shop( "Extra", "http://www.extra.com.br/", "http://buscando.extra.com.br/search?w=<BUSCA>", new ExtraSearch() ) );
		shops.add( new Shop( "E-Virtua", "http://www.evirtua.com.br/", "http://www.evirtua.com.br/Busca?search_department_id=0&SearchTerm=<BUSCA>", new EVirtuaSearch() ) );
		shops.add( new Shop( "Fast Games", "http://www.fastgames.com.br/", "http://www.fastgames.com.br/loja/busca.php?loja=187970&palavra_busca=<BUSCA>", new FastGamesSearch() ) );
		shops.add( new Shop( "Fast Shop", "http://www.fastshop.com.br/loja/", "https://www.fastshop.com.br/webapp/wcs/stores/servlet/SearchDisplay?searchTerm=<BUSCA>&categoryId=&storeId=10151&catalogId=11052&langId=-6&pageSize=9&beginIndex=0&sType=SimpleSearch&resultCatEntryType=2&showResultsPage=true&searchSource=Q&pageView=&hotsite=fastshop", new FastShopSearch() ) );
		shops.add( new Shop( "FNAC", "http://www.fnac.com.br/index.html", "http://www.fnac.com.br/<BUSCA>", new FNACSearch() ) );
		shops.add( new Shop( "Kabum", "http://www.kabum.com.br/", "http://www.kabum.com.br/cgi-local/site/listagem/listagem.cgi?string=<BUSCA>&btnG=", new KabumSearch() ) );
		shops.add( new Shop( "Livraria Cultura", "http://www.livrariacultura.com.br/", "http://www.livrariacultura.com.br/busca?N=0&Ntt=<BUSCA>", new LivrariaCulturaSearch() ) );
		shops.add( new Shop( "Magazine Luiza", "http://www.magazineluiza.com.br/", "http://www.magazineluiza.com.br/busca/<BUSCA>/", new MagazineLuizaSearch() ) );
		shops.add( new Shop( "Mula Games", "http://www.mulagames.com.br/", "https://www.mulagames.com.br/busca-busca_produtos?nome_produto=<BUSCA>", new MulaGamesSearch() ) );
		shops.add( new Shop( "Net Shoes", "http://www.netshoes.com.br/", "http://www.netshoes.com.br/search?Ntt=<BUSCA>", new NetShoesSearch()) );
		shops.add( new Shop( "Ponto Frio", "http://www.pontofrio.com.br/", "http://search.pontofrio.com.br/search?w=<BUSCA>", new PontoFrioSearch() ) );
		shops.add( new Shop( "Ricardo Eletro", "http://www.ricardoeletro.com.br/", "http://www.ricardoeletro.com.br/Busca/Resultado/?q=<BUSCA>", new RicardoEletroSearch() ) );
		shops.add( new Shop( "RiHappy", "http://www.rihappy.com.br/", "http://www.rihappy.com.br/<BUSCA>", new RiHappySearch() ) );
		shops.add( new Shop( "Saraiva", "http://www.saraiva.com.br/", "http://busca.saraiva.com.br/?q=<BUSCA>", new SaraivaSearch() ) );
		shops.add( new Shop( "Shop Facil", "http://ww2.shopfacil.com.br/", "http://www.shopfacil.com.br/<BUSCA>", new ShopFacilSearch( )) );
		shops.add( new Shop( "Shop Time", "http://www.shoptime.com.br/", "http://busca.shoptime.com.br/busca.php?q=<BUSCA>", new ShopTimeSearch()) );
		shops.add( new Shop( "ShopB", "http://www.shopb.com.br/", "http://www.shopb.com.br/buscar?q=<BUSCA>", new ShopBSearch() ) );
		shops.add( new Shop( "Submarino", "http://www.submarino.com.br/", "http://busca.submarino.com.br/busca.php?q=<BUSCA>", new SubmarinoSearch() ) );
		shops.add( new Shop( "Walmart", "http://www.walmart.com.br/", "http://www.walmart.com.br/busca/?ft=<BUSCA>&PS=20", new WalmartSearch() ) );
		shops.add( new Shop( "Zig Store", "http://www.zigstore.com.br/", "http://www.zigstore.com.br/pesquisa/?p=<BUSCA>", new ZigStoreSearch() ) );
		
		
		return shops;
	}

	private static File readFile() {
		return new File( gameListAdress );
	}
}
