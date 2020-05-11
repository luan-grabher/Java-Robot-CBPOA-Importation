package cbpoa_importacao;

import Entity.Executavel;
import Executor.Execution;
import Robo.AppRobo;
import TemplateContabil.Control.ControleTemplates;
import java.util.ArrayList;
import java.util.List;

public class CBPOA_Importacao {

    public static void main(String[] args) {
        String nomeApp = "CB POA Importacao ";
        AppRobo robo = new AppRobo(nomeApp);

        robo.definirParametros();
        int mes = robo.getParametro("mes").getMes();
        int ano = robo.getParametro("ano").getInteger();

        nomeApp += mes + "/" + ano;
        robo.setNome(nomeApp);
        robo.executar(principal(nomeApp, mes, ano));
    }

    public static String principal(String nomeApp, int mes, int ano) {
        try {
            String r = "";

            ControleTemplates controle = new ControleTemplates(mes, ano, 645, "CB Porto Alegre Comercio de Alimentos Ltda");
            controle.definirFilesAndPaths();
            controle.definirVariaveisEstaticasModeloBanco();

            System.out.println("Definindo execução");
            List<Executavel> executaveis = new ArrayList<>();
            executaveis.add(controle.new definirFileTemplatePadrao());
            executaveis.add(controle.new importacaoPadraoBancoOfx("Banrisul", "banri", 13));
            executaveis.add(controle.new importacaoPadraoBancoOfx("Bradesco", "brad", 9));
            executaveis.add(controle.new importacaoPadraoBancoOfx("Banco do Brasil", "extrato;bb", 7));

            System.out.println("Iniciando execução");
            Execution execucao = new Execution(nomeApp);
            execucao.setExecutaveis(executaveis);
            execucao.setMostrarMensagens(false);
            execucao.rodarExecutaveis();
            r = execucao.getRetorno();

            return r;
        } catch (Exception e) {
            e.printStackTrace();
            return "Erro inesperado no Java: " + e;
        }

    }

}
