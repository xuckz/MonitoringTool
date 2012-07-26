package mr.xuckz.monitoringTool.snmp.data.storage;

import org.snmp4j.smi.OID;

public class StorageFactory
{
	public static Storage getStorage(Integer index, String description, OID storageTypeOID, Integer allocation_units, Integer bytes_size, Integer bytes_used)
	{
		return new Storage(index, description, StorageType.get(storageTypeOID), allocation_units, bytes_size, bytes_used);
	}
}
