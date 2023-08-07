package cotuba.cli;

import cotuba.application.Cotuba;
import java.nio.file.Path;

public class Main {

	public static void main(String[] args) {

		var commandLineReader = new CommandLineReader(args);

		Path diretorioDosMD;
		String formato;
		Path arquivoDeSaida;
		boolean modoVerboso = false;

		try {
			diretorioDosMD = commandLineReader.getMdDirectory();
			formato = commandLineReader.getFormat();
			arquivoDeSaida = commandLineReader.getOutputFile();
			modoVerboso = commandLineReader.isVerboseMethod();
			var cotuba = new Cotuba();
			cotuba.execute(diretorioDosMD, formato, arquivoDeSaida);

		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			if (modoVerboso) {
				ex.printStackTrace();
			}
			System.exit(1);
		}
	}
}
