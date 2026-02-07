import java.util.ArrayList;

class DDARasterizer implements LineRasterizer {

    public Point[] rasterize(Point p1, Point p2) {

        ArrayList<Point> points = new ArrayList<>();

        int x1 = p1.x;
        int y1 = p1.y;
        int x2 = p2.x;
        int y2 = p2.y;

        int dx = x2 - x1;
        int dy = y2 - y1;

        int steps = Math.max(Math.abs(dx), Math.abs(dy));

        if (steps == 0) {
            // Same point
            points.add(new Point(x1, y1));
            return points.toArray(new Point[0]);
        }

        float xInc = dx / (float) steps;
        float yInc = dy / (float) steps;

        float x = x1;
        float y = y1;

        for (int i = 0; i <= steps; i++) {
            points.add(new Point(Math.round(x), Math.round(y)));
            x += xInc;
            y += yInc;
        }

        return points.toArray(new Point[0]);
    }
}
