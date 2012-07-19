package mr.xuckz.monitoringTool.snmp.data;

import mr.xuckz.monitoringTool.snmp.data.type.StorageType;

public class Storage
{
    private String name;
    private StorageType storageType;
    private Integer bytes_size;
    private Integer bytes_free;
    private Integer bytes_used;

    public Storage(String name, StorageType storageType, Integer bytes_size, Integer bytes_free, Integer bytes_used)
    {
        this.name = name;
        this.storageType = storageType;
        this.bytes_size = bytes_size;
        this.bytes_free = bytes_free;
        this.bytes_used = bytes_used;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public StorageType getStorageType()
    {
        return storageType;
    }

    public void setStorageType(StorageType storageType)
    {
        this.storageType = storageType;
    }

    public Integer getBytes_size()
    {
        return bytes_size;
    }

    public void setBytes_size(Integer bytes_size)
    {
        this.bytes_size = bytes_size;
    }

    public Integer getBytes_free()
    {
        return bytes_free;
    }

    public void setBytes_free(Integer bytes_free)
    {
        this.bytes_free = bytes_free;
    }

    public Integer getBytes_used()
    {
        return bytes_used;
    }

    public void setBytes_used(Integer bytes_used)
    {
        this.bytes_used = bytes_used;
    }
}
