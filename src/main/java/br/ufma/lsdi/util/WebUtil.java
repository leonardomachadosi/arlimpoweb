package br.ufma.lsdi.util;

import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

public class WebUtil {
    public static String BUSCAR_SUCESSO = "Pesquisa realizada com sucesso.";
    public static String BUSCAR_OBRIGATORIO = "Preencha o campo.";
    public static String BUSCAR_ERROR = "Ops :(, ocorreu um erro ao realizar essa pesquisa, tente novamente.";
    public static String BUSCA_SEM_RESULTADOS = "Nenhum registro encontrado!";
    public static String DADOS_SALVO = "Seus dados foram salvos com sucesso.";
    public static String DADOS_ATUALIZADO = "Seus dados foram atualizados com sucesso.";
    public static String DADOS_EXCLUIDO = "Seus dados foram excluido com sucesso.";
    public static String ERROR_PADRAO = "Ops :(, ocorreu um erro ao realizar essa operação, tente novamente.";
    public static String ERROR_SALVAR = "Erro ao salvar, por favor verifique!";
    public static String ERROR_EXCLUIR = "Erro ao excluir, verifique se o registro não possua algum vinculo!";
    public static String OPERACAO_SUCESSO = "Operação realizado com sucesso!";


    public static FacesContext getContext() {
        return FacesContext.getCurrentInstance();
    }

    public static Flash flashScope() {
        return (FacesContext.getCurrentInstance().getExternalContext().getFlash());
    }

}
