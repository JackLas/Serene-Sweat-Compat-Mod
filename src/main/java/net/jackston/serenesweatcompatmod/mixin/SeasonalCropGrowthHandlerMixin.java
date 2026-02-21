package net.jackston.serenesweatcompatmod.mixin;
import net.jackston.serenesweatcompatmod.growth.ConditionHelper;
import sereneseasons.season.SeasonalCropGrowthHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(SeasonalCropGrowthHandler.class)
public class SeasonalCropGrowthHandlerMixin {
    /**
     * Redirect SeasonalCropGrowthHandler.isGlassAboveBlock into isOutOfSeasonGrowthAllowed
     * @param level level
     * @param cropPos position of crop to check for
     * @return true if conditions for out of season growth are met
     */
    @Redirect(method = "onCropGrowth", at = @At(value = "INVOKE", target="isGlassAboveBlock"), remap = false)
    private static boolean isOutOfSeasonGrowthAllowed(Level level, BlockPos cropPos) {
        return ConditionHelper.isOutOfSeasonGrowthAllowed(level, cropPos);
    }
}

/* todo: for testing, remove
/fill ~1 ~ ~1 ~36 ~ ~36 wheat[age=0] replace wheat
/fill ~1 ~ ~1 ~18 ~ ~18 wheat[age=0] replace wheat
/spark profiler --timeout 300
 */