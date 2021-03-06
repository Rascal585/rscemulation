package org.rscemulation.server.npchandler;

import org.rscemulation.server.model.Player;
import org.rscemulation.server.model.Npc;
import org.rscemulation.server.model.World;
import org.rscemulation.server.model.InvItem;
import org.rscemulation.server.model.ChatMessage;
import org.rscemulation.server.model.MenuHandler;
import org.rscemulation.server.event.ShortEvent;

public class Abbot_Langley implements NpcHandler {
	
	public void handleNpc(final Npc npc, Player player) throws Exception {
      		player.informOfNpcMessage(new ChatMessage(npc, "do you seek to make your prayer last longer?", player));
      		player.informOfNpcMessage(new ChatMessage(npc, "would you like me to bless an Unblessed Holy Symbol?", player));
      		player.setBusy(true);
      		World.getDelayedEventHandler().add(new ShortEvent(player) {
      			public void action() {
      				owner.setBusy(false);
				String[] options = new String[]{"perhaps another time", "Yes please!"};
				owner.setMenuHandler(new MenuHandler(options) {
					public void handleReply(final int option, final String reply) {
						if(owner.isBusy()) {
							return;
						}
						owner.informOfChatMessage(new ChatMessage(owner, reply, npc));
						owner.setBusy(true);
						World.getDelayedEventHandler().add(new ShortEvent(owner) {
							public void action() {
								owner.setBusy(false);
								if(option == 1) {
									if(owner.getInventory().remove(45, 1) > -1) {
										owner.getActionSender().sendMessage("Abbot Langley blesses the Unblessed Holy Symbol");
										owner.getInventory().add(new InvItem(385, 1));
										owner.getActionSender().sendInventory();
										npc.unblock();
									}
									else {
										owner.informOfChatMessage(new ChatMessage(npc, "you must have forgoten to bring it with you", owner));
										owner.setBusy(true);
										World.getDelayedEventHandler().add(new ShortEvent(owner) {
											public void action() {
												owner.setBusy(false);
												owner.informOfNpcMessage(new ChatMessage(npc, "Come back when you have an Unblessed Holy Symbol with you", owner));
												npc.unblock();
											}
										});
									}
								}
								else {
									npc.unblock();
								}
							}
						});
					}
				});
				owner.getActionSender().sendMenu(options);
      			}
      		});
      		npc.blockedBy(player);
	}
	
}