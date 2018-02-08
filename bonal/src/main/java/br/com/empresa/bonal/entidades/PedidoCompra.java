package br.com.empresa.bonal.entidades;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

@Entity
public class PedidoCompra {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fornecedor")
	private Fornecedor fornecedor;	
	
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_cadastro")
	private Calendar dataCadastro = Calendar.getInstance();
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "funcionario")
	private Funcionario funcionario;	
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "conta")
	private Conta conta;
	
	private BigDecimal precoTotal;
	

	@Version
	private Integer version;
	
	private Boolean status;
	
	private Date vencimento; 
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "pedido_id")
	private List<ItemDaCompra> itensDaCompra;

	public List<ItemDaCompra> getItensDaVenda() {
		return itensDaCompra;
	}

	public void setItensDaVenda(List<ItemDaCompra> itensDaCompra) {
		this.itensDaCompra = itensDaCompra;
	}

	public Long getId() {
		return id;
	}
	
	public void addItem(ItemDaCompra itemDaCompra){
		if(itensDaCompra == null){
			itensDaCompra = new ArrayList<>();
		}
		
		itensDaCompra.add(itemDaCompra);
	}
	
	public void delItem(ItemDaCompra itemDaCompra){
				
		itensDaCompra.remove(itemDaCompra);
	}

	
	public Date getVencimento() {
		return vencimento;
	}

	public void setVencimento(Date vencimento) {
		this.vencimento = vencimento;
	}

	

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public BigDecimal getPrecoTotal() {
		return precoTotal;
	}

	public void setPrecoTotal(BigDecimal precoTotal) {
		this.precoTotal = precoTotal;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getDataCadastro() {
		SimpleDateFormat fmt = new SimpleDateFormat("dd-MM-yyyy");
	    fmt.setCalendar(dataCadastro);
	    String dateFormatted = fmt.format(dataCadastro.getTime());
	    return dateFormatted;
	}
	
	
	
	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public Integer getVersion() {
		return version;
	}

	@Override
	public String toString() {
		return "PedidoVenda [id=" + ", fornecedor=" + fornecedor.getNome() + ", dataCadastro=" + getDataCadastro() + ", funcionario="
				+ funcionario.getNome() + ", conta=" + ", precoTotal=" + precoTotal.toString() + ", version="+ ", status="
				+ status + ", vencimento=" + vencimento.getTime() + ", itensDaCompra=" + itensDaCompra.size() + "]";
	}

	
	
	

}
