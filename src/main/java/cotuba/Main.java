package cotuba;

import cotuba.model.Chapter;
import cotuba.model.EBook;
import java.nio.file.Path;
import java.util.List;

public class Main {

	public static void main(String[] args) {

		var commandLineReader = new CommandLineReader(args);

		Path diretorioDosMD = commandLineReader.getDiretorioDosMD();
		String formato = commandLineReader.getFormato();
		Path arquivoDeSaida = commandLineReader.getArquivoDeSaida();
		boolean modoVerboso = commandLineReader.isModoVerboso();

		MdToHtmlRenderer mdToHtmlRenderer = new MdToHtmlRenderer();
		List<Chapter> render = mdToHtmlRenderer.render(diretorioDosMD);
		EBook eBook = EBook.of(formato, arquivoDeSaida, render);
		try {
			if ("pdf".equals(formato)) {
				var pdfGenerator = new PDFGenerator();
				pdfGenerator.generate(eBook);

			} else if ("epub".equals(formato)) {
				var epubGenerator = new EPUBGenerator();
				epubGenerator.generate(eBook);
			} else {
				throw new IllegalArgumentException("Formato do ebook inválido: " + formato);
			}

			System.out.println("Arquivo gerado com sucesso: " + arquivoDeSaida);

		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			if (modoVerboso) {
				ex.printStackTrace();
			}
			System.exit(1);
		}
	}

}
