package service;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import lombok.Setter;

public abstract class BaseService<T> extends Service<T> {
    @Setter
    protected static String url;

    @Override
    protected abstract Task<T> createTask();
}
