package cotuba;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubWriter;
import nl.siegmann.epublib.service.MediatypeService;

public class EPUBGenerator {

	public void generate(Path mdDirectory, Path outputFile) {
		var epub = new Book();

		// TODO: usar título do capítulo
		epub.addSection("Capítulo",
			new Resource(html.getBytes(), MediatypeService.XHTML));

		var epubWriter = new EpubWriter();

		try {
			epubWriter.write(epub, Files.newOutputStream(outputFile));
		} catch (IOException ex) {
			throw new IllegalStateException(
				"Erro ao criar arquivo EPUB: " + outputFile.toAbsolutePath(), ex);
		}
	}

}
