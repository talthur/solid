package cotuba.application;

import cotuba.model.Chapter;
import cotuba.model.EBook;
import java.nio.file.Path;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Cotuba {

	private final MdToHtmlRenderer mdToHtmlRenderer;
	private final PDFGenerator pdfGenerator;
	private final EPUBGenerator epubGenerator;

	@Autowired
	public Cotuba(MdToHtmlRenderer mdToHtmlRenderer, PDFGenerator pdfGenerator, EPUBGenerator epubGenerator) {
		this.mdToHtmlRenderer = mdToHtmlRenderer;
		this.pdfGenerator = pdfGenerator;
		this.epubGenerator = epubGenerator;
	}

	public void execute(CotubaParameters cotubaParameters) {

		Path mdDirectory = cotubaParameters.getMdDirectory();
		String format = cotubaParameters.getFormat();
		Path outputFile = cotubaParameters.getOutputFile();
		List<Chapter> render = mdToHtmlRenderer.render(mdDirectory);
		EBook eBook = EBook.of(format, outputFile, render);
		if ("pdf".equals(format)) {
			pdfGenerator.generate(eBook);

		} else if ("epub".equals(format)) {
			epubGenerator.generate(eBook);
		} else {
			throw new IllegalArgumentException("Formato do ebook inválido: " + format);
		}

		System.out.println("Arquivo gerado com sucesso: " + outputFile);
	}

}
