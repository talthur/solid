package cotuba.application;

import cotuba.model.EBook;
import cotuba.model.EbookFormat;
import java.util.HashMap;
import java.util.Map;

public interface EBookGenerator {

	Map<String, EBookGenerator> GENERATORS = new HashMap<>();

	void generate(EBook eBook);

	static EBookGenerator create(EbookFormat format) {
		return format.geteBookGenerator();
	}

}
