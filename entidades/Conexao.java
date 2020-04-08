package entidades;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

public class Conexao {

	public Connection sessao = null;

	public Conexao() {

		try (FileInputStream config = new FileInputStream("config.db")) {

			Properties propriedades = new Properties();
			propriedades.load(config);
			Connection sessao = DriverManager.getConnection(propriedades.getProperty("dburl"), propriedades);

		}

		catch (IOException | SQLException e) {
			System.out.println(e);

		}

	}

	private int inserir() {
		Scanner teclado = new Scanner(System.in);
		System.out.print("==== NOVO REGISTRO ====\n");
		System.out.print("Nome: ");
		String nome = teclado.next();
		System.out.print("Numero: ");
		int numero = teclado.nextInt();
			
		Fone fone = new Fone(gerarId(), nome, numero);
		try {
			sessao.prepareStatement("insert into fones " 
			+ "(id, nome, fone) "
			+ "VALUES "
			+ "(?,?, ?)",
			Statement.RETURN_GENERATED_KEYS);
			
		} catch (SQLException e) {
		System.out.println(e);
		}
		
		
		
			
		}
	
	
	private int gerarId () {

	String aux = Integer.toString(Calendar.HOUR) + 
			     Integer.toString(Calendar.MINUTE) +
			     Integer.toString(Calendar.SECOND) + 
			     Integer.toString(Calendar.MILLISECOND);
	
		return Integer.parseInt(aux);	
		
	}

}
