package ru.tisov.denis.life.message;

import ru.tisov.denis.life.domain.Life;

import java.io.Serializable;

public class NewLifeRq implements Serializable {

    public final Life currentLife;
    public final int startRow;
    public final int finishRow;

    public NewLifeRq(Life currentLife, int startRow, int finishRow) {
        this.currentLife = currentLife;
        this.startRow = startRow;
        this.finishRow = finishRow;
    }
}
