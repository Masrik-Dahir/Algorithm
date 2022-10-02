
package geometry;

import static java.lang.Math.*;

public class LongitudeLatitudeGeographicDistance {



  public static double dist(double lat1, double lon1, double lat2, double lon2) {
    double dLat = toRadians(lat2 - lat1);
    double dLon = toRadians(lon2 - lon1);
    double a =
        sin(dLat / 2.0) * sin(dLat / 2.0)
            + cos(toRadians(lat1)) * cos(toRadians(lat2)) * sin(dLon / 2.0) * sin(dLon / 2.0);
    return 2.0 * atan2(sqrt(a), sqrt(1 - a));
  }
}
