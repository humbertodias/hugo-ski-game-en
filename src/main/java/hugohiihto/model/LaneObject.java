package hugohiihto.model;

/**
 * One of the four things coming down a ski track: empty, money, enemy, scroll or finish.
 */
public final class LaneObject {
    private String symbol = "E";
    private int x;
    private int y;
    private int width = 1;
    private int height = 1;

    public String symbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public void place(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void move(int dx, int dy) {
        x += dx;
        y += dy;
    }

    public void grow(int dw, int dh) {
        width += dw;
        height += dh;
    }
}
