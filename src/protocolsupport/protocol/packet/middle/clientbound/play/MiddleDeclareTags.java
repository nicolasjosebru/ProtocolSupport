package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.ArraySerializer;
import protocolsupport.protocol.serializer.StringSerializer;

public abstract class MiddleDeclareTags extends ClientBoundMiddlePacket {

	public MiddleDeclareTags(MiddlePacketInit init) {
		super(init);
	}

	protected Tag[] blocks;
	protected Tag[] items;
	protected Tag[] fluids;
	protected Tag[] entities;

	@Override
	protected void readServerData(ByteBuf serverdata) {
		blocks = readTags(serverdata);
		items = readTags(serverdata);
		fluids = readTags(serverdata);
		entities = readTags(serverdata);
	}

	@Override
	protected void cleanup() {
		blocks = null;
		items = null;
		fluids = null;
		entities = null;
	}

	protected static Tag[] readTags(ByteBuf from) {
		return ArraySerializer.readVarIntTArray(from, Tag.class, lFrom -> {
			return new Tag(StringSerializer.readVarIntUTF8String(lFrom), ArraySerializer.readVarIntVarIntArray(lFrom));
		});
	}

	protected static class Tag {
		protected final String tagId;
		protected final int[] taggedIds;
		public Tag(String tagId, int[] taggedIds) {
			this.tagId = tagId;
			this.taggedIds = taggedIds;
		}
		public String getTagId() {
			return tagId;
		}
		public int[] getTaggedIds() {
			return taggedIds;
		}
	}

}
