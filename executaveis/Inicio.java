package executaveis;

import java.sql.Statement;

import entidades.Conexao;

public class Inicio {

	public static void main(String[] args) {
	
		Conexao conexao = new Conexao();
		
		Statement consulta  = conexao.createStatement();

	}

}
