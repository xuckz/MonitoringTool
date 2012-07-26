package mr.xuckz.monitoringTool.snmp.data.device;

import org.snmp4j.smi.OID;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public enum DeviceType
{
	CPU(new OID("1.3.6.1.2.1.25.3.1.3")),
	NETWORK(new OID("1.3.6.1.2.1.25.3.1.4")),
    UNKNOWN(new OID());

	private OID oid;

	private DeviceType(OID oid)
	{
		this.oid = oid;
	}

	private static final Map<OID, DeviceType> lookup = new HashMap();

	static
	{
		for (DeviceType dt : DeviceType.values())
			lookup.put(dt.getOID(), dt);
	}

	private OID getOID()
	{
		return this.oid;
	}

	public static DeviceType get(OID oid)
	{
		if(lookup.containsKey(oid))
			return lookup.get(oid);

		else
			return UNKNOWN;
	}
}
