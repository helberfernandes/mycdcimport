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
import br.com.wofsolutions.model.Glossario;
import br.com.wofsolutions.util.HibernateUtil;

/**
 * 
 * @author Helber Fernandes
 *
 */
public class ImportIndiceMaterias {



	public static void main(String[] args) {
		// TODO Auto-generated method stub

		lerArquivo("C:\\Users\\Helber\\Documents\\INDICE_DE_MATERIAS.txt");
	}

	/**
	 * @param arquivo
	 */
	public static void lerArquivo(String arquivo) {
		File f = new File(arquivo);

		if (!f.exists()) {
			System.out.println("Arquivo" + arquivo + " n�o existe");
			return;
		}

		try {
			//Codex Iuris Canonici, -cic
			// fluxo de entrada a partir de um arquivo
			InputStream is = new FileInputStream(arquivo);
			// classe que converte os bytes em chars
			InputStreamReader isr = new InputStreamReader(is);
			// armazena os chars em mem�ria,2*1024*1024
			BufferedReader br = new BufferedReader(isr);
			// inicia na primeira linha
			br.readLine();
			String s = br.readLine();

			// [a-zA-Z\\p{L}[- ]?]*[ �]* \\([a-zA-Z\\p{L}[-]?]*\\) [�]*
			// � primaz: nao pode aparecer e sub item
			
			//Abadia territorial () �
			Pattern pegaJava = Pattern.compile("[+A-Z\\p{L}- ]*[ �]*#");
			// primeiro Java est� em ma�usculo!
			Matcher m;

			//HibernateUtil.gerabanco();

			// Livro livro = new Livro();
			// livro.setTitulo("ssss");
			//
			//LivroDAOImpl livroDAOImpl = new LivroDAOImpl();
			
			Glossario glossario=null;
			List<Glossario> list = new ArrayList<Glossario>();
			
			while (s != null) {

				s = br.readLine();
				if (s != null) {
					
					
					m = pegaJava.matcher(s);
					
					while (m.find()) {
						if(glossario!=null){
						//	livroDAOImpl.salvar(glossario);
							list.add(glossario);
						}
						glossario = new Glossario();
						glossario.setPalavra(m.group());
						s=s.replaceAll(glossario.getPalavra().replaceAll("�", ""), "");						
					}
				
					
					if(glossario!=null){						
						glossario.setSignificado(glossario.getSignificado()+s.replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("-", "").replaceAll("�", "").replaceAll(glossario.getPalavra().replaceAll("\\(", "").replaceAll("\\)", "").replaceAll("-", "").replaceAll("�", ""), ""));
					}
						
					
				}
			}
			
			
			
			
			for(Glossario glossario2: list){
				System.out.println(glossario2.getPalavra() + " "+glossario2.getSignificado());
			}
			
			
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
