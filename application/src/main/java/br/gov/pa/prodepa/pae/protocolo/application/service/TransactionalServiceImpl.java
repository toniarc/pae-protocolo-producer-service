package br.gov.pa.prodepa.pae.protocolo.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import br.gov.pa.prodepa.pae.protocolo.domain.port.TransactionalService;

@Component
public class TransactionalServiceImpl implements TransactionalService {

	private final TransactionTemplate transactionTemplate;

	@Autowired
	public TransactionalServiceImpl(PlatformTransactionManager transactionManager) {
		super();
		this.transactionTemplate = new TransactionTemplate(transactionManager);
		this.transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
	}
	
	public <T> T executarEmTransacaoSeparada(TransactionCallback<T> action) {
		return transactionTemplate.execute(status -> {
			return action.doInTransaction(status);
		});
	}
}
