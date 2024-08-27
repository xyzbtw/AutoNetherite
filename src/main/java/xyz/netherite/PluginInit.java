package xyz.netherite;

import org.rusherhack.client.api.RusherHackAPI;
import org.rusherhack.client.api.plugin.Plugin;

/**
 *
 * @author xyzbtw
 */
public class PluginInit extends Plugin {
	
	@Override
	public void onLoad() {
		
		this.getLogger().info("Loaded autonetherite plugin!");
		
		final AutoNetherite autoNetherite = new AutoNetherite();
		RusherHackAPI.getModuleManager().registerFeature(autoNetherite);
		
	}
	
	@Override
	public void onUnload() {
		this.getLogger().info("autonetherite plugin unloaded!");
	}
	
}