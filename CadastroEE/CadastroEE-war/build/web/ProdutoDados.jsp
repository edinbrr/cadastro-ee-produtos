<%-- 
    Document   : ProdutoDados
    Created on : 25 de nov. de 2025
    Author     : Edson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="cadastroee.model.Produto"%>

<%
    // Recupera o produto e a ação vindos do ServletProdutoFC
    Produto produto = (Produto) request.getAttribute("produto");
    String acao = (String) request.getAttribute("acao");

    if (acao == null) {
        acao = "incluir";  // padrão
    }

    boolean alterar = "alterar".equals(acao);
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><%= alterar ? "Alterar" : "Incluir" %> Produto</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-4">

    <h1 class="mb-4"><%= alterar ? "Alterar" : "Novo" %> Produto</h1>

    <form class="form" method="post" action="ServletProdutoFC">
        <!-- ação que o servlet vai executar -->
        <input type="hidden" name="acao" value="<%= acao %>"/>

        <% if (alterar && produto != null) { %>
            <input type="hidden" name="id" value="<%= produto.getId() %>"/>
        <% } %>

        <div class="mb-3">
            <label class="form-label" for="nome">Nome</label>
            <input class="form-control" type="text" id="nome" name="nome"
                   value="<%= (produto != null ? produto.getNome() : "") %>" required/>
        </div>

        <div class="mb-3">
            <label class="form-label" for="quantidade">Quantidade</label>
            <input class="form-control" type="number" id="quantidade" name="quantidade" min="0"
                   value="<%= (produto != null ? produto.getQuantidade() : "") %>" required/>
        </div>

        <div class="mb-3">
            <label class="form-label" for="precoVenda">Preço de venda</label>
            <input class="form-control" type="text" id="precoVenda" name="precoVenda"
                   value="<%= (produto != null ? produto.getPrecoVenda() : "") %>" required/>
        </div>

        <button type="submit" class="btn btn-primary">
            <%= alterar ? "Salvar alterações" : "Incluir" %>
        </button>
        <a href="ServletProdutoFC?acao=listar" class="btn btn-secondary">Cancelar</a>
    </form>

</div>
</body>
</html>