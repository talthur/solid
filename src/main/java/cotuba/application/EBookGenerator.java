package cotuba.application;

import cotuba.model.EBook;
import cotuba.model.EbookFormat;

public interface EBookGenerator {

	void generate(EBook eBook);

	static EBookGenerator create(EbookFormat format) {
		return format.geteBookGenerator();
	}

}
