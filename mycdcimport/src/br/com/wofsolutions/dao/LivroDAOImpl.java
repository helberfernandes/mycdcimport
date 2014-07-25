/**
 * 
 */
package br.com.wofsolutions.dao;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.com.wofsolutions.model.Artigo;
import br.com.wofsolutions.model.Canone;
import br.com.wofsolutions.model.Capitulo;
import br.com.wofsolutions.model.Livro;
import br.com.wofsolutions.model.Parte;
import br.com.wofsolutions.model.Seccao;
import br.com.wofsolutions.model.Titulo;
import br.com.wofsolutions.util.ConexaoUtil;

/**
 * @author hfernandes
 * 
 */
public class LivroDAOImpl implements Serializable {

	public void salvar(Livro livro) {
		ConexaoUtil conexaoUtil = new ConexaoUtil();

		try {
			PreparedStatement ps = conexaoUtil.getCon().prepareStatement(
					"INSERT INTO wof_livros (descricao) VALUES (?)");
			ps.setString(1, livro.getDescricao());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void salvar(Titulo titulo) {
		ConexaoUtil conexaoUtil = new ConexaoUtil();

		try {
			PreparedStatement ps = conexaoUtil
					.getCon()
					.prepareStatement(
							"INSERT INTO wof_titulos (descricao, livro_id) VALUES (?, ?)");
			ps.setString(1, titulo.getDescricao());
			ps.setInt(2, titulo.getLivro().getLivroId());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void salvar(Capitulo capitulo) {
		ConexaoUtil conexaoUtil = new ConexaoUtil();
		PreparedStatement ps;
		try {
			if (capitulo.getParte().getParteId() == null) {
				ps = conexaoUtil
						.getCon()
						.prepareStatement(
								"INSERT INTO wof_capitulos (descricao, titulo_id) VALUES (?, ?)");
				ps.setString(1, capitulo.getDescricao());
				ps.setInt(2, capitulo.getTitulo().getTituloId());

			} else {
				ps = conexaoUtil
						.getCon()
						.prepareStatement(
								"INSERT INTO wof_capitulos (descricao, titulo_id, parte_id) VALUES (?, ?,?)");
				ps.setString(1, capitulo.getDescricao());
				ps.setInt(2, capitulo.getTitulo().getTituloId());
				ps.setInt(3, capitulo.getParte().getParteId());
			}
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void salvar(Artigo artigo) {
		ConexaoUtil conexaoUtil = new ConexaoUtil();

		try {
			PreparedStatement ps = conexaoUtil
					.getCon()
					.prepareStatement(
							"INSERT INTO wof_artigos (descricao, capitulo_id) VALUES (?, ?)");
			ps.setString(1, artigo.getDescricao());
			ps.setInt(2, artigo.getCapitulo().getCapituloId());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void salvar(Parte parte) {
		ConexaoUtil conexaoUtil = new ConexaoUtil();

		try {
			PreparedStatement ps = conexaoUtil
					.getCon()
					.prepareStatement(
							"INSERT INTO wof_partes (descricao, livro_id) VALUES (?, ?)");
			ps.setString(1, parte.getDescricao());
			ps.setInt(2, parte.getLivro().getLivroId());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void salvar(Seccao seccao) {
		ConexaoUtil conexaoUtil = new ConexaoUtil();

		try {
			PreparedStatement ps = conexaoUtil
					.getCon()
					.prepareStatement(
							"INSERT INTO wof_seccao (descricao, parte_id) VALUES (?, ?)");
			ps.setString(1, seccao.getDescricao());
			ps.setInt(2, seccao.getParte().getParteId());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void salvar(Canone canone) {
		ConexaoUtil conexaoUtil = new ConexaoUtil();
		PreparedStatement ps = null;

		try {

			if (canone.getTitulo() != null) {
			//	if (canone.getTitulo().getTituloId() != null) {

					if (getCanonePeloNumero(canone).getCanoneId() == null) {

						ps = conexaoUtil
								.getCon()
								.prepareStatement(
										"INSERT INTO wof_canones (descricao, numero,  livro_id, titulo_id, parte_id) VALUES (?, ?,?,?,?)");
						ps.setString(1, canone.getDescricao());
						ps.setString(2, canone.getNumero());
						ps.setInt(3, canone.getLivro().getLivroId()==null?0:canone.getLivro().getLivroId());
						ps.setInt(4, canone.getTitulo().getTituloId()==null?0:canone.getTitulo().getTituloId());
						if(canone.getParte()==null){
							canone.setParte(new Parte());
						}
						ps.setInt(5, canone.getParte().getParteId()==null?0:canone.getParte().getParteId());
						ps.execute();
					}
				}

//			} else {
//				ps = conexaoUtil
//						.getCon()
//						.prepareStatement(
//								"INSERT INTO wof_canones (descricao, numero,  livro_id) VALUES (?, ?,?)");
//				ps.setString(1, canone.getDescricao());
//				ps.setString(2, canone.getNumero());
//				ps.setInt(3, canone.getLivro().getLivroId());
//				ps.execute();
//			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void update(Canone canone) {
		ConexaoUtil conexaoUtil = new ConexaoUtil();
		PreparedStatement ps = null;

		try {

			if(canone.getNumero()==null){
				System.out.println(canone.getNumero());
			}
				
			if (canone.getNumero().equals("204")) {
				System.out.println(canone.getDescricao());
				System.out.println(canone.getParte().getDescricao());
				System.out.println(canone.getParte().getParteId());
			}

			if (canone.getParte() != null) {

				ps = conexaoUtil.getCon().prepareStatement(
						"update wof_canones set  parte_id=? where numero=?");
				ps.setInt(1, canone.getParte().getParteId());
				ps.setString(2, canone.getNumero());
				ps.executeUpdate();

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Livro getLivro(Livro livro) {
		ConexaoUtil conexaoUtil = new ConexaoUtil();

		try {
			PreparedStatement ps = conexaoUtil.getCon().prepareStatement(
					"select livro_id from wof_livros where descricao=?");
			ps.setString(1, livro.getDescricao());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				livro.setLivroId(rs.getInt("livro_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return livro;
	}

	public Titulo getTitulo(Titulo titulo) {
		ConexaoUtil conexaoUtil = new ConexaoUtil();

		try {
			PreparedStatement ps = conexaoUtil.getCon().prepareStatement(
					"select titulo_id from wof_titulos where descricao=?");
			ps.setString(1, titulo.getDescricao());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				titulo.setTituloId(rs.getInt("titulo_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return titulo;
	}

	public Capitulo getCapitulo(Capitulo capitulo) {
		ConexaoUtil conexaoUtil = new ConexaoUtil();

		try {
			PreparedStatement ps = conexaoUtil.getCon().prepareStatement(
					"select capitulo_id from wof_capitulos where descricao=?");
			ps.setString(1, capitulo.getDescricao());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {

				capitulo.setCapituloId(rs.getInt("capitulo_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return capitulo;
	}

	public Parte getParte(Parte parte) {
		ConexaoUtil conexaoUtil = new ConexaoUtil();

		try {
			PreparedStatement ps = conexaoUtil.getCon().prepareStatement(
					"select parte_id from wof_partes where descricao=?");
			ps.setString(1, parte.getDescricao());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				parte.setParteId(rs.getInt("parte_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return parte;
	}

	public Canone getCanonePeloNumero(Canone canone) {
		ConexaoUtil conexaoUtil = new ConexaoUtil();

		try {
			PreparedStatement ps = conexaoUtil.getCon().prepareStatement(
					"select canone_id from wof_canones where numero=?");
			ps.setString(1, canone.getNumero());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				canone.setCanoneId(rs.getInt("canone_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return canone;
	}

}
