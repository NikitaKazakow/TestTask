package db.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@RequiredArgsConstructor
@DatabaseTable(tableName = "record")
public class RecordEntity {

    @DatabaseField(generatedId = true)
    private long id;

    @Getter
    @NonNull
    @DatabaseField
    private String word;

    @Getter
    @NonNull
    @DatabaseField
    private long count;

    @DatabaseField(
            foreign = true,
            foreignAutoRefresh = true,
            foreignAutoCreate = true,
            columnDefinition = "INTEGER CONSTRAINT STATISTIC_FK REFERENCES statistic(id) ON DELETE CASCADE")
    private StatisticEntity statistic;
}
