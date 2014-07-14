package br.com.wofsolutions.interfaces;

public interface HibernateDAO<E, H, I> {
	public boolean salvar(E obj);
}
