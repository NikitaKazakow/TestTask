import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.Data;
import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class Model {

    private final String regex = "[ ,.!?\";:\\[\\]()\n\r\t\\-'/]";

    private final ObservableList<StatisticModel> statistic;

    public Model() {
        statistic = FXCollections.observableArrayList();
    }

    public synchronized void getHtmlStatistic(String url) {
        statistic.clear();
        Map<String, Integer> statisticMap = new HashMap<>();
        try {
            Document document = Jsoup.connect(url).get();
            String htmlText = document.body().text();
            String[] words = htmlText.split(regex);
            for (String word : words) {
                if (statisticMap.containsKey(word)) {
                    int count = statisticMap.get(word);
                    statisticMap.replace(word, count, count + 1);
                }
                else {
                    statisticMap.put(word, 1);
                }
            }
            statistic.addAll(statisticMap.entrySet().stream().map(stringIntegerEntry -> new StatisticModel(stringIntegerEntry.getKey(), stringIntegerEntry.getValue())).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Data
    public static class StatisticModel {
        private final String word;
        private final Integer count;
    }
}
