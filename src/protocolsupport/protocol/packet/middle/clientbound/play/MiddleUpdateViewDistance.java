package protocolsupport.protocol.packet.middle.clientbound.play;

import io.netty.buffer.ByteBuf;
import protocolsupport.protocol.packet.middle.ClientBoundMiddlePacket;
import protocolsupport.protocol.serializer.VarNumberSerializer;

public abstract class MiddleUpdateViewDistance extends ClientBoundMiddlePacket {

	protected int distance;

	public MiddleUpdateViewDistance(MiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void readServerData(ByteBuf serverdata) {
		distance = VarNumberSerializer.readVarInt(serverdata);
	}

}
