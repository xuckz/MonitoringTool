package mr.xuckz.monitoringTool.services;

import mr.xuckz.monitoringTool.handler.StatusHandler;
import mr.xuckz.monitoringTool.model.Status;

public class StatusService
{
	private StatusHandler statusHandler;

    public StatusService()
	{

	}

    public Status retrieveStatus(String ip) throws Exception
    {
        Status status = StatusHandler.retrieveStatus(ip);

        return status;
    }

    public StatusHandler getStatusHandler()
    {
        return statusHandler;
    }

    public void setStatusHandler(StatusHandler statusHandler)
    {
        this.statusHandler = statusHandler;
    }
}
