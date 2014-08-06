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
import br.com.wofsolutions.model.Glossario;
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
	PreparedStatement ps;
	ConexaoUtil conexaoUtil = new ConexaoUtil();
	public void salvar(Livro livro) {
		

		try {
			 ps = conexaoUtil.getCon().prepareStatement(
					"INSERT INTO wof_livros (descricao) VALUES (?)");
			ps.setString(1, livro.getDescricao());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void salvar(Titulo titulo) {
		

		try {

			 ps = conexaoUtil
					.getCon()
					.prepareStatement(
							"INSERT INTO wof_titulos (descricao, livro_id, parte_id, seccao_id) VALUES (?, ?, ?,?)");
			ps.setString(1, titulo.getDescricao());
			ps.setInt(2, titulo.getLivro().getLivroId());
			if (titulo.getParte() != null) {
				ps.setInt(3, titulo.getParte().getParteId());
			} else {
				ps.setNull(3, 0);
			}

			if (titulo.getSeccao() != null) {
				ps.setInt(4, titulo.getSeccao().getSeccaoId());
			} else {
				ps.setNull(4, 0);
			}

			ps.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void salvar(Capitulo capitulo) {
		
		
		try {

			ps = conexaoUtil
					.getCon()
					.prepareStatement(
							"INSERT INTO wof_capitulos (descricao, titulo_id, parte_id, seccao_id) VALUES (?, ?,?,?)");
			ps.setString(1, capitulo.getDescricao());
			ps.setInt(2, capitulo.getTitulo().getTituloId());

			if (capitulo.getParte() != null) {
				ps.setInt(3, capitulo.getParte().getParteId());
			} else {
				ps.setNull(3, 0);
			}

			if (capitulo.getSeccao() != null) {
				ps.setInt(4, capitulo.getSeccao().getSeccaoId());
			} else {
				ps.setNull(4, 0);
			}

			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void salvar(Artigo artigo) {
		

		try {
			ps = conexaoUtil
					.getCon()
					.prepareStatement(
							"INSERT INTO wof_artigos (descricao, capitulo_id) VALUES (?, ?)");
			ps.setString(1, artigo.getDescricao());
if(artigo.getCapitulo()!=null){
			ps.setInt(2, artigo.getCapitulo().getCapituloId());
}else{
	ps.setNull(2, 0);		
			}
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void salvar(Parte parte) {
		

		try {
			 ps = conexaoUtil
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
		

		try {
			 ps = conexaoUtil
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
		
		 ps = null;

		try {
			Integer canoneId=getCanonePeloNumero(canone).getCanoneId();
			if (canoneId == null) {

				ps = conexaoUtil
						.getCon()
						.prepareStatement(
								"INSERT INTO wof_canones (descricao, numero,  livro_id, titulo_id, parte_id, capitulo_id, artigo_id) "
										+ "VALUES (?, ?,?,?,?,?,?)");
				ps.setString(1, canone.getDescricao());
				ps.setString(2, canone.getNumero());

				if (canone.getLivro() != null) {
					ps.setInt(3, canone.getLivro().getLivroId());
				} else {
					ps.setNull(3, 0);
				}

				if (canone.getTitulo() != null) {
					ps.setInt(4, canone.getTitulo().getTituloId());
				} else {
					ps.setNull(4, 0);
				}
				if (canone.getParte() != null) {
					ps.setInt(5, canone.getParte().getParteId());
				} else {
					ps.setNull(5, 0);
				}

				if (canone.getCapitulo() != null) {
					ps.setInt(6, canone.getCapitulo().getCapituloId());
				} else {
					ps.setNull(6, 0);
				}

				if (canone.getArtigo() != null) {					
					ps.setInt(7, canone.getArtigo().getArtigoId());
				} else {
					ps.setNull(7, 0);
				}

				ps.execute();
			}else{
				ps = conexaoUtil
						.getCon()
						.prepareStatement(
								"update wof_canones set descricao=?, numero=?,  livro_id=?, titulo_id=?, parte_id=?, capitulo_id=?, artigo_id=? "
										+ "where canone_id=?");
				ps.setString(1, canone.getDescricao());
				ps.setString(2, canone.getNumero());

				if (canone.getLivro() != null) {
					ps.setInt(3, canone.getLivro().getLivroId());
				} else {
					ps.setNull(3, 0);
				}

				if (canone.getTitulo() != null) {
					ps.setInt(4, canone.getTitulo().getTituloId());
				} else {
					ps.setNull(4, 0);
				}
				if (canone.getParte() != null) {
					ps.setInt(5, canone.getParte().getParteId());
				} else {
					ps.setNull(5, 0);
				}

				if (canone.getCapitulo() != null) {
					ps.setInt(6, canone.getCapitulo().getCapituloId());
				} else {
					ps.setNull(6, 0);
				}

				if (canone.getArtigo() != null) {					
					ps.setInt(7, canone.getArtigo().getArtigoId());
				} else {
					ps.setNull(7, 0);
				}

				ps.setInt(8, canoneId);
					
				ps.execute();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void update(Canone canone) {
		
		

		try {

			if (canone.getNumero() == null) {
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
		

		try {
			 ps = conexaoUtil.getCon().prepareStatement(
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
		

		try {
			ps = conexaoUtil.getCon().prepareStatement(
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
		

		try {
			 ps = conexaoUtil.getCon().prepareStatement(
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
		

		try {
			 ps = conexaoUtil.getCon().prepareStatement(
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

	public Seccao getSeccao(Seccao seccao) {
		

		try {
			 ps = conexaoUtil.getCon().prepareStatement(
					"select seccao_id from wof_seccao where descricao=?");
			ps.setString(1, seccao.getDescricao());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				seccao.setSeccaoId(rs.getInt("seccao_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return seccao;
	}

	public Artigo getArtigo(Artigo artigo) {
		

		try {
			 ps = conexaoUtil.getCon().prepareStatement(
					"select artigo_id from wof_artigos where descricao=?");
			ps.setString(1, artigo.getDescricao());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				artigo.setArtigoId(rs.getInt("artigo_id"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return artigo;
	}

	public Canone getCanonePeloNumero(Canone canone) {
		

		try {
			 ps = conexaoUtil.getCon().prepareStatement(
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

	
	
	
	
	public void salvar(Glossario glossario) {
		

		try {
			 ps = conexaoUtil.getCon().prepareStatement(
					"INSERT INTO wof_glossario (palavra, significado) VALUES (?,?)");
			ps.setString(1, glossario.getPalavra());
			ps.setString(2, glossario.getSignificado());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
