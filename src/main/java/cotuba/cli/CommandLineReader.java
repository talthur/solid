package cotuba.cli;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

class CommandLineReader {

	private Path mdDirectory;
	private String format;
	private Path outputFile;
	private boolean verboseMethod = false;

	public CommandLineReader(String[] args) {
		try {
			var options = createOptions();
			CommandLine cmd = parseCommandLine(args, options);
			treatMDDirectory(cmd);
			treatFormat(cmd);
			treatOutputFile(cmd);
			treatVerboseMethod(cmd);

		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	private void treatVerboseMethod(CommandLine cmd) {
		verboseMethod = cmd.hasOption("verbose");
	}

	private void treatOutputFile(CommandLine cmd) throws IOException {
		String nomeDoArquivoDeSaidaDoEbook = cmd.getOptionValue("output");
		outputFile = Paths.get(
			Objects.requireNonNullElseGet(nomeDoArquivoDeSaidaDoEbook, () -> "book." + format.toLowerCase()));

		if (Files.isDirectory(outputFile)) {
			// deleta arquivos do diretório recursivamente
			try (Stream<Path> walk = Files.walk(outputFile);) {
				walk.sorted(Comparator.reverseOrder())
					.map(Path::toFile).forEach(File::delete);
			}

		} else {
			Files.deleteIfExists(outputFile);
		}
	}

	private void treatFormat(CommandLine cmd) {
		String nomeDoFormatoDoEbook = cmd.getOptionValue("format");

		if (nomeDoFormatoDoEbook != null) {
			format = nomeDoFormatoDoEbook.toLowerCase();
		} else {
			format = "pdf";
		}
	}

	private void treatMDDirectory(CommandLine cmd) {
		Optional<String> optionalMdDirectory = Optional.ofNullable(cmd.getOptionValue("dir"));

		if (optionalMdDirectory.isPresent()) {
			this.mdDirectory = Paths.get(optionalMdDirectory.get());
			if (!Files.isDirectory(this.mdDirectory)) {
				throw new IllegalArgumentException(optionalMdDirectory + " não é um diretório.");
			}
		} else {
			Path diretorioAtual = Paths.get("");
			this.mdDirectory = diretorioAtual;
		}
	}

	private CommandLine parseCommandLine(String[] args, Options options) {
		CommandLineParser cmdParser = new DefaultParser();
		var helpFormatter = new HelpFormatter();
		CommandLine cmd;

		try {
			cmd = cmdParser.parse(options, args);
		} catch (ParseException e) {
			helpFormatter.printHelp("cotuba", options);
			throw new IllegalArgumentException("Opção inválida");
		}
		return cmd;
	}

	private Options createOptions() {
		var options = new Options();

		var mdDirectoryOption = new Option("d", "dir", true,
			"Diretório que contém os arquivos md. Default: diretório atual.");
		options.addOption(mdDirectoryOption);

		var opcaoDeFormatoDoEbook = new Option("f", "format", true,
			"Formato de saída do ebook. Pode ser: pdf ou epub. Default: pdf");
		options.addOption(opcaoDeFormatoDoEbook);

		var opcaoDeArquivoDeSaida = new Option("o", "output", true,
			"Arquivo de saída do ebook. Default: book.{formato}.");
		options.addOption(opcaoDeArquivoDeSaida);

		var opcaoModoVerboso = new Option("v", "verbose", false,
			"Habilita modo verboso.");
		options.addOption(opcaoModoVerboso);
		return options;
	}

	public Path getMdDirectory() {
		return mdDirectory;
	}

	public String getFormat() {
		return format;
	}

	public Path getOutputFile() {
		return outputFile;
	}

	public boolean isVerboseMethod() {
		return verboseMethod;
	}
}
