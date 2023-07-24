package cotuba;

import cotuba.model.Chapter;
import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.Heading;
import org.commonmark.node.Text;

class CotubaVisitor extends AbstractVisitor {

	private final Chapter chapter;

	public CotubaVisitor(Chapter chapter) {
		this.chapter = chapter;
	}

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

}
