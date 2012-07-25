package mr.xuckz.monitoringTool.snmp.data;

import mr.xuckz.monitoringTool.snmp.util.SnmpConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SnmpObjectType
{
	protected static final Logger log = LoggerFactory.getLogger(SnmpObjectType.class);

	protected SnmpConnection target;

	public SnmpObjectType(SnmpConnection target)
	{
		this.target = target;
	}

	public String toHtmlString()
	{
		return toString().replaceAll("\n", "<br>");
	}

	public String bytesToString(long bytes)
	{
		double result = 0.00;

		if (bytes <= 1024)
		{
			return bytes + " B";
		}

		else if (bytes > 1024 && bytes <= 1048576)
		{
			result = (new Double(bytes) / 1024 * 100);
			return new Double(new Double(java.lang.Math.round(result)) / 100).toString() + " KB";
		}

		else if (bytes > 1048576 && (bytes <= 1073741824))
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

	public boolean update()
	{
		return false;
	}

	public boolean initialize()
	{
		return false;
	}

	public abstract String toString();
}
