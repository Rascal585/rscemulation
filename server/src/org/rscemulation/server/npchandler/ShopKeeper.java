package org.rscemulation.server.npchandler;

import org.rscemulation.server.model.Player;
import org.rscemulation.server.model.Npc;
import org.rscemulation.server.model.ChatMessage;
import org.rscemulation.server.model.MenuHandler;
import org.rscemulation.server.model.Shop;
import org.rscemulation.server.model.World;
import org.rscemulation.server.event.ShortEvent;

public class ShopKeeper implements NpcHandler {

	public void handleNpc(final Npc npc, Player player) throws Exception {
		final Shop shop = World.getShop(npc.getLocation());
		if(shop == null) {
			return;
		}
		if(shop.getGreeting() != null) {
      			player.informOfNpcMessage(new ChatMessage(npc, shop.getGreeting(), player));
      		}
      		player.setBusy(true);
      		World.getDelayedEventHandler().add(new ShortEvent(player) {
      			public void action() {
      				if(owner.isAdmin())
      					owner.sendMessage("ShopID:" + shop.getID()+"");
      				owner.setBusy(false);
      				owner.setMenuHandler(new MenuHandler(shop.getOptions()) {
      					public void handleReply(final int option, final String reply) {
      						if(owner.isBusy()) {
      							return;
      						}
      						owner.informOfChatMessage(new ChatMessage(owner, reply, npc));
      						owner.setBusy(true);
      						World.getDelayedEventHandler().add(new ShortEvent(owner) {
      							public void action() {
      								owner.setBusy(false);
      								if(option == 0) {
      									owner.setAccessingShop(shop);
      									owner.showShop(shop);
      								}
      								npc.unblock();
      							}
      						});
      					}
      				});
      				owner.sendMenu(shop.getOptions());
      			}
      		});
      		npc.blockedBy(player);
	}
	
}
