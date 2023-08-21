package cotuba.md;

import cotuba.application.MdToHtmlRenderer;
import cotuba.model.Chapter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.List;
import java.util.stream.Stream;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;

@Component
public class MdToHtmlRendererCommonMark implements MdToHtmlRenderer {

	@Override
	public List<Chapter> render(Path mdDirectory) {
		List<Path> mdFiles = getMDFiles(mdDirectory);

		return mdFiles.stream().map(mdFile -> {
			Chapter chapter = new Chapter();
			Node document = parseDocument(mdFile, chapter);
			renderHTML(mdFile, chapter, document);
			return chapter;
		}).toList();
	}

	private List<Path> getMDFiles(Path mdDirectory) {
		PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**/*.md");
		try (Stream<Path> arquivosMD = Files.list(mdDirectory)) {
			return arquivosMD
				.filter(matcher::matches)
				.sorted().toList();

		} catch (IOException ex) {
			throw new IllegalStateException(
				"Erro tentando encontrar arquivos .md em " + mdDirectory.toAbsolutePath(), ex);
		}
	}

	private Node parseDocument(Path mdFile, Chapter chapter) {
		Parser parser = Parser.builder().build();
		Node document = null;
		try {
			document = parser.parseReader(Files.newBufferedReader(mdFile));
			document.accept(new CotubaVisitor(chapter));
			return document;
		} catch (Exception ex) {
			throw new IllegalStateException(
				"Erro ao fazer parse do arquivo " + mdFile, ex);
		}
	}

	private void renderHTML(Path mdFile, Chapter chapter, Node document) {
		try {
			HtmlRenderer renderer = HtmlRenderer.builder().build();
			String html = renderer.render(document);
			chapter.setHTMLContent(html);
		} catch (Exception ex) {
			throw new IllegalStateException(
				"Erro ao renderizar para HTML o arquivo " + mdFile, ex);
		}
	}

}
