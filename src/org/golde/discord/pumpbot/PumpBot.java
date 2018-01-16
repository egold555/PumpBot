package org.golde.discord.pumpbot;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.golde.discord.pumpbot.commands.CommandAddAlts;
import org.golde.discord.pumpbot.states.UserState;
import org.golde.java.discordbotapi.DiscordAPIException;
import org.golde.java.discordbotapi.DiscordBot;
import org.golde.java.discordbotapi.DiscordCommand;

import sx.blah.discord.handle.obj.IChannel;
import sx.blah.discord.handle.obj.IMessage;
import sx.blah.discord.handle.obj.IPrivateChannel;
import sx.blah.discord.handle.obj.IUser;

public class PumpBot extends DiscordBot {
	
	public HashMap<Long, UserState> userStates = new HashMap<Long, UserState>();
	
	long CHANNEL_DEBUG = 402680152888442881L;
	
	public PumpBot() {
		super(BotConstants.TOKEN);
		setPrefix(">");
	}

	@Override
	public void onMessage(IUser user, IChannel channel, IMessage msg) {
		IChannel dbg = getClient().getChannelByID(CHANNEL_DEBUG);
		
		if(channel instanceof IPrivateChannel) {
			//is a DM
			/*String[] lines = msg.getContent().split("\\r?\\n");
			List<String> alts = new ArrayList<String>();
			for(String line:lines) {
				if(line.contains(":") && line.length() > 10) {
					alts.add(line);
				}
			}
			if(alts.size() > 0) {
				channel.sendMessage("Thanks for the " + alts.size() + " alts!");
			}
			dbg.sendMessage(user.mention() + " added " + alts.size() + " alts via DM!");*/
			
			//Any spam will be illiminated via this line
			if(userStates.containsKey(user.getLongID())) {
				UserState userState = userStates.get(user.getLongID());
				userState.recieved(user, channel, msg.getContent());
			}
			
		}
		
	}

	@Override
	public void onReady() {
		IChannel channel = getClient().getChannelByID(CHANNEL_DEBUG);
		channel.sendMessage("Enabled: " + new Date());
	}

	@Override
	public void onShutdown() {
		
	}

	@Override
	public void onTick() {
		
	}

	@Override
	public void registerCommands(List<DiscordCommand> cmds) throws DiscordAPIException {
		cmds.add(new CommandAddAlts());
	}

}
