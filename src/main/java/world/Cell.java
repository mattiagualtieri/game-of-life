package world;

import enums.Status;

import java.awt.*;

public class Cell extends Panel {

    private boolean alive;
    private Status status;
    private Status nextStatus;
    private Color color;

    public Cell(boolean alive) {
        this.alive = alive;
        status = this.alive ? Status.ALIVE : Status.DEAD;
        color = this.alive ? Color.WHITE : Color.BLACK;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setStatus(Status status) {
        this.status = status;
        alive = this.status == Status.ALIVE;
        color = this.alive ? Color.WHITE : Color.BLACK;
    }

    public void setNextStatus(Status nextStatus) {
        this.nextStatus = nextStatus;
    }

    public boolean updateToNextStatus() {
        if (status == nextStatus) {
            return false;
        }
        setStatus(nextStatus);
        return true;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

}
