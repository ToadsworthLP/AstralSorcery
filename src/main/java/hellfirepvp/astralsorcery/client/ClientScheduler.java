/*******************************************************************************
 * HellFirePvP / Astral Sorcery 2017
 *
 * This project is licensed under GNU GENERAL PUBLIC LICENSE Version 3.
 * The source code is available on github: https://github.com/HellFirePvP/AstralSorcery
 * For further details, see the License file there.
 ******************************************************************************/

package hellfirepvp.astralsorcery.client;

import hellfirepvp.astralsorcery.common.auxiliary.tick.ITickHandler;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This class is part of the Astral Sorcery Mod
 * The complete source code for this mod can be found on github.
 * Class: ClientScheduler
 * Created by HellFirePvP
 * Date: 11.08.2016 / 13:04
 */
public class ClientScheduler implements ITickHandler {

    private static long clientTick = 0;

    private boolean inTick = false;
    private Map<Runnable, Integer> queuedRunnables = new HashMap<>();
    private Map<Runnable, Integer> waitingRunnables = new HashMap<>();

    @Override
    public void tick(TickEvent.Type type, Object... context) {
        clientTick++;

        inTick = true;
        Iterator<Runnable> iterator = queuedRunnables.keySet().iterator();
        while (iterator.hasNext()) {
            Runnable r = iterator.next();
            int delay = queuedRunnables.get(r);
            delay--;
            if(delay <= 0) {
                r.run();
                iterator.remove();
            } else {
                queuedRunnables.put(r, delay);
            }
        }
        inTick = false;
        for (Map.Entry<Runnable, Integer> waiting : waitingRunnables.entrySet()) {
            queuedRunnables.put(waiting.getKey(), waiting.getValue());
        }
        waitingRunnables.clear();
    }

    public static long getClientTick() {
        return clientTick;
    }

    @Override
    public EnumSet<TickEvent.Type> getHandledTypes() {
        return EnumSet.of(TickEvent.Type.CLIENT);
    }

    @Override
    public boolean canFire(TickEvent.Phase phase) {
        return phase == TickEvent.Phase.END;
    }

    @Override
    public String getName() {
        return "Client Scheduler";
    }

    public void addRunnable(Runnable r, int tickDelay) {
        if(inTick) {
            waitingRunnables.put(r, tickDelay);
        } else {
            queuedRunnables.put(r, tickDelay);
        }
    }

}
