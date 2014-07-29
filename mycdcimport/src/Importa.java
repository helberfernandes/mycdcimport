import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.wofsolutions.dao.LivroDAOImpl;
import br.com.wofsolutions.model.Artigo;
import br.com.wofsolutions.model.Canone;
import br.com.wofsolutions.model.Capitulo;
import br.com.wofsolutions.model.Livro;
import br.com.wofsolutions.model.Parte;
import br.com.wofsolutions.model.Seccao;
import br.com.wofsolutions.model.Titulo;
import br.com.wofsolutions.util.HibernateUtil;
/**
 * 
 * @author Helber Fernandes
 *
 */
public class Importa {

	private static List<Livro> livros = new ArrayList<Livro>();

	public static void main(String[] args) {
		lerArquivo("C:\\Users\\Helber\\Documents\\codex-iuris-canonici_po.txt");

	}

	public static void lerArquivo(String arquivo) {
		File f = new File(arquivo);

		if (!f.exists()) {
			System.out.println("Arquivo" + arquivo + " nï¿½o existe");
			return;
		}

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

			Pattern pegaJava = Pattern.compile("LIVRO[ A-Z -]*");
			// primeiro Java está em maíusculo!
			Matcher m;

			Livro livro = null;
			Titulo titulo = null;
			Parte parte = null;
			Capitulo capitulo = null;
			Artigo artigo = null;
			Seccao seccao = null;
			Canone canone = null;

		//	HibernateUtil.gerabanco();

			// Livro livro = new Livro();
			// livro.setTitulo("ssss");
			//
			LivroDAOImpl livroDAOImpl = new LivroDAOImpl();

			while (s != null) {

				s = br.readLine();
				if (s != null) {

					pegaJava = Pattern
							.compile("LIVRO [A-Z]{1,3} - [A-Z \\p{L}]*");
					m = pegaJava.matcher(s);

					while (m.find()) {
						if (livro != null) {
							livros.add(livro);
						}
						livro = new Livro();
						livro.setDescricao(m.group());

						livroDAOImpl.salvar(livro);

						// inicia novamente os outros itens
						parte = null;
						titulo = null;
						seccao = null;
						capitulo = null;
						artigo = null;
						canone = null;
					}

					pegaJava = Pattern
							.compile("PARTE [A-Z]{1,4} - [A-Z \\p{L}]*");
					m = pegaJava.matcher(s);

					while (m.find()) {
						parte = new Parte();
						parte.setDescricao(m.group());
						parte.setLivro(livroDAOImpl.getLivro(livro));
						livro.getPartes().add(parte);

						livroDAOImpl.salvar(parte);
					}

					pegaJava = Pattern
							.compile("SECÇÃO [A-Z]{1,4} - [A-Z \\p{L}]*");
					m = pegaJava.matcher(s);

					while (m.find()) {
						seccao = new Seccao();
						seccao.setDescricao(m.group());
						seccao.setParte(livroDAOImpl.getParte(parte));
						parte.getSeccaos().add(seccao);

						livroDAOImpl.salvar(seccao);
					}

					pegaJava = Pattern
							.compile("TÍTULO [A-Z]{1,4} - [A-Z \\p{L}]*");
					m = pegaJava.matcher(s);

					while (m.find()) {
						titulo = new Titulo();
						titulo.setDescricao(m.group());
						titulo.setLivro(livroDAOImpl.getLivro(livro));

						livro.getTitulos().add(titulo);

						if (seccao != null) {
							titulo.setSeccao(livroDAOImpl.getSeccao(seccao));
						}

						if (parte != null) {
							titulo.setParte(livroDAOImpl.getParte(parte));
						}

						livroDAOImpl.salvar(titulo);

					}

					pegaJava = Pattern
							.compile("CAPÍTULO [A-Z]{1,4} - [A-Z \\p{L}]*");
					m = pegaJava.matcher(s);

					while (m.find()) {
						capitulo = new Capitulo();
						capitulo.setDescricao(m.group());
						if (titulo != null) {
							titulo.getCapitulos().add(capitulo);
							capitulo.setTitulo(livroDAOImpl.getTitulo(titulo));
						}

						if (seccao != null) {
							capitulo.setSeccao(livroDAOImpl.getSeccao(seccao));
						}

						if (parte != null) {
							capitulo.setParte(livroDAOImpl.getParte(parte));
						}

						livroDAOImpl.salvar(capitulo);

					}

					pegaJava = Pattern
							.compile("Art. [0-9]{1,4} - [A-Z \\p{L}]*");
					m = pegaJava.matcher(s);

					while (m.find()) {
						artigo = new Artigo();
						artigo.setDescricao(m.group());
						if (capitulo != null) {
							capitulo.getArtigos().add(artigo);
							artigo.setCapitulo(livroDAOImpl
									.getCapitulo(capitulo));
						}
						livroDAOImpl.salvar(artigo);
					}

					if (s.contains("Cân.")) {

						String numcan = s.substring(s.indexOf("Cân."),
								s.indexOf("—") + 1);
						numcan = numcan.replace("Cân.", "");
						numcan = numcan.replace("—", "");

						if (canone != null) {
							livroDAOImpl.salvar(canone);
						}
						canone = new Canone();
						canone.setNumero(numcan.trim());
						// s= s.replace(s.substring(s.indexOf("Cï¿½n."),
						// s.indexOf("ï¿½")+1), "");
						canone.setLivro(livroDAOImpl.getLivro(livro));

						if (titulo != null) {
							canone.setTitulo(livroDAOImpl.getTitulo(titulo));
						}
						if (capitulo != null) {
							canone.setCapitulo(livroDAOImpl
									.getCapitulo(capitulo));
						}
						if (parte != null) {
							canone.setParte(livroDAOImpl.getParte(parte));
						}
						if (artigo != null) {
							canone.setArtigo(livroDAOImpl.getArtigo(artigo));
						}

						canone.setDescricao(s);

						// System.out.println(canone.getNumero()+"------------"+canone.getLivro().getDescricao()+"------------"+canone.getDescricao());

						// fimCan = false;
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

						}

					} else if (!s.trim().isEmpty()) {
						if (canone != null) {

							canone.setDescricao(canone.getDescricao() + s);

						}
					}

				}

			}

			if (canone != null) {
				if (canone.getNumero().equals("1752")) {// ultimo canone
					livroDAOImpl.salvar(canone);
				}
			}
			livros.add(livro);// o ultimo livro so e pego quando termina o while

			for (Livro livro2 : livros) {
				System.out.println(livro2.getDescricao());

				if (livro2.getPartes().size() > 0) {// parte vem antes de todos
													// os outros itens
					imprimeParte(livro2.getPartes());
				} else {// caso o livro já comece com um titulo.
					imprimeTitulo(livro2.getTitulos());
				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void imprimeTitulo(List<Titulo> list) {
		for (Titulo titulo : list) {
			if (titulo.getSeccao() == null) { // devemos imprimir a seccao
												// primeiro
				System.out.println("|__" + titulo.getDescricao());
				if (titulo.getCapitulos().size() > 0) {
					imprimeCapitulo(titulo.getCapitulos());
				}

			}
		}
	}

	private static void imprimeCapitulo(List<Capitulo> list) {
		for (Capitulo capitulo : list) {
			System.out.println("|____" + capitulo.getDescricao());
			imprimeArtigo(capitulo.getArtigos());
		}
	}

	private static void imprimeArtigo(List<Artigo> list) {
		for (Artigo artigo : list) {
			System.out.println("|______" + artigo.getDescricao());
		}
	}

	private static void imprimeSeccao(List<Seccao> list) {
		for (Seccao seccao : list) {
			System.out.println("|______" + seccao.getDescricao());
		}
	}

	private static void imprimeParte(List<Parte> list) {
		for (Parte parte : list) {
			System.out.println("|__" + parte.getDescricao());

			if (parte.getSeccaos().size() > 0) {// caso a parte inicie com uma
												// seccao
				imprimeSeccao(parte.getSeccaos());
			}
		}
	}

}