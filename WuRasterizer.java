import java.util.ArrayList;

class WuRasterizer implements LineRasterizer {

    private int ipart(float x) {
        return (int) Math.floor(x);
    }

    private float fpart(float x) {
        return x - (float) Math.floor(x);
    }

    private float rfpart(float x) {
        return 1 - fpart(x);
    }

    public Point[] rasterize(Point p1, Point p2) {

        ArrayList<Point> points = new ArrayList<>();

        float x0 = p1.x;
        float y0 = p1.y;
        float x1 = p2.x;
        float y1 = p2.y;

        boolean steep = Math.abs(y1 - y0) > Math.abs(x1 - x0);

        if (steep) {
            float temp;
            temp = x0;
            x0 = y0;
            y0 = temp;
            temp = x1;
            x1 = y1;
            y1 = temp;
        }

        if (x0 > x1) {
            float temp;
            temp = x0;
            x0 = x1;
            x1 = temp;
            temp = y0;
            y0 = y1;
            y1 = temp;
        }

        float dx = x1 - x0;
        float dy = y1 - y0;
        float gradient = (dx == 0) ? 1 : dy / dx;

        // First endpoint
        float xend = Math.round(x0);
        float yend = y0 + gradient * (xend - x0);
        float xgap = rfpart(x0 + 0.5f);
        int xpxl1 = (int) xend;
        int ypxl1 = ipart(yend);

        if (steep) {
            points.add(new Point(ypxl1, xpxl1));
            points.add(new Point(ypxl1 + 1, xpxl1));
        } else {
            points.add(new Point(xpxl1, ypxl1));
            points.add(new Point(xpxl1, ypxl1 + 1));
        }

        float intery = yend + gradient;

        // Second endpoint
        xend = Math.round(x1);
        yend = y1 + gradient * (xend - x1);
        int xpxl2 = (int) xend;
        int ypxl2 = ipart(yend);

        if (steep) {
            points.add(new Point(ypxl2, xpxl2));
            points.add(new Point(ypxl2 + 1, xpxl2));
        } else {
            points.add(new Point(xpxl2, ypxl2));
            points.add(new Point(xpxl2, ypxl2 + 1));
        }

        // Main loop
        for (int x = xpxl1 + 1; x < xpxl2; x++) {
            if (steep) {
                points.add(new Point(ipart(intery), x));
                points.add(new Point(ipart(intery) + 1, x));
            } else {
                points.add(new Point(x, ipart(intery)));
                points.add(new Point(x, ipart(intery) + 1));
            }
            intery += gradient;
        }

        return points.toArray(new Point[0]);
    }
}
