package cotuba;

import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.property.AreaBreakType;
import cotuba.model.Chapter;
import cotuba.model.EBook;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class PDFGenerator {

	public void generate(EBook eBook) {
		try (var writer = new PdfWriter(Files.newOutputStream(eBook.getOutputFile()));
			var pdf = new PdfDocument(writer);
			var pdfDocument = new Document(pdf)) {
			for (Chapter chapter : eBook.getChapters()) {
				String html = chapter.getHTMLContent();
				List<IElement> convertToElements = HtmlConverter.convertToElements(html);
				for (IElement element : convertToElements) {
					pdfDocument.add((IBlockElement) element);
				}
				// TODO: não adicionar página depois do último capítulo
				pdfDocument.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
			}

		} catch (Exception ex) {
			throw new IllegalStateException(
				"Erro ao criar arquivo PDF: " + eBook.getOutputFile().toAbsolutePath(), ex);
		}
	}

}
