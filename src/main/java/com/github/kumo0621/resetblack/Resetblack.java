package com.github.kumo0621.resetblack;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Resetblack extends JavaPlugin {
    private int taskId;

    @Override
    public void onEnable() {
        // プラグインが有効になった時に呼び出される処理

        // 1時間ごとにブロックを置き換える処理を開始する
        taskId = startBlockReplaceTask();
    }

    @Override
    public void onDisable() {
        // プラグインが無効になる時に呼び出される処理

        // ブロックの置き換え処理を停止する
        stopBlockReplaceTask();
    }

    private int startBlockReplaceTask() {
        // 1時間ごとにブロックを置き換えるタスクを開始する

        // 20 ticks = 1 second, 60 seconds = 1 minute, 60 minutes = 1 hour
        // 1時間ごとにタスクを実行するためには、20 * 60 * 60 ticks が必要
        int interval = 20 * 60 *60;

        // BukkitRunnableを使用してタスクを実行する
        BukkitRunnable task = new BukkitRunnable() {
            @Override
            public void run() {
                // 10分前と10秒前の告知
                if (getTaskId() == 12000) { // 1200 ticks = 60 seconds * 20 ticks (1 minute)
                    Bukkit.broadcastMessage("ブロックの置き換えまであと10分です！");
                    System.out.println("10分");
                } else if (getTaskId() == 200) { // 20 ticks = 1 second
                    Bukkit.broadcastMessage("ブロックの置き換えまであと10秒です！");
                    System.out.println("10秒");
                }
                // 置き換えを行いたい範囲の座標を指定する
                World world = Bukkit.getWorld("world"); // ワールド名を適宜変更する
                int minX = -35;
                int minY = -60;
                int minZ = 29;
                int maxX = 9;
                int maxY = -13;
                int maxZ = 73;

                // 置き換える前のブロックの種類と置き換える後のブロックの種類を指定する
                Material originalBlockType = Material.AIR;
                Material replacementBlockType = Material.COAL_ORE;

                // 指定した範囲内の全てのブロックをチェックし、置き換える
                for (int x = minX; x <= maxX; x++) {
                    for (int y = minY; y <= maxY; y++) {
                        for (int z = minZ; z <= maxZ; z++) {
                            Location location = new Location(world, x, y, z);
                            if (location.getBlock().getType() == originalBlockType) {
                                location.getBlock().setType(replacementBlockType);
                            }
                        }
                    }
                }
            }
        };

        // タスクを1時間ごとに実行するようにスケジュールする
        int taskId = task.runTaskTimer(this, interval, interval).getTaskId();

        return taskId;
    }

    private void stopBlockReplaceTask() {
        // ブロックの置き換えタスクを停止する
        Bukkit.getScheduler().cancelTask(taskId);
    }
}