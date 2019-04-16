<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="sx" uri="/struts-dojo-tags"%>
<!DOCTYPE html>
<html>
<head>
<title>Struts 2 - Desafio Crud</title>
<sx:head/>
<script type="text/javascript" src="js/jquery-3.1.1.min.js"></script>
<script type="text/javascript" src="js/bootstrap.min.js"></script>
</head>

<body>
	<h2>Struts 2 - Desafio Crud</h2>
	<s:actionerror />
	<s:actionmessage />
	<s:div style="margins:auto;width:600px;">
		<s:div>
			<s:form action="/pessoa/cadastrar">
				<s:if test="pessoa.id>0">
					<s:textfield name="pessoa.id" label="Código" size="40" disabled="true" />
				</s:if>
				<s:else>
					<s:textfield name="pessoa.id" label="Código" size="50" disabled="false" id="codigo" />
				</s:else>
				<s:textfield name="pessoa.nome" label="Nome" size="50" maxlength="100" id="nome" />
				<s:textfield name="pessoa.idade" label="Idade" size="50" maxlength="3" id="idade" />
				<s:textfield name="pessoa.sexo" label="Sexo" size="50" maxlength="1" id="sexo" />
				<s:submit value="Gravar" />
			</s:form>
		</s:div>
		<s:div style="float:right;width:50%">
			<s:url action="listar" var="listarURL" />
			<s:a href="%{listarURL}">
				<input type="button" value="Cancelar" />
			</s:a>
		</s:div>
	</s:div>


	<p><br/></p>
	
		<s:div style="width:600px">
		<table border="1" style="">
			<tr>
				<th>Código</th>
				<th>Nome</th>
				<th>Idade</th>
				<th>Sexo</th>
				<th>Ações</th>
			</tr>
				<s:if test="lista.size() > 0">
					<s:iterator value="lista">
						<tr>
							<td><s:property value="id" escape="false"/></td>
							<td><s:property value="nome" escape="false"/></td>
							<td><s:property value="idade" escape="false"/></td>
							<td><s:property value="sexo" escape="false"/></td>
							<td>
								<s:url action="listar" var="urlC">
									<s:param name="id" value="id" />
								</s:url>
								<a href="<s:property value="#urlC" />"  >Consultar</a>
								
								<s:url action="excluir" var="urlE">
									<s:param name="id" value="id" />
								</s:url>
								<a href="<s:property escape="false" value="#urlE" />" onclick="return confirmaExclusao()">Excluir</a>
								</td>
						</tr>
					</s:iterator>
				</s:if>
		</table>
		</s:div>
	
<script>
   function confirmaExclusao() {
      var resposta = window.confirm("Confirma exclusão do funcionário ");
      if (resposta) 
    	  	return true;
      else 
    	  	return false;
   }
   
 </script>
</body>
</html>