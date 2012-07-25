package mr.xuckz.monitoringTool.snmp.data.device.cpu;

public class Cpu
{
    private Integer index;
	private String description;
	private Integer load;

	public Cpu(){}

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
		String toString = "";
		toString += "Description: " + description + "\n";
		toString += "Load: " + load.toString() + " %";

		return toString;
	}

	public String toHtmlString()
	{
		return toString().replaceAll("\n", "<br>");
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

	public Integer getLoad()
	{
		return load;
	}

	public void setLoad(Integer load)
	{
		this.load = load;
	}
}
