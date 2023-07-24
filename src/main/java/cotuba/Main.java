package cotuba;

import java.nio.file.Path;

public class Main {

	public static void main(String[] args) {

		var commandLineReader = new CommandLineReader(args);

		Path diretorioDosMD = commandLineReader.getDiretorioDosMD();
		String formato = commandLineReader.getFormato();
		Path arquivoDeSaida = commandLineReader.getArquivoDeSaida();
		boolean modoVerboso = commandLineReader.isModoVerboso();

		try {
			if ("pdf".equals(formato)) {
				var pdfGenerator = new PDFGenerator();
				pdfGenerator.generate(diretorioDosMD, arquivoDeSaida);

			} else if ("epub".equals(formato)) {
				var epubGenerator = new EPUBGenerator();
				epubGenerator.generate(diretorioDosMD, arquivoDeSaida);
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
