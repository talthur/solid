package cotuba;

import cotuba.model.Cotuba;
import java.nio.file.Path;

public class Main {

	public static void main(String[] args) {

		var commandLineReader = new CommandLineReader(args);

		Path diretorioDosMD;
		String formato;
		Path arquivoDeSaida;
		boolean modoVerboso = false;

		try {
			diretorioDosMD = commandLineReader.getDiretorioDosMD();
			formato = commandLineReader.getFormato();
			arquivoDeSaida = commandLineReader.getArquivoDeSaida();
			modoVerboso = commandLineReader.isModoVerboso();
			var cotuba = new Cotuba();
			cotuba.execute(diretorioDosMD, formato, arquivoDeSaida, modoVerboso);

		} catch (Exception ex) {
			System.err.println(ex.getMessage());
			if (modoVerboso) {
				ex.printStackTrace();
			}
			System.exit(1);
		}
	}
}
