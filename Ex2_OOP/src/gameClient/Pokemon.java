package gameClient;
import api.edge_data;
import gameClient.util.Point3D;
import org.json.JSONObject;

public class Pokemon {

	private edge_data _edge;
	private double _value;
	private int _type;


	private Point3D _pos;
	private double min_dist;
	private int min_ro;
	//I added a boolean to signified to my all other agent if my pokemon are already targeted by an other agent in the game.
	private boolean targeted ;
	private double distFromAgent;



	public Pokemon(Point3D p, int t, double v, double s, edge_data e, boolean targeted, double distFromAgent) {
		_type = t;
		//	_speed = s;
		_value = v;

		set_edge(e);
		_pos = p;
		min_dist = -1;
		min_ro = -1;
		targeted=false;
		distFromAgent =0;
	}
	public static Pokemon init_from_json(String json) {
		Pokemon ans = null;
		try {
			JSONObject p = new JSONObject(json);
			int id = p.getInt("id");

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ans;
	}
	public String toString() {return "Pokemon:{ "+" v="+_value+", t="+_type+" , Targeted :"+targeted+", Edge: "+_edge+" }";}
	public edge_data get_edge() {
		return _edge;
	}

	public void set_edge(edge_data _edge) {
		this._edge = _edge;
	}

	public Point3D getLocation() {
		return _pos;
	}
	public int getType() {return _type;}
	//	public double getSpeed() {return _speed;}
	public double getValue() {return _value;}

	public double getMin_dist() {
		return min_dist;
	}

	public void setMin_dist(double mid_dist) {
		this.min_dist = mid_dist;
	}

	public int getMin_ro() {
		return min_ro;
	}

	public void setMin_ro(int min_ro) {
		this.min_ro = min_ro;
	}

	public void setTargeted(boolean targeted) {
		this.targeted = targeted;
	}
	public boolean isTargeted() {
		return targeted;
	}

	public double getDistFromAgent() {
		return distFromAgent;
	}

	public void setDistFromAgent(double distFromAgent) {
		this.distFromAgent = distFromAgent;
	}

	public Point3D get_pos() {
		return _pos;
	}
}
