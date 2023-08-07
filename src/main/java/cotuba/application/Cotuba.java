package cotuba.application;

import cotuba.epub.EPUBGenerator;
import cotuba.md.MdToHtmlRenderer;
import cotuba.pdf.PDFGenerator;
import cotuba.model.Chapter;
import cotuba.model.EBook;
import java.nio.file.Path;
import java.util.List;

public class Cotuba {

	public void execute(Path mdDirectory, String format, Path outputFile) {
		MdToHtmlRenderer mdToHtmlRenderer = new MdToHtmlRenderer();
		List<Chapter> render = mdToHtmlRenderer.render(mdDirectory);
		EBook eBook = EBook.of(format, outputFile, render);
		if ("pdf".equals(format)) {
			var pdfGenerator = new PDFGenerator();
			pdfGenerator.generate(eBook);

		} else if ("epub".equals(format)) {
			var epubGenerator = new EPUBGenerator();
			epubGenerator.generate(eBook);
		} else {
			throw new IllegalArgumentException("Formato do ebook inválido: " + format);
		}

		System.out.println("Arquivo gerado com sucesso: " + outputFile);
	}

}
