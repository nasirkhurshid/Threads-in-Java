class MinVal extends Thread {
	private int[] values;
	private int start;
	private int end;
	private int min;

	public MinVal(int[] val, int s, int e) {
		this.values = val;
		this.start = s;
		this.end = e;
	}

	@Override
	public void run() {
		min = Integer.MAX_VALUE;
		for (int i = start; i < end; i++) {
			min = Math.min(min, values[i]);
		}
	}

	public int getMin() {
		return min;
	}
}