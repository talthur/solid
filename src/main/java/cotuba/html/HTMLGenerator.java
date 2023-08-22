package cotuba.html;

import cotuba.application.EBookGenerator;
import cotuba.model.Chapter;
import cotuba.model.EBook;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.Normalizer;

public class HTMLGenerator implements EBookGenerator {

	@Override
	public void generate(EBook eBook) {
		Path outputFile = eBook.getOutputFile();
		try {
			Path htmlDirectory = Files.createDirectory(outputFile);
			int i = 1;
			for (Chapter chapter : eBook.getChapters()) {
				String nomeDoArquivoHTMLDoCapitulo = obtemNomeDoArquivoHTMLDoCapitulo(i, chapter);

				Path arquivoHTMLDoCapitulo = htmlDirectory.resolve(nomeDoArquivoHTMLDoCapitulo);
				String html = """
					<!DOCTYPE html>
					<html lang="pt-BR">
					  <head>
					    <meta charset="UTF-8">
					    <title>%s</title>
					  </head>
					  <body>
					    %s
					  </body>
					</html>
					""".formatted(chapter.getTitle(), chapter.getHTMLContent());
				Files.writeString(arquivoHTMLDoCapitulo, html, StandardCharsets.UTF_8);

				i++;
			}

		} catch (IOException ex) {
			throw new IllegalStateException("Erro ao criar HTML: " + outputFile.toAbsolutePath(), ex);
		}

	}

	private String obtemNomeDoArquivoHTMLDoCapitulo(int i, Chapter chapter) {
		return i + "-"
			+ removeAcentos(chapter.getTitle().toLowerCase())
			.replaceAll("[^\\w]", "")
			+ ".html";
	}

	private String removeAcentos(String texto) {
		return Normalizer.normalize(texto, Normalizer.Form.NFD)
			.replaceAll("[^\\p{ASCII}]", "");
	}
}
