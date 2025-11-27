/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package cadastroee.servlets;

import cadastroee.controller.ProdutoFacadeLocal;
import cadastroee.model.Produto;
import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "ServletProdutoFC", urlPatterns = {"/ServletProdutoFC"})
public class ServletProdutoFC extends HttpServlet {

    @EJB
    private ProdutoFacadeLocal facade;

    private Integer getInt(HttpServletRequest request, String nomeParam) {
        try {
            String valor = request.getParameter(nomeParam);
            if (valor == null || valor.isBlank()) {
                return null;
            }
            return Integer.valueOf(valor);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Double getDouble(HttpServletRequest request, String nomeParam) {
        try {
            String valor = request.getParameter(nomeParam);
            if (valor == null || valor.isBlank()) {
                return null;
            }
            valor = valor.replace(",", ".");
            return Double.valueOf(valor);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String acao = request.getParameter("acao");
        if (acao == null || acao.isBlank()) {
            acao = "listar";
        }

        String destino = "ProdutoLista.jsp";

        try {

            switch (acao) {

                case "formIncluir": {
                    Produto p = new Produto();
                    request.setAttribute("produto", p);
                    request.setAttribute("acao", "incluir");
                    destino = "ProdutoDados.jsp";
                    break;
                }

                case "formAlterar": {
                    Integer id = getInt(request, "id");
                    if (id == null) {
                        request.setAttribute("mensagemErro", "ID do produto não informado.");
                        break;
                    }

                    Produto p = facade.find(id);
                    if (p == null) {
                        request.setAttribute("mensagemErro", "Produto não encontrado para o ID " + id + ".");
                        break;
                    }

                    request.setAttribute("produto", p);
                    request.setAttribute("acao", "alterar");
                    destino = "ProdutoDados.jsp";
                    break;
                }

                case "incluir": {
                    String nome = request.getParameter("nome");
                    Integer qtd = getInt(request, "quantidade");
                    Double preco = getDouble(request, "precoVenda");

                    if (nome == null || nome.isBlank() || qtd == null || preco == null) {
                        request.setAttribute("mensagemErro",
                                "Preencha corretamente Nome, Quantidade e Preço de Venda.");
                        destino = "ProdutoDados.jsp";

                        Produto p = new Produto();
                        p.setNome(nome);
                        if (qtd != null) p.setQuantidade(qtd);
                        if (preco != null) p.setPrecoVenda(preco);
                        request.setAttribute("produto", p);
                        request.setAttribute("acao", "incluir");

                        break;
                    }

                    Produto p = new Produto();
                    p.setNome(nome);
                    p.setQuantidade(qtd);
                    p.setPrecoVenda(preco);

                    facade.create(p);

                    request.setAttribute("mensagemSucesso", "Produto cadastrado com sucesso!");
                    request.setAttribute("lista", facade.findAll());
                    destino = "ProdutoLista.jsp";
                    break;
                }

                case "alterar": {
                    Integer id = getInt(request, "id");
                    if (id == null) {
                        request.setAttribute("mensagemErro", "ID do produto não informado.");
                        request.setAttribute("lista", facade.findAll());
                        break;
                    }

                    Produto p = facade.find(id);
                    if (p == null) {
                        request.setAttribute("mensagemErro", "Produto não encontrado para o ID " + id + ".");
                        request.setAttribute("lista", facade.findAll());
                        break;
                    }

                    String nome = request.getParameter("nome");
                    Integer qtd = getInt(request, "quantidade");
                    Double preco = getDouble(request, "precoVenda");

                    if (nome == null || nome.isBlank() || qtd == null || preco == null) {
                        request.setAttribute("mensagemErro",
                                "Preencha corretamente Nome, Quantidade e Preço de Venda.");
                        request.setAttribute("produto", p);
                        request.setAttribute("acao", "alterar");
                        destino = "ProdutoDados.jsp";
                        break;
                    }

                    p.setNome(nome);
                    p.setQuantidade(qtd);
                    p.setPrecoVenda(preco);

                    facade.edit(p);

                    request.setAttribute("mensagemSucesso", "Produto alterado com sucesso!");
                    request.setAttribute("lista", facade.findAll());
                    destino = "ProdutoLista.jsp";
                    break;
                }

                case "excluir": {
                    Integer id = getInt(request, "id");

                    if (id == null) {
                        request.setAttribute("mensagemErro", "ID do produto não informado.");
                    } else {
                        Produto p = facade.find(id);
                        if (p == null) {
                            request.setAttribute("mensagemErro",
                                    "Produto não encontrado para o ID " + id + ".");
                        } else {
                            facade.remove(p);
                            request.setAttribute("mensagemSucesso", "Produto excluído com sucesso!");
                        }
                    }

                    request.setAttribute("lista", facade.findAll());
                    destino = "ProdutoLista.jsp";
                    break;
                }

                case "listar":
                default: {
                    List<Produto> lista = facade.findAll();
                    request.setAttribute("lista", lista);
                    destino = "ProdutoLista.jsp";
                    break;
                }
            }

        } catch (Exception e) {
            // fallback para qualquer erro inesperado
            e.printStackTrace();
            request.setAttribute("mensagemErro",
                    "Ocorreu um erro ao processar a ação: " + e.getMessage());
            request.setAttribute("lista", facade.findAll());
            destino = "ProdutoLista.jsp";
        }

        RequestDispatcher rd = request.getRequestDispatcher(destino);
        rd.forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}