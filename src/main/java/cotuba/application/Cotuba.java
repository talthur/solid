package cotuba.application;

import cotuba.md.MdToHtmlRenderer;
import cotuba.model.Chapter;
import cotuba.model.EBook;
import cotuba.model.EbookFormat;
import java.nio.file.Path;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Cotuba {

	private final MdToHtmlRenderer mdToHtmlRenderer;
	private final List<EBookGenerator> eBookGenerators;

	@Autowired
	public Cotuba(MdToHtmlRenderer mdToHtmlRenderer, List<EBookGenerator> epubGenerator) {
		this.mdToHtmlRenderer = mdToHtmlRenderer;
		this.eBookGenerators = epubGenerator;
	}

	public void execute(CotubaParameters cotubaParameters) {

		Path mdDirectory = cotubaParameters.getMdDirectory();
		EbookFormat format = cotubaParameters.getFormat();
		Path outputFile = cotubaParameters.getOutputFile();
		List<Chapter> render = mdToHtmlRenderer.render(mdDirectory);
		EBook eBook = EBook.of(format, outputFile, render);

		EBookGenerator eBookGenerator = eBookGenerators.stream()
			.filter(generator -> generator.accept(format))
			.findAny().orElseThrow(() -> new IllegalArgumentException("Invalid ebook format! " + format));

		eBookGenerator.generate(eBook);
		System.out.println("Arquivo gerado com sucesso: " + outputFile);
	}

}
