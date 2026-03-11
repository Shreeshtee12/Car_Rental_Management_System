package Model;


public class ModeleCriterion implements Criterion<Car> {
	private String model;

	public ModeleCriterion(String model){
		this.model = model;
	}

	public void finalize() throws Throwable {

	}

	@Override
	public boolean isSatisfiedBy(Car c) {
		return c.getModel().equals(this.model);
	}

	public String getModel() {
		return model;
	}
}