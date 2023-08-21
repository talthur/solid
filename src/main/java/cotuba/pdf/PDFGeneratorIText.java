package cotuba.pdf;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.property.AreaBreakType;
import cotuba.application.PDFGenerator;
import cotuba.model.Chapter;
import cotuba.model.EBook;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class PDFGeneratorIText implements PDFGenerator {

	@Override
	public void generate(EBook eBook) {
		try (var writer = new PdfWriter(Files.newOutputStream(eBook.getOutputFile()));
			var pdf = new PdfDocument(writer);
			var pdfDocument = new Document(pdf)) {

			for (Chapter chapter : eBook.getChapters()) {
				addChaptersToPDF(eBook, chapter, pdfDocument);
			}

		} catch (Exception ex) {
			throw new IllegalStateException(
				"Erro ao criar arquivo PDF: " + eBook.getOutputFile().toAbsolutePath(), ex);
		}
	}

	private void addChaptersToPDF(EBook eBook, Chapter chapter, Document pdfDocument) throws IOException {
		String html = chapter.getHTMLContent();
		List<IElement> convertToElements = HtmlConverter.convertToElements(html);
		for (IElement element : convertToElements) {
			pdfDocument.add((IBlockElement) element);
		}
		if (!eBook.isTheLastChapter(chapter)) {
			pdfDocument.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
		}
	}

}
