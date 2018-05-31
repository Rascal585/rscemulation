/**
* Generated By NPCScript :: A scripting engine created for RSCEmulation by Zilent
*/
package org.rscemulation.server.npchandler.Goblin_Diplomacy;

import org.rscemulation.server.event.SingleEvent;
import org.rscemulation.server.logging.Logger;
import org.rscemulation.server.logging.model.eventLog;
import org.rscemulation.server.model.Npc;
import org.rscemulation.server.model.ChatMessage;
import org.rscemulation.server.model.MenuHandler;
import org.rscemulation.server.model.World;
import org.rscemulation.server.event.DelayedQuestChat;
import org.rscemulation.server.model.InvItem;
import org.rscemulation.server.model.Player;
import org.rscemulation.server.model.Quest;
import org.rscemulation.server.npchandler.NpcHandler;
import org.rscemulation.server.util.DataConversions;
public class General implements NpcHandler { //QID 6

	public void handleNpc(final Npc npc, final Player owner) throws Exception {
		npc.blockedBy(owner);
		owner.setBusy(true);
		final Npc wartface = World.getNpc(151, 321, 326, 445, 449);
		final Npc bentnoze = World.getNpc(152, 321, 326, 445, 449);
		if(wartface != null && bentnoze != null) {
			Quest q = owner.getQuest(6);
			if(q != null) {
				if(q.finished()) {
					questFinished(owner);
				} else {
					intro(wartface, bentnoze, owner, q.getStage());
				}
			} else {
				intro(wartface, bentnoze, owner, -1);
			}
		} else {
			owner.setBusy(false);
			owner.sendMessage("@red@Error with 'Goblin Diplomacy' quest, please contact Zilent");
		}
	}
	
	private final void questFinished(final Player owner) {
		owner.sendMessage("You have already completed this quest");
		owner.setBusy(false);
	}
	
	private void intro(final Npc wartface, final Npc bentnoze, final Player owner, final int stage) {
		final String[] messages3 = {"green armour best"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(wartface, owner, messages3, true) {
			public void finished() {
				wartface.unblock();
				bentnoze.blockedBy(owner);
				final String[] messages4 = {"No no Red every time"};
				World.getDelayedEventHandler().add(new DelayedQuestChat(bentnoze, owner, messages4) {
					public void finished() {
						bentnoze.unblock();
						wartface.blockedBy(owner);
						final String[] messages5 = {"go away human, we busy"};
						World.getDelayedEventHandler().add(new DelayedQuestChat(wartface, owner, messages5) {
							public void finished() {
								if(stage != -1) {
									switch(stage) {
										case 0:
											questJustStarted(wartface, bentnoze, owner);
											break;
										case 1:
											hasOrange(wartface, bentnoze, owner);
											break;
										case 2:
											hasDarkBlue(wartface, bentnoze, owner);
											break;
										case 3:
											hasLightBlue(wartface, bentnoze, owner);
											break;
									}
								} else {
									wartface.unblock();
									owner.setBusy(false);
								}
							}
						});
					}
				});
			}
		});
	}
	
	private void hasOrange(final Npc wartface, final Npc bentnoze, final Player owner) {
		if(owner.getInventory().contains(new InvItem(274, 1))) {
			World.getDelayedEventHandler().add(new DelayedQuestChat(wartface, owner, new String[] {"Oh it you"}) {
				public void finished() {
					World.getDelayedEventHandler().add(new DelayedQuestChat(owner, wartface, new String[] {"I have some orange armour"}) {
						public void finished() {
							World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
								public void action() {
									owner.sendMessage("You give some goblin armour to the goblins");
									owner.getInventory().remove(new InvItem(274, 1));
									owner.sendInventory();
									World.getDelayedEventHandler().add(new DelayedQuestChat(wartface, owner, new String[] {"No i don't like that much"}) {
										public void finished() {
											World.getDelayedEventHandler().add(new DelayedQuestChat(bentnoze, owner, new String[] {"It clashes with my skin colour"}) {
												public void finished() {
													World.getDelayedEventHandler().add(new DelayedQuestChat(wartface, owner, new String[] {"Try bringing us dark blue armour"}) {
														public void finished() {
															if(owner.getQuest(6).getStage() == 1) {
																owner.incQuestCompletionStage(6);
															}
															owner.setBusy(false);
															wartface.unblock();
															bentnoze.unblock();
														}
													});
												}
											});
										}
									});
								}
							});
						}
					});
				}
			});		
		} else {
			World.getDelayedEventHandler().add(new DelayedQuestChat(wartface, owner, new String[] {"Have you got some orange goblin armour yet?"}) {
				public void finished() {
					World.getDelayedEventHandler().add(new DelayedQuestChat(owner, wartface, new String[] {"Err no"}) {
						public void finished() {
							World.getDelayedEventHandler().add(new DelayedQuestChat(wartface, owner, new String[] {"Come back when you have some"}) {
								public void finished() {
									owner.setBusy(false);
									bentnoze.unblock();
									wartface.unblock();
								}
							});
						}
					});
				}
			});
		}
	}
	
	private void hasDarkBlue(final Npc wartface, final Npc bentnoze, final Player owner) {
		if(owner.getInventory().contains(new InvItem(275, 1))) {
			World.getDelayedEventHandler().add(new DelayedQuestChat(owner, wartface, new String[] {"I have some dark blue armour"}) {
				public void finished() {
					World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
						public void action() {
							owner.sendMessage("You give some goblin armour to the goblins");
							owner.getInventory().remove(new InvItem(275, 1));
							owner.sendInventory();
							World.getDelayedEventHandler().add(new DelayedQuestChat(wartface, owner, new String[] {"Doesn't seem quite right"}) {
								public void finished() {
									World.getDelayedEventHandler().add(new DelayedQuestChat(bentnoze, owner, new String[] {"Maybe if it was a bit lighter"}) {
										public void finished() {
											World.getDelayedEventHandler().add(new DelayedQuestChat(wartface, owner, new String[] {"Yeah try light blue"}) {
												public void finished() {
													World.getDelayedEventHandler().add(new DelayedQuestChat(owner, wartface, new String[] {"I thought that was the armour you were changing from", "But never mind, anything is worth a try"}) {
														public void finished() {
															if(owner.getQuest(6).getStage() == 2) {
																owner.incQuestCompletionStage(6);
															}
															owner.setBusy(false);
															wartface.unblock();
															bentnoze.unblock();
														}
													});											
												}
											});
										}
									});
								}
							});
						}
					});
				}
			});		
		} else {
			World.getDelayedEventHandler().add(new DelayedQuestChat(wartface, owner, new String[] {"Have you got some Dark Blue goblin armour yet?"}) {
				public void finished() {
					World.getDelayedEventHandler().add(new DelayedQuestChat(owner, wartface, new String[] {"Err no"}) {
						public void finished() {
							World.getDelayedEventHandler().add(new DelayedQuestChat(wartface, owner, new String[] {"Come back when you have some"}) {
								public void finished() {
									owner.setBusy(false);
									bentnoze.unblock();
									wartface.unblock();
								}
							});
						}
					});
				}
			});
		}
	}
	
	private void hasLightBlue(final Npc wartface, final Npc bentnoze, final Player owner) {	
		if(owner.getInventory().contains(new InvItem(273, 1))) {
			World.getDelayedEventHandler().add(new DelayedQuestChat(owner, wartface, new String[] {"Ok I've got the light blue armour"}) {
				public void finished() {
					World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
						public void action() {
							owner.sendMessage("You give some goblin armour to the goblins");
							owner.getInventory().remove(new InvItem(273, 1));
							owner.sendInventory();
							World.getDelayedEventHandler().add(new DelayedQuestChat(wartface, owner, new String[] {"That is rather nice"}) {
								public void finished() {
									World.getDelayedEventHandler().add(new DelayedQuestChat(bentnoze, owner, new String[] {"Yes I could see myself wearing somethin' like that"}) {
										public void finished() {
											World.getDelayedEventHandler().add(new DelayedQuestChat(wartface, owner, new String[] {"It' a deal then", "Light blue it is", "Thank you for sorting our argument"}) {
												public void finished() {
													World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
														public void action() {
															if(owner.getQuest(6).getStage() == 3) {
																owner.sendMessage("Well done you have completed the goblin diplomacy quest");
																owner.sendMessage("@gre@You have gained 5 quest points!");
																owner.sendMessage("general wartface gives you a gold bar as thanks");
																owner.getInventory().add(new InvItem(172, 1));
																owner.sendInventory();
																owner.finishQuest(6);
																Logger.log(new eventLog(owner.getUsernameHash(), owner.getAccount(), owner.getIP(), DataConversions.getTimeStamp(), "<strong>" + owner.getUsername() + "</strong>" + " has completed the <span class=\"recent_quest\">Goblin Diplomacy</span> quest!"));
															}
															owner.setBusy(false);
															bentnoze.unblock();
															wartface.unblock();
														}
													});
												}
											});
										}
									});
								}
							});
						}
					});
				}
			});		
		} else {
			World.getDelayedEventHandler().add(new DelayedQuestChat(wartface, owner, new String[] {"Have you got some Light Blue goblin armour yet?"}) {
				public void finished() {
					World.getDelayedEventHandler().add(new DelayedQuestChat(owner, wartface, new String[] {"Err no"}) {
						public void finished() {
							World.getDelayedEventHandler().add(new DelayedQuestChat(wartface, owner, new String[] {"Come back when you have some"}) {
								public void finished() {
									owner.setBusy(false);
									wartface.unblock();
									bentnoze.unblock();
								}
							});
						}
					});
				}
			});
		}
	}
	
	private final void arguing(final Npc wartface, final Npc bentnoze, final Player owner) {
		final String[] messages20 = {"We decide to celebrate goblin new century", "By changing the colour of our armour", "Light blue get boring after a bit", "And we want change", "Problem is they want different change to us"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(wartface, owner, messages20) {
			public void finished() {
				owner.setBusy(false);
				wartface.unblock();
				bentnoze.unblock();
			}
		});
	}
	
	private final void peace(final Npc wartface, final Npc bentnoze, final Player owner) {
		World.getDelayedEventHandler().add(new DelayedQuestChat(wartface, owner, new String[] {"Yeah peace is good as long as it is peace wearing Green armour"}) {
			public void finished() {
				World.getDelayedEventHandler().add(new DelayedQuestChat(bentnoze, owner, new String[] {"But green to much like skin!", "Nearly make you look naked!"}) {
					public void finished() {
						owner.setBusy(false);
						wartface.unblock();
						bentnoze.unblock();
					}
				});
			}
		});
	}
	
	private final void questJustStarted(final Npc wartface, final Npc bentnoze, final Player owner) {
		World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
			public void action() {
				final String[] options1 = {"Why are you arguing about the colour of your armour?", "Wouldn't you prefer peace?", "Do you want me to pick an armour colour for you?"};
				owner.setBusy(false);
				owner.sendMenu(options1);
				owner.setMenuHandler(new MenuHandler(options1) {
					public void handleReply(final int option, final String reply) {
						owner.setBusy(true);
						for(Player informee : owner.getViewArea().getPlayersInView()) {
							informee.informOfChatMessage(new ChatMessage(owner, reply, wartface));
						}
						switch(option) {
							case 0:
								arguing(wartface, bentnoze, owner);
								break;
							case 1:
								peace(wartface, bentnoze, owner);
								break;
							case 2:
								World.getDelayedEventHandler().add(new DelayedQuestChat(owner, wartface, new String[] {"Different to either green or red"}) {
									public void finished() {
										pickForYou(wartface, bentnoze, owner);
									}
								});
								break;
						}
					}
				});
			}
		});
	}
	
	private final void pickForYou(final Npc wartface, final Npc bentnoze, final Player owner) {
		World.getDelayedEventHandler().add(new DelayedQuestChat(wartface, owner, new String[] {"Hmm me dunno what that'd look like", "You'd have to bring me some, so us could decide"}) {
			public void finished() {
				World.getDelayedEventHandler().add(new DelayedQuestChat(bentnoze, owner, new String[] {"Yep bring us orange armour"}) {
					public void finished() {
						World.getDelayedEventHandler().add(new DelayedQuestChat(wartface, owner, new String[] {"Yep orange might be good"}) {
							public void finished() {
								owner.incQuestCompletionStage(6);
								owner.setBusy(false);
								wartface.unblock();
								bentnoze.unblock();
							}
						});
					}
				});
			}
		});
	}
}