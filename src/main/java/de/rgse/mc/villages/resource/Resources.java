package de.rgse.mc.villages.resource;

import de.rgse.mc.villages.VillagesMod;
import de.rgse.mc.villages.util.IdentifierUtil;
import de.rgse.mc.villages.util.NameUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;

import java.util.Collection;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Resources {

    private static final SimpleSynchronousResourceReloadListener RESOURCE_LISTENER = new SimpleSynchronousResourceReloadListener() {
        @Override
        public Identifier getFabricId() {
            return IdentifierUtil.create("resource_listener");
        }

        @Override
        public void reload(ResourceManager manager) {
            initNames(manager);
        }

        private void initNames(ResourceManager manager) {
            Collection<Identifier> names = manager.findResources("names", path -> path.endsWith(".json"));
            NameUtil.initialize(names, manager);
        }
    };

    public static void init() {
        ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(RESOURCE_LISTENER);
    }
}
