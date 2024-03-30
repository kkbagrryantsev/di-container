package ru.nsu.di_container.snakes;

public class ReptileTerrarium implements Terrarium {
    public Integer glassThickness;

    @Override
    public Integer getGlassThickness() {
        return glassThickness;
    }
}
