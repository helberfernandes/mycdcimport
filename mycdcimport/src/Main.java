import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import br.com.wofsolutions.dao.LivroDAOImpl;
import br.com.wofsolutions.model.Artigo;
import br.com.wofsolutions.model.Canone;
import br.com.wofsolutions.model.Capitulo;
import br.com.wofsolutions.model.Livro;
import br.com.wofsolutions.model.Parte;
import br.com.wofsolutions.model.Seccao;
import br.com.wofsolutions.model.Titulo;
import br.com.wofsolutions.util.HibernateUtil;

public class Main {
	private static List<Livro> livros = new ArrayList<Livro>();
	private static List<Canone> canones = new ArrayList<Canone>();
	public static void main(String[] args) {
		// TODO Codex Iuris Canonici

		HibernateUtil.gerabanco();

		// Livro livro = new Livro();
		// livro.setTitulo("ssss");
		//
		LivroDAOImpl livroDAOImpl = new LivroDAOImpl();
		// livroDAOImpl.salvarObjeto(livro);

		lerArquivo2("C:\\Users\\Helber\\Documents\\codex-iuris-canonici_po.txt");

		System.out.println(" ");
		System.out.println(" ");
		System.out.println(" ");
		System.out.println("------------------------------------------");

		for (Livro livro : livros) {

			livroDAOImpl.salvar(livro);
			for (Titulo titulo : livro.getTitulos()) {

				titulo.setLivro(livroDAOImpl.getLivro(livro));
				livroDAOImpl.salvar(titulo);

//				for (Canone canone : titulo.getCanones()) {
//					canone.setLivro(livroDAOImpl.getLivro(livro));
//					canone.setTitulo(livroDAOImpl.getTitulo(titulo));
//					livroDAOImpl.salvar(canone);
//				}

				for (Capitulo capitulo : titulo.getCapitulos()) {
					capitulo.setTitulo(livroDAOImpl.getTitulo(titulo));
					livroDAOImpl.salvar(capitulo);
					for (Artigo artigo : capitulo.getArtigos()) {
						artigo.setCapitulo(livroDAOImpl.getCapitulo(capitulo));
						livroDAOImpl.salvar(artigo);
					}
				}
			}
			for (Parte parte : livro.getPartes()) {

				parte.setLivro(livroDAOImpl.getLivro(livro));
				livroDAOImpl.salvar(parte);
				for (Seccao seccao : parte.getSeccaos()) {
					seccao.setParte(livroDAOImpl.getParte(parte));
					livroDAOImpl.salvar(seccao);
				}

//				for (Canone canone : parte.getCanones()) {
//					canone.setParte(livroDAOImpl.getParte(parte));
//					if (canone.getNumero().equals("204")) {
//					 livroDAOImpl.update(canone);
//					}
//				}

			}

//			for (Canone canone : livro.getCanones()) {
//				canone.setLivro(livroDAOImpl.getLivro(livro));
//				livroDAOImpl.salvar(canone);
//			}
		}
		
		for (Canone canone :canones) {
			//canone.setLivro(livroDAOImpl.getLivro(livro));
			System.out.println(canone.getTitulo().getDescricao());
			
			
			//livroDAOImpl.salvar(canone);
		}
	}

	public static void lerArquivo2(String arquivo) {
		// HibernateUtil.getCurrentSession();

		File f = new File(arquivo);

		if (!f.exists()) {
			System.out.println("Arquivo" + arquivo + " nï¿½o existe");
			return;
		}
		int i = 1;
		try {
			// fluxo de entrada a partir de um arquivo
			InputStream is = new FileInputStream(arquivo);
			// classe que converte os bytes em chars
			InputStreamReader isr = new InputStreamReader(is);
			// armazena os chars em memï¿½ria,2*1024*1024
			BufferedReader br = new BufferedReader(isr);
			// inicia na primeira linha
			br.readLine();
			String s = br.readLine();

			int contCapiLinha = 0;
			int contTituloLinha = 0;
			int contLivroLinha = 0;
			int contParteLinha = 0;
			int contSeccaoLinha = 0;
			boolean fimCan = false;
			boolean fimLiv = false;
			int linhas = 0;

			Livro livro = null;
			Canone canone = null;
			Titulo titulo = null;
			Capitulo capitulo = null;
			Parte parte = null;
			Seccao seccao = null;
			Artigo artigo = null;
			LivroDAOImpl livroDAOImpl = new LivroDAOImpl();
			String titulo2;

			while (s != null) {

				s = br.readLine();
				if (s != null) {

					// if ( s.contains("LIV.")) {
					// System.out.println(s.replace("LIV.",
					// "LIVRO").toUpperCase());
					// titulo++;
					// fimLiv=true;
					// }

					if (s.contains("LIVRO") && s.contains("-")) {

						// novo livro zera tudo
						titulo = null;
						capitulo = null;
						parte = null;
						seccao = null;
						artigo = null;
						livro = new Livro();
						livro.setDescricao(s);

						if (livro != null) {

							// livroDAOImpl.salvar(livro);
							livros.add(livro);
							titulo2 = livro.getDescricao();

						}
					} else if (s.contains("TÍTULO") && s.contains("-")) {

						titulo = new Titulo();

						// titulo.setParte(parte);
						titulo.setDescricao(s);

						if (titulo != null) {
							// System.out.println("|---" + titulo.getTitulo());

							// System.out.println(" dd "+livro.getTitulo() );
							livro.getTitulos().add(titulo);
							// livroDAOImpl.salvar(titulo);

						}

					}

					else if (s.contains("PARTE") && s.contains("-")) {// trabalhar
																		// por
																		// ultimo
																		// pois
																		// a
																		// parte
																		// pode
																		// compreender
																		// ate
																		// mesmo
																		// o
																		// livro

						parte = new Parte();
						parte.setLivro(livro);
						parte.setDescricao(s);

						if (parte != null) {
							livro.getPartes().add(parte);
							parte.getCanones().add(canone);
						}

					}

					else if (s.contains("CAPÍTULO")) {

						capitulo = new Capitulo();
						capitulo.setTitulo(titulo);
						capitulo.setDescricao(s);

						if (capitulo != null) {
							titulo.getCapitulos().add(capitulo);
						}
					}

					// else if (s.contains("LIV.")) {
					//
					else if (s.contains("Cân.")) {

						String numcan = s.substring(s.indexOf("Cân."),
								s.indexOf("—") + 1);
						numcan = numcan.replace("Cân.", "");
						numcan = numcan.replace("—", "");

						canone = new Canone();
						canone.setNumero(numcan.trim());
						// s= s.replace(s.substring(s.indexOf("Cï¿½n."),
						// s.indexOf("ï¿½")+1), "");
						canone.setLivro(livro);
						canone.setTitulo(titulo);
						canone.setCapitulo(capitulo);
						canone.setParte(parte);
						// canone.setArtigo(artigo);

						canone.setDescricao(s);

						// System.out.println(canone.getNumero()+"------------"+canone.getLivro().getDescricao()+"------------"+canone.getDescricao());

						// fimCan = false;

						if (canone != null) {
							livro.getCanones().add(canone);
							if (canone.getTitulo() != null) {
								titulo.getCanones().add(canone);
							} else if (canone.getCapitulo() != null) {

							} else if (canone.getLivro() != null) {// caso o
																	// canone
																	// nao tenha
																	// nem
																	// capitulo
																	// nem
																	// titulo
																	// ele e do
																	// livro
								// livro.getCanones().add(canone);
							}

							if (canone.getParte() != null) {

								parte.getCanones().add(canone);
							}
							
							canones.add(canone);						}

					} else if (!s.trim().isEmpty()) {
						if (canone != null) {
							canone.setDescricao(canone.getDescricao() + s);
						}
					} else if (s.contains("SECÇÃO")) {

						seccao = new Seccao();
						seccao.setParte(parte);
						seccao.setDescricao(s);
						if (seccao != null) {
							parte.getSeccaos().add(seccao);
						}
					} else if (s.contains("Art.")) {

						artigo = new Artigo();
						artigo.setDescricao(s);
						artigo.setCapitulo(capitulo);
						if (artigo != null) {
							// livroDAOImpl.salvarObjeto(artigo);
							capitulo.getArtigos().add(artigo);
						}
					}

					// if (s.contains("LIVRO")&& !s.contains("LIVROS")) {
					//
					// System.out.println(linhas+"--x"+s);
					// contLivroLinha=linhas;
					// fimLiv=true;
					// }else if(linhas==(contLivroLinha+2)){
					//
					// System.out.println("--"+s);
					// }

					// if (s.contains("TIT.")) {
					// //System.out.println("--------"+s);
					// }else if (s.contains("Tï¿½TULO")) {
					// contTituloLinha=linhas;
					// System.out.println("----------"+s);
					//
					// }else if(linhas==(contTituloLinha+2)){
					//
					// System.out.println("------------"+s);
					// }
					//
					//

					// else if(linhas==(contCapiLinha+2)){
					//
					// System.out.println("------------"+s);
					// }
					// //

					// else if (s.contains("PARTE") && s.contains("ï¿½") ) {
					// //System.out.println("----"+s);
					// }else if (s.contains("PARTE") && !s.contains("ï¿½")) {
					// contParteLinha=linhas;
					// System.out.println("----------"+s);
					//
					// }else if(linhas==(contParteLinha+2)){
					//
					// System.out.println("------------"+s);
					// }
					//

					// }else if(linhas==(contSeccaoLinha+2)){
					//
					// System.out.println("------------"+s);
					// }

					// //
					//
					// //
					//

					if (s.contains("APï¿½NDICES")) {
						break;
					}

					if (s.contains("ï¿½NDICE GERAL")) {
						break;
					}

					if (s.contains("ï¿½NDICE DE MATï¿½RIAS")) {
						break;
					}

					if (s.contains("BREVE GLOSSï¿½RIO JURï¿½DICO-CANï¿½NICO*")) {
						break;
					}

					if (s.contains("PREFï¿½CIO")) {
						break;
					}

					linhas++;
				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
