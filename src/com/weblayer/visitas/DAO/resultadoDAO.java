package com.weblayer.visitas.DAO;

import com.weblayer.visitas.DTO.resultadoDTO;
import java.util.ArrayList;
import java.util.List;


public class resultadoDAO {


    public resultadoDTO Get(String id) {

       return null;

    }

    public List<resultadoDTO> fillCombo() {

        List<resultadoDTO> all = new ArrayList<resultadoDTO>();

        resultadoDTO resultado = new resultadoDTO();
        resultado.setid("0");
        resultado.setds_descricao("Selecione...");
        all.add(resultado);

        resultado = new resultadoDTO();
        resultado.setid("1");
        resultado.setds_descricao("Solicitou Orçamento/Proposta");
        all.add(resultado);

        resultado = new resultadoDTO();
        resultado.setid("2");
        resultado.setds_descricao("Pedido Fechado");
        all.add(resultado);

        resultado = new resultadoDTO();
        resultado.setid("4");
        resultado.setds_descricao("Visita Reagendada");
        all.add(resultado);

        resultado = new resultadoDTO();
        resultado.setid("5");
        resultado.setds_descricao("Agendou Treinamento / Palestra");
        all.add(resultado);

        resultado = new resultadoDTO();
        resultado.setid("6");
        resultado.setds_descricao("Agendou Demonstração");
        all.add(resultado);

        resultado = new resultadoDTO();
        resultado.setid("7");
        resultado.setds_descricao("Agendou Testes e Aplicações de Produtos");
        all.add(resultado);

        resultado = new resultadoDTO();
        resultado.setid("8");
        resultado.setds_descricao("Solicitou amostras e Catálogos");
        all.add(resultado);

        resultado = new resultadoDTO();
        resultado.setid("9");
        resultado.setds_descricao("Aguardar retorno do Cliente");
        all.add(resultado);

        resultado = new resultadoDTO();
        resultado.setid("10");
        resultado.setds_descricao("Retornar contato para o Cliente");
        all.add(resultado);

        resultado = new resultadoDTO();
        resultado.setid("11");
        resultado.setds_descricao("Conclusão da Arrumação/Manutenção do espaço fischer");
        all.add(resultado);

        resultado = new resultadoDTO();
        resultado.setid("12");
        resultado.setds_descricao("Obra encerrada");
        all.add(resultado);

        resultado = new resultadoDTO();
        resultado.setid("13");
        resultado.setds_descricao("Treinamento Concluído");
        all.add(resultado);

        return all;


    }
}
