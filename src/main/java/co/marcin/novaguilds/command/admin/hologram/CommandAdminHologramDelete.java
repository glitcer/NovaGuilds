package co.marcin.novaguilds.command.admin.hologram;

import co.marcin.novaguilds.basic.NovaHologram;
import co.marcin.novaguilds.enums.Commands;
import co.marcin.novaguilds.enums.Message;
import co.marcin.novaguilds.interfaces.Executor;
import co.marcin.novaguilds.interfaces.ExecutorReversedAdminHologram;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class CommandAdminHologramDelete implements Executor, ExecutorReversedAdminHologram {
	private final Commands command;
	private NovaHologram hologram;

	public CommandAdminHologramDelete(Commands command) {
		this.command = command;
		plugin.getCommandManager().registerExecutor(command, this);
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if(!command.hasPermission(sender)) {
			Message.CHAT_NOPERMISSIONS.send(sender);
			return;
		}

		if(!command.allowedSender(sender)) {
			Message.CHAT_CMDFROMCONSOLE.send(sender);
			return;
		}

		hologram.delete();

		Map<String, String> vars = new HashMap<>();
		vars.put("NAME", hologram.getName());
		Message.CHAT_ADMIN_HOLOGRAM_DELETE_SUCCESS.vars(vars).send(sender);
	}

	@Override
	public void hologram(NovaHologram hologram) {
		this.hologram = hologram;
	}
}

