package br.com.teste.struts.actions;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

import br.com.teste.struts.dao.PessoaDAO;
import br.com.teste.struts.model.Pessoa;

@Namespace(value = "/pessoa")
public class PessoaAction extends ActionSupport {

	private static final long serialVersionUID = 1L;

	private Pessoa pessoa;
	private List<Pessoa> lista;
	private int id;
	private int codigo;
	private String nome;
	private int idade;
	private String sexo;

	@Action(value = "pesquisar", results = { 
			@Result(name = "success", location = "/cadastro.jsp"),
			@Result(name = "error", location = "/cadastro.jsp")
	})
	public String pesquisar() {
		try {
			if (codigo < 1 && StringUtils.isBlank(nome) && idade < 1 && StringUtils.isBlank(sexo)) {
				addActionMessage("Informe ao menos um dos campos para pesquisar!");
				lista = new PessoaDAO().listar();
				return ERROR;
			}
			Pessoa pes = new Pessoa(codigo, nome, idade, sexo);
			lista = new PessoaDAO().pesquisar(pes);
			return SUCCESS;
		} catch (Exception e) {
			addActionMessage("Falha ao listar pessoas: " + e.getMessage());
			return ERROR;
		}
	}

	@Action(value = "listar", results = @Result(name = "success", location = "/cadastro.jsp"))
	public String listar() {
		try {
			if (this.id != 0) {
				pessoa = new PessoaDAO().consultar(id);
			}
			lista = new PessoaDAO().listar();
			return SUCCESS;
		} catch (Exception e) {
			addActionMessage("Falha ao listar pessoas: " + e.getMessage());
			return ERROR;
		}
	}

	@Action(value = "cadastrar", results = {
			@Result(name = "success", type = "redirectAction", location = "listar"),
			@Result(name = "error", location = "/cadastro.jsp")
	})
	public String cadastrar() {
		try {
			if (pessoa.getNome().isEmpty()) {
				addActionMessage("Campo Nome: preenchimento obrigatório!");
				lista = new PessoaDAO().listar();
				return ERROR;
			}

			if (!pessoa.getSexo().isEmpty()) {
				if (!pessoa.getSexo().equalsIgnoreCase("M") && !pessoa.getSexo().equalsIgnoreCase("F")) {
					addActionMessage("Campo Sexo: caso preenchido, informar M ou F !");
					lista = new PessoaDAO().listar();
					return ERROR;
				}
			}
			
			if (pessoa.getId() == 0) {
				new PessoaDAO().inserir(pessoa);
			} else {
				new PessoaDAO().alterar(pessoa);
			}
			addActionMessage("Pessoa salva com sucesso!");
			pessoa = new Pessoa();
			lista = new PessoaDAO().listar();
			return SUCCESS;
		} catch (Exception e) {
			addActionMessage("Falha ao gravar pessoa: " + e.getMessage());
			return ERROR;
		}
	}

	@Action(value = "excluir", results = @Result(name = "success", type = "redirectAction", location = "listar"))
	public String excluir() {
		try {
			new PessoaDAO().excluir(this.id);
			addActionMessage("Pessoa excluída com sucesso!");
			return SUCCESS;
		} catch (Exception e) {
			addActionMessage("Falha ao excluir pessoa: " + e.getMessage());
			return ERROR;
		}
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Pessoa> getLista() {
		return lista;
	}

	public void setLista(List<Pessoa> lista) {
		this.lista = lista;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getIdade() {
		return idade;
	}

	public void setIdade(int idade) {
		this.idade = idade;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

}