package br.com.wofsolutions.util;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;
import org.hibernate.stat.Statistics;
import org.hibernate.tool.hbm2ddl.SchemaExport;



public class HibernateUtil {
	 
	/**
	 * 
	 * Construtor no-arg Protegido para evitar a criaÃ§Ã£o da classe
	 */

	protected HibernateUtil() {

		super();

	}

	/* Constante de caminho do arquivo de configuraÃ§Ã£o do Hibernate */

	/* Threads que controlarÃ£o a sessÃ£o e a transaÃ§Ã£o */

	private static final ThreadLocal<Session> threadSession = new ThreadLocal<Session>();

	private static final ThreadLocal<Transaction> threadTransaction = new ThreadLocal<Transaction>();

	/* Variaveis do Hibernate */

	private static final Configuration cfg = new AnnotationConfiguration();

	private static SessionFactory sessionFactory;

	private static synchronized void buildSessionFactory() throws Exception {

		if (sessionFactory == null) {

			try {

				cfg.configure();

				sessionFactory = cfg.buildSessionFactory();
				
			} catch (Exception e) {

				throw e;

			}

		}

	}

	/**
	 * 
	 * MÃ©todo que retorna a instancia da SessÃ£o.
	 * 
	 * 
	 * 
	 * @return Session
	 * 
	 * @throws Exception
	 * 
	 * @throws SessionFactoryException
	 */

	public static Session getCurrentSession() throws HibernateException {

		Session session = threadSession.get();

		try {

			if (session == null || !session.isOpen()) {

				if (sessionFactory == null) {

					buildSessionFactory();
				
				}

				session = (sessionFactory != null) ? sessionFactory

				.openSession() : null;

			
				threadSession.set(session);
				
			}

		} catch (Exception e) {

			e.printStackTrace();

			throw new HibernateException(e);

		}

		return session;

	}

	/**
	 * 
	 * MÃ©todo que fecha a sessÃ£o do Hibernate.
	 * 
	 * 
	 * 
	 * @throws SessionFactoryException
	 */

	public static void doCloseSession() throws HibernateException {

		Session session = threadSession.get();

		threadSession.set(null);

		try {

			if (session != null) {
				session.clear();
				session.flush();				
				session.close();
				
			}

			session = threadSession.get();

		} catch (Exception e) {

			throw new HibernateException(e);

		}

	}

	/**
	 * 
	 * MÃ©todo que inicia a transaÃ§Ã£o do Hibernate.
	 * 
	 * 
	 * 
	 * @throws SessionFactoryException
	 */

	public static void doBeginTransaction() throws HibernateException {

		Transaction tx = threadTransaction.get();

		try {

			if (tx == null) {

				tx = getCurrentSession().beginTransaction();

				threadTransaction.set(tx);

			}

		} catch (Exception e) {

			throw new HibernateException(e);

		}

	}

	/**
	 * 
	 * MÃ©todo que executa o rollback da transaÃ§Ã£o.
	 * 
	 * 
	 * 
	 * @throws SessionFactoryException
	 */

	public static void doRollback() throws HibernateException {

		Transaction tx = threadTransaction.get();

		try {

			if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {

				tx.rollback();

				threadTransaction.set(null);

			}

		} catch (Exception e) {

			throw new HibernateException(e);

		}

	}

	// LIMPANDO CACHE PRIMÃ�RIO DO HIBERNATE

	// UTILIZADO PARA OPERACOES EM BATCH

	public static void doFlush() {

		try {

			getCurrentSession().flush();

			getCurrentSession().clear();

		} catch (Exception e) {

			throw new HibernateException(e);

		}

	}

	/**
	 * 
	 * MÃ©todo que commita a transaÃ§Ã£o.
	 * 
	 * 
	 * 
	 * @throws SessionFactoryException
	 */

	public static void doCommit() throws HibernateException {

		Transaction tx = threadTransaction.get();

		try {

			if (tx != null && !tx.wasCommitted() && !tx.wasRolledBack()) {
				
				tx.commit();
				
				threadTransaction.set(null);

			}

		} catch (Exception e) {

			doRollback();

			throw new HibernateException(e);

		}

	}

	public static Statistics getStatistics() {

		return sessionFactory.getStatistics();

	}

	public static ThreadLocal<Transaction> getThreadtransaction() {
		return threadTransaction;
	}
	

//	@SuppressWarnings("unchecked")
//	public static void clearHibernateCache() {
//
//		try {
//
//			SessionFactory sf = sessionFactory;
//
//			Map<Object, EntityPersister> classMetadata = sf
//
//			.getAllClassMetadata();
//
//			for (EntityPersister ep : classMetadata.values()) {
//
//				if (ep.hasCache()) {
//
//					try {
//
//						ep.getCache().clear();
//
//					} catch (HibernateException e) {
//
//						e.printStackTrace();
//
//					}
//
//				}
//
//			}
//
//			Map<Object, AbstractCollectionPersister> collMetadata = sf
//
//			.getAllCollectionMetadata();
//
//			for (AbstractCollectionPersister acp : collMetadata.values()) {
//
//				if (acp.hasCache()) {
//
//					try {
//
//						acp.getCache().clear();
//
//					} catch (CacheException e) {
//
//						e.printStackTrace();
//
//					}
//
//				}
//
//			}
//
//			return;
//
//		} catch (Exception e) {
//
//			// TODO: handle exception
//
//		}
//
//	}
	
	public static void  gerabanco(){
		SchemaExport export = new SchemaExport(cfg.configure());
		export.create(true, true);
	}

}