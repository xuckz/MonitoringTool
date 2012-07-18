package mr.xuckz.monitoringTool.web.controller;

import mr.xuckz.monitoringTool.web.model.Client;
import mr.xuckz.monitoringTool.web.model.Status;
import mr.xuckz.monitoringTool.persist.ClientDAO;
import mr.xuckz.monitoringTool.persist.StatusDAO;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HistoryStatusController implements Controller
{

    private ClientDAO clientDAO;
    private StatusDAO statusDAO;

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        String ip = request.getParameter("ip");
        Client client = clientDAO.findByIp(ip);
        List<Status> statuses = statusDAO.recentForClient(client);

        Map<String,Object> model = new HashMap<String,Object>();
        model.put("client",client);
        model.put("statuses",statuses);

        return new ModelAndView("history", model);
    }

    public ClientDAO getClientDAO()
    {
        return clientDAO;
    }

    public void setClientDAO(ClientDAO clientDAO)
    {
        this.clientDAO = clientDAO;
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
