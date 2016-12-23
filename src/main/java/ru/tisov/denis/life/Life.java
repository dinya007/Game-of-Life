package ru.tisov.denis.life;

import java.util.Arrays;

public class Life {

    private final boolean[][] lifeArrayWithEdges;

    public Life(boolean[][] lifeArray) {
        this.lifeArrayWithEdges = createLifeWithEdges(lifeArray);
    }

    public int rowNum() {
        return lifeArrayWithEdges.length - 2;
    }

    public int columnNum() {
        return lifeArrayWithEdges[0].length - 2;
    }

    public boolean[][] newGenerationForRows(int startRow, int finishRow) {
        boolean[][] result = new boolean[finishRow - startRow][lifeArrayWithEdges[0].length - 2];
        ++startRow;
        ++finishRow;

        int columnNum = lifeArrayWithEdges[0].length;
        for (int i = startRow; i < finishRow; i++) {
            for (int j = 1; j < columnNum - 1; j++) {
                int count = count(i, j);
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

    boolean[] addFirstAndLastColumnToRow(boolean[] row) {
        boolean[] result = new boolean[row.length + 2];
        System.arraycopy(row, 0, result, 1, row.length);
        result[0] = row[row.length - 1];
        result[result.length - 1] = row[0];
        return result;
    }

    boolean[][] createLifeWithEdges(boolean[][] lifeArray) {
        boolean[][] lifeWithEdges = new boolean[lifeArray.length + 2][lifeArray.length + 2];
        for (int i = 0; i < lifeArray.length; i++) {
            lifeWithEdges[i + 1] = addFirstAndLastColumnToRow(lifeArray[i]);
            if (i == 0) lifeWithEdges[i] = addFirstAndLastColumnToRow(lifeArray[lifeArray.length - 1]);
            if (i == lifeArray.length - 1) lifeWithEdges[i + 2] = addFirstAndLastColumnToRow(lifeArray[0]);
        }
        return lifeWithEdges;
    }

    int count(int rowIndex, int columnIndex) {
        int count = 0;
        for (int i = rowIndex - 1; i <= rowIndex + 1; i++) {
            for (int j = columnIndex - 1; j <= columnIndex + 1; j++) {
                if (i == rowIndex && j == columnIndex) continue;
                if (lifeArrayWithEdges[i][j]) ++count;
            }
        }
        return count;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (int i = 1; i < lifeArrayWithEdges.length - 1; i++) {
            result.append("[");
            String[] columns = new String[lifeArrayWithEdges[0].length - 2];
            for (int j = 1; j < lifeArrayWithEdges[0].length - 1; j++) {
                if (lifeArrayWithEdges[i][j]) {
                    columns[j - 1] = "*";
                } else {
                    columns[j - 1] = "-";
                }
            }
            result.append(String.join(" ", columns));
            result.append("]\n");
        }

        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Life life = (Life) o;

        return Arrays.deepEquals(lifeArrayWithEdges, life.lifeArrayWithEdges);

    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(lifeArrayWithEdges);
    }
}
