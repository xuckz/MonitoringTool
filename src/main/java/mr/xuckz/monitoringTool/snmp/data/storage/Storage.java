package mr.xuckz.monitoringTool.snmp.data.storage;

import mr.xuckz.monitoringTool.snmp.data.SnmpDataObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Storage extends SnmpDataObject
{
	static final Logger log = LoggerFactory.getLogger(Storage.class);

    private String description;
    private StorageType storageType;
	private Integer allocation_units;
    private long bytes_size;
    private long bytes_free;
    private long bytes_used;

    public Storage(Integer index, String description, StorageType storageType, Integer allocation_units, Integer bytes_size, Integer bytes_used)
    {
		this.index = index;
        this.description = description;
        this.storageType = storageType;
		this.allocation_units = allocation_units;

        this.bytes_size = (long)(bytes_size.intValue()) * (long)(allocation_units.intValue());
        this.bytes_free = ((long)(bytes_size.intValue()) - (long)(bytes_used.intValue())) * (long)(allocation_units.intValue());
        this.bytes_used = (long)bytes_used.intValue() * (long)(allocation_units.intValue());
    }

	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		sb.append("Storage #" + this.index + " Description: " + this.description + "\n");
		sb.append("Storage #" + this.index + " Type: " + this.storageType + "\n");
		sb.append("Storage #" + this.index + " Size: " + this.bytes_size + "\n");
		sb.append("Storage #" + this.index + " Used: " + this.bytes_used + "\n");
		sb.append("Storage #" + this.index + " Free: " + this.bytes_free);

		return sb.toString();
	}

    public void update(Integer bytes_used)
    {
        this.bytes_free = (bytes_size - (long)(bytes_used.intValue())) * (long)(allocation_units.intValue());
        this.bytes_used = (long)bytes_used.intValue() * (long)(allocation_units.intValue());
    }

	public String getDescription()
	{
		return description;
	}

	public StorageType getStorageType()
	{
		return storageType;
	}

	public long getBytes_size()
	{
		return bytes_size;
	}

	public long getBytes_free()
	{
		return bytes_free;
	}

	public long getBytes_used()
	{
		return bytes_used;
	}
}
