package com.xifeng.cot_complement.traits.tools;

import com.xifeng.cot_complement.utils.Functions;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.data.NBTConverter;
import crafttweaker.mc1120.enchantments.MCEnchantmentDefinition;
import crafttweaker.mc1120.events.handling.MCBlockBreakEvent;
import crafttweaker.mc1120.events.handling.MCBlockHarvestDropsEvent;
import crafttweaker.mc1120.events.handling.MCEntityLivingHurtEvent;
import crafttweaker.mc1120.events.handling.MCPlayerBreakSpeedEvent;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.BlockEvent;
import slimeknights.mantle.util.RecipeMatch;
import slimeknights.tconstruct.library.modifiers.IToolMod;
import slimeknights.tconstruct.library.modifiers.ModifierTrait;
import slimeknights.tconstruct.library.traits.ITrait;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class ToolTrait extends ModifierTrait implements ITrait {

    Functions.AfterBlockBreak afterBlockBreak = null;
    Functions.BeforeBlockBreak beforeBlockBreak = null;
    Functions.BlockHarvestDrops onBlockHarvestDrops = null;
    Functions.Damage calcDamage = null;
    Functions.IsCriticalHit calcCrit = null;
    Functions.MiningSpeed getMiningSpeed = null;
    Functions.OnHit onHit = null;
    Functions.OnUpdate onUpdate = null;
    Functions.AfterHit afterHit = null;
    Functions.KnockBack calcKnockBack = null;
    Functions.OnBlock onBlock = null;
    Functions.OnToolDamage onToolDamage = null;
    Functions.OnToolHeal calcToolHeal = null;
    Functions.OnToolRepair onToolRepair = null;
    Functions.OnPlayerHurt onPlayerHurt = null;
    Functions.ApplyEffect applyEffect = null;
    Functions.ApplyTogetherToolTrait applyTogetherToolTrait = null;
    Functions.ApplyTogetherToolEnchantment applyTogetherToolEnchantment = null;
    Functions.ExtraToolInfo extraToolInfo = null;
    String localizedName = null;
    String localizedDescription = null;
    boolean hidden = false;
    private final ToolTraitRepresentation thisTrait = new ToolTraitRepresentation(this);

    public ToolTrait(@Nonnull String identifier, int color, int maxLevel, int countPerLevel) {
        super(identifier, color, maxLevel, countPerLevel);

    }

    @Override
    public boolean isHidden() {
        return hidden;
    }

    @Override
    public void onPlayerHurt(ItemStack tool, EntityPlayer player, EntityLivingBase attacker, LivingHurtEvent event) {
        if (onPlayerHurt != null) {
            onPlayerHurt.handle(thisTrait, CraftTweakerMC.getIItemStack(tool), CraftTweakerMC.getIPlayer(player), CraftTweakerMC.getIEntityLivingBase(attacker), new MCEntityLivingHurtEvent(event));
        } else {
            super.onPlayerHurt(tool, player, attacker, event);
        }
    }

    @Override
    public void onUpdate(ItemStack tool, World world, Entity entity, int itemSlot, boolean isSelected) {
        if (onUpdate != null) {
            onUpdate.handle(thisTrait, CraftTweakerMC.getIItemStack(tool), CraftTweakerMC.getIWorld(world), CraftTweakerMC.getIEntity(entity), itemSlot, isSelected);
        } else {
            super.onUpdate(tool, world, entity, itemSlot, isSelected);
        }
    }

    @Override
    public void miningSpeed(ItemStack tool, PlayerEvent.BreakSpeed event) {
        if (getMiningSpeed != null) {
            getMiningSpeed.handle(thisTrait, CraftTweakerMC.getIItemStack(tool), new MCPlayerBreakSpeedEvent(event));
        } else {
            super.miningSpeed(tool, event);
        }
    }

    @Override
    public void beforeBlockBreak(ItemStack tool, BlockEvent.BreakEvent event) {
        if (beforeBlockBreak != null) {
            beforeBlockBreak.handle(thisTrait, CraftTweakerMC.getIItemStack(tool), new MCBlockBreakEvent(event));
        } else {
            super.beforeBlockBreak(tool, event);
        }
    }

    @Override
    public void afterBlockBreak(ItemStack tool, World world, IBlockState state, BlockPos pos, EntityLivingBase player, boolean wasEffective) {
        if (afterBlockBreak != null) {
            afterBlockBreak.handle(thisTrait, CraftTweakerMC.getIItemStack(tool), CraftTweakerMC.getIWorld(world), CraftTweakerMC.getBlockState(state), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getIEntityLivingBase(player), wasEffective);
        } else {
            super.afterBlockBreak(tool, world, state, pos, player, wasEffective);
        }
    }

    @Override
    public void blockHarvestDrops(ItemStack tool, BlockEvent.HarvestDropsEvent event) {
        if (onBlockHarvestDrops != null) {
            onBlockHarvestDrops.handle(thisTrait, CraftTweakerMC.getIItemStack(tool), new MCBlockHarvestDropsEvent(event));
        } else {
            super.blockHarvestDrops(tool, event);
        }
    }

    @Override
    public boolean isCriticalHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target) {
        if (calcCrit != null) {
            return calcCrit.handle(thisTrait, CraftTweakerMC.getIItemStack(tool), CraftTweakerMC.getIEntityLivingBase(player), CraftTweakerMC.getIEntityLivingBase(target));
        } else {
            return super.isCriticalHit(tool, player, target);
        }
    }

    @Override
    public float damage(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float newDamage, boolean isCritical) {
        if (calcDamage != null) {
            return calcDamage.handle(thisTrait, CraftTweakerMC.getIItemStack(tool), CraftTweakerMC.getIEntityLivingBase(player), CraftTweakerMC.getIEntityLivingBase(target), damage, newDamage, isCritical);
        } else {
            return super.damage(tool, player, target, damage, newDamage, isCritical);
        }
    }

    @Override
    public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical) {
        if (onHit != null) {
            onHit.handle(thisTrait, CraftTweakerMC.getIItemStack(tool), CraftTweakerMC.getIEntityLivingBase(player), CraftTweakerMC.getIEntityLivingBase(target), damage, isCritical);
        } else {
            super.onHit(tool, player, target, damage, isCritical);
        }
    }

    @Override
    public void afterHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damageDealt, boolean wasCritical, boolean wasHit) {
        if (afterHit != null) {
            afterHit.handle(thisTrait, CraftTweakerMC.getIItemStack(tool), CraftTweakerMC.getIEntityLivingBase(player), CraftTweakerMC.getIEntityLivingBase(target), damageDealt, wasCritical, wasHit);
        } else {
            super.afterHit(tool, player, target, damageDealt, wasCritical, wasHit);
        }
    }

    @Override
    public float knockBack(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, float knockback, float newKnockback, boolean isCritical) {
        if (calcKnockBack != null) {
            return calcKnockBack.handle(thisTrait, CraftTweakerMC.getIItemStack(tool), CraftTweakerMC.getIEntityLivingBase(player), CraftTweakerMC.getIEntityLivingBase(target), damage, knockback, newKnockback, isCritical);
        } else {
            return super.knockBack(tool, player, target, damage, knockback, newKnockback, isCritical);
        }
    }

    @Override
    public void onBlock(ItemStack tool, EntityPlayer player, LivingHurtEvent event) {
        if (onBlock != null) {
            onBlock.handle(thisTrait, CraftTweakerMC.getIItemStack(tool), CraftTweakerMC.getIPlayer(player), new MCEntityLivingHurtEvent(event));
        } else {
            super.onBlock(tool, player, event);
        }
    }

    @Override
    public int onToolDamage(ItemStack tool, int damage, int newDamage, EntityLivingBase entity) {
        if (onToolDamage != null) {
            return onToolDamage.handle(thisTrait, CraftTweakerMC.getIItemStack(tool), damage, newDamage, CraftTweakerMC.getIEntityLivingBase(entity));
        } else {
            return super.onToolDamage(tool, damage, newDamage, entity);
        }
    }

    @Override
    public int onToolHeal(ItemStack tool, int amount, int newAmount, EntityLivingBase entity) {
        if (calcToolHeal != null) {
            return calcToolHeal.handle(thisTrait, CraftTweakerMC.getIItemStack(tool), amount, newAmount, CraftTweakerMC.getIEntityLivingBase(entity));
        } else {
            return super.onToolHeal(tool, amount, newAmount, entity);
        }
    }

    @Override
    public void onRepair(ItemStack tool, int amount) {
        if (onToolRepair != null) {
            onToolRepair.handle(thisTrait, CraftTweakerMC.getIItemStack(tool), amount);
        } else {
            super.onRepair(tool, amount);
        }
    }

    @Override
    public void applyEffect(NBTTagCompound rootCompound, NBTTagCompound modifierTag) {
        if (applyEffect != null) {
            applyEffect.handle(NBTConverter.from(rootCompound,true), NBTConverter.from(modifierTag, true));
        }
    }

    @Override
    public boolean canApplyTogether(IToolMod otherModifier) {
        if (applyTogetherToolTrait != null) {
            return applyTogetherToolTrait.handle(thisTrait, otherModifier.getIdentifier());
        }
        return super.canApplyTogether(otherModifier);
    }

    @Override
    public boolean canApplyTogether(Enchantment enchantment) {
        if (applyTogetherToolEnchantment != null) {
            return applyTogetherToolEnchantment.handle(thisTrait, new MCEnchantmentDefinition(enchantment));
        }
        return super.canApplyTogether(enchantment);
    }

    @Override
    public List<String> getExtraInfo(ItemStack tool, NBTTagCompound modifierTag) {
        if (extraToolInfo != null) {
            return Arrays.asList(extraToolInfo.handle(thisTrait, CraftTweakerMC.getIItemStack(tool), NBTConverter.from(modifierTag, true)));
        }
        return super.getExtraInfo(tool, modifierTag);
    }

    @Override
    public String getLocalizedName() {
        if (localizedName != null) {
            return localizedName;
        }
        return super.getLocalizedName();
    }

    @Override
    public String getLocalizedDesc() {
        if (localizedDescription != null) {
            return localizedDescription;
        }
        return super.getLocalizedDesc();
    }

    public void addItem(RecipeMatch recipeMatch) {
        this.items.add(recipeMatch);
    }

    public void addRecipeMatch(RecipeMatch recipeMatch) {
        this.items.add(recipeMatch);
    }
}
