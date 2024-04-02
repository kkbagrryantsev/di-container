package ru.nsu.di_container.classes;

import ru.nsu.di_container.annotations.Lookup;

public interface PrototypeInterface {
    @Lookup
    void doAction();
}
