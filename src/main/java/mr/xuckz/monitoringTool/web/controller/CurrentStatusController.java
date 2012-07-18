package mr.xuckz.monitoringTool.web.controller;

import mr.xuckz.monitoringTool.web.model.Status;
import mr.xuckz.monitoringTool.persist.StatusDAO;
import mr.xuckz.monitoringTool.web.services.StatusService;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CurrentStatusController implements Controller
{
    private StatusService statusService;
    private StatusDAO statusDAO;

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        String ip = request.getParameter("ip");
        Status status = statusService.retrieveStatus(ip);
        statusDAO.save(status);

        return new ModelAndView("status", "status", status);
    }

    public StatusService getStatusService()
    {
        return statusService;
    }

    public void setStatusService(StatusService statusService)
    {
        this.statusService = statusService;
    }

    public StatusDAO getStatusDAO()
    {
        return statusDAO;
    }

    public void setStatusDAO(StatusDAO statusDAO)
    {
        this.statusDAO = statusDAO;
    }
}
