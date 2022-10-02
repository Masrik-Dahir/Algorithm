



var EPS = 0.0000001;


function Point(x, y) {
  this.x = x;
  this.y = y;
}


function Circle(x,y,r) {
  this.x = x;
  this.y = y;
  this.r = r;
}




function acossafe(x) {
  if (x >= +1.0) return 0;
  if (x <= -1.0) return Math.PI;
  return Math.acos(x);
}


function rotatePoint(fp, pt, a) {
  var x = pt.x - fp.x;
  var y = pt.y - fp.y;
  var xRot = x * Math.cos(a) + y * Math.sin(a);
  var yRot = y * Math.cos(a) - x * Math.sin(a);
  return new Point(fp.x+xRot,fp.y+yRot);
}



function circleCircleIntersectionPoints(c1, c2) {
  
  var r, R, d, dx, dy, cx, cy, Cx, Cy;
  
  if (c1.r < c2.r) {
    r  = c1.r;  R = c2.r;
    cx = c1.x; cy = c1.y;
    Cx = c2.x; Cy = c2.y;
  } else {
    r  = c2.r; R  = c1.r;
    Cx = c1.x; Cy = c1.y;
    cx = c2.x; cy = c2.y;
  }
  

  dx = cx - Cx;
  dy = cy - Cy;


  d = Math.sqrt( dx*dx + dy*dy );
  


  if (d < EPS && Math.abs(R-r) < EPS) return [];
  


  else if (d < EPS) return [];
  
  var x = (dx / d) * R + Cx;
  var y = (dy / d) * R + Cy;
  var P = new Point(x, y);
  

  if (Math.abs((R+r)-d) < EPS || Math.abs(R-(r+d)) < EPS) return [P];
  


  if ( (d+r) < R || (R+r < d) ) return [];
  
  var C = new Point(Cx, Cy);
  var angle = acossafe((r*r-d*d-R*R)/(-2.0*d*R));
  var pt1 = rotatePoint(C, P, +angle);
  var pt2 = rotatePoint(C, P, -angle);
  return [pt1, pt2];
  
}





