package executaveis;

import entidades.Conexao;

public class Inicio {

	public static void main(String[] args) {

//		int linhas;
		Conexao conexao = new Conexao();
//		linhas = conexao.inserir();
//		System.out.println("Registro(s) inserido(s): " + linhas);
		
		
		System.out.println(conexao.superId());

	}

}
