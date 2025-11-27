/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package cadastroee.controller;

import cadastroee.model.Produto;
import jakarta.ejb.Local;
import java.util.List;

@Local
public interface ProdutoFacadeLocal {
    void create(Produto p);
    void edit(Produto p);
    void remove(Produto p);
    Produto find(Object id);
    List<Produto> findAll();
}