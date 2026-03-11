package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class RatingDAO extends DAO<Rating> {

	public RatingDAO(Connection connection) {
		super(connection);
	}

	@Override
	public boolean create(Rating obj) {
		try {
			String query = "INSERT INTO ranting (registrationNumber, CIN, isReturned, rentalDate, returnDate) " +
					"VALUES (?, ?, ?, ?, ?)";
			PreparedStatement state = this.connection.prepareStatement(query);
			state.setString(1, obj.getCar().getRegistrationNumber());
			state.setString(2, obj.getClient().getCIN());
			state.setBoolean(3, obj.isReturned());
			state.setDate(4, obj.getRentalDate());
			state.setDate(5, obj.getReturnDate());
			state.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Rating obj) {
		try {
			String query = "DELETE FROM ranting WHERE rantID = ?";
			PreparedStatement state = this.connection.prepareStatement(query);
			state.setInt(1, obj.getRantID());
			state.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Rating obj) {
		try {
			String query = "UPDATE ranting SET isReturned = ?, rentalDate = ?, returnDate = ?, registrationNumber = ?, CIN = ? WHERE rantID = ?";
			PreparedStatement state = this.connection.prepareStatement(query);
			state.setBoolean(1, obj.isReturned());
			state.setDate(2, obj.getRentalDate());
			state.setDate(3, obj.getReturnDate());
			state.setString(4, obj.getCar().getRegistrationNumber());
			state.setString(5, obj.getClient().getCIN());
			state.setInt(6, obj.getRantID());
			state.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Rating find(String ref) {
		try {
			String query = "SELECT * FROM ranting WHERE rantID = ?";
			PreparedStatement state = this.connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setInt(1, Integer.parseInt(ref));
			ResultSet result = state.executeQuery();

			if (result.next()) { // Check if there are any results
				return new Rating(
						result.getInt("rantID"),
						(new CarDAO(this.connection)).find(result.getString("registrationNumber")),
						(new ClientDAO(this.connection)).find(result.getString("CIN")),
						result.getBoolean("isReturned"),
						result.getDate("rentalDate"),
						result.getDate("returnDate")
				);
			} else {
				System.out.println("No ranting found with rantID: " + ref);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Rating> all() {
		Vector<Rating> ratings = new Vector<>();
		try {
			String query = "SELECT * FROM ranting";
			PreparedStatement state = this.connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet result = state.executeQuery();

			while (result.next()) {
				ratings.add(new Rating(
						result.getInt("rantID"),
						(new CarDAO(this.connection)).find(result.getString("registrationNumber")),
						(new ClientDAO(this.connection)).find(result.getString("CIN")),
						result.getBoolean("isReturned"),
						result.getDate("rentalDate"),
						result.getDate("returnDate")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ratings;
	}

	@Override
	public List<Rating> available() {
		// This method is not implemented, keeping as-is
		return null;
	}

	public List<Rating> all(String ref) {
		// This method is not implemented, keeping as-is
		return null;
	}

	public void finalize() throws Throwable {
		super.finalize();
	}
}