package world;

import enums.Status;

import java.awt.*;
import java.util.Random;

public class World {

    private final Panel world;
    private final int rows;
    private final int columns;
    private final Cell[][] cellMatrix;
    private final int birthNeighboursCount;
    private final int overcrowdingNeighboursCount;
    private final int lonelinessNeighboursCount;

    public World(int rows, int columns, double alivePercentage, int birthNeighboursCount, int overcrowdingNeighboursCount, int lonelinessNeighboursCount) {
        this.rows = rows;
        this.columns = columns;
        world = new Panel(new GridLayout(rows, columns));
        world.setBackground(Color.BLACK);
        cellMatrix = new Cell[rows][columns];
        Random random = new Random();
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                boolean alive = random.nextDouble() <= alivePercentage;
                cellMatrix[row][column] = new Cell(alive);
                world.add(cellMatrix[row][column]);
            }
        }
        this.birthNeighboursCount = birthNeighboursCount;
        this.overcrowdingNeighboursCount = overcrowdingNeighboursCount;
        this.lonelinessNeighboursCount = lonelinessNeighboursCount;
    }

    public Panel getWorld() {
        return world;
    }

    public void updateAsync() {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                updateCellAsync(row, column);
            }
        }
    }

    public void updateCellAsync(int row, int column) {
        int aliveNeighbors = countAliveNeighbors(row, column);

        if (cellMatrix[row][column].isAlive()) {
            // 1. Any live cell with fewer than two live neighbours dies, as if by underpopulation.
            // 3. Any live cell with more than three live neighbours dies, as if by overpopulation.
            if (aliveNeighbors < lonelinessNeighboursCount || aliveNeighbors > overcrowdingNeighboursCount) {
                cellMatrix[row][column].setStatus(Status.DEAD);
                cellMatrix[row][column].repaint();
            }
            // 2. Any live cell with two or three live neighbours lives on to the next generation.
        } else {
            // 4. Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
            if (aliveNeighbors == birthNeighboursCount) {
                cellMatrix[row][column].setStatus(Status.ALIVE);
                cellMatrix[row][column].repaint();
            }
        }
    }

    public void updateSync() {
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                updateCellSync(row, column);
            }
        }
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (cellMatrix[row][column].updateToNextStatus()) {
                    cellMatrix[row][column].repaint();
                }
            }
        }
    }

    public void updateCellSync(int row, int column) {
        int aliveNeighbors = countAliveNeighbors(row, column);

        if (cellMatrix[row][column].isAlive()) {
            if (aliveNeighbors < lonelinessNeighboursCount || aliveNeighbors > overcrowdingNeighboursCount) {
                // 1. Any live cell with fewer than two live neighbours dies, as if by underpopulation.
                // 3. Any live cell with more than three live neighbours dies, as if by overpopulation.
                cellMatrix[row][column].setNextStatus(Status.DEAD);
            } else {
                // 2. Any live cell with two or three live neighbours lives on to the next generation.
                cellMatrix[row][column].setNextStatus(Status.ALIVE);
            }
        } else {
            // 4. Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.
            if (aliveNeighbors == birthNeighboursCount) {
                cellMatrix[row][column].setNextStatus(Status.ALIVE);
            } else {
                cellMatrix[row][column].setNextStatus(Status.DEAD);
            }
        }
    }

    private int countAliveNeighbors(int row, int column) {
        int aliveNeighbors = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                if (i == row && j == column) {
                    continue;
                }
                if (i >= 0 && i < rows && j >= 0 && j < columns) {
                    if (cellMatrix[i][j].isAlive()) {
                        aliveNeighbors++;
                    }
                }
            }
        }
        return aliveNeighbors;
    }

    public double getDensity() {
        double totalCells = rows * columns;
        double aliveCells = 0;
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                if (cellMatrix[row][column].isAlive()) {
                    aliveCells++;
                }
            }
        }
        return  aliveCells / totalCells;
    }
}
