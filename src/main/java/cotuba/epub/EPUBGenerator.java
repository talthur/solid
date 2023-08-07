package cotuba.epub;

import cotuba.model.Chapter;
import cotuba.model.EBook;
import java.io.IOException;
import java.nio.file.Files;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubWriter;
import nl.siegmann.epublib.service.MediatypeService;

public class EPUBGenerator {

	public void generate(EBook eBook) {
		var epub = new Book();
		for (Chapter chapter : eBook.getChapters()) {

			epub.addSection(chapter.getTitle(),
				new Resource(chapter.getHTMLContent().getBytes(), MediatypeService.XHTML));
		}
		var epubWriter = new EpubWriter();

		try {
			epubWriter.write(epub, Files.newOutputStream(eBook.getOutputFile()));
		} catch (IOException ex) {
			throw new IllegalStateException(
				"Erro ao criar arquivo EPUB: " + eBook.getOutputFile().toAbsolutePath(), ex);
		}
	}

}
