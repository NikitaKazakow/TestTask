package model.service;

import db.Record;
import javafx.concurrent.Task;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class GetStatisticService extends BaseService<List<Record>> {

    private final String regex = "[ ,.!?\";:\\[\\]()\n\r\t\\-'/]";

    @Override
    protected Task<List<Record>> createTask() {
        return new Task<List<Record>>() {
            @Override
            protected List<Record> call() {
                Map<String, Integer> statisticMap = new HashMap<>();
                try {
                    Document document = Jsoup.connect(url).get();
                    String htmlText = document.body().text();
                    String[] words = htmlText.split(regex);
                    for (String word : words) {
                        if (statisticMap.containsKey(word)) {
                            int count = statisticMap.get(word);
                            statisticMap.replace(word, count, count + 1);
                        } else {
                            statisticMap.put(word, 1);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return statisticMap.entrySet().stream().map(stringIntegerEntry ->
                        new Record(stringIntegerEntry.getKey(),
                                stringIntegerEntry.getValue())).collect(Collectors.toList());
            }
        };
    }
}
