package protocolsupport.protocol.typeremapper.entity.metadata.types.object.arrow;

import protocolsupport.api.ProtocolVersion;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.metadata.object.value.NetworkEntityMetadataObjectIndexValueTransformer;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObject;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.types.networkentity.metadata.objects.NetworkEntityMetadataObjectVarInt;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class TippedArrowNetworkEntityMetadataFormatTransformerFactory extends ArrowEntityNetworkEntityMetadataFormatTransformerFactory {

	public static final TippedArrowNetworkEntityMetadataFormatTransformerFactory INSTANCE = new TippedArrowNetworkEntityMetadataFormatTransformerFactory();

	protected TippedArrowNetworkEntityMetadataFormatTransformerFactory() {
		addTransformer(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.TippedArrow.COLOR, 9), ProtocolVersionsHelper.UP_1_16);
		addTransformer(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.TippedArrow.COLOR, 10), ProtocolVersionsHelper.RANGE__1_14__1_15_2);
		addTransformer(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.TippedArrow.COLOR, 8), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_13_1, ProtocolVersion.MINECRAFT_1_13_2));
		addTransformer(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.TippedArrow.COLOR, 7), ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_11_1, ProtocolVersion.MINECRAFT_1_13));
		addTransformer(new NetworkEntityMetadataObjectIndexValueTransformer<NetworkEntityMetadataObjectVarInt>(NetworkEntityMetadataObjectIndex.TippedArrow.COLOR, 7) {
			@Override
			public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectVarInt object) {
				int color = object.getValue();
				return new NetworkEntityMetadataObjectVarInt(color == -1 ? 0 : color);
			}
		}, ProtocolVersion.getAllBetween(ProtocolVersion.MINECRAFT_1_10, ProtocolVersion.MINECRAFT_1_11));
		addTransformer(new NetworkEntityMetadataObjectIndexValueTransformer<NetworkEntityMetadataObjectVarInt>(NetworkEntityMetadataObjectIndex.TippedArrow.COLOR, 6) {
			@Override
			public NetworkEntityMetadataObject<?> transformValue(NetworkEntityMetadataObjectVarInt object) {
				int color = object.getValue();
				return new NetworkEntityMetadataObjectVarInt(color == -1 ? 0 : color);
			}
		}, ProtocolVersionsHelper.ALL_1_9);
	}

}
