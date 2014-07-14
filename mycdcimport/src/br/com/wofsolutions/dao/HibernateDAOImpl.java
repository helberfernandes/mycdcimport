/**
 * 
 */
package br.com.wofsolutions.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.wofsolutions.util.HibernateUtil;

/**
 * @author hfernandes
 * 
 */
public abstract class HibernateDAOImpl<E, H, I> {

	private static Session session;

	public boolean salvar(E obj) {
		Transaction tx = null;
		//try {

		
			HibernateUtil.doBeginTransaction();
			// tx = getSession().getTransaction();
			
			getSession().merge(obj);
			//getSession().saveOrUpdate(obj);
			
			HibernateUtil.doCommit();			
		//	tx.commit();
			//HibernateUtil.getThreadtransaction().get().commit();
		
	//	} catch (Exception e) {
			// tx.rollback();
			//HibernateUtil.doRollback();
		//	e.printStackTrace();
	//	} finally {

		//}

		return true;
	}

	public boolean salvarObjeto(H obj) {
		Transaction tx = null;
		try {

			
			//HibernateUtil.doBeginTransaction();
			// tx = getSession().beginTransaction();
			
			getSession().saveOrUpdate(obj);
			//HibernateUtil.doCommit();
		} catch (Exception e) {
			// tx.rollback();
			HibernateUtil.doRollback();
			e.printStackTrace();
		} finally {

		}

		return true;
	}

	public boolean salvarObjeto2(I obj) {
		Transaction tx = null;
		try {

			
			HibernateUtil.doBeginTransaction();
			// tx = getSession().beginTransaction();
			
			getSession().saveOrUpdate(obj);
			HibernateUtil.doCommit();
		} catch (Exception e) {
			// tx.rollback();
			HibernateUtil.doRollback();
			e.printStackTrace();
		} finally {

		}

		return true;
	}

	public boolean excluir(E obj) {
		Transaction tx = null;
		try {
			tx = getSession().beginTransaction();
			
			getSession().delete(obj);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {

		}
		return tx.wasCommitted();
	}

	public boolean excluirObjecto(H obj) {
		Transaction tx = null;
		try {
			tx = getSession().beginTransaction();
			getSession().delete(obj);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
		}
		
		return tx.wasCommitted();
	}

	public Session getSession() {	
		return HibernateUtil.getCurrentSession();
	}
}
