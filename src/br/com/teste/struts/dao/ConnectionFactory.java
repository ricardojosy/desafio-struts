package br.com.teste.struts.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionFactory {

	public Connection getConexao() {
		try {
			Class.forName("com.mysql.jdbc.Driver"); 
			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/desafio", "root", "");
			return conn;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
//		try {
//			Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/desafio", "root" , "filhas");
//			return conn;
//		} catch (Exception e) {
//			throw new RuntimeException("Erro de conexão: " + e);
//		}
	}
}


