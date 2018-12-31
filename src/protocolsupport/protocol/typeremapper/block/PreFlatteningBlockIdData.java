package protocolsupport.protocol.typeremapper.block;

import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.Rotatable;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import protocolsupport.api.MaterialAPI;
import protocolsupport.protocol.typeremapper.legacy.LegacyBlockFace;
import protocolsupport.protocol.utils.MappingsData;
import protocolsupport.protocol.utils.minecraftdata.MinecraftData;
import protocolsupport.utils.JsonUtils;
import protocolsupport.utils.Utils;
import protocolsupport.zplatform.ServerPlatform;

@SuppressWarnings("deprecation")
public class PreFlatteningBlockIdData {

	protected static final int[] toLegacyId = new int[MinecraftData.BLOCKDATA_COUNT];

	protected static int formLegacyCombinedId(int id, int data) {
		return (id << 4) | data;
	}

	protected static void register(String modernBlockState, int legacyId, int legacyData) {
		toLegacyId[ServerPlatform.get().getMiscUtils().getBlockDataNetworkId(Bukkit.createBlockData(modernBlockState))] = formLegacyCombinedId(legacyId, legacyData);
	}

	protected static byte getBannerLegacyData(BlockData banner) {
		if (banner instanceof Rotatable) {
			return LegacyBlockFace.getLegacyRotatableId(((Rotatable) banner).getRotation());
		} else if (banner instanceof Directional) {
			return LegacyBlockFace.getLegacyDirectionalId(((Directional) banner).getFacing());
		} else {
			return 1;
		}
	}

	protected static byte getSkullLegacyData(BlockData skull) {
		if (skull instanceof Directional) {
			return LegacyBlockFace.getLegacyDirectionalId(((Directional) skull).getFacing());
		} else {
			return 1;
		}
	}

	static {
		Arrays.fill(toLegacyId, formLegacyCombinedId(1, 0));
		//air
		toLegacyId[0] = 0;
		//load list of autogenerated block mappings
		for (JsonElement element : Utils.iterateJsonArrayResource(MappingsData.getResourcePath("preflatteningblockiddata.json"))) {
			JsonObject object = element.getAsJsonObject();
			register(JsonUtils.getString(object, "blockdata"), JsonUtils.getInt(object, "legacyid"), JsonUtils.getInt(object, "legacydata"));
		}
		//manual mappings for some blocks
		for (Material material : Arrays.asList(
			Material.CREEPER_HEAD, Material.CREEPER_WALL_HEAD,
			Material.ZOMBIE_HEAD, Material.ZOMBIE_WALL_HEAD,
			Material.SKELETON_SKULL, Material.SKELETON_WALL_SKULL,
			Material.WITHER_SKELETON_SKULL, Material.WITHER_SKELETON_WALL_SKULL,
			Material.PLAYER_HEAD, Material.PLAYER_WALL_HEAD,
			Material.DRAGON_HEAD, Material.DRAGON_WALL_HEAD
		)) {
			for (BlockData blockdata : MaterialAPI.getBlockDataList(material)) {
				register(blockdata.getAsString(), Material.LEGACY_SKULL.getId(), getSkullLegacyData(blockdata));
			}
		}
		for (Material material : Arrays.asList(
			Material.BLACK_BANNER, Material.BLUE_BANNER, Material.BROWN_BANNER, Material.CYAN_BANNER,
			Material.GRAY_BANNER, Material.GREEN_BANNER, Material.LIGHT_BLUE_BANNER, Material.LIGHT_GRAY_BANNER,
			Material.LIME_BANNER, Material.MAGENTA_BANNER, Material.ORANGE_BANNER, Material.PINK_BANNER,
			Material.PURPLE_BANNER, Material.RED_BANNER, Material.WHITE_BANNER, Material.YELLOW_BANNER
		)) {
			for (BlockData blockdata : MaterialAPI.getBlockDataList(material)) {
				register(blockdata.getAsString(), Material.LEGACY_STANDING_BANNER.getId(), getBannerLegacyData(blockdata));
			}
		}
		for (Material material : Arrays.asList(
			Material.BLACK_WALL_BANNER, Material.BLUE_WALL_BANNER, Material.BROWN_WALL_BANNER, Material.CYAN_WALL_BANNER,
			Material.GRAY_WALL_BANNER, Material.GREEN_WALL_BANNER, Material.LIGHT_BLUE_WALL_BANNER, Material.LIGHT_GRAY_WALL_BANNER,
			Material.LIME_WALL_BANNER, Material.MAGENTA_WALL_BANNER, Material.ORANGE_WALL_BANNER, Material.PINK_WALL_BANNER,
			Material.PURPLE_WALL_BANNER, Material.RED_WALL_BANNER, Material.WHITE_WALL_BANNER, Material.YELLOW_WALL_BANNER
		)) {
			for (BlockData blockdata : MaterialAPI.getBlockDataList(material)) {
				register(blockdata.getAsString(), Material.LEGACY_WALL_BANNER.getId(), getBannerLegacyData(blockdata));
			}
		}
	}

	public static int getCombinedId(int modernId) {
		return toLegacyId[modernId];
	}

	public static int getIdFromCombinedId(int legacyId) {
		return legacyId >> 4;
	}

	public static int getDataFromCombinedId(int oldId) {
		return oldId & 0xF;
	}

	public static int convertCombinedIdToM12(int blockstate) {
		return (getIdFromCombinedId(blockstate)) | (getDataFromCombinedId(blockstate) << 12);
	}

	public static int convertCombinedIdToM16(int blockstate) {
		return (getIdFromCombinedId(blockstate)) | (getDataFromCombinedId(blockstate) << 16);
	}

}
