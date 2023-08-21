package cotuba.application;

import cotuba.epub.EPUBGeneratorEpublib;
import cotuba.model.Chapter;
import cotuba.model.EBook;
import cotuba.pdf.PDFGeneratorIText;
import java.nio.file.Path;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Cotuba {

	private final MdToHtmlRenderer mdToHtmlRenderer;

	@Autowired
	public Cotuba(MdToHtmlRenderer mdToHtmlRenderer) {
		this.mdToHtmlRenderer = mdToHtmlRenderer;
	}

	public void execute(CotubaParameters cotubaParameters) {

		Path mdDirectory = cotubaParameters.getMdDirectory();
		String format = cotubaParameters.getFormat();
		Path outputFile = cotubaParameters.getOutputFile();
		List<Chapter> render = mdToHtmlRenderer.render(mdDirectory);
		EBook eBook = EBook.of(format, outputFile, render);
		EBookGenerator eBookGenerator;
		if ("pdf".equals(format)) {
			eBookGenerator = new PDFGeneratorIText();

		} else if ("epub".equals(format)) {
			eBookGenerator = new EPUBGeneratorEpublib();
		} else {
			throw new IllegalArgumentException("Formato do ebook inválido: " + format);
		}

		eBookGenerator.generate(eBook);

		System.out.println("Arquivo gerado com sucesso: " + outputFile);
	}

}
