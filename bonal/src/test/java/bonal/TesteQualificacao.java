package bonal;

import java.util.Calendar;

import br.com.empresa.bonal.controles.QualificacaoControle;
import br.com.empresa.bonal.entidades.Funcionario;
import br.com.empresa.bonal.entidades.QualificacaoProfissional;

public class TesteQualificacao {

	public static void main(String[] args) {
		QualificacaoProfissional qf = new QualificacaoProfissional();
		qf.setTitulo("montagem de pc");
		qf.setDescricao("mexer em muita lata velha");
		qf.setDataInicio(Calendar.getInstance());
		qf.setDataFim(Calendar.getInstance());
		
		Funcionario f = new Funcionario();
		
		
		qf.setFuncionario(f);
		
		System.out.println(qf.toString());
		
		
		QualificacaoControle qc = new QualificacaoControle();
		
	}
}
