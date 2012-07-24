package mr.xuckz.monitoringTool.snmp.data.device.cpu;

public class CpuFactory
{
	public static Cpu getCpu(String description, Integer index, Integer load)
	{
		return new Cpu(description, index, load);
	}
}
