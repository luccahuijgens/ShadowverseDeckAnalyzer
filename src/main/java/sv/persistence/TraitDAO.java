package sv.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import sv.domain.Card;

public class TraitDAO extends BaseDAO {

	public TraitDAO() {
	}

	public Map<Card, Integer> getEarthSigilCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn
						.prepareStatement("select* from cards where card_id=? AND tribe_name='Earth Sigil'");
				stmt.setInt(1, c.getCard_id());
				ResultSet results = stmt.executeQuery();
				boolean isAdded = false;
				if (results.next()) {
					boolean isPresent = false;
					for (Map.Entry<Card, Integer> entry : cards.entrySet()) {
						if (entry.getKey().getCard_id() == c.getCard_id()) {
							cards.put(entry.getKey(), entry.getValue() + 1);
							isPresent = true;
						}
					}
					if (isPresent == false) {
						cards.put(c, 1);
					}
					isAdded = true;
				}
				boolean hasApplicableTokens = false;
				if (c.getTokens() != null) {
					List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
					for (String s : items) {
						stmt = conn.prepareStatement("select* from cards where card_id=? AND tribe_name='Earth Sigil'");
						stmt.setInt(1, Integer.parseInt(s));
						ResultSet tokenresults = stmt.executeQuery();
						if (tokenresults.next() && hasApplicableTokens == false) {
							hasApplicableTokens = true;
						}
					}
				}
				if (hasApplicableTokens == true && isAdded == false) {
					boolean isPresent = false;
					for (Map.Entry<Card, Integer> entry : cards.entrySet()) {
						if (entry.getKey().getCard_id() == c.getCard_id()) {
							cards.put(entry.getKey(), entry.getValue() + 1);
							isPresent = true;
						}
					}
					if (isPresent == false) {
						cards.put(c, 1);
					}
				}
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cards;
	}

	public Map<Card, Integer> getLootCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn
						.prepareStatement("select* from cards where card_id=? AND tribe_name='Loot'");
				stmt.setInt(1, c.getCard_id());
				ResultSet results = stmt.executeQuery();
				boolean isAdded = false;
				if (results.next()) {
					boolean isPresent = false;
					for (Map.Entry<Card, Integer> entry : cards.entrySet()) {
						if (entry.getKey().getCard_id() == c.getCard_id()) {
							cards.put(entry.getKey(), entry.getValue() + 1);
							isPresent = true;
						}
					}
					if (isPresent == false) {
						cards.put(c, 1);
					}
					isAdded = true;
				}
				boolean hasApplicableTokens = false;
				if (c.getTokens() != null) {
					List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
					for (String s : items) {
						stmt = conn.prepareStatement("select* from cards where card_id=? AND tribe_name='Loot'");
						stmt.setInt(1, Integer.parseInt(s));
						ResultSet tokenresults = stmt.executeQuery();
						if (tokenresults.next() && hasApplicableTokens == false) {
							hasApplicableTokens = true;
						}
					}
				}
				if (hasApplicableTokens == true && isAdded == false) {
					boolean isPresent = false;
					for (Map.Entry<Card, Integer> entry : cards.entrySet()) {
						if (entry.getKey().getCard_id() == c.getCard_id()) {
							cards.put(entry.getKey(), entry.getValue() + 1);
							isPresent = true;
						}
					}
					if (isPresent == false) {
						cards.put(c, 1);
					}
				}
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cards;
	}

	public Map<Card, Integer> getArtifactCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn
						.prepareStatement("select* from cards where card_id=? AND tribe_name='Artifact'");
				stmt.setInt(1, c.getCard_id());
				ResultSet results = stmt.executeQuery();
				boolean isAdded = false;
				if (results.next()) {
					boolean isPresent = false;
					for (Map.Entry<Card, Integer> entry : cards.entrySet()) {
						if (entry.getKey().getCard_id() == c.getCard_id()) {
							cards.put(entry.getKey(), entry.getValue() + 1);
							isPresent = true;
						}
					}
					if (isPresent == false) {
						cards.put(c, 1);
					}
					isAdded = true;
				}
				boolean hasApplicableTokens = false;
				if (c.getTokens() != null) {
					List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
					for (String s : items) {
						stmt = conn.prepareStatement("select* from cards where card_id=? AND tribe_name='Artifact'");
						stmt.setInt(1, Integer.parseInt(s));
						ResultSet tokenresults = stmt.executeQuery();
						if (tokenresults.next() && hasApplicableTokens == false) {
							hasApplicableTokens = true;
						}
					}
				}
				if (hasApplicableTokens == true && isAdded == false) {
					boolean isPresent = false;
					for (Map.Entry<Card, Integer> entry : cards.entrySet()) {
						if (entry.getKey().getCard_id() == c.getCard_id()) {
							cards.put(entry.getKey(), entry.getValue() + 1);
							isPresent = true;
						}
					}
					if (isPresent == false) {
						cards.put(c, 1);
					}
				}
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cards;
	}
	
	public Map<Card, Integer> getMachinaCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn
						.prepareStatement("select* from cards where card_id=? AND tribe_name='Machina'");
				stmt.setInt(1, c.getCard_id());
				ResultSet results = stmt.executeQuery();
				boolean isAdded = false;
				if (results.next()) {
					boolean isPresent = false;
					for (Map.Entry<Card, Integer> entry : cards.entrySet()) {
						if (entry.getKey().getCard_id() == c.getCard_id()) {
							cards.put(entry.getKey(), entry.getValue() + 1);
							isPresent = true;
						}
					}
					if (isPresent == false) {
						cards.put(c, 1);
					}
					isAdded = true;
				}
				boolean hasApplicableTokens = false;
				if (c.getTokens() != null) {
					List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
					for (String s : items) {
						stmt = conn.prepareStatement("select* from cards where card_id=? AND tribe_name='Machina'");
						stmt.setInt(1, Integer.parseInt(s));
						ResultSet tokenresults = stmt.executeQuery();
						if (tokenresults.next() && hasApplicableTokens == false) {
							hasApplicableTokens = true;
						}
					}
				}
				if (hasApplicableTokens == true && isAdded == false) {
					boolean isPresent = false;
					for (Map.Entry<Card, Integer> entry : cards.entrySet()) {
						if (entry.getKey().getCard_id() == c.getCard_id()) {
							cards.put(entry.getKey(), entry.getValue() + 1);
							isPresent = true;
						}
					}
					if (isPresent == false) {
						cards.put(c, 1);
					}
				}
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cards;
	}

	public Map<Card, Integer> getMysteriaCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn
						.prepareStatement("select* from cards where card_id=? AND tribe_name='Mysteria'");
				stmt.setInt(1, c.getCard_id());
				ResultSet results = stmt.executeQuery();
				boolean isAdded = false;
				if (results.next()) {
					boolean isPresent = false;
					for (Map.Entry<Card, Integer> entry : cards.entrySet()) {
						if (entry.getKey().getCard_id() == c.getCard_id()) {
							cards.put(entry.getKey(), entry.getValue() + 1);
							isPresent = true;
						}
					}
					if (isPresent == false) {
						cards.put(c, 1);
					}
					isAdded = true;
				}
				boolean hasApplicableTokens = false;
				if (c.getTokens() != null) {
					List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
					for (String s : items) {
						stmt = conn.prepareStatement("select* from cards where card_id=? AND tribe_name='Mysteria'");
						stmt.setInt(1, Integer.parseInt(s));
						ResultSet tokenresults = stmt.executeQuery();
						if (tokenresults.next() && hasApplicableTokens == false) {
							hasApplicableTokens = true;
						}
					}
				}
				if (hasApplicableTokens == true && isAdded == false) {
					boolean isPresent = false;
					for (Map.Entry<Card, Integer> entry : cards.entrySet()) {
						if (entry.getKey().getCard_id() == c.getCard_id()) {
							cards.put(entry.getKey(), entry.getValue() + 1);
							isPresent = true;
						}
					}
					if (isPresent == false) {
						cards.put(c, 1);
					}
				}
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cards;
	}

	public Map<Card, Integer> getOfficerCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn
						.prepareStatement("select* from cards where card_id=? AND tribe_name='Officer'");
				stmt.setInt(1, c.getCard_id());
				ResultSet results = stmt.executeQuery();
				boolean isAdded = false;
				if (results.next()) {
					boolean isPresent = false;
					for (Map.Entry<Card, Integer> entry : cards.entrySet()) {
						if (entry.getKey().getCard_id() == c.getCard_id()) {
							cards.put(entry.getKey(), entry.getValue() + 1);
							isPresent = true;
						}
					}
					if (isPresent == false) {
						cards.put(c, 1);
					}
					isAdded = true;
				}
				boolean hasApplicableTokens = false;
				if (c.getTokens() != null) {
					List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
					for (String s : items) {
						stmt = conn.prepareStatement("select* from cards where card_id=? AND tribe_name='Officer'");
						stmt.setInt(1, Integer.parseInt(s));
						ResultSet tokenresults = stmt.executeQuery();
						if (tokenresults.next() && hasApplicableTokens == false) {
							hasApplicableTokens = true;
						}
					}
				}
				if (hasApplicableTokens == true && isAdded == false) {
					boolean isPresent = false;
					for (Map.Entry<Card, Integer> entry : cards.entrySet()) {
						if (entry.getKey().getCard_id() == c.getCard_id()) {
							cards.put(entry.getKey(), entry.getValue() + 1);
							isPresent = true;
						}
					}
					if (isPresent == false) {
						cards.put(c, 1);
					}
				}
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cards;
	}

	public Map<Card, Integer> getCommanderCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn
						.prepareStatement("select* from cards where card_id=? AND tribe_name='Commander'");
				stmt.setInt(1, c.getCard_id());
				ResultSet results = stmt.executeQuery();
				boolean isAdded = false;
				if (results.next()) {
					boolean isPresent = false;
					for (Map.Entry<Card, Integer> entry : cards.entrySet()) {
						if (entry.getKey().getCard_id() == c.getCard_id()) {
							cards.put(entry.getKey(), entry.getValue() + 1);
							isPresent = true;
						}
					}
					if (isPresent == false) {
						cards.put(c, 1);
					}
					isAdded = true;
				}
				boolean hasApplicableTokens = false;
				if (c.getTokens() != null) {
					List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
					for (String s : items) {
						stmt = conn.prepareStatement("select* from cards where card_id=? AND tribe_name='Commander'");
						stmt.setInt(1, Integer.parseInt(s));
						ResultSet tokenresults = stmt.executeQuery();
						if (tokenresults.next() && hasApplicableTokens == false) {
							hasApplicableTokens = true;
						}
					}
				}
				if (hasApplicableTokens == true && isAdded == false) {
					boolean isPresent = false;
					for (Map.Entry<Card, Integer> entry : cards.entrySet()) {
						if (entry.getKey().getCard_id() == c.getCard_id()) {
							cards.put(entry.getKey(), entry.getValue() + 1);
							isPresent = true;
						}
					}
					if (isPresent == false) {
						cards.put(c, 1);
					}
				}
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cards;
	}
}
