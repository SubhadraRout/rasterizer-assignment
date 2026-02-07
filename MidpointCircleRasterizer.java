import java.util.ArrayList;

class MidpointCircleRasterizer implements LineRasterizer {

    public Point[] rasterize(Point p1, Point p2) {

        ArrayList<Point> points = new ArrayList<>();

        // Center of circle (midpoint of diameter)
        int xc = (p1.x + p2.x) / 2;
        int yc = (p1.y + p2.y) / 2;

        // Radius (distance / 2)
        int dx = p2.x - p1.x;
        int dy = p2.y - p1.y;
        int r = (int) Math.round(Math.sqrt(dx * dx + dy * dy) / 2.0);

        int x = 0;
        int y = r;
        int p = 1 - r;

        while (x <= y) {

            // 8-way symmetry
            points.add(new Point(xc + x, yc + y));
            points.add(new Point(xc - x, yc + y));
            points.add(new Point(xc + x, yc - y));
            points.add(new Point(xc - x, yc - y));
            points.add(new Point(xc + y, yc + x));
            points.add(new Point(xc - y, yc + x));
            points.add(new Point(xc + y, yc - x));
            points.add(new Point(xc - y, yc - x));

            x++;

            if (p < 0) {
                p += 2 * x + 1;
            } else {
                y--;
                p += 2 * (x - y) + 1;
            }
        }

        return points.toArray(new Point[0]);
    }
}
