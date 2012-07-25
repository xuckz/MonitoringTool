package mr.xuckz.monitoringTool.snmp.data.device.network;

import java.util.Date;

public class Network
{
    Integer index;
    String description;
	private String type;
    long bytes_in = 0;
    long bytes_out = 0;
    long delta_in;       //per second
    long delta_out;      //per second
    Date date_old;

	public Network(){}

    public Network(String description, String type, Integer index, long bytes_in, long bytes_out, Date date)
    {
        this.date_old = date;
		this.type = type;

        this.description = description;
        this.index = index;

        this.delta_in = 0;
        this.delta_out = 0;

        this.bytes_in = bytes_in;
        this.bytes_out = bytes_out;
    }

    public void update(long bytes_in, long bytes_out)
    {
        Date date_update = new Date();
        long difference_in_s = (date_update.getTime() - date_old.getTime()) / 1000;  //to calculate bandwith per second
        this.date_old = date_update;

        if (difference_in_s != 0)
        {
            this.delta_in = (bytes_in - this.bytes_in) / difference_in_s;
            this.delta_out = (bytes_out - this.bytes_out) / difference_in_s;

            this.bytes_in = bytes_in;
            this.bytes_out = bytes_out;
        }
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Integer getIndex()
    {
        return index;
    }

    public void setIndex(Integer index)
    {
        this.index = index;
    }

    public long getBytes_in()
    {
        return bytes_in;
    }

    public void setBytes_in(long bytes_in)
    {
        this.bytes_in = bytes_in;
    }

    public long getBytes_out()
    {
        return bytes_out;
    }

    public void setBytes_out(long bytes_out)
    {
        this.bytes_out = bytes_out;
    }

    public long getDelta_in()
    {
        return delta_in;
    }

    public void setDelta_in(long delta_in)
    {
        this.delta_in = delta_in;
    }

    public long getDelta_out()
    {
        return delta_out;
    }

    public void setDelta_out(long delta_out)
    {
        this.delta_out = delta_out;
    }

    public Date getDate_old()
    {
        return date_old;
    }

    public void setDate_old(Date date_old)
    {
        this.date_old = date_old;
    }

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}
}
