package db.entity;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@DatabaseTable(tableName = "statistic")
public class StatisticEntity {
    @DatabaseField(generatedId = true)
    private long id;

    @Getter
    @DatabaseField
    private final Date date = new Date();

    @Getter
    @Setter
    @DatabaseField
    private String url;

    @Getter
    @Setter
    @ForeignCollectionField(eager = true)
    private ForeignCollection<RecordEntity> records;
}
