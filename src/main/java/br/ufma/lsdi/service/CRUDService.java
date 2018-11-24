package br.ufma.lsdi.service;

import java.io.Serializable;
import java.util.List;

import org.hibernate.service.spi.ServiceException;

public interface CRUDService<E> {

	E save(E entity) throws ServiceException;
	
	E getById(Serializable id) throws ServiceException;
	
	List<E> getAll() throws ServiceException;
	
	void delete(Serializable id) throws ServiceException;
}
