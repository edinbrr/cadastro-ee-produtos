<%-- 
    Document   : ProdutoLista
    Created on : 25 de nov. de 2025
    Author     : Edson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.List"%>
<%@page import="cadastroee.model.Produto"%>

<%
    List<Produto> lista = (List<Produto>) request.getAttribute("lista");
    String mensagemErro = (String) request.getAttribute("mensagemErro");
    String mensagemSucesso = (String) request.getAttribute("mensagemSucesso");
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Produtos</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">
<div class="container py-4">

    <h1 class="mb-4">Lista de Produtos</h1>

    <% if (mensagemErro != null) { %>
        <div class="alert alert-danger"><%= mensagemErro %></div>
    <% } %>

    <% if (mensagemSucesso != null) { %>
        <div class="alert alert-success"><%= mensagemSucesso %></div>
    <% } %>

    <a class="btn btn-primary mb-3"
       href="ServletProdutoFC?acao=formIncluir">Novo Produto</a>

    <table class="table table-striped table-hover">
        <thead class="table-dark">
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Quantidade</th>
            <th>Preço de Venda</th>
            <th>Ações</th>
        </tr>
        </thead>
        <tbody>
        <%
            if (lista != null) {
                for (Produto p : lista) {
        %>
        <tr>
            <td><%= p.getId() %></td>
            <td><%= p.getNome() %></td>
            <td><%= p.getQuantidade() %></td>
            <td>R$ <%= String.format(java.util.Locale.US, "%.2f", p.getPrecoVenda()) %></td>
            <td>
                <a class="btn btn-sm btn-primary"
                   href="ServletProdutoFC?acao=formAlterar&id=<%= p.getId() %>">Alterar</a>
                <a class="btn btn-sm btn-danger"
                   href="ServletProdutoFC?acao=excluir&id=<%= p.getId() %>"
                   onclick="return confirm('Confirma excluir este produto?');">Excluir</a>
            </td>
        </tr>
        <%
                }
            }
        %>
        </tbody>
    </table>

</div>
</body>
</html>