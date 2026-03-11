package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

/**
 * DAO for managing Car entities in the database.
 */
public class CarDAO extends DAO<Car> {

	public CarDAO(Connection connection) {
		super(connection);
	}

	@Override
	public boolean create(Car obj) {
		try {
			String query = "INSERT INTO car VALUES (?, ?, ?, ?)";
			PreparedStatement state = this.connection.prepareStatement(query);
			state.setString(1, obj.getRegistrationNumber());
			state.setString(2, obj.getBrand());
			state.setString(3, obj.getModel());
			state.setDouble(4, obj.getPrice());
			state.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Car obj) {
		try {
			String query = "DELETE FROM car WHERE registrationNumber = ?";
			PreparedStatement state = this.connection.prepareStatement(query);
			state.setString(1, obj.getRegistrationNumber());
			state.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Car obj) {
		try {
			String query = "UPDATE car SET brand = ?, model = ?, price = ? WHERE registrationNumber = ?";
			PreparedStatement state = this.connection.prepareStatement(query);
			state.setString(1, obj.getBrand());
			state.setString(2, obj.getModel());
			state.setDouble(3, obj.getPrice());
			state.setString(4, obj.getRegistrationNumber());
			state.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Car find(String ref) {
		try {
			String query = "SELECT * FROM car WHERE registrationNumber = ?";
			PreparedStatement state = this.connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setString(1, ref);
			ResultSet result = state.executeQuery();
			if (result.next()) { // Check if the result has rows
				return new Car(
						ref,
						result.getString("model"),
						result.getString("brand"),
						result.getDouble("price")
				);
			} else {
				System.out.println("No car found with registration number: " + ref);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Car> all() {
		Vector<Car> cars = new Vector<>();
		try {
			String query = "SELECT * FROM car";
			PreparedStatement state = this.connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet result = state.executeQuery();
			while (result.next()) {
				cars.add(new Car(
						result.getString("registrationNumber"),
						result.getString("model"),
						result.getString("brand"),
						result.getDouble("price")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cars;
	}

	public List<Car> available() {
		Vector<Car> cars = new Vector<>();
		try {
			String query = "SELECT * FROM car WHERE registrationNumber NOT IN (SELECT registrationNumber FROM ranting)";
			PreparedStatement state = this.connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet result = state.executeQuery();
			while (result.next()) {
				cars.add(new Car(
						result.getString("registrationNumber"),
						result.getString("model"),
						result.getString("brand"),
						result.getDouble("price")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cars;
	}

	public List<Car> search(CarCriterionInter criterion) {
		if (!criterion.getCriterions().isEmpty()) {
			StringBuilder query = new StringBuilder("SELECT * FROM car WHERE ");
			if (criterion.getCriterions().firstElement() instanceof ModeleCriterion) {
				query.append("model = '").append(((ModeleCriterion) criterion.getCriterions().firstElement()).getModel()).append("'");
			} else if (criterion.getCriterions().firstElement() instanceof BrandCriterion) {
				query.append("brand = '").append(((BrandCriterion) criterion.getCriterions().firstElement()).getBrand()).append("'");
			} else if (criterion.getCriterions().firstElement() instanceof RegistrationNumberCriterion) {
				query.append("registrationNumber = '").append(((RegistrationNumberCriterion) criterion.getCriterions().firstElement()).getRegistrationNumber()).append("'");
			} else if (criterion.getCriterions().firstElement() instanceof PriceCriterion) {
				query.append("price <= ").append(((PriceCriterion) criterion.getCriterions().firstElement()).getPrice());
			}

			for (Criterion<Car> item : criterion.getCriterions()) {
				if (item instanceof ModeleCriterion) {
					query.append(" AND model = '").append(((ModeleCriterion) item).getModel()).append("'");
				} else if (item instanceof BrandCriterion) {
					query.append(" AND brand = '").append(((BrandCriterion) item).getBrand()).append("'");
				} else if (item instanceof RegistrationNumberCriterion) {
					query.append(" AND registrationNumber = '").append(((RegistrationNumberCriterion) item).getRegistrationNumber()).append("'");
				} else if (item instanceof PriceCriterion) {
					query.append(" AND price <= ").append(((PriceCriterion) item).getPrice());
				}
			}
			query.append(";");
			Vector<Car> cars = new Vector<>();
			try {
				PreparedStatement state = this.connection.prepareStatement(query.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet result = state.executeQuery();
				while (result.next()) {
					cars.add(new Car(
							result.getString("registrationNumber"),
							result.getString("model"),
							result.getString("brand"),
							result.getDouble("price")
					));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return cars;
		}
		return null;
	}

	public void finalize() throws Throwable {
		super.finalize();
	}
}