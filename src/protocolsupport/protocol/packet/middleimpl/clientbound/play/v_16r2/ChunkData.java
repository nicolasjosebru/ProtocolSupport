package protocolsupport.protocol.packet.middleimpl.clientbound.play.v_16r2;

import org.bukkit.block.Biome;

import protocolsupport.protocol.packet.PacketType;
import protocolsupport.protocol.packet.middle.clientbound.play.MiddleChunkData;
import protocolsupport.protocol.packet.middleimpl.ClientBoundPacketData;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.ItemStackSerializer;
import protocolsupport.protocol.serializer.MiscSerializer;
import protocolsupport.protocol.serializer.PositionSerializer;
import protocolsupport.protocol.serializer.VarNumberSerializer;
import protocolsupport.protocol.storage.netcache.ClientCache;
import protocolsupport.protocol.typeremapper.basic.BiomeRemapper;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData;
import protocolsupport.protocol.typeremapper.block.FlatteningBlockData.FlatteningBlockDataTable;
import protocolsupport.protocol.typeremapper.block.LegacyBlockData;
import protocolsupport.protocol.typeremapper.chunk.ChunkWriterVaries;
import protocolsupport.protocol.typeremapper.tile.TileEntityRemapper;
import protocolsupport.protocol.typeremapper.utils.MappingTable.EnumMappingTable;
import protocolsupport.protocol.typeremapper.utils.MappingTable.IdMappingTable;

public class ChunkData extends MiddleChunkData {

	public ChunkData(MiddlePacketInit init) {
		super(init);
	}

	protected final ClientCache clientCache = cache.getClientCache();

	protected final EnumMappingTable<Biome> biomeRemappingTable = BiomeRemapper.REGISTRY.getTable(version);
	protected final IdMappingTable blockDataRemappingTable = LegacyBlockData.REGISTRY.getTable(version);
	protected final FlatteningBlockDataTable flatteningBlockDataTable = FlatteningBlockData.REGISTRY.getTable(version);
	protected final TileEntityRemapper tileRemapper = TileEntityRemapper.getRemapper(version);

	@Override
	protected void writeToClient() {
		ClientBoundPacketData chunkdata = ClientBoundPacketData.create(PacketType.CLIENTBOUND_PLAY_CHUNK_SINGLE);
		PositionSerializer.writeIntChunkCoord(chunkdata, coord);
		chunkdata.writeBoolean(full);
		VarNumberSerializer.writeVarInt(chunkdata, blockMask);
		ItemStackSerializer.writeDirectTag(chunkdata, heightmaps);
		if (full) {
			VarNumberSerializer.writeVarInt(chunkdata, biomes.length);
			for (int biome : biomes) {
				VarNumberSerializer.writeVarInt(chunkdata, BiomeRemapper.mapBiome(biome, clientCache, biomeRemappingTable));
			}
		}
		MiscSerializer.writeVarIntLengthPrefixedType(chunkdata, this, (to, chunksections) -> {
			ChunkWriterVaries.writeSectionsPadded(
				to, chunksections.blockMask, 15,
				chunksections.blockDataRemappingTable,
				chunksections.flatteningBlockDataTable,
				chunksections.sections
			);
		});
		ArraySerializer.writeVarIntTArray(
			chunkdata,
			tiles,
			(to, tile) -> ItemStackSerializer.writeDirectTag(to, tileRemapper.remap(tile).getNBT())
		);
		codec.write(chunkdata);
	}

}
