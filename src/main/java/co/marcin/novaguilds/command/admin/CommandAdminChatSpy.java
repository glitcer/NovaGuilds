/*
 *     NovaGuilds - Bukkit plugin
 *     Copyright (C) 2016 Marcin (CTRL) Wieczorek
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package co.marcin.novaguilds.command.admin;

import co.marcin.novaguilds.api.basic.NovaPlayer;
import co.marcin.novaguilds.command.abstractexecutor.AbstractCommandExecutor;
import co.marcin.novaguilds.enums.Message;
import co.marcin.novaguilds.enums.Permission;
import co.marcin.novaguilds.enums.VarKey;
import co.marcin.novaguilds.manager.PlayerManager;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class CommandAdminChatSpy extends AbstractCommandExecutor {
	@Override
	public void execute(CommandSender sender, String[] args) throws Exception {
		NovaPlayer nPlayer = PlayerManager.getPlayer(sender);
		NovaPlayer nPlayerChange;

		if(args.length == 1) {
			if(!Permission.NOVAGUILDS_ADMIN_CHATSPY_OTHER.has(sender)) {
				Message.CHAT_NOPERMISSIONS.send(sender);
				return;
			}

			nPlayerChange = PlayerManager.getPlayer(args[0]);

			if(nPlayerChange == null) {
				Message.CHAT_PLAYER_NOTEXISTS.send(sender);
				return;
			}
		}
		else {
			nPlayerChange = nPlayer;
		}

		nPlayerChange.getPreferences().toggleSpyMode();
		Map<VarKey, String> vars = new HashMap<>();
		vars.put(VarKey.MODE, Message.getOnOff(nPlayerChange.getPreferences().getSpyMode()));

		//Notify message
		if(!nPlayer.equals(nPlayerChange)) {
			vars.put(VarKey.PLAYER_NAME, nPlayerChange.getName());

			Message.CHAT_ADMIN_SPYMODE_NOTIFY.clone().vars(vars).send(nPlayerChange);
			Message.CHAT_ADMIN_SPYMODE_SUCCESS_OTHER.clone().vars(vars).send(sender);
			return;
		}

		Message.CHAT_ADMIN_SPYMODE_SUCCESS_SELF.clone().vars(vars).send(sender);
	}
}
