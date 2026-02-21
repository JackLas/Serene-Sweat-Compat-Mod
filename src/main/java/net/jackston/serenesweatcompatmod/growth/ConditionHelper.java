package net.jackston.serenesweatcompatmod.growth;

import com.mojang.datafixers.util.Pair;
import com.momosoftworks.coldsweat.util.world.WorldHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import sereneseasons.api.season.Season;
import sereneseasons.api.season.SeasonHelper;

public class ConditionHelper {
    /**
     * Main function to check if conditions for out of season growth are met
     * - A crop should see the sky (glass blocks are acceptable)
     * - A crop should have specific insulation number depending on current season
     * @param level level
     * @param cropPos position of crop to check for
     * @return true if conditions for out of season growth are met
     */
    public static boolean isOutOfSeasonGrowthAllowed(Level level, BlockPos cropPos) {
        if (!isSkyVisible(level, cropPos)) {
            return false;
        }

        return getInsulationStatus(level, cropPos) == InsulationStatus.OK;
    }

    /**
     * check if sky visible for a block with given level and position
     * @param level level
     * @param cropPos position
     * @return true if visible
     */
    public static boolean isSkyVisible(Level level, BlockPos cropPos) {
        return level.canSeeSky(cropPos);
    }

    /**
     * Check if a crop with given level and world has needed insulation for growth
     * A crop should be under ColdSweat's Hearth influence
     * - For Winter and Spring - heating mode
     * - For Summer and Autumn - cooling mode
     * @param level level
     * @param cropPos position
     * @return InsulationStatus.OK if no heating/cooling needed
     *         InsulationStatus.HeatingNeeded if Hearth's heating needed
     *         InsulationStatus.CoolingNeeded if Hearth's cooling needed
     *         InsulationStatus.Overheating if Hearth's heating should be disabled
     *         InsulationStatus.Overcooling if Hearth's cooling should be disabled
     */
    public static InsulationStatus getInsulationStatus(Level level, BlockPos cropPos) {
        //  <Cooling, Heating>
        Pair<Integer, Integer> insulation = WorldHelper.getInsulationAt(level, cropPos, 2);
        Season season = SeasonHelper.getSeasonState(level).getSeason();

        if ((season == Season.WINTER) || (season == Season.SPRING)) {
            if (insulation.getFirst() > 0 ) {
                return InsulationStatus.Overcooling;
            }

            if (insulation.getSecond() <= 0 ) {
                return InsulationStatus.HeatingNeeded;
            }
        }

        if ((season == Season.SUMMER) || (season == Season.AUTUMN)) {
            if (insulation.getSecond() > 0) {
                return InsulationStatus.Overheating;
            }
            if (insulation.getFirst() <= 0) {
                return InsulationStatus.CoolingNeeded;
            }
        }

        return InsulationStatus.OK;
    }

}
