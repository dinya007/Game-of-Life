package ru.tisov.denis.life.actor;

import akka.actor.AbstractActor;
import akka.actor.Status;
import akka.japi.pf.ReceiveBuilder;
import ru.tisov.denis.life.Life;
import ru.tisov.denis.life.message.NewLifeRq;
import ru.tisov.denis.life.message.NewLifeRs;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

public class NewGenerationActor extends AbstractActor {

    @Override
    public PartialFunction<Object, BoxedUnit> receive() {
        return ReceiveBuilder.match(NewLifeRq.class, rq -> {
            boolean[][] result = newGenerationForRows(rq.currentLife, rq.startRow, rq.finishRow);
            sender().tell(new NewLifeRs(rq.startRow, rq.finishRow, result), self());
        })
                .matchAny(x -> sender().tell(new Status.Failure(new RuntimeException("Unknown message")), self()))
                .build();
    }

    public boolean[][] newGenerationForRows(Life life, int startRow, int finishRow) {
        boolean[][] result = new boolean[finishRow - startRow][life.columnNum()];
        ++startRow;
        ++finishRow;

        boolean[][] lifeArrayWithEdges = life.getLifeArrayWithEdges();
        int columnNum = lifeArrayWithEdges[0].length;
        for (int i = startRow; i < finishRow; i++) {
            for (int j = 1; j < columnNum - 1; j++) {
                int count = count(life, i, j);
                boolean element = lifeArrayWithEdges[i][j];
                if (element && (count == 2 || count == 3)) {
                    result[i - startRow][j - 1] = true;
                } else if (!element && (count == 3)) {
                    result[i - startRow][j - 1] = true;
                } else {
                    result[i - startRow][j - 1] = false;
                }
            }
        }
        return result;
    }

    int count(Life life, int rowIndex, int columnIndex) {
        int count = 0;
        boolean[][] lifeArrayWithEdges = life.getLifeArrayWithEdges();
        for (int i = rowIndex - 1; i <= rowIndex + 1; i++) {
            for (int j = columnIndex - 1; j <= columnIndex + 1; j++) {
                if (i == rowIndex && j == columnIndex) continue;
                if (lifeArrayWithEdges[i][j]) ++count;
            }
        }
        return count;
    }

}
