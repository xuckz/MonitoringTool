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

		sb.append("CPU #").append(this.index).append(" Description: ").append(description).append("\n");
		sb.append("CPU #").append(this.index).append(" Load: ").append(load.toString()).append(" %");

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
