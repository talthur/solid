package cotuba.application;

import java.nio.file.Path;

public interface CotubaParameters {

	Path getMdDirectory();

	String getFormat();

	Path getOutputFile();
}
