package scraper;

import lib.WebDriverManager;
import pages.DrakecircusShopsPge;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ScraperRetailInformation {
    private static String CSV_FILE_PATH = new File("").getAbsolutePath() + File.separator +
            "files" + File.separator + "csv-results";


    public static void main(String[] args) throws IOException {
        String link = "https://www.drakecircus.com/eatdrinkshop";
        WebDriverManager.getWebDriver().get(link);
        DrakecircusShopsPge drakecircusShopsPge = new DrakecircusShopsPge(WebDriverManager.getWebDriver());
        drakecircusShopsPge.acceptAllCookies();
        wrightToCsv(drakecircusShopsPge.getShopsInfo(), "shops.csv");
        WebDriverManager.driverQuite();
    }


    public static void wrightToCsv(List<List<String>> dataLines, String fileName) throws IOException {
        File csvOutputFile = new File(CSV_FILE_PATH + File.separator + fileName);
        try (PrintWriter pw = new PrintWriter(csvOutputFile)) {
            pw.println("Name,Link");
            dataLines.stream()
                    .map(e -> String.join(",", e))
                    .forEach(pw::println);
        }
    }
}
