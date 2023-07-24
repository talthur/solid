package cotuba.model;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EBookTest {

	private EBook eBook;
	private Chapter lastChapter;

	@BeforeEach
	void setUp() {
		List<Chapter> chapterList = new ArrayList<>();
		lastChapter = new Chapter();
		chapterList.add(new Chapter());
		eBook = EBook.of("PDF", Paths.get("caminho"), chapterList);
	}

	@Test
	void isTheLastChapter() {
		Assertions.assertTrue(eBook.isTheLastChapter(lastChapter));
	}
}
