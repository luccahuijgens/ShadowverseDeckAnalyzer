package sv.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import sv.domain.Card;

public class KeywordDAO extends BaseDAO {


	public KeywordDAO() {
	}



	public Map<Card, Integer> getAmuletCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			if (c.getChar_type() == 3 || c.getChar_type() == 2) {
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
		}
		return cards;
	}

	

	

	public Map<Card, Integer> getBurialRiteCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn.prepareStatement(
						"select* from cards where card_id=? AND ((skill_disc like '%Burial Rite: %' or skill_disc like '%Burial Rite -%') or (evo_skill_disc like '%Burial Rite: %' or evo_skill_disc like '%Burial Rite -%'))");
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
						stmt = conn.prepareStatement(
								"select* from cards where card_id=? AND ((skill_disc like '%Burial Rite: %' or skill_disc like '%Burial Rite -%') or (evo_skill_disc like '%Burial Rite: %' or evo_skill_disc like '%Burial Rite -%'))");
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

	public Map<Card, Integer> getCountdownCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn.prepareStatement("select* from cards where card_id=? AND char_type=3");
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
						stmt = conn.prepareStatement("select* from cards where card_id=? AND char_type=3");
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

	public Map<Card, Integer> getEvolveCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn
						.prepareStatement("select* from cards where card_id=? AND evo_skill_disc like '%Evolve:%'");
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
						stmt = conn.prepareStatement(
								"select* from cards where card_id=? AND evo_skill_disc like '%Evolve:%'");
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

	

	public Map<Card, Integer> getAmbushCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn.prepareStatement(
						"select* from cards where card_id=? AND (skill_disc like '%Ambush. <br>%' or skill_disc='Ambush.' or skill_disc like '%Ambush.<br>%' or skill_disc like '%<br>Ambush%' or (skill_disc like '%gain%' and skill_disc like '%ambush%')))");
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
						stmt = conn.prepareStatement(
								"select* from cards where card_id=? AND (skill_disc like '%Ambush. <br>%' or skill_disc='Ambush.' or skill_disc like '%Ambush.<br>%' or skill_disc like '%<br>Ambush%' or (skill_disc like '%gain%' and skill_disc like '%ambush%')))");
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

	public Map<Card, Integer> getWardCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn.prepareStatement(
						"select* from cards where card_id=? AND ((skill_disc like '%Ward. <br>%' or skill_disc like '%Gain Ward%' or skill_disc like '%Gains Ward%' or (skill_disc like '%Gain%' and (skill_disc like '%and Ward%' or skill_disc like '%or Ward%' or skill_disc like '%, Ward%')) or (skill_disc like '%Summon%' and skill_disc like '%Ward%' and skill_disc like '%Give%')or skill_disc like '%Ward.<br>%' or skill_disc like '%<br>Ward%' or skill_disc='Ward.') or  (evo_skill_disc like '%Ward. <br>%' or evo_skill_disc like '%Gain Ward%' or evo_skill_disc like '%Gains Ward%' or (evo_skill_disc like '%Gain%' and (evo_skill_disc like '%and Ward%' or evo_skill_disc like '%or Ward%' or evo_skill_disc like '%, Ward%')) or (evo_skill_disc like '%Summon%' and evo_skill_disc like '%Ward%' and evo_skill_disc like '%Give%')or evo_skill_disc like '%Ward.<br>%' or evo_skill_disc like '%<br>Ward%' or evo_skill_disc='Ward.'))");
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
						stmt = conn.prepareStatement(
								"select* from cards where card_id=? AND ((skill_disc like '%Ward. <br>%' or skill_disc like '%Gain Ward%' or skill_disc like '%Gains Ward%' or (skill_disc like '%Gain%' and (skill_disc like '%and Ward%' or skill_disc like '%or Ward%' or skill_disc like '%, Ward%')) or (skill_disc like '%Summon%' and skill_disc like '%Ward%' and skill_disc like '%Give%')or skill_disc like '%Ward.<br>%' or skill_disc like '%<br>Ward%' or skill_disc='Ward.') or  (evo_skill_disc like '%Ward. <br>%' or evo_skill_disc like '%Gain Ward%' or evo_skill_disc like '%Gains Ward%' or (evo_skill_disc like '%Gain%' and (evo_skill_disc like '%and Ward%' or evo_skill_disc like '%or Ward%' or evo_skill_disc like '%, Ward%')) or (evo_skill_disc like '%Summon%' and evo_skill_disc like '%Ward%' and evo_skill_disc like '%Give%')or evo_skill_disc like '%Ward.<br>%' or evo_skill_disc like '%<br>Ward%' or evo_skill_disc='Ward.'))");
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

	public Map<Card, Integer> getLastWordsCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn.prepareStatement(
						"select* from cards where card_id=? AND skill_disc like '%Last Words:%' or evo_skill_disc like '%Last Words:%'");
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
						stmt = conn.prepareStatement(
								"select* from cards where card_id=? AND skill_disc like '%Last Words:%' or evo_skill_disc like '%Last Words:%'");
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

	public Map<Card, Integer> getFanfareCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn
						.prepareStatement("select* from cards where card_id=? AND skill_disc like '%Fanfare:%'");
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
						stmt = conn.prepareStatement(
								"select* from cards where card_id=? AND skill_disc like '%Fanfare:%'");
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

	public Map<Card, Integer> getEnhanceCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn
						.prepareStatement("select* from cards where card_id=? AND skill_disc like '%Enhance (%'");
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
						stmt = conn.prepareStatement(
								"select* from cards where card_id=? AND skill_disc like '%Enhance (%'");
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

	public Map<Card, Integer> getAccelerateCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn
						.prepareStatement("select* from cards where card_id=? AND skill_disc like '%Accelerate (%'");
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
						stmt = conn.prepareStatement(
								"select* from cards where card_id=? AND skill_disc like '%Accelerate (%'");
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

	public Map<Card, Integer> getRushCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn.prepareStatement(
						"select* from cards where card_id=? AND (skill_disc like '%gain Rush%' or skill_disc like '%or Rush%' or skill_disc like '%, Rush,%' or skill_disc like '%and Rush.%' or skill_disc like '%Rush.<br>%' or skill_disc like '%Rush. <br>%' or skill_disc='Rush.' or (skill_disc like '%give%' and skill_disc like '%Rush%' and (skill_disc like '%all allied%' or skill_disc like '%other%')) or (skill_disc like '%give%' and skill_disc like '%Rush%' and skill_disc like '%it%')) or (evo_skill_disc like '%gain Rush%' or evo_skill_disc like '%or Rush%' or evo_skill_disc like '%, Rush,%' or evo_skill_disc like '%and Rush.%' or evo_skill_disc like '%Rush.<br>%' or evo_skill_disc like '%Rush. <br>%' or evo_skill_disc='Rush.' or (evo_skill_disc like '%give%' and evo_skill_disc like '%Rush%' and (evo_skill_disc like '%all allied%' or evo_skill_disc like '%other%')) or (evo_skill_disc like '%give%' and evo_skill_disc like '%Rush%' and evo_skill_disc like '%it%'))");
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
						stmt = conn.prepareStatement(
								"select* from cards where card_id=? AND (skill_disc like '%gain Rush%' or skill_disc like '%or Rush%' or skill_disc like '%, Rush,%' or skill_disc like '%and Rush.%' or skill_disc like '%Rush.<br>%' or skill_disc like '%Rush. <br>%' or skill_disc='Rush.' or (skill_disc like '%give%' and skill_disc like '%Rush%' and (skill_disc like '%all allied%' or skill_disc like '%other%')) or (skill_disc like '%give%' and skill_disc like '%Rush%' and skill_disc like '%it%')) or (evo_skill_disc like '%gain Rush%' or evo_skill_disc like '%or Rush%' or evo_skill_disc like '%, Rush,%' or evo_skill_disc like '%and Rush.%' or evo_skill_disc like '%Rush.<br>%' or evo_skill_disc like '%Rush. <br>%' or evo_skill_disc='Rush.' or (evo_skill_disc like '%give%' and evo_skill_disc like '%Rush%' and (evo_skill_disc like '%all allied%' or evo_skill_disc like '%other%')) or (evo_skill_disc like '%give%' and evo_skill_disc like '%Rush%' and evo_skill_disc like '%it%'))");
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

	public Map<Card, Integer> getStormCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn.prepareStatement(
						"select* from cards where card_id=? AND  where (skill_disc like '%gain Storm%' or skill_disc='Storm.' or skill_disc like '%Storm. <br>%' or skill_disc like '%Storm.<br>%' or (skill_disc like '%gain%' and skill_disc like '%Storm%')) or (evo_skill_disc like '%gain Storm%' or evo_skill_disc='Storm.' or evo_skill_disc like '%Storm. <br>%' or evo_skill_disc like '%Storm.<br>%' or (evo_skill_disc like '%gain%' and evo_skill_disc like '%Storm%'))");
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
						stmt = conn.prepareStatement(
								"select* from cards where card_id=? AND select* from cards where (skill_disc like '%gain Rush%' or skill_disc like '%or Rush%' or skill_disc like '%, Rush,%' or skill_disc like '%and Rush.%' or skill_disc like '%Rush.<br>%' or skill_disc like '%Rush. <br>%' or skill_disc='Rush.' or (skill_disc like '%give%' and skill_disc like '%Rush%' and (skill_disc like '%all allied%' or skill_disc like '%other%')) or (skill_disc like '%give%' and skill_disc like '%Rush%' and skill_disc like '%it%')) or (evo_skill_disc like '%gain Rush%' or evo_skill_disc like '%or Rush%' or evo_skill_disc like '%, Rush,%' or evo_skill_disc like '%and Rush.%' or evo_skill_disc like '%Rush.<br>%' or evo_skill_disc like '%Rush. <br>%' or evo_skill_disc='Rush.' or (evo_skill_disc like '%give%' and evo_skill_disc like '%Rush%' and (evo_skill_disc like '%all allied%' or evo_skill_disc like '%other%')) or (evo_skill_disc like '%give%' and evo_skill_disc like '%Rush%' and evo_skill_disc like '%it%'))");
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

	

	

	public Map<Card, Integer> getFollowerCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			if (c.getChar_type() == 1) {
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
		}
		return cards;
	}

	

	public Map<Card, Integer> getInvocationCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn
						.prepareStatement("select* from cards where card_id=? AND (skill_disc like '%Invocation:%')");
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
						stmt = conn.prepareStatement(
								"select* from cards where card_id=? AND (skill_disc like '%Invocation:%')");
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

	

	

	

	// tribes
	

	public Map<Card, Integer> getNecromancyCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn.prepareStatement(
						"select* from cards where card_id=? AND (skill_disc like '%Necromancy (%' or evo_skill_disc like '%Necromancy (%')");
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
						stmt = conn.prepareStatement(
								"select* from cards where card_id=? AND (skill_disc like '%Necromancy (%' or evo_skill_disc like '%Necromancy (%')");
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

	

	public Map<Card, Integer> getReanimateCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn.prepareStatement(
						"select* from cards where card_id=? AND (skill_disc like '%Reanimate (%' or evo_skill_disc like '%Reanimate (%' )");
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
						stmt = conn.prepareStatement(
								"select* from cards where card_id=? AND (skill_disc like '%Reanimate (%' or evo_skill_disc like '%Reanimate (%' )");
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
	
	public Map<Card, Integer> getSpellboostCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn.prepareStatement(
						"select* from cards where card_id=? AND (skill_disc like '%this card has been spellboosted%' or evo_skill_disc like '%this card has been spellboosted%' or skill_disc like '%spellboost:%' or evo_skill_disc like '%spellboost:%')");
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
						stmt = conn.prepareStatement(
								"select* from cards where card_id=? AND (skill_disc like '%this card has been spellboosted%' or evo_skill_disc like '%this card has been spellboosted%' or skill_disc like '%spellboost:%' or evo_skill_disc like '%spellboost:%')");
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

	

	public Map<Card, Integer> getSpellCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			if (c.getChar_type() == 4) {
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
		}
		return cards;
	}
}
