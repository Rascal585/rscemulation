/**
* Generated By NPCScript :: A scripting engine created for RSCEmulation by Zilent
*/
package org.rscemulation.server.npchandler.Lost_City;
import org.rscemulation.server.event.DelayedQuestChat;
import org.rscemulation.server.event.SingleEvent;
import org.rscemulation.server.model.ChatMessage;
import org.rscemulation.server.model.MenuHandler;
import org.rscemulation.server.model.Npc;
import org.rscemulation.server.model.Player;
import org.rscemulation.server.model.Quest;
import org.rscemulation.server.model.World;
import org.rscemulation.server.npchandler.NpcHandler;


// this class is clean.
public class Priest implements NpcHandler {



	public void handleNpc(final Npc npc, final Player owner) throws Exception {
	npc.blockedBy(owner);
		owner.setBusy(true);
		Quest q = owner.getQuest(19);
		if(q != null) {
			if(q.finished()) {
				finished(npc, owner);
			} else {
				switch(q.getStage()) {
					case 0:
						noQuestStarted(npc, owner);
						break;
					case 1:
						questStarted(npc,owner);
						break;
					default:
						questStarted(npc, owner);
				}
			}
		} else {
			noQuestStarted(npc, owner);
		}
	}
		
	private void noQuestStarted(final Npc npc, final Player owner) {
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Hello there fellow adventurer", "What business do you have here?"}, true) {
			public void finished() {
				World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
					public void action() {
						final String[] options107 = {"Who are you?", "I'm in search of a quest", "What are you doing here?"};
						owner.setBusy(false);
						owner.sendMenu(options107);
						owner.setMenuHandler(new MenuHandler(options107) {
							public void handleReply(final int option, final String reply) {
								owner.setBusy(true);
								for(Player informee : owner.getViewArea().getPlayersInView()) {
									informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
								}
								switch(option) {
									case 0:
										whoAreYou(npc, owner);
										break;
									case 1:
										quest(npc, owner);
										break;
									case 2:
										doingHere(npc, owner);
										break;
								}
							}
						});
					}
				});
			}
		});
	}
	
	
	private void whoAreYou(final Npc npc, final Player owner) {
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"I am a priest, a master of peace and tranquility", "Why are you here adventurer?"}) {
			public void finished() {
				World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
					public void action() {
						final String[] options107 = {"I'm in search of a quest", "What are you doing here?", "No reason, I'll be on my way now"};
						owner.setBusy(false);
						owner.sendMenu(options107);
						owner.setMenuHandler(new MenuHandler(options107) {
							public void handleReply(final int option, final String reply) {
								owner.setBusy(true);
								for(Player informee : owner.getViewArea().getPlayersInView()) {
									informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
								}
								switch(option) {
									case 0:
										quest(npc, owner);
										break;
									case 1:
										doingHere(npc, owner);
										break;
									case 2:
										iLeave(npc, owner);
										break;
								}
							}
						});
					}
				});
			}
		});
	}
	
	private void doingHere(final Npc npc, final Player owner) {
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Well our group is looking for a magical lost city", "I'm not going to share any information though", "because we want it for ourselves."}) {
			public void finished() {
				World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
					public void action() {
						final String[] options108 = {"Oh come on, can you tell me anything?", "Alright, I'll be on my way now"};
						owner.setBusy(false);
						owner.sendMenu(options108);
						owner.setMenuHandler(new MenuHandler(options108) {
							public void handleReply(final int option, final String reply) {
								owner.setBusy(true);
								for(Player informee : owner.getViewArea().getPlayersInView()) {
									informee.informOfChatMessage(new ChatMessage(owner, reply, npc));
								}
								switch(option) {
									case 0:
										noInfo(npc, owner);
										break;
									case 1:
										iLeave(npc, owner);
										break;
								}
							}
						});
					}
				});
			}
		});
	}
	
	private final void noInfo(final Npc npc, final Player owner) {
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Look pal, theres no way I'm giving you any information"}) {
			public void finished() {
				World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Please?"}) {
					public void finished() {
						World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Leave at once!"}) {
							public void finished() {
								owner.setBusy(false);
								npc.unblock();
								owner.addQuest(19, 2);
								owner.incQuestCompletionStage(19);
							}
						});
					}
				});
			}
		});
	}
	
	private final void quest(final Npc npc, final Player owner) {
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Well go look for a quest elsewhere","me and my group are busy doing our own quest"}) {
			public void finished() {
				World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Can I join you?"}) {
					public void finished() {
						World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"No, go mine some ore or something..."}) {
							public void finished() {
								owner.setBusy(false);
								npc.unblock();
								owner.addQuest(19, 2);
								owner.incQuestCompletionStage(19);
							}
						});
					}
				});
			}
		});
	}
	
	private final void questStarted(final Npc npc, final Player owner) {
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"I thought I told you to leave","me and my group are busy doing our own quest"}, true) {
			public void finished() {
				World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"Can I join you?"}) {
					public void finished() {
						World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"No, go mine some ore or something..."}) {
							public void finished() {
								owner.setBusy(false);
								npc.unblock();
							}
						});
					}
				});
			}
		});
	}
	
	private void iLeave(final Npc npc, final Player owner) {
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"Farewell adventurer, may saradomin watch over you..."}) {
			public void finished() {
				owner.setBusy(false);
				npc.unblock();
			}
		});
	}
	
	private final void finished(final Npc npc, final Player owner) {
		World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, new String[] {"oh its you","did you find out anything about zanaris?"}, true) {
			public void finished() {
				World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, new String[] {"I think I'll keep that to myself"}) {
					public void finished() {
						owner.setBusy(false);
						npc.unblock();
					}
				});
			}
		});
	}
		
	
	
}