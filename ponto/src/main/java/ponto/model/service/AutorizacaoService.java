package ponto.model.service;

import java.util.HashSet;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ponto.model.domain.Autorizacao;
import ponto.model.domain.Usuario;
import ponto.model.repository.AutorizacaoRepository;

@Service
public class AutorizacaoService {

	@Autowired
	private AutorizacaoRepository repository;

	@Transactional(propagation = Propagation.MANDATORY)
	public void concederAutorizacao(Usuario usuario, String nomeAutorizacao) {
		if (CollectionUtils.isEmpty(usuario.getAutorizacoes())) {
			usuario.setAutorizacoes(new HashSet<Autorizacao>());
		}
		Autorizacao autorizacao = repository
				.buscarAutorizacaoPorNome(nomeAutorizacao);
		if (!usuario.getAutorizacoes().contains(autorizacao)) {
			usuario.getAutorizacoes().add(autorizacao);
		}
	}

}
