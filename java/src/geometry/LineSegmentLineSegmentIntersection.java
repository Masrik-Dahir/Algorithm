
package geometry;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

import java.util.ArrayList;
import java.util.List;

public class LineSegmentLineSegmentIntersection {


  private static final double EPS = 1e-7;


  public static class Pt {
    double x, y;

    public Pt(double x, double y) {
      this.x = x;
      this.y = y;
    }

    public boolean equals(Pt pt) {
      return abs(x - pt.x) < EPS && abs(y - pt.y) < EPS;
    }
  }



  public static Pt[] lineSegmentLineSegmentIntersection(Pt p1, Pt p2, Pt p3, Pt p4) {


    if (!segmentsIntersect(p1, p2, p3, p4)) return new Pt[] {};


    if (p1.equals(p2) && p2.equals(p3) && p3.equals(p4)) return new Pt[] {p1};

    List<Pt> endpoints = getCommonEndpoints(p1, p2, p3, p4);
    int n = endpoints.size();




    boolean singleton = p1.equals(p2) || p3.equals(p4);
    if (n == 1 && singleton) return new Pt[] {endpoints.get(0)};


    if (n == 2) return new Pt[] {endpoints.get(0), endpoints.get(1)};

    boolean collinearSegments = (orientation(p1, p2, p3) == 0) && (orientation(p1, p2, p4) == 0);



    if (collinearSegments) {


      if (pointOnLine(p1, p2, p3) && pointOnLine(p1, p2, p4)) return new Pt[] {p3, p4};


      if (pointOnLine(p3, p4, p1) && pointOnLine(p3, p4, p2)) return new Pt[] {p1, p2};



      Pt midPoint1 = pointOnLine(p1, p2, p3) ? p3 : p4;
      Pt midPoint2 = pointOnLine(p3, p4, p1) ? p1 : p2;


      if (midPoint1.equals(midPoint2)) return new Pt[] {midPoint1};

      return new Pt[] {midPoint1, midPoint2};
    }

    


    if (abs(p1.x - p2.x) < EPS) {
      double m = (p4.y - p3.y) / (p4.x - p3.x);
      double b = p3.y - m * p3.x;
      return new Pt[] {new Pt(p1.x, m * p1.x + b)};
    }


    if (abs(p3.x - p4.x) < EPS) {
      double m = (p2.y - p1.y) / (p2.x - p1.x);
      double b = p1.y - m * p1.x;
      return new Pt[] {new Pt(p3.x, m * p3.x + b)};
    }

    double m1 = (p2.y - p1.y) / (p2.x - p1.x);
    double m2 = (p4.y - p3.y) / (p4.x - p3.x);
    double b1 = p1.y - m1 * p1.x;
    double b2 = p3.y - m2 * p3.x;
    double x = (b2 - b1) / (m1 - m2);
    double y = (m1 * b2 - m2 * b1) / (m1 - m2);

    return new Pt[] {new Pt(x, y)};
  }






  private static int orientation(Pt a, Pt b, Pt c) {
    double value = (b.y - a.y) * (c.x - b.x) - (b.x - a.x) * (c.y - b.y);
    if (abs(value) < EPS) return 0;
    return (value > 0) ? -1 : +1;
  }




  private static boolean pointOnLine(Pt a, Pt b, Pt c) {
    return orientation(a, b, c) == 0
        && min(a.x, b.x) <= c.x
        && c.x <= max(a.x, b.x)
        && min(a.y, b.y) <= c.y
        && c.y <= max(a.y, b.y);
  }


  private static boolean segmentsIntersect(Pt p1, Pt p2, Pt p3, Pt p4) {



    int o1 = orientation(p1, p2, p3);
    int o2 = orientation(p1, p2, p4);
    int o3 = orientation(p3, p4, p1);
    int o4 = orientation(p3, p4, p2);





    if (o1 != o2 && o3 != o4) return true;


    if (o1 == 0 && pointOnLine(p1, p2, p3)) return true;
    if (o2 == 0 && pointOnLine(p1, p2, p4)) return true;
    if (o3 == 0 && pointOnLine(p3, p4, p1)) return true;
    if (o4 == 0 && pointOnLine(p3, p4, p2)) return true;

    return false;
  }

  private static List<Pt> getCommonEndpoints(Pt p1, Pt p2, Pt p3, Pt p4) {

    List<Pt> points = new ArrayList<>();

    if (p1.equals(p3)) {
      points.add(p1);
      if (p2.equals(p4)) points.add(p2);

    } else if (p1.equals(p4)) {
      points.add(p1);
      if (p2.equals(p3)) points.add(p2);

    } else if (p2.equals(p3)) {
      points.add(p2);
      if (p1.equals(p4)) points.add(p1);

    } else if (p2.equals(p4)) {
      points.add(p2);
      if (p1.equals(p3)) points.add(p1);
    }

    return points;
  }

  public static void main(String[] args) {


    Pt p1, p2, p3, p4;

    p1 = new Pt(-2, 4);
    p2 = new Pt(3, 3);
    p3 = new Pt(0, 0);
    p4 = new Pt(2, 4);
    Pt[] points = lineSegmentLineSegmentIntersection(p1, p2, p3, p4);
    Pt point = points[0];


    System.out.printf("(%.3f, %.3f)\n", point.x, point.y);

    p1 = new Pt(-10, 0);
    p2 = new Pt(+10, 0);
    p3 = new Pt(-5, 0);
    p4 = new Pt(+5, 0);
    points = lineSegmentLineSegmentIntersection(p1, p2, p3, p4);
    Pt point1 = points[0], point2 = points[1];


    System.out.printf("(%.3f, %.3f) (%.3f, %.3f)\n", point1.x, point1.y, point2.x, point2.y);
  }
}
