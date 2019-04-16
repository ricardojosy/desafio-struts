package br.com.teste.struts.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import br.com.teste.struts.model.Pessoa;

public class PessoaDAO {

	private Connection conn;
	private PreparedStatement pstmt;
	private Statement stmt;

	public PessoaDAO() {
		conn = new ConnectionFactory().getConexao();
	}

	public void inserir(Pessoa pessoa) {
		String sql = "insert into pessoa (nome, idade, sexo) values (?,?,?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pessoa.getNome());

			if (pessoa.getIdade() > 0) {
				pstmt.setInt(2, pessoa.getIdade());
			} else {
				pstmt.setNull(2, Types.INTEGER);
			}
			
			if (!pessoa.getSexo().isEmpty()) {
				pstmt.setString(3, pessoa.getSexo());
			} else {
				pstmt.setNull(3, Types.CHAR);
			}
			
			pstmt.execute();
			pstmt.close();
		} catch (Exception e) {
			throw new RuntimeException("Erro na inclusão: " + e);
		}
	}

	public void alterar(Pessoa pessoa) {
		String sql = "update pessoa set nome = ?, idade = ?, sexo = ? where id = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, pessoa.getNome());

			if (pessoa.getIdade() > 0) {
				pstmt.setInt(2, pessoa.getIdade());
			} else {
				pstmt.setNull(2, Types.INTEGER);
			}
			
			if (!pessoa.getSexo().isEmpty()) {
				pstmt.setString(3, pessoa.getSexo());
			} else {
				pstmt.setNull(3, Types.CHAR);
			}			
			
			pstmt.setInt(4, pessoa.getId());
			pstmt.execute();
			pstmt.close();
		} catch (Exception e) {
			throw new RuntimeException("Erro na alteração: " + e);
		}
	}

	public void excluir(int id) {
		String sql = "delete from pessoa where id = ?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, id);
			pstmt.execute();
			pstmt.close();
		} catch (Exception e) {
			throw new RuntimeException("Erro na exclusão: " + e);
		}
	}

	public Pessoa consultar(int id) {
		String sql = "select * from pessoa where id = " + id;
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if (rs.next()) {
				Pessoa p = new Pessoa();
				p.setId(rs.getInt("id"));
				p.setNome(rs.getString("nome"));
				p.setIdade(rs.getInt("idade"));
				p.setSexo(rs.getString("sexo"));
				return p;
			}
			return null;
		} catch (Exception e) {
			throw new RuntimeException("Erro na consulta: " + e);
		}
	}

	public List<Pessoa> listar() {
		String sql = "select * from pessoa order by nome";
		List<Pessoa> lista = new ArrayList<>();
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Pessoa p = new Pessoa();
				p.setId(rs.getInt("id"));
				p.setNome(rs.getString("nome"));
				p.setIdade(rs.getInt("idade"));
				p.setSexo(rs.getString("sexo"));
				lista.add(p);
			}
			return lista;
		} catch (Exception e) {
			throw new RuntimeException("Erro no listar: " + e);
		}
	}

	public List<Pessoa> pesquisar(Pessoa pessoa) {
		StringBuilder sql = new StringBuilder(); 
		sql.append("select * from pessoa ");
		sql.append(" where 1 = 1 ");
		if (pessoa.getId() > 0) {
			sql.append(" and id = ?");
		}
		if (StringUtils.isNotBlank(pessoa.getNome())) {
			sql.append(" and nome like '%?%'");
		}
		if (pessoa.getIdade() > 0) {
			sql.append(" and idade = ?");
		}
		if (StringUtils.isNotBlank(pessoa.getSexo())) {
			sql.append(" and sexo = ?");
		}
		sql.append(" order by nome");
		List<Pessoa> lista = new ArrayList<>();
		try {
			pstmt = conn.prepareStatement(sql.toString());
			if (pessoa.getId() > 0) {
				pstmt.setInt(1, pessoa.getId());
			}
			if (StringUtils.isNotBlank(pessoa.getNome())) {
				pstmt.setString(2, pessoa.getNome());
			}
			if (pessoa.getIdade() > 0) {
				pstmt.setInt(3, pessoa.getIdade());
			}
			if (StringUtils.isNotBlank(pessoa.getSexo())) {
				pstmt.setString(4, pessoa.getSexo());
			}
			ResultSet rs = pstmt.executeQuery(sql.toString());
			while (rs.next()) {
				Pessoa p = new Pessoa();
				p.setId(rs.getInt("id"));
				p.setNome(rs.getString("nome"));
				p.setIdade(rs.getInt("idade"));
				p.setSexo(rs.getString("sexo"));
				lista.add(p);
			}
			pstmt.close();
			return lista;
		} catch (Exception e) {
			throw new RuntimeException("Erro no pesquisar: " + e);
		}
	}

}
