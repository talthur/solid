package cotuba.model;

import cotuba.application.EBookGenerator;
import cotuba.epub.EPUBGenerator;
import cotuba.html.HTMLGenerator;
import cotuba.pdf.PDFGenerator;

public enum EbookFormat {
	PDF(new PDFGenerator()),
	EPUB(new EPUBGenerator()),
	HTML(new HTMLGenerator());

	private EBookGenerator eBookGenerator;

	EbookFormat(EBookGenerator eBookGenerator) {
		this.eBookGenerator = eBookGenerator;
	}

	public EBookGenerator geteBookGenerator() {
		return eBookGenerator;
	}
}
