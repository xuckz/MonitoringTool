package mr.xuckz.monitoringTool.snmp.data.storage;

import org.snmp4j.smi.OID;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("unchecked")
public enum StorageType
{
	OTHER(new OID("1.3.6.1.2.1.25.2.1.1")),
	RAM(new OID("1.3.6.1.2.1.25.2.1.2")),
	VIRTUAL_MEMORY(new OID("1.3.6.1.2.1.25.2.1.3")),
	FIXED_DISK(new OID("1.3.6.1.2.1.25.2.1.4")),
	REMOVABLE_DISK(new OID("1.3.6.1.2.1.25.2.1.5")),
	FLOPPY_DISK(new OID("1.3.6.1.2.1.25.2.1.6")),
	COMPACT_DISK(new OID("1.3.6.1.2.1.25.2.1.7")),
	RAM_DISK(new OID("1.3.6.1.2.1.25.2.1.8")),
	FLASH_MEMORY(new OID("1.3.6.1.2.1.25.2.1.9")),
	NETWORK_DISK(new OID("1.3.6.1.2.1.25.2.1.10")),
	UNKNOWN(new OID());

	private OID oid;
	private static final Map<OID, StorageType> lookup = new HashMap();

	private StorageType(OID oid)
	{
		this.oid = oid;
	}

	static
	{
		for (StorageType st : StorageType.values())
			lookup.put(st.getOID(), st);
	}

	private OID getOID()
	{
		return this.oid;
	}

	public static StorageType get(OID oid)
	{
		if(lookup.containsKey(oid))
			return lookup.get(oid);

		else
			return UNKNOWN;
	}
}
