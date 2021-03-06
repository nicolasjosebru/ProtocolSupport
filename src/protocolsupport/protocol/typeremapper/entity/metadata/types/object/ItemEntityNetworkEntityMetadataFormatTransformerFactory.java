package protocolsupport.protocol.typeremapper.entity.metadata.types.object;

import protocolsupport.protocol.typeremapper.entity.metadata.object.value.NetworkEntityMetadataObjectIndexValueNoOpTransformer;
import protocolsupport.protocol.typeremapper.entity.metadata.types.base.BaseNetworkEntityMetadataFormatTransformerFactory;
import protocolsupport.protocol.types.networkentity.metadata.NetworkEntityMetadataObjectIndex;
import protocolsupport.protocol.utils.ProtocolVersionsHelper;

public class ItemEntityNetworkEntityMetadataFormatTransformerFactory extends BaseNetworkEntityMetadataFormatTransformerFactory {

	public static final ItemEntityNetworkEntityMetadataFormatTransformerFactory INSTANCE = new ItemEntityNetworkEntityMetadataFormatTransformerFactory();

	protected ItemEntityNetworkEntityMetadataFormatTransformerFactory() {
		addTransformer(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Item.ITEM, 7), ProtocolVersionsHelper.UP_1_14);
		addTransformer(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Item.ITEM, 6), ProtocolVersionsHelper.RANGE__1_10__1_13_2);
		addTransformer(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Item.ITEM, 5), ProtocolVersionsHelper.ALL_1_9);
		addTransformer(new NetworkEntityMetadataObjectIndexValueNoOpTransformer(NetworkEntityMetadataObjectIndex.Item.ITEM, 10), ProtocolVersionsHelper.DOWN_1_8);
	}

}
