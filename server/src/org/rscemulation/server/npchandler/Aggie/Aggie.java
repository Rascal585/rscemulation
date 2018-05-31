/**
* Generated By NPCScript :: A scripting engine created for RSCEmulation by Zilent
*/
package org.rscemulation.server.npchandler.Aggie;

import org.rscemulation.server.event.SingleEvent;
import org.rscemulation.server.model.Npc;
import org.rscemulation.server.model.ChatMessage;
import org.rscemulation.server.model.MenuHandler;
import org.rscemulation.server.model.World;
import org.rscemulation.server.event.DelayedQuestChat;
import org.rscemulation.server.event.MiniEvent;
import org.rscemulation.server.model.InvItem;
import org.rscemulation.server.model.Player;
import org.rscemulation.server.model.Quest;
import org.rscemulation.server.npchandler.NpcHandler;
public class Aggie implements NpcHandler {
	public void handleNpc(final Npc npc, final Player owner) throws Exception {
		npc.blockedBy(owner);
		owner.setBusy(true);
		Quest q = owner.getQuest(10);
		if(q != null) {
			if(q.finished()) {
				questNotStarted(npc, owner);
			} else {
				questUnderway(npc, owner);
			}
		} else {
			questNotStarted(npc, owner);
		}
	}

	private final void questUnderway(final Npc npc, final Player owner) {
		final String[] messages0 = {"What can I help you with?"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages0, true) {
			public void finished() {
				World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
					public void action() {
						final String[] options0 = {"Could you think of a way to make pink skin paste", "What could you make for me", "Cool, do you turn people into frogs?", "You mad old witch, you can't help me", "Can you make dyes for me please"};
						owner.setBusy(false);
						owner.sendMenu(options0);
						owner.setMenuHandler(new MenuHandler(options0) {
							public void handleReply(final int option, final String reply) {
								owner.setBusy(true);
								for(Player informee : owner.getViewArea().getPlayersInView()) {
									informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
								}
								switch(option) {
									case 0:
										skinPaste(npc, owner);
										break;
									case 1:
										whatCouldYou(npc, owner);
										break;
									case 2:
										frogs(npc, owner);
										break;
									case 3:
										madWitch(npc, owner);
										break;
									case 4:
										makeDyes(npc, owner);
										break;
								}
							}
						});
					}
				});
			}
		});
	}
	
	private final void skinPaste(final Npc npc, final Player owner) {
		final String[] messages1 = {"Why, its one of my most popular potions", "The women here, they like to have smooth looking skin", "(and I must admit, some of the men buy it too)", "I can make it for you, just get me whats needed"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages1) {
			public void finished() {
				if(owner.getInventory().countId(181) < 1 || owner.getInventory().countId(136) < 1 || (owner.getInventory().countId(141) < 1 && owner.getInventory().countId(342) < 1 && owner.getInventory().countId(50) < 1) || owner.getInventory().countId(236) < 1) {
					missingItems(npc, owner);
				} else {
					hasItems(npc, owner);
				}
			}
		});
	}

	private final void hasItems(final Npc npc, final Player owner) {
		final String[] messages4 = {"Yes I can, you have the ingredients for it already", "Would you like me to mix you some?"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages4) {
			public void finished() {
				World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
					public void action() {
						final String[] options1 = {"Yes please, mix me some skin paste", "No thankyou, I don't need paste"};
						owner.setBusy(false);
						owner.sendMenu(options1);
						owner.setMenuHandler(new MenuHandler(options1) {
							public void handleReply(final int option, final String reply) {
								owner.setBusy(true);
								for(Player informee : owner.getViewArea().getPlayersInView()) {
									informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
								}
								switch(option) {
									case 0:
										final String[] messages5 = {"That should be simple, hand the things to Aggie then"};
										World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages5) {
											public void finished() {
												owner.sendMessage("You hand ash, flour, water, and redberries to Aggie");
												owner.getInventory().remove(new InvItem(236, 1));
												owner.getInventory().remove(new InvItem(136, 1));
												owner.getInventory().remove(new InvItem(181, 1));
												if(owner.getInventory().remove(new InvItem(141, 1)) > 0){}
												else if(owner.getInventory().remove(new InvItem(342, 1)) > 0){}
												else if(owner.getInventory().remove(new InvItem(50, 1)) > 0){}
												owner.sendInventory();
												owner.sendMessage("She tips it into a cauldron and mutters some words");
												final String[] messages6 = {"Tourniquet, Fenderbaum, Tottenham, MonsterMunch, MarbleArch"};
												World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages6) {
													public void finished() {
														owner.sendMessage("Aggie hands you the skin paste");
														owner.getInventory().add(new InvItem(240, 1));
														owner.sendInventory();
														final String[] messages7 = {"There you go dearie, your skin potion", "That will make you look good at the Varrock dances"};
														World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages7) {
															public void finished() {
																owner.setBusy(false);
																npc.unblock();
															}
														});
													}
												});
											}
										});
										break;
									case 1:
										owner.setBusy(false);
										npc.unblock();
										break;
								}
							}
						});
					}
				});
			}
		});
	}
	
	private final void missingItems(final Npc npc, final Player owner) {
		final String[] messages2 = {"What do you need to make it?"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, messages2) {
			public void finished() {
				final String[] messages3 = {"Well dary, you need a base for the paste", "That's a mix of ash, flour, and water", "Then you need red berries to colour it as you want", "bring those four items to me and I will make you some"};
				World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages3) {
					public void finished() {
						owner.setBusy(false);
						npc.unblock();
					}
				});
			}
		});
	}
	
	private final void questNotStarted(final Npc npc, final Player owner) {
		final String[] messages11 = {"What can I help you with?"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages11, true) {
			public void finished() {
				World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
					public void action() {
						final String[] options6 = {"What could you make for me", "Cool, do you turn people into frogs?", "You mad old witch, you can't help me", "Can you make dyes for me please"};
						owner.setBusy(false);
						owner.sendMenu(options6);
						owner.setMenuHandler(new MenuHandler(options6) {
							public void handleReply(final int option, final String reply) {
								owner.setBusy(true);
								for(Player informee : owner.getViewArea().getPlayersInView()) {
									informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
								}
								switch(option) {
									case 0:
										whatCouldYou(npc, owner);
										break;
									case 1:
										frogs(npc, owner);
										break;
									case 2:
										madWitch(npc, owner);
										break;
									case 3:
										makeDyes(npc, owner);
										break;
								}
							}
						});
					}
				});
			}
		});
	}

	private final void madWitch(final Npc npc, final Player owner) {
		final String[] messages13 = {"Oh, you like to call a witch names, do you?", "You should be careful about insulting a Witch", "You never know what shape you could wake up in"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages13) {
			public void finished() {
				owner.setBusy(false);
				npc.unblock();
			}
		});
	}
	
	private final void frogs(final Npc npc, final Player owner) {
		final String[] messages12 = {"Oh, not for years, but if you meet a talking chicken,", "You have probably met the professor in the Manor north of here", "A few years ago it was a flying fish, that machine is a menace"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages12) {
			public void finished() {
				owner.setBusy(false);
				npc.unblock();
			}
		});
	}
	
	private void whatCouldYou(final Npc npc, final Player owner) {
		final String[] messages14 = {"I mostly just make what I find pretty", "I sometimes make dye for the womens clothes, brighten the place up", "I can make red, yellow, and blue dyes", "Would you like some?"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages14) {
			public void finished() {
				World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
					public void action() {
						final String[] options7 = {"What do you need to make some red dye please", "What do you need to make some yellow dye please", "What do you need to make some blue dye please", "No thanks, I am happy the colour I am"};
						owner.setBusy(false);
						owner.sendMenu(options7);
						owner.setMenuHandler(new MenuHandler(options7) {
							public void handleReply(final int option, final String reply) {
								owner.setBusy(true);
								for(Player informee : owner.getViewArea().getPlayersInView()) {
									informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
								}
								switch(option) {
									case 0:
										redDye(npc, owner);
										break;
									case 1:
										yellowDye(npc, owner);
										break;
									case 2:
										blueDye(npc, owner);
										break;
									case 3:
										final String[] messages15 = {"You are easily pleased with yourself then", "when you need dyes, come to me"};
										World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages15) {
											public void finished() {
												owner.setBusy(false);
												npc.unblock();
											}
										});
										break;
								}
							}
						});
					}
				});
			}
		});
	}

	private void redDye(final Npc npc, final Player owner) {
		final String[] messages16 = {"3 lots of Red berries, and 5 coins, to you"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages16) {
			public void finished() {
				World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
					public void action() {
						final String[] options8 = {"Okay, make me some red dye please", "I don't think I have all the ingredients yet", "I can do without dye at that price"};
						owner.setBusy(false);
						owner.sendMenu(options8);
						owner.setMenuHandler(new MenuHandler(options8) {
							public void handleReply(final int option, final String reply) {
								owner.setBusy(true);
								switch(option) {
									case 0:
										if(owner.getInventory().contains(new InvItem(10, 5))) {
											if(owner.getInventory().countId(236) > 2) {
												for(Player informee : owner.getViewArea().getPlayersInView()) {
													informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
												}
												World.getDelayedEventHandler().add(new MiniEvent(owner) {
													public void action() {
														owner.getInventory().remove(new InvItem(236, 1));
														owner.getInventory().remove(new InvItem(236, 1));
														owner.getInventory().remove(new InvItem(236, 1));
														owner.getInventory().remove(new InvItem(10, 5));
														owner.getInventory().add(new InvItem(238, 1));
														owner.sendInventory();
														owner.sendMessage("You hand the red berries and payment to Aggie");
														owner.sendMessage("she takes a red bottle from nowhere and hands it to you");
													}
												});
											} else {
												owner.sendMessage("You don't have enough red berries!");
											}
										} else {
											owner.sendMessage("You don't have enough coins to pay for the dye!");
										}
										owner.setBusy(false);
										npc.unblock();
										break;
									case 1:
										for(Player informee : owner.getViewArea().getPlayersInView()) {
											informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
										}
										noIngredients(npc, owner);
										break;
									case 2:
										for(Player informee : owner.getViewArea().getPlayersInView()) {
											informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
										}
										canDoWithout(npc, owner);
										break;
								}
							}
						});
					}
				});
			}
		});
	}

	private void makeDyes(final Npc npc, final Player owner) {
		final String[] messages17 = {"What sort of dye would you like? Red, yellow, or blue?"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages17) {
			public void finished() {
				World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
					public void action() {
						final String[] options9 = {"What do you need to make some red dye please", "What do you need to make some yellow dye please", "What do you need to make some blue dye please"};
						owner.setBusy(false);
						owner.sendMenu(options9);
						owner.setMenuHandler(new MenuHandler(options9) {
							public void handleReply(final int option, final String reply) {
								owner.setBusy(true);
								for(Player informee : owner.getViewArea().getPlayersInView()) {
									informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
								}
								switch(option) {
									case 0:
										redDye(npc, owner);
										break;
									case 1:
										yellowDye(npc, owner);
										break;
									case 2:
										blueDye(npc, owner);
										break;
								}
							}
						});
					}
				});
			}
		});
	}

	private void blueDye(final Npc npc, final Player owner) {
		final String[] messages18 = {"2 woad leaves, and 5 coins, to you"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages18) {
			public void finished() {
				World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
					public void action() {
						final String[] options10 = {"Okay, make me some blue dye please", "I don't think I have all the ingredients yet", "I can do without dye at that price"};
						owner.setBusy(false);
						owner.sendMenu(options10);
						owner.setMenuHandler(new MenuHandler(options10) {
							public void handleReply(final int option, final String reply) {
								owner.setBusy(true);
								switch(option) {
									case 0:
										if(owner.getInventory().contains(new InvItem(10, 5))) {
											if(owner.getInventory().countId(281) > 1) {
												for(Player informee : owner.getViewArea().getPlayersInView()) {
													informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
												}
												World.getDelayedEventHandler().add(new MiniEvent(owner) {
													public void action() {
														owner.getInventory().remove(new InvItem(281, 2));
														owner.getInventory().remove(new InvItem(10, 5));
														owner.getInventory().add(new InvItem(272, 1));
														owner.sendInventory();
														owner.sendMessage("You hand the woad leaves and payment to Aggie");
														owner.sendMessage("she takes a blue bottle from nowhere and hands it to you");
													}
												});
											} else {
												owner.sendMessage("You don't have enough woad leaves!");
											}
										} else {
											owner.sendMessage("You don't have enough coins to pay for the dye!");
										}
										owner.setBusy(false);
										npc.unblock();
										break;
									case 1:
										for(Player informee : owner.getViewArea().getPlayersInView()) {
											informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
										}
										noIngredients(npc, owner);
										break;
									case 2:
										for(Player informee : owner.getViewArea().getPlayersInView()) {
											informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
										}
										canDoWithout(npc, owner);
										break;
								}
							}
						});
					}
				});
			}
		});
	}

	private void yellowDye(final Npc npc, final Player owner) {
		final String[] messages19 = {"Yellow is a strange colour to get, comes from onion skins", "I need 2 onions, and 5 coins to make yellow"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages19) {
			public void finished() {
				World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
					public void action() {
						final String[] options11 = {"Ok, make me some yellow dye please", "I don't think I have all the ingredients yet", "I can do without dye at that price"};
						owner.setBusy(false);
						owner.sendMenu(options11);
						owner.setMenuHandler(new MenuHandler(options11) {
							public void handleReply(final int option, final String reply) {
								owner.setBusy(true);
								switch(option) {
									case 0:
										if(owner.getInventory().contains(new InvItem(10, 5))) {
											if(owner.getInventory().countId(241) > 1) {
												for(Player informee : owner.getViewArea().getPlayersInView()) {
													informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
												}
												World.getDelayedEventHandler().add(new MiniEvent(owner) {
													public void action() {
														owner.getInventory().remove(new InvItem(241, 1));
														owner.getInventory().remove(new InvItem(241, 1));
														owner.getInventory().remove(new InvItem(10, 5));
														owner.getInventory().add(new InvItem(239, 1));
														owner.sendMessage("You hand the onions and payment to Aggie");
														owner.sendMessage("she takes a yellow bottle from nowhere and hands it to you");
														owner.sendInventory();
													}
												});
											} else {
												owner.sendMessage("You don't have enough yellow berries!");
											}
										} else {
											owner.sendMessage("You don't have enough coins to pay for the dye!");
										}
										owner.setBusy(false);
										npc.unblock();
										break;
									case 1:
										for(Player informee : owner.getViewArea().getPlayersInView()) {
											informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
										}
										noIngredients(npc, owner);
										break;
									case 2:
										for(Player informee : owner.getViewArea().getPlayersInView()) {
											informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
										}
										canDoWithout(npc, owner);
										break;
								}
							}
						});
					}
				});
			}
		});
	}

	private void canDoWithout(final Npc npc, final Player owner) {
		final String[] messages20 = {"That's your choice, but I would think you have killed for less", "I can see it in your eyes"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages20) {
			public void finished() {
				owner.setBusy(false);
				npc.unblock();
			}
		});
	}

	private void noIngredients(final Npc npc, final Player owner) {
		final String[] messages21 = {"You know what you need to get now, come back when you have them", "goodbye for now"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages21) {
			public void finished() {
				owner.setBusy(false);
				npc.unblock();
			}
		});
	}
}