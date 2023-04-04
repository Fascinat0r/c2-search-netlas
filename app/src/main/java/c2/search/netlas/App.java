package c2.search.netlas;

import c2.search.netlas.cli.CLArgumentsManager;
import c2.search.netlas.cli.Config;
import java.io.PrintStream;
import java.util.List;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
  private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
  private static final String CONFIG_FILENAME = "config.properties";
  private static Config config;
  private static PrintStream out;
  private static C2Detect c2detect;

  static {
    config = new Config(CONFIG_FILENAME);
    out = System.out;
    c2detect = new C2Detect(null, out);
  }

  public static PrintStream getOut() {
    return out;
  }

  public static void setOut(PrintStream out) {
    App.out = out;
  }

  public static String getConfigFilename() {
    return CONFIG_FILENAME;
  }

  public static Config getConfig() {
    return config;
  }

  public static void setConfig(Config config) {
    App.config = config;
  }

  public static CLArgumentsManager getParseCmdArgs(String[] args) {
    CommandLine cmd = null;
    CommandLineParser parser = getDefaultParser();
    try {
      cmd = parser.parse(setupOptions(), args);
    } catch (ParseException e) {
      LOGGER.info("Error parsing command line arguments", e);
    }

    CLArgumentsManager parseCmdArgs = new CLArgumentsManager(cmd, config);
    return parseCmdArgs;
  }

  public static C2Detect getC2detect() {
    return c2detect;
  }

  public static void setC2detect(C2Detect c2Detect) {
    App.c2detect = c2Detect;
  }

  public static void main(String[] args) {
    CLArgumentsManager parseCmdArgs = getParseCmdArgs(args);
    c2detect.setCommandLineArgumentsManager(parseCmdArgs);

    if (parseCmdArgs.isInvalid() || parseCmdArgs.isHelp()) {
      HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp("c2detect", setupOptions());
    } else if (parseCmdArgs.isChangeApiKey()) {
      parseCmdArgs.setApiKey(parseCmdArgs.getApiKey());
    } else {
      startScan(args);
    }
  }

  public static void startScan(String[] args) {
    try {
      c2detect.run(args);
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  protected static CommandLineParser getDefaultParser() {
    return new DefaultParser();
  }

  protected static Options setupOptions() {
    Options options = new Options();
    Option setOption =
        Option.builder("s")
            .longOpt("set")
            .hasArg(true)
            .argName("API_KEY")
            .desc("Set the API key to use for the application")
            .build();
    Option targetOption =
        Option.builder("t")
            .longOpt("target")
            .hasArg(true)
            .argName("TARGET_DOMAIN")
            .desc("Set the target domain for the application")
            .build();
    Option portOption =
        Option.builder("p")
            .longOpt("port")
            .hasArg(true)
            .argName("TARGET_PORT")
            .desc("Set the target port for the application")
            .build();
    Option printVerbosOption =
        Option.builder("v").longOpt("verbose").hasArg(false).desc("Print verbose output").build();
    Option helpOption = Option.builder("h").longOpt("help").desc("Print this help message").build();

    List<Option> list = List.of(setOption, targetOption, portOption, printVerbosOption, helpOption);
    for (Option option : list) {
      options.addOption(option);
    }
    return options;
  }
}
