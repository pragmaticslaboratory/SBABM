package inputManager;

import utils.Console;
import utils.Error;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Configuration {
    public final static String DEFAULT_FILE_NAME = "LIGHTINTHEBOX_AMAZON_2";
    public final static int DISABLED = -1;

    private final static int D_PERIODS = 30;
    private final static int D_AGENTS = 10;
    private final static int D_CONTACTS = 17;
    private final static double D_FRIENDS = .7;
    private final static int D_LEVELS = 2; //2 or 3
    private final static int D_REPETITIONS = 0;
    private final static boolean D_GUI = false; //could be removed
    private final static double D_BASE = 1.2;
    private final static int D_MEMORY = -1;    //-1 infinite
    private final static int D_LEARNING_PERIODS = 100;
    private final static boolean D_MARKET_QUOTA = false;
    private final static boolean D_WOM = false;
    private final static int D_SCENARIO = -1;

    private final static boolean D_COMPRESSED_RESULTS = false;
    private final static boolean D_SAVED_ENDORSEMENTS = false;
    private final static boolean D_SAVED_AGENT_DECISIONS = false;
    private final static boolean D_SAVED_DETAILED_AGENT_DECISIONS = false;
    private final static boolean D_SAVED_SALES_PER_MARKET = false;

    public static String FILE_NAME;
    public static String OUTPUT_DIRECTORY;

    public static int MARKETS;
    public static int ATTRIBUTES_M;
    public static int ATTRIBUTES_B;

    public static int PERIODS = D_PERIODS;
    public static int AGENTS = D_AGENTS;
    public static int CONTACTS = D_CONTACTS;
    public static double FRIENDS = D_FRIENDS;
    public static int LEVELS = D_LEVELS; //2 or 3
    public static int REPETITIONS = D_REPETITIONS;
    public static boolean GUI = D_GUI;
    public static double BASE = D_BASE;
    public static int MEMORY = D_MEMORY;
    public static boolean MARKET_QUOTA = D_MARKET_QUOTA;
    public static boolean WOM = D_WOM;
    public static int SCENARIO = D_SCENARIO;
    public static int LEARNING_PERIODS = D_LEARNING_PERIODS;

    //debug to save information
    public static boolean COMPRESSED_RESULTS = D_COMPRESSED_RESULTS;
    public static boolean SAVED_SALES_PER_MARKET = D_SAVED_SALES_PER_MARKET;
    public static boolean SAVED_DETAILED_AGENT_DECISIONS = D_SAVED_DETAILED_AGENT_DECISIONS;
    public static boolean SAVED_AGENT_DECISIONS = D_SAVED_AGENT_DECISIONS;
    public static boolean SAVED_ENDORSEMENTS = D_SAVED_ENDORSEMENTS;

    public static void set(HashMap<String, Double> conf) {
        checkConfigurationInput(conf);

        PERIODS = conf.get("PERIODS") != null ? conf.get("PERIODS").intValue() : D_PERIODS;
        AGENTS = conf.get("AGENTS") != null ? conf.get("AGENTS").intValue() : D_AGENTS;
        CONTACTS = conf.get("CONTACTS") != null ? conf.get("CONTACTS").intValue() : D_CONTACTS;
        FRIENDS = conf.get("FRIENDS") != null ? conf.get("FRIENDS") : D_FRIENDS;
        LEVELS = conf.get("LEVELS") != null ? conf.get("LEVELS").intValue() : D_LEVELS;
        REPETITIONS = conf.get("REPETITIONS") != null ? conf.get("REPETITIONS").intValue() : D_REPETITIONS;
        GUI = conf.get("GUI") != null ? conf.get("GUI") == 1 : D_GUI;
        BASE = conf.get("BASE") != null ? conf.get("BASE") : D_BASE;
        MEMORY = conf.get("MEMORY") != null ? conf.get("MEMORY").intValue() : D_MEMORY;
        MARKET_QUOTA = conf.get("MARKET_QUOTA") != null ? conf.get("MARKET_QUOTA") == 1 : D_MARKET_QUOTA;
        WOM = conf.get("WOM") != null ? conf.get("WOM") == 1 : D_WOM;
        SCENARIO = conf.get("SCENARIO") != null ? conf.get("SCENARIO").intValue() : D_SCENARIO;
        LEARNING_PERIODS = conf.get("LEARNING_PERIODS") != null ? conf.get("LEARNING_PERIODS").intValue() : D_LEARNING_PERIODS;

        COMPRESSED_RESULTS = conf.get("COMPRESSED_RESULTS") != null ? conf.get("COMPRESSED_RESULTS") == 1 : D_COMPRESSED_RESULTS;
        SAVED_ENDORSEMENTS = conf.get("SAVED_ENDORSEMENTS") != null ? conf.get("SAVED_ENDORSEMENTS") == 1 : D_SAVED_ENDORSEMENTS;
        SAVED_AGENT_DECISIONS = conf.get("SAVED_AGENT_DECISIONS") != null ? conf.get("SAVED_AGENT_DECISIONS") == 1 : D_SAVED_AGENT_DECISIONS;
        SAVED_DETAILED_AGENT_DECISIONS = conf.get("SAVED_DETAILED_AGENT_DECISIONS") != null ? conf.get("SAVED_DETAILED_AGENT_DECISIONS") == 1 : D_SAVED_DETAILED_AGENT_DECISIONS;
        SAVED_SALES_PER_MARKET = conf.get("SAVED_SALES_PER_MARKET") != null ? conf.get("SAVED_SALES_PER_MARKET") == 1 : D_SAVED_SALES_PER_MARKET;
    }

    private static void creatingOutputFolder(String output) {
        try {
            if (new File(output).mkdir()) {
                Console.info("Directory was created: " + output);
            } else {
                Console.error("Directory was NOT create: " + output);
            }
        } catch (SecurityException se) {
            Error.trigger("Directory cannot be created: " + output + "\n ERROR: " + se, se);
        }
    }

    public static void setPath(String fileName) {
        FILE_NAME = fileName;
        DateFormat df = new SimpleDateFormat("dd-MM-yy(HH-mm-ss)");
        OUTPUT_DIRECTORY = "output/" + fileName + "_" + df.format(new Date());

        //checking and creating the output folder
        if (Files.notExists(Paths.get("output"))) {
            creatingOutputFolder("output");
        }

        //making the simulation directory
        try {
            if (new File(OUTPUT_DIRECTORY).mkdir()) {
                Console.info("Configuration.setPath: Directory was created: " + OUTPUT_DIRECTORY);
            } else {
                Console.error("Configuration.setPath: Directory was NOT create: " + OUTPUT_DIRECTORY);
            }
        } catch (SecurityException se) {
            Error.trigger("Configuration.setPath: Directory cannot be created: " + OUTPUT_DIRECTORY +
                    "Configuration.setPath: ERROR: " + se, se);
        }
    }

    public static void setAttributes(int markets, int buyers) {
        set("ATTRIBUTES_M", markets);
        set("ATTRIBUTES_B", buyers);
    }

    public static void setMarkets(int markets) {
        set("MARKETS", markets);
    }

    private static void set(String name, double value) {
        switch (name.toUpperCase()) {
            case "PERIODS":
                PERIODS = (int) value;
                break;
            case "AGENTS":
                AGENTS = (int) value;
                break;
            case "CONTACTS":
                CONTACTS = (int) value;
                break;
            case "FRIENDS":
                FRIENDS = value;
                break;
            case "ATTRIBUTES_M":
                ATTRIBUTES_M = (int) value;
                break;
            case "ATTRIBUTES_B":
                ATTRIBUTES_B = (int) value;
                break;
            case "MARKETS":
                MARKETS = (int) value;
                break;
            case "REPETITIONS":
                REPETITIONS = (int) value;
                break;
            case "LEVELS":
                LEVELS = (int) value;
                break;
            case "GUI":
                GUI = value == 1;
                break;
            case "BASE":
                BASE = value;
                break;
            case "MEMORY":
                MEMORY = (int) value;
                break;
            case "MARKET_QUOTA":
                MARKET_QUOTA = value == 1;
                break;
            case "WOM":
                WOM = value == 1;
                break;
            case "SCENARIO":
                SCENARIO = (int) value;
                break;
            case "LEARNING_PERIODS":
                LEARNING_PERIODS = (int) value;
                break;
            case "COMPRESSED_RESULTS":
                COMPRESSED_RESULTS = value == 1;
                break;
            case "SAVED_ENDORSEMENT":
                SAVED_ENDORSEMENTS = value == 1;
                break;
            case "SAVED_DETAILED_AGENT_DECISIONS":
                SAVED_DETAILED_AGENT_DECISIONS = value == 1;
                break;
            case "SAVED_AGENT_DECISIONS":
                SAVED_AGENT_DECISIONS = value == 1;
                break;
            case "SAVED_SALES_PER_MARKET":
                SAVED_SALES_PER_MARKET = value == 1;
                break;
            default:
                Console.error("CONFIGURATOR.SET: Wrong Parameter: " + name.toUpperCase());
        }
    }

    private static void checkConfigurationInput(HashMap<String, Double> conf) {
        //find a way to test parameters without using a string

        String[] parameters = new String[]{"PERIODS", "AGENTS", "CONTACTS", "FRIENDS", "LEVELS", "REPETITIONS", "GUI",
                "BASE", "MEMORY", "MARKET_QUOTA", "WOM", "SCENARIO", "LEARNING_PERIODS",
                "SAVED_ENDORSEMENTS", "SAVED_SALES_PER_MARKET", "SAVED_DETAILED_AGENT_DECISIONS",
                "SAVED_AGENT_DECISIONS", "COMPRESSED_RESULTS"};

        for (String param : parameters) {
            if (conf.get(param) == null) {
                Console.warn(param + " is missing.");
            }
        }
    }

    public static Map<String, Double> toMap() {
        Map<String, Double> conf = new HashMap<>();
        conf.put("PERIODS", (double) PERIODS);
        conf.put("AGENTS", (double) AGENTS);
        conf.put("CONTACTS", (double) CONTACTS);
        conf.put("FRIENDS", FRIENDS);
        conf.put("LEVELS", (double) LEVELS);
        conf.put("REPETITIONS", (double) REPETITIONS);
        conf.put("GUI", GUI ? 1.0 : 0.0);
        conf.put("BASE", BASE);
        conf.put("MEMORY", (double) MEMORY);
        conf.put("MARKET_QUOTA", MARKET_QUOTA ? 1.0 : 0.0);
        conf.put("WOM", WOM ? 1.0 : 0.0);
        conf.put("SCENARIO", (double) SCENARIO);
        conf.put("LEARNING_PERIODS", (double) LEARNING_PERIODS);

        conf.put("COMPRESSED_RESULTS", COMPRESSED_RESULTS ? 1.0 : 0.0);
        conf.put("SAVED_ENDORSEMENTS", SAVED_ENDORSEMENTS ? 1.0 : 0.0);
        conf.put("SAVED_DETAILED_AGENT_DECISIONS", SAVED_DETAILED_AGENT_DECISIONS ? 1.0 : 0.0);
        conf.put("SAVED_AGENT_DECISIONS", SAVED_AGENT_DECISIONS ? 1.0 : 0.0);
        conf.put("SAVED_SALES_PER_MARKET", SAVED_SALES_PER_MARKET ? 1.0 : 0.0);

        return conf;
    }

    public static String toStringConfiguration() {
        return Configuration.toMap().toString();
    }
}
