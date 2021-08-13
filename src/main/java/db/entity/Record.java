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
public class Record {

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

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private Statistic statistic;
}
