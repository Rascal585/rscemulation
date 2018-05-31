/**
* Generated By NPCScript :: A scripting engine created for RSCEmulation by Zilent
*/
package org.rscemulation.server.npchandler.Prince_Ali_Rescue;

import org.rscemulation.server.event.SingleEvent;
import org.rscemulation.server.model.Npc;
import org.rscemulation.server.model.ChatMessage;
import org.rscemulation.server.model.MenuHandler;
import org.rscemulation.server.model.World;
import org.rscemulation.server.event.DelayedQuestChat;
import org.rscemulation.server.model.Player;
import org.rscemulation.server.npchandler.NpcHandler;
public class JailGuard implements NpcHandler {
	public void handleNpc(final Npc npc, final Player owner) throws Exception {
		npc.blockedBy(owner);
		owner.setBusy(true);
		final String[] messages0 = {"Hi, who are you guarding here?"};
		World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, messages0, true) {
			public void finished() {
				final String[] messages1 = {"Can't say, all very secret. you should get out of here", "I am not supposed to talk while I guard"};
				World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages1) {
					public void finished() {
						World.getDelayedEventHandler().add(new SingleEvent(owner, 1500) {
							public void action() {
								final String[] options0 = {"I had better leave, I don't want trouble", "Hey, chill out, I won't cause you trouble"};
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
												final String[] messages2 = {"Thanks I appreciate that", "Talking on duty can be punishable by having your mouth stitched up", "These are tough people, no mistake"};
												World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages2) {
													public void finished() {
														owner.setBusy(false);
														npc.unblock();
													}
												});
												break;
											case 1:
												final String[] messages3 = {"I was just wondering what you do to relax"};
												World.getDelayedEventHandler().add(new DelayedQuestChat(owner, npc, messages3) {
													public void finished() {
														final String[] messages4 = {"You never relax with these people, but its a good career for a young man"};
														World.getDelayedEventHandler().add(new DelayedQuestChat(npc, owner, messages4) {
															public void finished() {
																owner.setBusy(false);
																npc.unblock();
															}
														});
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
		});
	}
}