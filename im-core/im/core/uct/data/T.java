package im.core.uct.data;

public class T {

	private String state = null;
	private int action = -1;
	private int q_value = 0;
	private int n_iter = 0;
	
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getAction() {
		return action;
	}
	public void setAction(int action) {
		this.action = action;
	}
	public int getQ_value() {
		return q_value;
	}
	public void setQ_value(int q_value) {
		this.q_value = q_value;
	}
	public int getN_iter() {
		return n_iter;
	}
	public void setN_iter(int n_iter) {
		this.n_iter = n_iter;
	}
	
	
	
}
