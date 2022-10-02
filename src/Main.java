import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        FileWriter recipesFile = new FileWriter("recipes.csv", true);
        recipesFile.write("url\n");
        Document doc = Jsoup.connect("https://foodnetwork.co.uk/").get();
        System.out.println(doc.title());
        Elements newsHeadlines = doc.select(".card-link");
        for (Element hicham : newsHeadlines) {
            recipesFile.write(hicham.absUrl("href")+"\n");
        }
        recipesFile.close();

    }
}