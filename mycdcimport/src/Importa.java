import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.wofsolutions.model.Artigo;
import br.com.wofsolutions.model.Capitulo;
import br.com.wofsolutions.model.Livro;
import br.com.wofsolutions.model.Parte;
import br.com.wofsolutions.model.Seccao;
import br.com.wofsolutions.model.Titulo;

public class Importa {

	private static List<Livro> livros = new ArrayList<Livro>();

	public static void main(String[] args) {
		lerArquivo("C:\\Users\\Helber\\Documents\\codex-iuris-canonici_po.txt");
	}

	public static void lerArquivo(String arquivo) {
		File f = new File(arquivo);

		if (!f.exists()) {
			System.out.println("Arquivo" + arquivo + " n�o existe");
			return;
		}

		try {
			// fluxo de entrada a partir de um arquivo
			InputStream is = new FileInputStream(arquivo);
			// classe que converte os bytes em chars
			InputStreamReader isr = new InputStreamReader(is);
			// armazena os chars em mem�ria,2*1024*1024
			BufferedReader br = new BufferedReader(isr);
			// inicia na primeira linha
			br.readLine();
			String s = br.readLine();

			Pattern pegaJava = Pattern.compile("LIVRO[ A-Z -]*");
			// primeiro Java est� em ma�usculo!
			Matcher m;

			Livro livro = null;
			Titulo titulo = null;
			Parte parte = null;
			Capitulo capitulo = null;
			Artigo artigo = null;
			Seccao seccao = null;

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
					}

					pegaJava = Pattern
							.compile("PARTE [A-Z]{1,4} - [A-Z \\p{L}]*");
					m = pegaJava.matcher(s);

					while (m.find()) {
						parte = new Parte();
						parte.setDescricao(m.group());
						livro.getPartes().add(parte);
					}

					pegaJava = Pattern
							.compile("SEC��O [A-Z]{1,4} - [A-Z \\p{L}]*");
					m = pegaJava.matcher(s);

					while (m.find()) {
						seccao = new Seccao();
						seccao.setDescricao(m.group());
						parte.getSeccaos().add(seccao);
					}
					
					if(!m.find()){
						seccao=null;
					}

					pegaJava = Pattern
							.compile("T�TULO [A-Z]{1,4} - [A-Z \\p{L}]*");
					m = pegaJava.matcher(s);

					while (m.find()) {
						titulo = new Titulo();
						titulo.setDescricao(m.group());
						livro.getTitulos().add(titulo);

						if (seccao != null) {
							titulo.setSeccao(seccao);
						}

						if (parte != null) {
							titulo.setParte(parte);
						}

					}
					
					
					if(!m.find()){
						titulo=null;
					}

					pegaJava = Pattern
							.compile("CAP�TULO [A-Z]{1,4} - [A-Z \\p{L}]*");
					m = pegaJava.matcher(s);

					while (m.find()) {
						capitulo = new Capitulo();
						capitulo.setDescricao(m.group());
						if(titulo!=null){
							titulo.getCapitulos().add(capitulo);
						}

						if (seccao != null) {
							capitulo.setSeccao(seccao);
						}

					}
					
					if(!m.find()){
						capitulo=null;
					}

					pegaJava = Pattern
							.compile("Art. [0-9]{1,4} - [A-Z \\p{L}]*");
					m = pegaJava.matcher(s);
					artigo = new Artigo();

					while (m.find()) {
						artigo.setDescricao(m.group());
						if(capitulo!=null){
						capitulo.getArtigos().add(artigo);
						}
						
					
					}

				}
			}

			livros.add(livro);// o ultimo livro so e pego quando termina o while

			for (Livro livro2 : livros) {
				System.out.println(livro2.getDescricao());

				for (Parte parte2 : livro2.getPartes()) {
					System.out.println("|__" + parte2.getDescricao());

					for (Seccao seccao2 : parte2.getSeccaos()) {
						System.out.println("|____" + seccao2.getDescricao());

						for (Titulo titulo2 : livro2.getTitulos()) {

							if (titulo2.getSeccao() != null) {
								if (seccao2.getDescricao().equals(
										titulo2.getSeccao().getDescricao())) {
									if (titulo2.getParte().getDescricao()
											.equals(parte2.getDescricao())) {
										System.out.println("|______"
												+ titulo2.getDescricao());

										for (Capitulo capitulo2 : titulo2
												.getCapitulos()) {
											System.out.println("|__________SSSS"
													+ capitulo2.getDescricao());
											for (Artigo artigo2 : capitulo2
													.getArtigos()) {
												System.out
														.println("|____________SSSS"
																+ artigo2
																		.getDescricao());
											}
										}
									}
								}
							}
						}
						
						
						for (Capitulo capitulo2 : seccao2
								.getCapitulos()) {
							
							if(capitulo2.getTitulo()==null){
							System.out.println("|________________________________"
									+ capitulo2.getDescricao());
							for (Artigo artigo2 : capitulo2
									.getArtigos()) {
								System.out
										.println("|_______________________________"
												+ artigo2
														.getDescricao());
							}
						}
						}
						

					}

					for (Titulo titulo2 : livro2.getTitulos()) {
						if (titulo2.getSeccao() == null) {
							if (titulo2.getParte().getDescricao()
									.equals(parte2.getDescricao())) {
								System.out.println("|____"
										+ titulo2.getDescricao());

								for (Capitulo capitulo2 : titulo2
										.getCapitulos()) {
									System.out.println("|______***"
											+ capitulo2.getDescricao());
									for (Artigo artigo2 : capitulo2
											.getArtigos()) {
										System.out.println("|________***"
												+ artigo2.getDescricao());
									}
								}
							}
						}
					}

				}
				if (livro2.getPartes().size() == 0) {
					for (Titulo titulo2 : livro2.getTitulos()) {
						System.out.println("|__" + titulo2.getDescricao());

						for (Capitulo capitulo2 : titulo2.getCapitulos()) {
							System.out.println("|____"
									+ capitulo2.getDescricao());
							for (Artigo artigo2 : capitulo2.getArtigos()) {
								System.out.println("|________"
										+ artigo2.getDescricao());
							}
						}
					}
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}