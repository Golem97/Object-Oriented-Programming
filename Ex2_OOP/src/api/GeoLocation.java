package api;

import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import static java.lang.StrictMath.pow;
import static java.lang.StrictMath.sqrt;

public class GeoLocation implements geo_location {

    private double x;
    private double y;
    private double z;
    private double distance;

    public GeoLocation(){
        this.x= 0;
        this.y= 0;
        this.z= 0;
        this.distance=0;
    }


    public GeoLocation(geo_location gl){
        this.x =gl.x();
        this.y= gl.y();
        this.z= gl.z();
    }

    public GeoLocation(double xa, double ya, double za){
        this.x = xa;
        this.y = ya;
        this.z = za;
    }

    @Override
    public double x() {
        return this.x;
    }

    @Override
    public double y() {
        return this.y;
    }

    @Override
    public double z() {
        return this.z;
    }

    @Override
    public double distance(geo_location g) {

       // double dist = sqrt( pow( 2,(g.x()-this.x))+pow( 2,(g.y()-this.y)) +pow( 2,(g.z()-this.z)));
        //return  dist;
        double dx = this.x() - g.x();
        double dy = this.y() - g.y();
        double dz = this.z() - g.z();
        double t = (dx*dx+dy*dy+dz*dz);
        return Math.sqrt(t);
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return x + "," + y + "," + z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
