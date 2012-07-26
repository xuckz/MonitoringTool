package mr.xuckz.monitoringTool.snmp.data.storage;

import mr.xuckz.monitoringTool.config.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "Status.byClient",
                query = "from Status s where s.client = :client")
})
public class Storage
{
	static final Logger log = LoggerFactory.getLogger(Storage.class);

    @Id
	private Integer index;

    private String description;
    private StorageType storageType;
	private Integer allocation_units;
    private long bytes_size;
    private long bytes_free;
    private long bytes_used;

    @ManyToOne(cascade=CascadeType.ALL)
    private Client client;

    public Storage(Client client, Integer index, String description, StorageType storageType, Integer allocation_units, Integer bytes_size, Integer bytes_used)
    {
        this.client = client;
		this.index = index;
        this.description = description;
        this.storageType = storageType;
		this.allocation_units = allocation_units;

        this.bytes_size = (long)(bytes_size.intValue()) * (long)(allocation_units.intValue());
        this.bytes_free = ((long)(bytes_size.intValue()) - (long)(bytes_used.intValue())) * (long)(allocation_units.intValue());
        this.bytes_used = (long)bytes_used.intValue() * (long)(allocation_units.intValue());
    }

    public void update(Integer bytes_used)
    {
        this.bytes_free = (bytes_size - (long)(bytes_used.intValue())) * (long)(allocation_units.intValue());
        this.bytes_used = (long)bytes_used.intValue() * (long)(allocation_units.intValue());
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

    public Client getClient()
    {
        return client;
    }

    public void setClient(Client client)
    {
        this.client = client;
    }
}
