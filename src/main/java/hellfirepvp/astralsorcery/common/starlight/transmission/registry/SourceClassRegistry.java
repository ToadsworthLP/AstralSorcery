package hellfirepvp.astralsorcery.common.starlight.transmission.registry;

import hellfirepvp.astralsorcery.common.starlight.IIndependentStarlightSource;
import hellfirepvp.astralsorcery.common.starlight.transmission.base.crystal.SimpleIndependentCrystalSource;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
* This class is part of the Astral Sorcery Mod
* The complete source code for this mod can be found on github.
* Class: SourceClassRegistry
* Created by HellFirePvP
* Date: 04.08.2016 / 16:33
*/
public class SourceClassRegistry {

    private static Map<String, SourceProvider> providerMap = new HashMap<>();

    @Nullable
    public static SourceProvider getProvider(String identifier) {
        return providerMap.get(identifier);
    }

    public static void register(SourceProvider provider) {
        if(providerMap.containsKey(provider.getIdentifier())) throw new RuntimeException("Already registered identifier SourceProvider: " + provider.getIdentifier());
        providerMap.put(provider.getIdentifier(), provider);
    }

    public static void setupRegistry() {
        register(new SimpleIndependentCrystalSource.Provider());
    }

    public static interface SourceProvider {

        public IIndependentStarlightSource provideEmptySource();

        public String getIdentifier();

    }

}