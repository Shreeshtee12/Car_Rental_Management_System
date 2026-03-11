package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class ClientDAO extends DAO<Client> {

	public ClientDAO(Connection connection) {
		super(connection);
	}

	@Override
	public boolean create(Client obj) {
		try {
			String query = "INSERT INTO client VALUES (?, ?, ?)";
			PreparedStatement state = this.connection.prepareStatement(query);
			state.setString(1, obj.getCIN());
			state.setString(2, obj.getFirstName());
			state.setString(3, obj.getLastName());
			state.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Client obj) {
		try {
			String query = "DELETE FROM client WHERE CIN = ?";
			PreparedStatement state = this.connection.prepareStatement(query);
			state.setString(1, obj.getCIN());
			state.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Client obj) {
		try {
			String query = "UPDATE client SET firstName = ?, lastName = ? WHERE CIN = ?";
			PreparedStatement state = this.connection.prepareStatement(query);
			state.setString(1, obj.getFirstName());
			state.setString(2, obj.getLastName());
			state.setString(3, obj.getCIN());
			state.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Client find(String ref) {
		try {
			String query = "SELECT * FROM client WHERE CIN = ?";
			PreparedStatement state = this.connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			state.setString(1, ref);
			ResultSet result = state.executeQuery();
			if (result.next()) { // Check if there are any results
				return new Client(
						ref,
						result.getString("firstName"),
						result.getString("lastName")
				);
			} else {
				System.out.println("No client found with CIN: " + ref);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Client> all() {
		Vector<Client> clients = new Vector<>();
		try {
			String query = "SELECT * FROM client";
			PreparedStatement state = this.connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet result = state.executeQuery();
			while (result.next()) {
				clients.add(new Client(
						result.getString("CIN"),
						result.getString("firstName"),
						result.getString("lastName")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clients;
	}

	public List<Client> available() {
		Vector<Client> clients = new Vector<>();
		try {
			String query = "SELECT * FROM client WHERE CIN NOT IN (SELECT CIN FROM ranting)";
			PreparedStatement state = this.connection.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet result = state.executeQuery();
			while (result.next()) {
				clients.add(new Client(
						result.getString("CIN"),
						result.getString("firstName"),
						result.getString("lastName")
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return clients;
	}

	public List<Client> search(ClientCriterionInter criterion) {
		if (!criterion.getCriterions().isEmpty()) {
			StringBuilder query = new StringBuilder("SELECT * FROM client WHERE ");
			if (criterion.getCriterions().firstElement() instanceof CINCriterion) {
				query.append("CIN = '").append(((CINCriterion) criterion.getCriterions().firstElement()).getCIN()).append("'");
			} else if (criterion.getCriterions().firstElement() instanceof FnameCriterion) {
				query.append("firstName = '").append(((FnameCriterion) criterion.getCriterions().firstElement()).getFname()).append("'");
			} else if (criterion.getCriterions().firstElement() instanceof LnameCriterion) {
				query.append("lastName = '").append(((LnameCriterion) criterion.getCriterions().firstElement()).getLname()).append("'");
			}

			for (Criterion<Client> item : criterion.getCriterions()) {
				if (item instanceof CINCriterion) {
					query.append(" AND CIN = '").append(((CINCriterion) item).getCIN()).append("'");
				} else if (item instanceof FnameCriterion) {
					query.append(" AND firstName = '").append(((FnameCriterion) item).getFname()).append("'");
				} else if (item instanceof LnameCriterion) {
					query.append(" AND lastName = '").append(((LnameCriterion) item).getLname()).append("'");
				}
			}

			Vector<Client> clients = new Vector<>();
			try {
				PreparedStatement state = this.connection.prepareStatement(query.toString(), ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				ResultSet result = state.executeQuery();
				while (result.next()) {
					clients.add(new Client(
							result.getString("CIN"),
							result.getString("firstName"),
							result.getString("lastName")
					));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return clients;
		}
		return null;
	}

	public void finalize() throws Throwable {
		super.finalize();
	}
}