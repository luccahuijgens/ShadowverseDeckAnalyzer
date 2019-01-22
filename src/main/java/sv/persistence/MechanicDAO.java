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

public class MechanicDAO extends BaseDAO {
	private String removalExceptions = "110521020";
	private String healExceptions = "108732010";

	public MechanicDAO() {
	}

	public Map<Card, Integer> getFriendlyFireCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn.prepareStatement(
						"select* from cards where card_id=? AND (skill_disc like '%banish an allied%') or (skill_disc like '%damage to an allied%') or (skill_disc like '%destroy an allied%') OR (skill_disc like '%damage to a follower%') or ((skill_disc like '%damage to a follower%') AND (skill_disc like '%to that follower%'))");
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
								"select* from cards where card_id=? AND (skill_disc like '%banish an allied%') or (skill_disc like '%damage to an allied%') or (skill_disc like '%destroy an allied%') OR (skill_disc like '%damage to a follower%') or ((skill_disc like '%damage to a follower%') AND (skill_disc like '%to that follower%'))");
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

	public Map<Card, Integer> getRemovalCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn.prepareStatement("select* from cards where card_id=? AND card_id not in ("
						+ removalExceptions
						+ ") AND (((skill_disc like '%gain Rush%' or (skill_disc like '%gain%' AND (skill_disc like '%or Rush%' or skill_disc like '%, Rush,%' or skill_disc like '%and Rush.%')) or skill_disc like '%Rush.<br>%' or skill_disc like '%Rush. <br>%' or skill_disc='Rush.' or (skill_disc like '%give%' and skill_disc like '%Rush%' and (skill_disc like '%all allied%' or skill_disc like '%other%')) or (skill_disc like '%give%' and skill_disc like '%Rush%' and skill_disc like '%it%')) or (evo_skill_disc like '%gain Rush%' or (evo_skill_disc like '%gain%' AND (evo_skill_disc like '%or Rush%' or evo_skill_disc like '%, Rush,%' or evo_skill_disc like '%and Rush.%')) or evo_skill_disc like '%Rush.<br>%' or evo_skill_disc like '%Rush. <br>%' or evo_skill_disc='Rush.' or (evo_skill_disc like '%give%' and evo_skill_disc like '%Rush%' and (evo_skill_disc like '%all allied%' or evo_skill_disc like '%other%')) or (evo_skill_disc like '%give%' and evo_skill_disc like '%Rush%' and evo_skill_disc like '%it%'))) OR ((skill_disc like '%gain Storm%' or skill_disc='Storm.' or skill_disc like '%Storm. <br>%' or skill_disc like '%Storm.<br>%' or (skill_disc like '%gain%' and skill_disc like '%Storm%')) or (evo_skill_disc like '%gain Storm%' or evo_skill_disc='Storm.' or evo_skill_disc like '%Storm. <br>%' or evo_skill_disc like '%Storm.<br>%' or (evo_skill_disc like '%gain%' and evo_skill_disc like '%Storm%'))) OR (((skill_disc like '%Bane%' and (skill_disc like '%Rush%' or skill_disc like '%Storm%')) or (skill_disc like '%destroy%' OR skill_disc like '%Destroy%') OR\r\n"
						+ "(skill_disc like '%Banish%' OR skill_disc like '%banish%') OR skill_disc like '%damage to%') and\r\n"
						+ "(((skill_disc like '%damage to your leader%' or skill_disc like 'to the enemy leader') AND (skill_disc like '%enemy follower%' OR skill_disc like '%all enemies%' OR skill_disc like '%all followers%' OR skill_disc like '%an enemy%')) or\r\n"
						+ "((skill_disc not like '%damage to your leader%' or skill_disc not like 'to the enemy leader') AND (skill_disc like '%enemy follower%' OR skill_disc like '%all enemies%' OR skill_disc like '%all followers%' OR skill_disc like '%an enemy%')))))");
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
						stmt = conn.prepareStatement("select* from cards where card_id=? AND card_id not in ("
								+ removalExceptions
								+ ") AND (((skill_disc like '%gain Rush%' or (skill_disc like '%gain%' AND (skill_disc like '%or Rush%' or skill_disc like '%, Rush,%' or skill_disc like '%and Rush.%')) or skill_disc like '%Rush.<br>%' or skill_disc like '%Rush. <br>%' or skill_disc='Rush.' or (skill_disc like '%give%' and skill_disc like '%Rush%' and (skill_disc like '%all allied%' or skill_disc like '%other%')) or (skill_disc like '%give%' and skill_disc like '%Rush%' and skill_disc like '%it%')) or (evo_skill_disc like '%gain Rush%' or (evo_skill_disc like '%gain%' AND (evo_skill_disc like '%or Rush%' or evo_skill_disc like '%, Rush,%' or evo_skill_disc like '%and Rush.%')) or evo_skill_disc like '%Rush.<br>%' or evo_skill_disc like '%Rush. <br>%' or evo_skill_disc='Rush.' or (evo_skill_disc like '%give%' and evo_skill_disc like '%Rush%' and (evo_skill_disc like '%all allied%' or evo_skill_disc like '%other%')) or (evo_skill_disc like '%give%' and evo_skill_disc like '%Rush%' and evo_skill_disc like '%it%'))) OR ((skill_disc like '%gain Storm%' or skill_disc='Storm.' or skill_disc like '%Storm. <br>%' or skill_disc like '%Storm.<br>%' or (skill_disc like '%gain%' and skill_disc like '%Storm%')) or (evo_skill_disc like '%gain Storm%' or evo_skill_disc='Storm.' or evo_skill_disc like '%Storm. <br>%' or evo_skill_disc like '%Storm.<br>%' or (evo_skill_disc like '%gain%' and evo_skill_disc like '%Storm%'))) OR (((skill_disc like '%Bane%' and (skill_disc like '%Rush%' or skill_disc like '%Storm%')) or (skill_disc like '%destroy%' OR skill_disc like '%Destroy%') OR\r\n"
								+ "(skill_disc like '%Banish%' OR skill_disc like '%banish%') OR skill_disc like '%damage to%') and\r\n"
								+ "(((skill_disc like '%damage to your leader%' or skill_disc like 'to the enemy leader') AND (skill_disc like '%enemy follower%' OR skill_disc like '%all enemies%' OR skill_disc like '%all followers%' OR skill_disc like '%an enemy%')) or\r\n"
								+ "((skill_disc not like '%damage to your leader%' or skill_disc not like 'to the enemy leader') AND (skill_disc like '%enemy follower%' OR skill_disc like '%all enemies%' OR skill_disc like '%all followers%' OR skill_disc like '%an enemy%')))))");
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

	public Map<Card, Integer> getSubtractionCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn.prepareStatement(
						"select* from cards where card_id=? AND clan=7 AND (skill_disc like '%Countdown%' and skill_disc like '%Subtract%' and (skill_disc like '%allied amulet%' or skill_disc like '%that%')) or (evo_skill_disc like '%Countdown%' and evo_skill_disc like '%Subtract%' and (evo_skill_disc like '%allied amulet%' or evo_skill_disc like '%that%'))");
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
								"select* from cards where card_id=? AND clan=7 AND (skill_disc like '%Countdown%' and skill_disc like '%Subtract%' and (skill_disc like '%allied amulet%' or skill_disc like '%that%')) or (evo_skill_disc like '%Countdown%' and evo_skill_disc like '%Subtract%' and (evo_skill_disc like '%allied amulet%' or evo_skill_disc like '%that%'))");
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

	public Map<Card, Integer> getSummonerCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn.prepareStatement(
						"select* from cards where card_id=? AND ((skill_disc like '%Summon%' or skill_disc like '%summon%') OR (evo_skill_disc like '%Summon%' or evo_skill_disc like '%summon%'))");
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
					if (!items.contains(String.valueOf(c.getCard_id()))) {
						for (String s : items) {
							if (Integer.parseInt(s) != c.getCard_id()) {
								stmt = conn.prepareStatement(
										"select* from cards where card_id=? AND ((skill_disc like '%Summon%' or skill_disc like '%summon%') OR (evo_skill_disc like '%Summon%' or evo_skill_disc like '%summon%'))");
								stmt.setInt(1, Integer.parseInt(s));
								ResultSet tokenresults = stmt.executeQuery();
								if (tokenresults.next() && hasApplicableTokens == false) {
									hasApplicableTokens = true;
								}
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
				}
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cards;
	}

	public Map<Card, Integer> getTutorCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn.prepareStatement(
						"select* from cards where card_id=? AND ((skill_disc like '%from your deck%' AND (skill_disc like '%into your hand%' or skill_disc like '%to your hand%')) OR (evo_skill_disc like '%from your deck%' AND (evo_skill_disc like '%to your hand%' or evo_skill_disc like '%into your hand%')))");
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
								"select* from cards where card_id=? AND ((skill_disc like '%from your deck%' AND (skill_disc like '%into your hand%' or skill_disc like '%to your hand%')) OR (evo_skill_disc like '%from your deck%' AND (evo_skill_disc like '%to your hand%' or evo_skill_disc like '%into your hand%')))");
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

	public Map<Card, Integer> getAoERemovalCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn.prepareStatement(
						"select* from cards where card_id=? AND ((skill_disc like '%destroy%' OR skill_disc like '%Destroy%') OR (skill_disc like '%Banish%' OR skill_disc like '%Banish%') OR skill_disc like '%damage to%') and skill_disc not like '%no enemy followers in play%' and (skill_disc like '%all enemies%' OR skill_disc like '%all followers%' OR skill_disc like '%all enemy followers%' OR (skill_disc like '%time%' AND skill_disc like '%enemy follower%'))");
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
								"select* from cards where card_id=? AND ((skill_disc like '%destroy%' OR skill_disc like '%Destroy%') OR (skill_disc like '%Banish%' OR skill_disc like '%Banish%') OR skill_disc like '%damage to%') and skill_disc not like '%no enemy followers in play%' and (skill_disc like '%all enemies%' OR skill_disc like '%all followers%' OR skill_disc like '%all enemy followers%' OR (skill_disc like '%time%' AND skill_disc like '%enemy follower%'))");
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

	public Map<Card, Integer> getBoardBuildingCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn.prepareStatement(
						"select* from cards where card_id=? AND (((skill_disc like '%Summon%' or skill_disc like '%summon%') OR (evo_skill_disc like '%Summon%' or evo_skill_disc like '%summon%')) OR tokens is not null)");
				stmt.setInt(1, c.getCard_id());
				ResultSet results = stmt.executeQuery();
				boolean doesItSummon = false;
				boolean isAdded = false;
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
					isAdded = true;
				} else if (results.next()) {
					doesItSummon = true;
				}
				boolean hasApplicableTokens = false;
				if (c.getTokens() != null && doesItSummon == true) {
					List<String> items = Arrays.asList(c.getTokens().split("\\s*,\\s*"));
					for (String s : items) {
						stmt = conn.prepareStatement("select* from cards where card_id=? AND char_type=1");
						stmt.setInt(1, Integer.parseInt(s));
						ResultSet summonresults = stmt.executeQuery();
						if (!summonresults.next()) {
							stmt = conn.prepareStatement(
									"select* from cards where card_id=? and ((skill_disc like '%Summon%' or skill_disc like '%summon%') OR (evo_skill_disc like '%Summon%' or evo_skill_disc like '%summon%'))");
							stmt.setInt(1, Integer.parseInt(s));
							ResultSet tokenresults = stmt.executeQuery();
							if ((tokenresults.next())) {
								if (tokenresults.getString("tokens") != null) {
									List<String> tokenitems = Arrays
											.asList(tokenresults.getString("tokens").split("\\s*,\\s*"));
									for (String token_s : tokenitems) {

										stmt = conn
												.prepareStatement("select* from cards where card_id=? AND char_type=1");
										stmt.setInt(1, Integer.parseInt(token_s));
										ResultSet tokentokenresults = stmt.executeQuery();
										if (tokentokenresults.next() && hasApplicableTokens == false) {
											hasApplicableTokens = true;
										}
									}
								}
							}
						} else if (hasApplicableTokens == false) {
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

	public Map<Card, Integer> getBounceCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn.prepareStatement(
						"select* from cards where card_id=? AND clan=1 AND (((skill_disc like '%return%') and (skill_disc not like '% in return%') and skill_disc not like '%is returned%') or ((evo_skill_disc like '%return%') and (evo_skill_disc not like '% in return%') and clan=1 and evo_skill_disc not like '%is returned%'))");
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
								"select* from cards where card_id=? AND clan=1 AND (((skill_disc like '%return%') and (skill_disc not like '% in return%') and skill_disc not like '%is returned%') or ((evo_skill_disc like '%return%') and (evo_skill_disc not like '% in return%') and clan=1 and evo_skill_disc not like '%is returned%'))");
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

	public Map<Card, Integer> getBuffCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn.prepareStatement(
						"select* from cards where card_id=? AND ((skill_disc like '%give%' and skill_disc like '%/%' and (skill_disc like '%in your hand%' or (skill_disc like '%a follower%' or skill_disc like '%allied%'or skill_disc like '%other%'))) or (evo_skill_disc like '%give%' and evo_skill_disc like '%/%' and (evo_skill_disc like '%in your hand%' or (evo_skill_disc like '%a follower%' or evo_skill_disc like '%allied%'or evo_skill_disc like '%other%'))))");
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
								"select* from cards where card_id=? AND ((skill_disc like '%give%' and skill_disc like '%/%' and (skill_disc like '%in your hand%' or (skill_disc like '%a follower%' or skill_disc like '%allied%'or skill_disc like '%other%'))) or (evo_skill_disc like '%give%' and evo_skill_disc like '%/%' and (evo_skill_disc like '%in your hand%' or (evo_skill_disc like '%a follower%' or evo_skill_disc like '%allied%'or evo_skill_disc like '%other%'))))");
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

	public Map<Card, Integer> getBurnCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn.prepareStatement(
						"select* from cards where card_id=? AND (((evo_skill_disc like '%Storm%') OR (evo_skill_disc like '%damage to all enemies%' OR evo_skill_disc like '% damage to an enemy.%' or evo_skill_disc like '%damage to the enemy leader%')) OR ((skill_disc like '%Storm%') OR (skill_disc like '%damage to all enemies%' OR skill_disc like '% damage to an enemy.%' or skill_disc like '%damage to the enemy leader%')))");
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
					if (!items.contains(String.valueOf(c.getCard_id()))) {
						for (String s : items) {
							stmt = conn.prepareStatement(
									"select* from cards where card_id=? AND (((evo_skill_disc like '%Storm%') OR (evo_skill_disc like '%damage to all enemies%' OR evo_skill_disc like '% damage to an enemy.%' or evo_skill_disc like '%damage to the enemy leader%')) OR ((skill_disc like '%Storm%') OR (skill_disc like '%damage to all enemies%' OR skill_disc like '% damage to an enemy.%' or skill_disc like '%damage to the enemy leader%')))");
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
				}
				stmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cards;
	}

	public Map<Card, Integer> getUntargatableCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn.prepareStatement(
						"select* from cards where card_id=? AND ((skill_disc like '%can''t be targeted by %' or skill_disc like '%gain resistance to targeted%') or (evo_skill_disc like '%can''t be targeted by %' or evo_skill_disc like '%gain resistance to targeted%'))");
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
								"select* from cards where card_id=? AND ((skill_disc like '%can''t be targeted by %' or skill_disc like '%gain resistance to targeted%') or (evo_skill_disc like '%can''t be targeted by %' or evo_skill_disc like '%gain resistance to targeted%'))");
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

	public Map<Card, Integer> getUndestroyableCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn.prepareStatement(
						"select* from cards where card_id=? AND (skill_disc like '%can\\t be destroyed by%' or evo_skill_disc like '%can''t be destroyed by%')");
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
								"skill_disc like '%can\\t be destroyed by%' or evo_skill_disc like '%can''t be destroyed by%')");
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

	public Map<Card, Integer> getUndamageableCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn.prepareStatement(
						"select* from cards where card_id=? AND (skill_disc like '%Reduce damage from spells and effects%' or evo_skill_disc like '%Reduce damage from spells and effects%')");
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
								"select* from cards where card_id=? AND (skill_disc like '%Reduce damage from spells and effects%' or evo_skill_disc like '%Reduce damage from spells and effects%')");
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

	public Map<Card, Integer> getEvoPointCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn.prepareStatement(
						"select* from cards where card_id=? AND (skill_disc like '%evolution point%' or evo_skill_disc like '%evolution point%')");
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
								"select* from cards where card_id=? AND (skill_disc like '%evolution point%' or evo_skill_disc like '%evolution point%')");
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

	public Map<Card, Integer> getResourceCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn.prepareStatement(
						"select* from cards where card_id=? AND (((skill_disc like '%put%' and skill_disc like '%your hand%' and skill_disc not like '%from your deck%') or (skill_disc like '%put%' and (skill_disc like '%into your deck%' OR skill_disc like '%in your deck%')) OR (evo_skill_disc like '%put%' and evo_skill_disc like '%your hand%' and evo_skill_disc not like '%from your deck%') or (skill_disc like '%put%' and (evo_skill_disc like '%into your deck%' OR evo_skill_disc like '%in your deck%'))) OR (((skill_disc like '%draw%' or skill_disc like '%Draw%')) OR (evo_skill_disc like '%from your deck%' AND (evo_skill_disc like '%draw%' or evo_skill_disc like '%Draw%')) OR ((skill_disc like '%from your deck%' AND (skill_disc like '%into your hand%' or skill_disc like '%to your hand%')) OR (evo_skill_disc like '%from your deck%' AND (evo_skill_disc like '%to your hand%' or evo_skill_disc like '%into your hand%')))))");
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
								"select* from cards where card_id=? AND (((skill_disc like '%put%' and skill_disc like '%your hand%' and skill_disc not like '%from your deck%') or (skill_disc like '%put%' and (skill_disc like '%into your deck%' OR skill_disc like '%in your deck%')) OR (evo_skill_disc like '%put%' and evo_skill_disc like '%your hand%' and evo_skill_disc not like '%from your deck%') or (skill_disc like '%put%' and (evo_skill_disc like '%into your deck%' OR evo_skill_disc like '%in your deck%'))) OR (((skill_disc like '%draw%' or skill_disc like '%Draw%')) OR (evo_skill_disc like '%from your deck%' AND (evo_skill_disc like '%draw%' or evo_skill_disc like '%Draw%')) OR ((skill_disc like '%from your deck%' AND (skill_disc like '%into your hand%' or skill_disc like '%to your hand%')) OR (evo_skill_disc like '%from your deck%' AND (evo_skill_disc like '%to your hand%' or evo_skill_disc like '%into your hand%')))))");
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

	public Map<Card, Integer> getGenerationCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn.prepareStatement(
						"select* from svcards.cards where card_id=? AND ((skill_disc like '%put%' and skill_disc like '%your hand%' and skill_disc not like '%from your deck%') or (skill_disc like '%put%' and (skill_disc like '%into your deck%' OR skill_disc like '%in your deck%')) OR (evo_skill_disc like '%put%' and evo_skill_disc like '%your hand%' and evo_skill_disc not like '%from your deck%') or (skill_disc like '%put%' and (evo_skill_disc like '%into your deck%' OR evo_skill_disc like '%in your deck%')))");
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
								"select* from svcards.cards where  card_id=? AND ((skill_disc like '%put%' and skill_disc like '%your hand%' and skill_disc not like '%from your deck%') or (skill_disc like '%put%' and (skill_disc like '%into your deck%' OR skill_disc like '%in your deck%')) OR (evo_skill_disc like '%put%' and evo_skill_disc like '%your hand%' and evo_skill_disc not like '%from your deck%') or (skill_disc like '%put%' and (evo_skill_disc like '%into your deck%' OR evo_skill_disc like '%in your deck%')))");
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

	public Map<Card, Integer> getPermanentCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn.prepareStatement(
						"select* from cards where card_id=? AND (skill_disc like '% lasts for the rest of the match%' or evo_skill_disc like '% lasts for the rest of the match%')");
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
								"select* from cards where card_id=? AND (skill_disc like '% lasts for the rest of the match%' or evo_skill_disc like '% lasts for the rest of the match%')");
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

	public Map<Card, Integer> getHealCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn.prepareStatement("select* from cards where card_id=? AND card_id not in ("
						+ healExceptions
						+ ") AND (skill_disc like '%Drain%' or evo_skill_disc like '%drain%' or skill_disc like '%restore%' and skill_disc like '%defense to%' or evo_skill_disc like '%restore%' and evo_skill_disc like '%defense to%')");
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
						stmt = conn.prepareStatement("select* from cards where card_id=? AND card_id not in ("
								+ healExceptions
								+ ") AND (skill_disc like '%Drain%' or evo_skill_disc like '%drain%' or skill_disc like '%restore%' and skill_disc like '%defense to%' or evo_skill_disc like '%restore%' and evo_skill_disc like '%defense to%')");
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

	public Map<Card, Integer> getDrawCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn.prepareStatement(
						"select* from cards where card_id=? AND (((skill_disc like '%draw%' or skill_disc like '%Draw%')) OR (evo_skill_disc like '%from your deck%' AND (evo_skill_disc like '%draw%' or evo_skill_disc like '%Draw%')) OR ((skill_disc like '%from your deck%' AND (skill_disc like '%into your hand%' or skill_disc like '%to your hand%')) OR (evo_skill_disc like '%from your deck%' AND (evo_skill_disc like '%to your hand%' or evo_skill_disc like '%into your hand%'))))");
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
								"select* from cards where card_id=? AND (((skill_disc like '%draw%' or skill_disc like '%Draw%')) OR (evo_skill_disc like '%from your deck%' AND (evo_skill_disc like '%draw%' or evo_skill_disc like '%Draw%')) OR ((skill_disc like '%from your deck%' AND (skill_disc like '%into your hand%' or skill_disc like '%to your hand%')) OR (evo_skill_disc like '%from your deck%' AND (evo_skill_disc like '%to your hand%' or evo_skill_disc like '%into your hand%'))))");
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

	public Map<Card, Integer> getDamageLimiterCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn.prepareStatement(
						"select* from cards where card_id=? AND (skill_disc like '%can''t take more than%' or evo_skill_disc like '%can''t take more than%')");
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
								"select* from cards where card_id=? AND (skill_disc like '%can''t take more than%' or evo_skill_disc like '%can''t take more than%')");
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

	public Map<Card, Integer> getUnattackableCardsFromDeck(ArrayList<Card> deck) {
		Map<Card, Integer> cards = new LinkedHashMap<Card, Integer>();
		for (Card c : deck) {
			try (Connection conn = getConnection()) {
				PreparedStatement stmt = conn.prepareStatement(
						"select* from cards where card_id=? AND (skill_disc like '%can''t take more than%' or evo_skill_disc like '%can''t take more than%')");
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
								"select* from cards where card_id=? AND (skill_disc like '%can''t take more than%' or evo_skill_disc like '%can''t take more than%')");
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
