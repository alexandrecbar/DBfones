package entidades;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
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
		int linhas = 0;
		Scanner teclado = new Scanner(System.in);
		System.out.print("==== NOVO REGISTRO ====\n");
		System.out.print("Nome: ");
		String nome = teclado.nextLine();
		System.out.print("Numero: ");
		int numero = teclado.nextInt();
		int id = gerarId();

		// Fone fone = new Fone(id, nome, numero);
		try {
			PreparedStatement sql = sessao.prepareStatement(
					"insert into fones " + "(id, nome, numero) " + "VALUES " + "(?,?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			sql.setInt(1, id);
			sql.setString(2, nome);
			sql.setInt(3, numero);

			linhas = sql.executeUpdate();

		} catch (SQLException e) {
			System.out.println(e);
		}
		teclado.close();
		return linhas;
	}

	public int gerarId() {

		Calendar auxData = Calendar.getInstance();
		
		System.out.println(auxData);

		int auxHoraInt = auxData.get(Calendar.HOUR_OF_DAY);
		String auxHoraStr = String.format("%02d", auxHoraInt);

		int auxMinInt = auxData.get(Calendar.MINUTE);
		String auxMinStr = String.format("%02d", auxMinInt);

		int auxSecInt = auxData.get(Calendar.SECOND);
		String auxSecStr = String.format("%02d", auxSecInt);

		int auxMilInt = auxData.get(Calendar.MILLISECOND);
		String auxMilStr = String.format("%03d", auxMilInt);

		String auxDataStr = auxHoraStr + auxMinStr + auxSecStr + auxMilStr;

//		Sting auxHora = Integer.toString(auxData.get(Calendar.HOUR));

//		String auxString = Integer.toString(auxData.get(Calendar.HOUR)) + Integer.toString(auxData.get(Calendar.MINUTE))
//				+ Integer.toString(auxData.get(Calendar.SECOND)) + Integer.toString(auxData.get(Calendar.MILLISECOND));
	

		return Integer.parseInt(auxDataStr);

	}

	public int superId() {
		
		Calendar aux = Calendar.getInstance();
		
		System.out.println(aux.getTime());
		
		return 0;
	}
	
	
}
