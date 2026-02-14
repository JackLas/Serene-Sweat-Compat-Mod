package net.jackston.serenesweatcompatmod.mixin;

import com.mojang.datafixers.util.Pair;
import net.jackston.serenesweatcompatmod.SereneSweatCompatMod;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import sereneseasons.init.ModFertility;

import com.momosoftworks.coldsweat.util.world.WorldHelper;

@Mixin(ModFertility.class)
public class ModFertilityMixin {
    @Inject (method = "isCropFertile", at = @At("HEAD"), remap = false)
    private static void isCropFertile(String cropName, Level level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        SereneSweatCompatMod.LOGGER.info(String.valueOf(WorldHelper.getTemperatureAt(level, pos)));

        Pair<Integer, Integer> insulation = WorldHelper.getInsulationAt(level, pos, 2);

        SereneSweatCompatMod.LOGGER.info(String.valueOf(insulation.getFirst()));
        SereneSweatCompatMod.LOGGER.info(String.valueOf(insulation.getSecond()));

        SereneSweatCompatMod.LOGGER.info(String.valueOf(level.canSeeSky(pos)));

        SereneSweatCompatMod.LOGGER.info("");
    }
}
