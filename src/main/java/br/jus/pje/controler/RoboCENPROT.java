package br.jus.pje.controler;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.opera.OperaDriver;

import br.jus.pje.model.DadoRetorno;
import br.jus.pje.util.Configuracoes;

public class RoboCENPROT {

	private WebDriver driver = null;
	private DadoRetorno dado = null;

	public RoboCENPROT(DadoRetorno dado) {

		this.dado = dado;

		driver = new OperaDriver();
        driver.get(Configuracoes.URL_CENPROT.getValor());
	}

	public boolean temFofoca() {

		return possuiProtesto();
	}

	private boolean possuiProtesto() {

		boolean retorno = false; 

//		for (String documento : dado.getDocumentos()) {
//
//			if (documento != null && !documento.isEmpty()) {
//
//				WebElement element = driver.findElement(By.id("cpf_cnpj"));
//				element.sendKeys(documento);
//
//				element = driver.findElement(By.id("bt-consultar"));
//				element.click();
//
//				element = driver.findElement(By.xpath("//*[@id=\"wrapper-estados\"]/div/div[1]/h4"));
//				System.out.println(element.getText());
//				System.out.println(element.getAttribute("innerHTML"));
//			}
//		}

		return retorno;
	}
}
