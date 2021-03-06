package mr.xuckz.monitoringTool.snmp.data.device.network;

import mr.xuckz.monitoringTool.snmp.data.SnmpDataObject;

import java.util.Date;

public class Network extends SnmpDataObject
{
    String description;
	private String type;
    long bytes_in = 0;
    long bytes_out = 0;
    long delta_in;       //per second
    long delta_out;      //per second
    Date date_old;

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

	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		sb.append("Interface #").append(this.index).append(": ").append(this.description).append("\n");
		sb.append("Interface #").append(this.index).append(" type: ").append(this.type).append("\n");
		sb.append("Interface #").append(this.index).append(" total in: ").append(bytesToString(this.bytes_in)).append("\n");
		sb.append("Interface #").append(this.index).append(" total out: ").append(bytesToString(this.bytes_out)).append("\n");
		sb.append("Interface #").append(this.index).append(" bytes in per second: ").append(bytesToString(this.delta_in)).append("\n");
		sb.append("Interface #").append(this.index).append(" bytes out per second: ").append(bytesToString(this.delta_out));

		return sb.toString();
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

    public long getBytes_in()
    {
        return bytes_in;
    }

    public long getBytes_out()
    {
        return bytes_out;
    }

    public long getDelta_in()
    {
        return delta_in;
    }

    public long getDelta_out()
    {
        return delta_out;
    }

	public String getType()
	{
		return type;
	}
}
