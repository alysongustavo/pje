package br.jus.pje;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import br.jus.pje.controler.RoboPJE;
import br.jus.pje.model.DadoRetorno;
import br.jus.pje.util.Configuracoes;
import br.jus.pje.util.LeitorArquivo;

public class App {

    public static void main(String[] args) throws InterruptedException, IOException {

    	try {
    		
    		LeitorArquivo leitor = new LeitorArquivo();
    		int[] dadosNavegacao = leitor.obterUltimaPaginaLinha();

    		RoboPJE neri = new RoboPJE(Configuracoes.URL_PJE.getValor());
    		List<DadoRetorno> dados = new LinkedList<DadoRetorno>();

    		neri.direcionarLoginAdvogado();
    		neri.logarAdvogado();
    		neri.navegarMenuProcessos();

    		neri.pesquisarProcessos();

    		int paginaArquivo = dadosNavegacao[0];
    		int linhaArquivo = dadosNavegacao[1];

    		neri.posicionarPaginaInicial(paginaArquivo);

    		do {

    			neri.proximaPagina(paginaArquivo);

    			paginaArquivo++;
    		} while (!neri.obterListaProcessos(dados, paginaArquivo, linhaArquivo));
    	} catch (Exception ex) {

    		main(null);
    	}
    }
}
