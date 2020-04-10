package entidades;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Properties;
import java.util.Scanner;

public class Conexao {

	Connection sessao = null;

	public Conexao() {

		try (FileInputStream config = new FileInputStream("config.db")) {

			Properties propriedades = new Properties();
			propriedades.load(config);
			sessao = DriverManager.getConnection(propriedades.getProperty("dburl"), propriedades);

		}

		catch (IOException | SQLException e) {
			System.out.println(e);

		}

	}

	public int inserir() {

		Scanner teclado = new Scanner(System.in);
		System.out.print("==== NOVO REGISTRO ====\n");
		System.out.print("Nome: ");
		String nome = teclado.nextLine();
		System.out.print("Numero: ");
		int numero = teclado.nextInt();
		int id = gerarId();

		int linhas = 0;
		try {
			PreparedStatement sql = sessao.prepareStatement(
					"insert into fones " + "(id, nome, numero) " + "VALUES " + "(?,?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			sql.setInt(1, id);
			sql.setString(2, nome);
			sql.setInt(3, numero);

			linhas = sql.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();

		}
		teclado.close();
		return linhas;

	}

	public int editar() {

		int linhas = 0;
		Scanner pegaLinha = new Scanner(System.in);
		Scanner pegaNumero = new Scanner(System.in);
		System.out.println("Informe o nome do registro a ser modificado: ");
		String resposta = pegaLinha.nextLine();

		try {

			PreparedStatement sql = sessao.prepareStatement("select  * from fones where nome = ?",
					Statement.RETURN_GENERATED_KEYS);

			sql.setString(1, resposta);

			ResultSet tabela = sql.executeQuery().getStatement().getResultSet();

			tabela.next();

			System.out.println(tabela.getInt(1) + " ," + tabela.getString(2) + " ," + tabela.getInt(3));

			Fone fone = new Fone(tabela.getInt(1), tabela.getString(2), tabela.getInt(3));

			System.out.println("Comprovacao da criacao do objeto: " + fone);

			System.out.print("Informe o campo a ser editado [1] Nome ou [2] Numero");

			int opcao = pegaNumero.nextInt();

			switch (opcao) {

			case 1:

				System.out.print("Novo nome: ");
				String resposta2 = pegaLinha.nextLine();
				PreparedStatement updateNome = sessao.prepareStatement(
						"update fones " + "set nome = ? " + "where nome = ?", Statement.RETURN_GENERATED_KEYS);
				updateNome.setString(1, resposta2);
				updateNome.setString(2, resposta);
				linhas = updateNome.executeUpdate();
				break;

			case 2:

				System.out.println("Novo numero: ");
				int novoNumero = pegaNumero.nextInt();
				PreparedStatement updateNumero = sessao.prepareStatement(
						"update fones " + "set numero = ? " + "where nome = ?", Statement.RETURN_GENERATED_KEYS);
				updateNumero.setInt(1, novoNumero);
				updateNumero.setString(2, resposta);
				linhas = updateNumero.executeUpdate();
				break;

			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		pegaLinha.close();
		pegaNumero.close();
		return linhas;
	}

	public int deletar() {

		int linhas = 0;
		Scanner pegaLinha = new Scanner(System.in);
		System.out.println("Informe o nome do registro a ser deletato: ");
		String resposta = pegaLinha.nextLine();

		try {

			PreparedStatement sql = sessao.prepareStatement("delete from fones where nome = ?",
					Statement.RETURN_GENERATED_KEYS);

			sql.setString(1, resposta);

			linhas = sql.executeUpdate();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		pegaLinha.close();
		return linhas;
	}

	public void listar() {

		try {
			Statement sql = sessao.createStatement();
			ResultSet resultado = sql.executeQuery("select * from fones").getStatement().getResultSet();
			while (resultado.next()) {

				System.out.println(
						"Id: " + resultado.getInt(1)+ ", " + "Nome: " + resultado.getString(2) + ", " + "Numero: " + resultado.getInt(3));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private int gerarId() {

		Calendar auxData = Calendar.getInstance();

		int auxHoraInt = auxData.get(Calendar.HOUR_OF_DAY);
		String auxHoraStr = String.format("%02d", auxHoraInt);

		int auxMinInt = auxData.get(Calendar.MINUTE);
		String auxMinStr = String.format("%02d", auxMinInt);

		int auxSecInt = auxData.get(Calendar.SECOND);
		String auxSecStr = String.format("%02d", auxSecInt);

		int auxMilInt = auxData.get(Calendar.MILLISECOND);
		String auxMilStr = String.format("%03d", auxMilInt);

		String auxDataStr = auxHoraStr + auxMinStr + auxSecStr + auxMilStr;

		return Integer.parseInt(auxDataStr);

	}

}
