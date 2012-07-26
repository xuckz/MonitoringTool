package mr.xuckz.monitoringTool.snmp.data;

public abstract class SnmpDataObject
{
	protected Integer index;

	public abstract String toString();

	public String toHtmlString()
	{
		return toString().replaceAll("\n", "<br>");
	}

	public Integer getIndex()
	{
		return index;
	}

	public String bytesToString(long bytes)
	{
		double result;

		if (bytes <= 1024)
		{
			return bytes + " B";
		}

		else if (bytes > 1024 && bytes <= 1048576)
		{
			result = ((double) bytes / 1024 * 100);
			return Double.toString((double) Math.round(result) / 100) + " KB";
		}

		else if (bytes > 1048576 && (bytes <= 1073741824))
		{
			result = ((double) bytes / 1024 / 1024 * 100);
			return Double.toString((double) Math.round(result) / 100) + " MB";
		}

		else
		{
			result = ((double) bytes / 1024 / 1024 / 1024 * 100);
			return Double.toString((double) Math.round(result) / 100) + " GB";
		}
	}
}
