import processing.core.PApplet;

class Point {
    int x, y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

public class LineRasterizerTest extends PApplet {

    // Global variables
    final int GRID_SIZE = 50;
    final int CELL_SIZE = 10;
    final int CANVAS_SIZE = GRID_SIZE * CELL_SIZE;

    Point endpoint1;
    Point endpoint2;
    Point dragging = null;
    final int ENDPOINT_RADIUS = 8;

    LineRasterizer rasterizer;

    public void settings() {
        size(500, 500);
    }

    public void setup() {
        // Initialize endpoints
        endpoint1 = new Point(10, 10);
        endpoint2 = new Point(40, 35);

        // Create rasterizer instance
        rasterizer = new WuRasterizer();

    }

    public void draw() {
        background(255);

        // Draw grid
        stroke(220);
        strokeWeight(1);
        for (int i = 0; i <= GRID_SIZE; i++) {
            line(i * CELL_SIZE, 0, i * CELL_SIZE, CANVAS_SIZE);
            line(0, i * CELL_SIZE, CANVAS_SIZE, i * CELL_SIZE);
        }

        // Get rasterized pixels
        Point[] rasterizedPixels = rasterizer.rasterize(endpoint1, endpoint2);

        // Draw rasterized pixels as filled cells
        fill(100, 100, 255, 150);
        noStroke();
        for (Point p : rasterizedPixels) {
            rect(p.x * CELL_SIZE, p.y * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        }

        // Draw reference line (1px thin line)
        stroke(255, 0, 0);
        strokeWeight(1);
        line(endpoint1.x * CELL_SIZE + CELL_SIZE / 2,
                endpoint1.y * CELL_SIZE + CELL_SIZE / 2,
                endpoint2.x * CELL_SIZE + CELL_SIZE / 2,
                endpoint2.y * CELL_SIZE + CELL_SIZE / 2);

        // Draw endpoints
        fill(255, 100, 100);
        stroke(200, 50, 50);
        strokeWeight(2);
        ellipse(endpoint1.x * CELL_SIZE + CELL_SIZE / 2,
                endpoint1.y * CELL_SIZE + CELL_SIZE / 2,
                ENDPOINT_RADIUS * 2, ENDPOINT_RADIUS * 2);
        ellipse(endpoint2.x * CELL_SIZE + CELL_SIZE / 2,
                endpoint2.y * CELL_SIZE + CELL_SIZE / 2,
                ENDPOINT_RADIUS * 2, ENDPOINT_RADIUS * 2);
    }

    public void mousePressed() {
        // Check if clicking on endpoint1
        float d1 = dist(mouseX, mouseY,
                endpoint1.x * CELL_SIZE + CELL_SIZE / 2,
                endpoint1.y * CELL_SIZE + CELL_SIZE / 2);
        if (d1 < ENDPOINT_RADIUS) {
            dragging = endpoint1;
            return;
        }

        // Check if clicking on endpoint2
        float d2 = dist(mouseX, mouseY,
                endpoint2.x * CELL_SIZE + CELL_SIZE / 2,
                endpoint2.y * CELL_SIZE + CELL_SIZE / 2);
        if (d2 < ENDPOINT_RADIUS) {
            dragging = endpoint2;
            return;
        }
    }

    public void mouseDragged() {
        if (dragging != null) {
            // Convert mouse position to grid coordinates
            int gridX = constrain(mouseX / CELL_SIZE, 0, GRID_SIZE - 1);
            int gridY = constrain(mouseY / CELL_SIZE, 0, GRID_SIZE - 1);

            dragging.x = gridX;
            dragging.y = gridY;
        }
    }

    public void mouseReleased() {
        dragging = null;
    }

    public static void main(String[] args) {
        PApplet.main("LineRasterizerTest");
    }
}
