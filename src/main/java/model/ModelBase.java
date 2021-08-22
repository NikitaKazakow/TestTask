package model;

import org.modelmapper.ModelMapper;

public abstract class ModelBase {

    protected ModelMapper modelMapper;

    public ModelBase() {
        modelMapper = new ModelMapper();
    }

    public abstract void clear();
}
