package cotuba.application;

import cotuba.model.Chapter;
import java.nio.file.Path;
import java.util.List;

public interface MdToHtmlRenderer {

	List<Chapter> render(Path mdDirectory);

}
