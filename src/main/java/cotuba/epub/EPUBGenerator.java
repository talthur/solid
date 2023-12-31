package cotuba.epub;

import cotuba.application.EBookGenerator;
import cotuba.model.Chapter;
import cotuba.model.EBook;
import cotuba.model.EbookFormat;
import java.io.IOException;
import java.nio.file.Files;
import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.Resource;
import nl.siegmann.epublib.epub.EpubWriter;
import nl.siegmann.epublib.service.MediatypeService;
import org.springframework.stereotype.Component;

@Component
public class EPUBGenerator implements EBookGenerator {

	@Override
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

	@Override
	public boolean accept(EbookFormat ebookFormat) {
		return EbookFormat.EPUB.equals(ebookFormat);
	}

}
