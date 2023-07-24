package cotuba.model;

import java.util.Objects;

public class Chapter {

	private String title;
	private String HTMLContent;

	public String getTitle() {
		return title;
	}

	public String getHTMLContent() {
		return HTMLContent;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setHTMLContent(String HTMLContent) {
		this.HTMLContent = HTMLContent;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Chapter chapter = (Chapter) o;
		return Objects.equals(title, chapter.title) && Objects.equals(HTMLContent,
			chapter.HTMLContent);
	}

	@Override
	public int hashCode() {
		return Objects.hash(title, HTMLContent);
	}
}
