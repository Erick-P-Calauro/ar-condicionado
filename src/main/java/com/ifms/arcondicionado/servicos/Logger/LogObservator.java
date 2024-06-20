package com.ifms.arcondicionado.servicos.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ifms.arcondicionado.servicos.ContentExtractor;
import com.ifms.arcondicionado.servicos.RegistroService;

public abstract class LogObservator<E> {
    
    @Autowired
    private JpaRepository<E, Long> repository;

    @Autowired
    private RegistroService logger;

    @Autowired
    private ContentExtractor<E> extractor;

    public E salvar(E entidade){
        E novaEntidade = repository.save(entidade);
        logger.adicionarRegistroCadastro(entidade.getClass().getName(), extractor.extract(novaEntidade));

        return novaEntidade;
    }

    public E editar(E antiga, E entidade) {
        String antigosDados = extractor.extract(antiga);
        E novaEntidade = repository.save(entidade);
        logger.adicionarRegistroUpdate(entidade.getClass().getName(), antigosDados, extractor.extract(novaEntidade));

        return novaEntidade;
    }

    public void deletar(E entidade) {
        logger.adicionarRegistroDelete(entidade.getClass().getName(), extractor.extract(entidade));

        repository.delete(entidade);
    }

}
