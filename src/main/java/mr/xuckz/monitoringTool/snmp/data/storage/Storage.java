package mr.xuckz.monitoringTool.snmp.data.storage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Storage
{
	static final Logger log = LoggerFactory.getLogger(Storage.class);

	private Integer index;
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
		String toString = "";
		toString += "Description: " + description + "\n";
		toString += "Type: " + storageType.toString() + "\n";
		toString += "Size: " + bytesToString(bytes_size).toString() + "\n";
		toString += "Used: " + bytesToString(bytes_used).toString() + "\n";
		toString += "Free: " + bytesToString(bytes_free).toString();

		return toString;
	}

	public String toHtmlString()
	{
		return toString().replaceAll("\n", "<br>");
	}

	private String bytesToString(long bytes)
	{
		double result = 0.00;

		if(bytes <= 1024)
		    return bytes + " B";

		else if(bytes > 1024 && bytes <= 1048576)
		{
			result = (new Double(bytes) / 1024 * 100);
			return new Double(new Double(java.lang.Math.round(result)) / 100).toString() + " KB";
		}

		else if(bytes > 1048576 && (bytes <= 1073741824))
		{
			result = (new Double(bytes) / 1024 / 1024 * 100);
			return new Double(new Double(java.lang.Math.round(result)) / 100).toString() + " MB";
		}

		else
		{
			result = (new Double(bytes) / 1024 / 1024 / 1024 * 100);
			return new Double(new Double(java.lang.Math.round(result)) / 100).toString() + " GB";
		}
	}

	public Integer getIndex()
	{
		return index;
	}

	public void setIndex(Integer index)
	{
		this.index = index;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public StorageType getStorageType()
	{
		return storageType;
	}

	public void setStorageType(StorageType storageType)
	{
		this.storageType = storageType;
	}

	public Integer getAllocation_units()
	{
		return allocation_units;
	}

	public void setAllocation_units(Integer allocation_units)
	{
		this.allocation_units = allocation_units;
	}

	public long getBytes_size()
	{
		return bytes_size;
	}

	public void setBytes_size(long bytes_size)
	{
		this.bytes_size = bytes_size;
	}

	public long getBytes_free()
	{
		return bytes_free;
	}

	public void setBytes_free(long bytes_free)
	{
		this.bytes_free = bytes_free;
	}

	public long getBytes_used()
	{
		return bytes_used;
	}

	public void setBytes_used(long bytes_used)
	{
		this.bytes_used = bytes_used;
	}
}
