package nl.nilsrijkaart.widm.util.gameUtil

import nl.nilsrijkaart.widm.game.GameManager
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class MsgCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (command.name != "msg") return true
        if (sender !is Player) return true
        if (args.isEmpty()) {
            sender.sendMessage("§cUsage: /msg <ontvanger> <bericht>. Let op! Als je een MSG boekje hebt dan wordt wordt deze automatisch gebruikt.")
            return true
        }
        val message = args.slice(1 until args.size).joinToString(" ")
        val receiver = Bukkit.getPlayer(args[0])
        if (receiver == null) {
            sender.sendMessage("§cDeze speler is niet online.")
            return true
        }

        if (GameManager.game != null) {


            if (GameManager.game!!.hosts.contains(sender) || GameManager.game!!.hosts.contains(receiver)) {
                MsgUtil.sendMsg(sender, receiver, message)
                return true
            }

            if (GameManager.game!!.slots.none {
                    it.player == sender.uniqueId
                } || GameManager.game!!.slots.none {
                    it.player == receiver.uniqueId
                }) {

                MsgUtil.sendMsg(sender, receiver, message)
                return true
            }

            MsgUtil.useMsgUtil(sender, receiver, message)
            return true
        }

        MsgUtil.sendMsg(sender, receiver, message)
        return true
    }
}