package mr.xuckz.monitoringTool.snmp.data.device.cpu;

import mr.xuckz.monitoringTool.snmp.data.SnmpDataObject;

public class Cpu extends SnmpDataObject
{
	private String description;
	private Integer load;

	public Cpu(String description, Integer index, Integer load)
	{
		this.description = description;
		this.index = index;
		this.load = load;
	}

	public void update(Integer load)
	{
		this.load = load;
	}

	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		sb.append("CPU #" + this.index + " Description: " + description + "\n");
		sb.append("CPU #" + this.index + "Load: " + load.toString() + " %");

		return sb.toString();
	}

	public String getDescription()
	{
		return description;
	}

	public Integer getLoad()
	{
		return load;
	}
}
