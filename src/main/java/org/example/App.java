package org.example;

import java.io.IOException;
import java.sql.SQLOutput;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class App {

    public static void main(String[] args) {

        try {
            int page = 1;
            String urlFormat = "https://lionfitness.pl/przepisy/page/%d";
            while(page < 169) {
                String url = String.format(urlFormat, page);
                Document doc = Jsoup.connect(url).get();
                System.out.printf("\nWebsite Title: %s\n\n", doc.title());

                // Pobiera wszystkie pola gdzie jest link
                Elements repositories = doc.getElementsByClass("card-content");

                //szuka linkow w tych polach
                for (Element repository : repositories) {
                    Elements repositortemp = repository.getElementsByClass("waves-effect waves-light btn cyan");
                    String link = repositortemp.attr("href");
                    Document linkDoc = Jsoup.connect("https://lionfitness.pl/" + link).get();

                    //wchodzi w link
                    Elements repositories2 = linkDoc.getElementsByClass("card-image waves-effect waves-block waves-light");
                    for (Element repository2 : repositories2) {
                        String title = repository2.getElementsByClass("card-title").text();
                        System.out.println(title);
                    }
                    Elements table = linkDoc.getElementsByClass("card-content");
                    Elements rows = table.select("tr");
                    for (Element row : rows) {
                        Elements columns = row.select("td");
                        for (Element column : columns) {
                            // Odczytaj wartość kolumny
                            String value = column.text();

                            // Przetwórz dane według potrzeb
                            System.out.println(value + " ");
                        }
                    }
                    Elements tags = linkDoc.getElementsByClass("tags-box");
                    for (Element tag : tags) {
                        Elements tags2 = tag.select("a");
                        System.out.println(tags2.text());
                    }
                    System.out.println("--------------");
                }
                page++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}