package automate.ActionClass;

import java.util.List;

import automate.FunCall;
import automate.Parameter;
import info3.game.modele.Entity;

public class Power extends FunCall {

	protected int probability;

	public Power(List<Parameter> parameters, int proba) {
		super("Power", parameters, proba);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void exec(Entity e) {
		e.power();
	}

	@Override
	public boolean eval(Entity e) {
		return false;
	}

	public String toString() {
		String s = "";
		s += "Power(";
		for (Parameter p : parameters) {
			s += p.toString();
			s += ", ";
		}
		s += ");";
		return s;
	}

}
