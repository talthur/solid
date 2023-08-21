package cotuba.cli;

import cotuba.CotubaConfig;
import cotuba.application.Cotuba;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {

	public static void main(String[] args) {

		var commandLineReader = new CommandLineReader(args);

		boolean modoVerboso = false;

		modoVerboso = commandLineReader.isVerboseMethod();

		try {
			ApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				CotubaConfig.class);
			Cotuba cotuba = applicationContext.getBean(Cotuba.class);
			cotuba.execute(commandLineReader);

		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			if (modoVerboso) {
				ex.printStackTrace();
			}
			System.exit(1);
		}
	}
}
