package xyz.netherite;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.SmithingScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.inventory.SmithingMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.SmithingTemplateItem;
import org.rusherhack.client.api.events.client.EventUpdate;
import org.rusherhack.client.api.feature.module.ModuleCategory;
import org.rusherhack.client.api.feature.module.ToggleableModule;
import org.rusherhack.client.api.utils.ChatUtils;
import org.rusherhack.client.api.utils.InventoryUtils;
import org.rusherhack.core.event.subscribe.Subscribe;
import org.rusherhack.core.setting.NumberSetting;
import org.rusherhack.core.setting.Setting;
import org.rusherhack.core.utils.Timer;

import java.awt.*;
import java.util.function.Predicate;

/**
 * AutoNetherite rusherhack module
 *
 * @author xyzbtw
 */
public class AutoNetherite extends ToggleableModule {

    public AutoNetherite() {
        super("AutoNetherite", ModuleCategory.MISC);
        this.registerSettings(
                this.delay
        );
    }
    private Setting<Integer> delay = new NumberSetting<>("Delay[ticks]", 1, 0, 5);

    private Timer timer = new Timer();

    int ticksPassed = 0;

    @Subscribe
    public void onTick(EventUpdate event) {
        if (mc.player == null || mc.level == null) return;

        ticksPassed++;
        if (ticksPassed < delay.getValue()) return;
        ticksPassed = 0;


        if (!(mc.screen instanceof SmithingScreen screen)) return;


        SmithingMenu menu = screen.getMenu();

        int templateSlot = findItem(
                itemStack -> itemStack.getItem() instanceof SmithingTemplateItem,
                menu
        );
        int netheriteSlot = findItem(Items.NETHERITE_INGOT, menu);

        int diamondItem = findItem(this::isDiamondThing, menu);

        if (templateSlot == -1 && !menu.getSlot(0).hasItem()){
            ChatUtils.print("No templates in inventory, disabling", Style.EMPTY.withColor(Color.RED.getRGB()));
            this.toggle();
            return;
        }

        if (diamondItem == -1 && !menu.getSlot(1).hasItem()){
            ChatUtils.print("No diamond item to upgrade in inventory, disabling", Style.EMPTY.withColor(Color.RED.getRGB()));
            this.toggle();
            return;
        }

        if (netheriteSlot == -1 && !menu.getSlot(2).hasItem()){
            ChatUtils.print("No netherite in inventory, disabling", Style.EMPTY.withColor(Color.RED.getRGB()));
            this.toggle();
            return;
        }

        if (menu.getSlot(3).hasItem()){
            InventoryUtils.clickSlot(3, true);
            return;
        }

        if (!menu.getSlot(0).hasItem()){
            InventoryUtils.clickSlot(templateSlot, true);
            System.out.println("CLICKING ON TEMPLATE SLOT " + templateSlot);
            return;
        }

        if (!menu.getSlot(1).hasItem()){
            InventoryUtils.clickSlot(diamondItem, true);
            System.out.println("CLICKING ON DIAMOND SLOT " + diamondItem);
            return;
        }

        if (!menu.getSlot(2).hasItem()){
            InventoryUtils.clickSlot(netheriteSlot, true);
            System.out.println("CLICKING ON NETHERITE SLOT " + netheriteSlot);
            return;
        }
    }

    public static int findItem(Item item, SmithingMenu menu) {
        Predicate<ItemStack> predicate = stack -> stack.getItem().equals(item);
        return findItem(predicate, menu);
    }

    public static int findItem(Predicate<ItemStack> predicate, SmithingMenu menu) {
        int maxSlots = menu.slots.size();


        for (int i = 4; i < maxSlots; i++) {
            if (predicate.test(menu.getSlot(i).getItem())) {
                return i;
            }
        }

        return -1;
    }



    private boolean isDiamondThing(ItemStack stack){
        boolean diamond = false;

        if (stack.getItem().equals(Items.DIAMOND_AXE)) diamond = true;
        if (stack.getItem().equals(Items.DIAMOND_HOE)) diamond = true;
        if (stack.getItem().equals(Items.DIAMOND_PICKAXE)) diamond = true;
        if (stack.getItem().equals(Items.DIAMOND_SHOVEL)) diamond = true;
        if (stack.getItem().equals(Items.DIAMOND_SWORD)) diamond = true;
        if (stack.getItem().equals(Items.DIAMOND_HELMET)) diamond = true;
        if (stack.getItem().equals(Items.DIAMOND_CHESTPLATE)) diamond = true;
        if (stack.getItem().equals(Items.DIAMOND_LEGGINGS)) diamond = true;
        if (stack.getItem().equals(Items.DIAMOND_BOOTS)) diamond = true;

        return diamond;
    }




}
