package play.learn.java;

public class LearnBuilder {
	
	private int sodium;
	private int fat;
	private int carbo;
	
	public static class Builder {
		private int sodium;
		private int fat;
		private int carbo;
		
		public Builder(int s) {
			this.sodium = s;
		}
		
		public Builder fat(int f) {
			this.fat = f;
			return this;
		}
		
		public Builder carbo(int c) {
			this.carbo = c;
			return this;
		}
		
		public LearnBuilder build() {
			return new LearnBuilder(this);
		}
	}
	
	private LearnBuilder(Builder b) {
		this.sodium = b.sodium;
		this.fat = b.fat;
		this.carbo = b.carbo;
	}

	public static void main(String[] args) {
		LearnBuilder lb = new LearnBuilder.Builder(10).carbo(23).fat(1).build();
		
		
	}

}
