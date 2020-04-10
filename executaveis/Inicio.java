package executaveis;

import java.util.Scanner;

import entidades.Conexao;

public class Inicio {

	public static void main(String[] args) {

		Conexao conexao = new Conexao();
		int linhas;

		Scanner teclado = new Scanner(System.in);

		System.out.println("====== MENU DO SISTEMA =====");
		System.out.println("[1] ======== Inserir =======");
		System.out.println("[2] ======== Editar ========");
		System.out.println("[3] ======== Deletar =======");
		System.out.println("[4] ======== Listar ========");

		int opcao = teclado.nextInt();

		switch (opcao) {

		case 1:

			linhas = conexao.inserir();
			System.out.println("Registro(s) inserido(s): " + linhas);
			break;

		case 2:

			linhas = conexao.editar();
			System.out.println();
			System.out.println("Registro(s) editado(s): " + linhas);
			break;

		case 3:

			linhas = conexao.deletar();
			System.out.println();
			System.out.println("Registro(s) deletado(s): " + linhas);
			break;

		case 4:

			
			System.out.println();
			conexao.listar();
			break;
		}

		teclado.close();
	}

}
