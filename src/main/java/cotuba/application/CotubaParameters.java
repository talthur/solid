package cotuba.application;

import cotuba.model.EbookFormat;
import java.nio.file.Path;

public interface CotubaParameters {

	Path getMdDirectory();

	EbookFormat getFormat();

	Path getOutputFile();
}
