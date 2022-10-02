



var EPS = 0.0000001;


function Point(x, y) {
  this.x = x;
  this.y = y;
}


function Circle(x, y, r) {
  this.x = x;
  this.y = y;
  this.r = r;
}


function LineSegment(x1, y1, x2, y2) {
  var d = Math.sqrt( (x1-x2)*(x1-x2) + (y1-y2)*(y1-y2) );
  if (d < EPS) throw 'A point is not a line segment';
  this.x1 = x1; this.y1 = y1;
  this.x2 = x2; this.y2 = y2;
}


function Line(a, b, c) {
  this.a = a; this.b = b; this.c = c;

  if (Math.abs(b) < EPS) {
    c /= a; a = 1; b = 0;
  } else { 
    a = (Math.abs(a) < EPS) ? 0 : a / b;
    c /= b; b = 1; 
  }
}




function circleLineIntersection(circle, line) {

  var a = line.a, b = line.b, c = line.c;
  var x = circle.x, y = circle.y, r = circle.r;









  var A = a*a + b*b;
  var B = 2*a*b*y - 2*a*c - 2*b*b*x;
  var C = b*b*x*x + b*b*y*y - 2*b*c*y + c*c - b*b*r*r;




  var D = B*B - 4*A*C;
  var x1,y1,x2,y2;


  if (Math.abs(b) < EPS) {


    x1 = c/a;


    if (Math.abs(x-x1) > r) return [];


    if (Math.abs((x1-r)-x) < EPS || Math.abs((x1+r)-x) < EPS)
      return [new Point(x1, y)];

    var dx = Math.abs(x1 - x);
    var dy = Math.sqrt(r*r-dx*dx);


    return [
      new Point(x1,y+dy),
      new Point(x1,y-dy)
    ];


} else if (Math.abs(D) < EPS) {

    x1 = -B/(2*A);
    y1 = (c - a*x1)/b;

    return [new Point(x1,y1)];


  } else if (D < 0) {

    return [];

  } else {

    D = Math.sqrt(D);

    x1 = (-B+D)/(2*A);
    y1 = (c - a*x1)/b;

    x2 = (-B-D)/(2*A);
    y2 = (c - a*x2)/b;

    return [
      new Point(x1, y1),
      new Point(x2, y2)
    ];

  }

}


function segmentToGeneralForm(x1,y1,x2,y2) {
  var a = y1 - y2;
  var b = x2 - x1;
  var c = x2*y1 - x1*y2;
  return new Line(a,b,c);
}


function pointInRectangle(pt,x1,y1,x2,y2) {
  var x = Math.min(x1,x2), X = Math.max(x1,x2);
  var y = Math.min(y1,y2), Y = Math.max(y1,y2);
  return x - EPS <= pt.x && pt.x <= X + EPS &&
         y - EPS <= pt.y && pt.y <= Y + EPS;
}


function lineSegmentCircleIntersection(segment, circle) {

  var x1 = segment.x1, y1 = segment.y1, x2 = segment.x2, y2 = segment.y2;
  var line = segmentToGeneralForm(x1,y1,x2,y2);
  var pts = circleLineIntersection(circle, line);


  if (pts.length === 0) return [];

  var pt1 = pts[0];
  var includePt1 = pointInRectangle(pt1,x1,y1,x2,y2);


  if (pts.length === 1) {
    if (includePt1) return [pt1];
    return [];
  }

  var pt2 = pts[1];
  var includePt2 = pointInRectangle(pt2,x1,y1,x2,y2);


  if (includePt1 && includePt2) return [pt1, pt2];
  if (includePt1) return [pt1];
  if (includePt2) return [pt2];
  return [];

}