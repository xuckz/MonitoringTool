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
}
