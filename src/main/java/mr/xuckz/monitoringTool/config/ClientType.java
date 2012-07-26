package mr.xuckz.monitoringTool.config;

import java.util.HashMap;
import java.util.Map;

public enum ClientType
{
	DESKTOP,
	DDWRT,
	UNKNOWN;

	private String name;
	private static final Map<String, ClientType> lookup = new HashMap();

	static
	{
		for (ClientType st : ClientType.values())
			lookup.put(st.toString(), st);
	}

	private String getName()
	{
		return this.name;
	}

	public static ClientType get(String name)
	{
		if (lookup.containsKey(name))
		{
			return lookup.get(name);
		}

		else
		{
			return UNKNOWN;
		}
	}
}
