package cotuba;

import cotuba.model.Chapter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Heading;
import org.commonmark.node.Node;
import org.commonmark.node.Text;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

public class MdToHtmlRenderer {

	public List<Chapter> render(Path mdDirectory) {
		List<Chapter> chapters = new ArrayList<>();
		PathMatcher matcher = FileSystems.getDefault().getPathMatcher("glob:**/*.md");
		try (Stream<Path> arquivosMD = Files.list(mdDirectory)) {
			arquivosMD
				.filter(matcher::matches)
				.sorted()
				.forEach(arquivoMD -> {
					Chapter chapter = new Chapter();
					Parser parser = Parser.builder().build();
					Node document = null;
					try {
						document = parser.parseReader(Files.newBufferedReader(arquivoMD));
						document.accept(new AbstractVisitor() {
							@Override
							public void visit(Heading heading) {
								if (heading.getLevel() == 1) {
									String tituloDoCapitulo = ((Text) heading.getFirstChild()).getLiteral();
									chapter.setTitle(tituloDoCapitulo);
								} else if (heading.getLevel() == 2) {
									// seção
								} else if (heading.getLevel() == 3) {
									// título
								}
							}

						});
					} catch (Exception ex) {
						throw new IllegalStateException(
							"Erro ao fazer parse do arquivo " + arquivoMD, ex);
					}

					try {
						HtmlRenderer renderer = HtmlRenderer.builder().build();
						String html = renderer.render(document);
						chapter.setHTMLContent(html);
						chapters.add(chapter);

					} catch (Exception ex) {
						throw new IllegalStateException(
							"Erro ao renderizar para HTML o arquivo " + arquivoMD, ex);
					}

				});
		} catch (IOException ex) {
			throw new IllegalStateException(
				"Erro tentando encontrar arquivos .md em " + mdDirectory.toAbsolutePath(), ex);
		}
		return chapters;
	}

}
