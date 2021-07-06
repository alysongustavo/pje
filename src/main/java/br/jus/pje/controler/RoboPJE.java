package br.jus.pje.controler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import br.jus.pje.model.DadoRetorno;
import br.jus.pje.model.Usuario;
import br.jus.pje.util.Configuracoes;
import br.jus.pje.util.EscritorArquivo;

public class RoboPJE {
    
    private WebDriver driver = null;

    private boolean concluirColeta = false;

    private static String separador = "|";

    private InputStream arquivo;

    public RoboPJE(String urlPrincipal) throws IOException {
        driver = new ChromeDriver();
        driver.get(urlPrincipal);
    }

    private void dormir(int tempoSono) throws NumberFormatException, InterruptedException {

    	Thread.sleep(Long.parseLong(String.valueOf(tempoSono * 1000)));
    }

    private void focoAba(int aba) {

    	List<String> abas = new ArrayList<String>(driver.getWindowHandles());
    	driver.switchTo().window(abas.get(aba - 1));
    }
    
    private void fecharAba(int aba) {
    	
    	List<String> abas = new ArrayList<String>(driver.getWindowHandles());
    	driver.switchTo().window(abas.get(aba - 1));
    	driver.close();
    }

    public void direcionarLoginAdvogado() throws InterruptedException {

    	dormir(1);

        WebElement element = driver.findElement(By.xpath("/html/body/div[3]/div/div[1]/select/option[16]"));
        element.click();

        dormir(2);

        element = driver.findElement(By.xpath("/html/body/div[3]/div/div[2]/select/option[3]"));
        element.click();

        element = driver.findElement(By.xpath("/html/body/div[3]/div/div[3]/button"));
        element.click();
    }

    public void logarAdvogado() throws NumberFormatException, InterruptedException {

    	focoAba(2);

    	Usuario advogado = new Usuario();

    	WebElement element = driver.findElement(By.id("username"));
    	element.sendKeys(advogado.getCpf());

    	element = driver.findElement(By.id("password"));
    	element.sendKeys(advogado.getSenha());

    	element = driver.findElement(By.id("btnEntrar"));
    	element.click();

    	dormir(2);
    }

    public void navegarMenuProcessos() throws NumberFormatException, InterruptedException {

    	focoAba(2);

    	WebElement element = driver.findElement(By.linkText("Abrir menu"));
    	element.click();

    	dormir(1);

    	element = driver.findElement(By.xpath("//*[@id=\"menu\"]/div[2]/ul/li[1]/a"));
        element.click();

        dormir(1);

        element = driver.findElement(By.xpath("//*[@id=\"menu\"]/div[2]/ul/li[1]/div/ul/li[4]/a"));
        element.click();

        dormir(1);

        element = driver.findElement(By.xpath("//*[@id=\"menu\"]/div[2]/ul/li[1]/div/ul/li[4]/div/ul/li/a"));
        element.click();
    }

    public void pesquisarProcessos() throws NumberFormatException, InterruptedException, IOException {

    	dormir(2);

    	arquivo = new FileInputStream("C:\\Users\\gdasi\\IdeaProject\\pje\\src\\main\\resources\\filtro.properties");

		Properties prop = new Properties();
		prop.load(arquivo);

		System.out.println();

		String assunto = prop.getProperty("assunto");
		String classeJudicial = prop.getProperty("classeJudicial");

		String dataAtuacaoInicial = prop.getProperty("dataAtuacaoInicial");
		String dataAtuacaoFinal = prop.getProperty("dataAtuacaoFinal");

		String valorCausaInicial = prop.getProperty("valorCausaInicial");
		String valorCausaFinal = prop.getProperty("valorCausaFinal");

    	WebElement element = driver.findElement(By.id("fPP:j_id226:assunto"));
    	element.sendKeys(assunto);

    	if(!classeJudicial.isEmpty()){
			element = driver.findElement(By.id("fPP:j_id235:classeJudicial"));
			element.sendKeys(classeJudicial);
		}

    	if(!dataAtuacaoInicial.isEmpty() && !dataAtuacaoFinal.isEmpty()){
			element = driver.findElement(By.id("fPP:dataAutuacaoDecoration:dataAutuacaoInicioInputDate"));
			element.sendKeys(dataAtuacaoInicial);

			element = driver.findElement(By.id("fPP:dataAutuacaoDecoration:dataAutuacaoFimInputDate"));
			element.sendKeys(dataAtuacaoFinal);
		}

    	element = driver.findElement(By.id("fPP:valorDaCausaDecoration:valorCausaInicial"));
    	element.sendKeys(valorCausaInicial);


    	element = driver.findElement(By.id("fPP:valorDaCausaDecoration:valorCausaFinal"));
    	element.sendKeys(valorCausaFinal);

    	element = driver.findElement(By.id("fPP:searchProcessos"));
    	element.click();    	
    }

    public void proximaPagina(int paginaAtual) throws NumberFormatException, InterruptedException {

    	if (paginaAtual == 0) return;

   		driver.findElement(By.xpath("//*[@id=\"fPP:processosTable:scTabela_table\"]/tbody/tr/td[15]")).click();
   		dormir(10);
    }

    public void posicionarPaginaInicial(int paginaAtual) throws NumberFormatException, InterruptedException {

    	if (paginaAtual == 0) return;

    	for (int i = 0; i < paginaAtual; i++) {

    		dormir(10);

    		driver.findElement(By.xpath("//*[@id=\"fPP:processosTable:scTabela_table\"]/tbody/tr/td[15]")).click();
    		dormir(10);
    	}
    }

    public boolean obterListaProcessos(List<DadoRetorno> dados, int paginaArquivo, int linhaArquivo) throws NumberFormatException, InterruptedException, IOException {

    	focoAba(2);

    	dormir(15);

    	WebElement element = driver.findElement(By.id("fPP:processosTable:tb"));

    	List<WebElement> linhas = element.findElements(By.cssSelector("tr"));
    	List<WebElement> colunas = null;

    	concluirColeta = (Integer.valueOf(Configuracoes.PROCESSO_POR_PAGINA.getValor()).intValue() != linhas.size()) ? true : false;

    	int linhaAtual = 1;

    	for (WebElement linha : linhas) {

    		if (linhaArquivo != 0 && linhaAtual != linhaArquivo) {

    			linhaAtual++;
    			continue;
    		}

    		DadoRetorno dado = new DadoRetorno();

    		colunas = linha.findElements(By.cssSelector("td"));

    		for (WebElement coluna : colunas) {

    			WebElement tagA = coluna.findElement(By.cssSelector("a"));
    			tagA.click();

    			dormir(4);

    			driver.switchTo().alert().accept();

    			dormir(4);

    			focoAba(3);

    			WebElement dropdown = driver.findElement(By.xpath("//*[@id=\"navbar\"]/ul/li/a[1]"));
    			dado.setNrProcesso(dropdown.getText().substring(7, 30));

    			WebElement juris = driver.findElement(By.xpath("//*[@id=\"maisDetalhes\"]/dl/dd[3]"));
    			dado.setJurisdicao(juris.getAttribute("innerHTML"));

    			WebElement orgao = driver.findElement(By.xpath("//*[@id=\"maisDetalhes\"]/div[1]/dl/dd"));
    			dado.setOrgaoJulgador(orgao.getAttribute("innerHTML"));

    			WebElement valorCausa = driver.findElement(By.xpath("//*[@id=\"maisDetalhes\"]/dl/dd[6]"));
    			dado.setValorCausa(valorCausa.getAttribute("innerHTML"));

    			WebElement detalhes = driver.findElement(By.xpath("//*[@id=\"poloAtivo\"]/table/tbody"));

    			List<WebElement> linhasDetalhe = detalhes.findElements(By.cssSelector("tr"));

    			for (WebElement linhaDetalhe : linhasDetalhe) {

    				WebElement colunaDetalhe = linhaDetalhe.findElements(By.cssSelector("td")).get(0);
    				String documento = tratarCampoCPF(colunaDetalhe.getAttribute("innerHTML"));
    				String nome = tratarCampoNome(colunaDetalhe.getAttribute("innerHTML"));

    				dado.addParte(documento, nome);
    			}

//    			RoboCENPROT celia = new RoboCENPROT(dado);
//
//    			if (celia.temFofoca()) {
//
//	    			dados.add(dado);
//    			}

    			fecharAba(3);
    			focoAba(2);

    			escreverDados(dado, paginaArquivo, linhaAtual);

    			linhaAtual++;

    			break;
    		}
    	}

    	paginaArquivo++;
    	return concluirColeta;
    }

    private static void escreverDados(DadoRetorno dado, int paginaAtual, int linhaAtual) throws IOException {

    	EscritorArquivo escritor = new EscritorArquivo();
    	escritor.escreverLinha(montarInformacao(dado, paginaAtual, linhaAtual));
    }

    private static String[] montarInformacao(DadoRetorno dado, int paginaAtual, int linhaAtual) {

    	String[] retorno = new String[dado.getPartes().size()];

    	StringBuilder informacaoPrincipal = new StringBuilder();

   		informacaoPrincipal.append(dado.getNrProcesso()).append(separador)
    			.append(dado.getJurisdicao()).append(separador)
    			.append(dado.getOrgaoJulgador()).append(separador)
    			.append(dado.getValorCausa()).append(separador);

   		int controlador = 0;

   		for (Entry<String, String> parte : dado.getPartes().entrySet()) {

   			StringBuilder informacaoParte = new StringBuilder();

   			informacaoParte.append(parte.getValue()).append(separador)
   				.append(parte.getKey()).append(separador)
   				.append(paginaAtual).append(separador).append(linhaAtual);

   			retorno[controlador] = informacaoPrincipal.toString() + informacaoParte.toString();
   			controlador++;
   		}

    	return retorno;
    }

    private String tratarCampoCPF(String html) {

    	String documento = new String();

    	if (html.contains("CPF:")) {

    		documento = html.substring(html.indexOf(":") + 2, html.indexOf(":") + 16);
    	}

    	return documento;
    }

    private String tratarCampoNome(String html) {

    	String documento = new String();

    	try {
    		
    		documento = html.substring(html.indexOf(">") + 1, html.indexOf("-"));
    	} catch (Exception ex){

    		try {

    			documento = html.substring(html.indexOf(">") + 1, html.indexOf("/") -1);
    		} catch (Exception e){

    			documento = html;
    		}
    	}
    	return documento;
    }

//    private String obterDataHora() {
//
//    	LocalDateTime agora = LocalDateTime.now();
//
//    	DateTimeFormatter formatterData = DateTimeFormatter.ofPattern("dd/MM/uuuu");
//    	String dataFormatada = formatterData.format(agora);
//
//    	DateTimeFormatter formatterHora = DateTimeFormatter.ofPattern("HH:mm:ss");
//    	String horaFormatada = formatterHora.format(agora);
//
//    	return dataFormatada + " " + horaFormatada;
//    }
}
