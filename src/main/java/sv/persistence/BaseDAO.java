package sv.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import sv.domain.Card;

public class BaseDAO {

	protected final Connection getConnection() {
		Connection result = null;
		try {
			InitialContext ic = new InitialContext();
			DataSource ds = (DataSource) ic.lookup("java:comp/env/jdbc/PostgresDS");
			result = ds.getConnection();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return result;
	}

	protected void checkAlreadyPresent(Map<Card, Integer> cards, Card c) {
		boolean isPresent = false;
		for (Map.Entry<Card, Integer> entry : cards.entrySet()) {
			if (entry.getKey().getCardID() == c.getCardID()) {
				cards.put(entry.getKey(), entry.getValue() + 1);
				isPresent = true;
			}
		}
		if (!isPresent) {
			cards.put(c, 1);
		}
	}

	protected Map<Card, Integer> getCardsFromStatement(String statement, List<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<>();
		try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(statement)) {
			for (Card c : deck) {
				boolean isAdded = false;
				stmt.setInt(1, c.getCardID());
				checkCardApplicability(cards, stmt, c, isAdded);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();

		}
		return cards;

	}

	private void checkCardApplicability(Map<Card, Integer> cards, PreparedStatement stmt, Card c, boolean isAdded) {
		try (ResultSet results = stmt.executeQuery()) {
			if (results.next()) {
				checkAlreadyPresent(cards, c);
				isAdded = true;
			}
			boolean hasApplicableTokens = checkApplicableTokens(stmt, c);
			if (hasApplicableTokens && !isAdded) {
				checkAlreadyPresent(cards, c);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected boolean checkApplicableTokens(PreparedStatement stmt, Card c) throws SQLException {
		boolean result = false;
		if (c.getTokens() != null) {
			List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
			for (String s : items) {
				stmt.setInt(1, Integer.parseInt(s));
				try (ResultSet tokenresults = stmt.executeQuery()) {
					if (tokenresults.next() && !result) {
						result = true;
					}
				} catch (SQLException e) {
					throw new SQLException(e);
				}
			}
		}
		return result;
	}
}
