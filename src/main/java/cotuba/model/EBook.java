package cotuba.model;

import java.nio.file.Path;
import java.util.List;

public class EBook {

	private final String format;
	private final Path outputFile;
	private final List<Chapter> chapters;

	private EBook(String format, Path outputFile, List<Chapter> chapters) {
		this.format = format;
		this.outputFile = outputFile;
		this.chapters = chapters;
	}

	public static EBook of(String format, Path outputFile, List<Chapter> chapters) {
		if (format == null || outputFile == null || chapters == null || chapters.isEmpty()) {
			throw new IllegalArgumentException();
		}
		return new EBook(format, outputFile,
			chapters);
	}

	public String getFormat() {
		return format;
	}

	public Path getOutputFile() {
		return outputFile;
	}

	public List<Chapter> getChapters() {
		return chapters;
	}

	public boolean isTheLastChapter(Chapter chapter) {
		return chapters.indexOf(chapter) == chapters.size() - 1;
	}
}
