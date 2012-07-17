package mr.xuckz.monitoringTool.controller;

import mr.xuckz.monitoringTool.model.Client;
import mr.xuckz.monitoringTool.persist.ClientDAO;
import mr.xuckz.monitoringTool.persist.StatusDAO;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HistoryStatusController implements Controller
{

    private ClientDAO clientDao;
    private StatusDAO statusDAO;

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        String ip = request.getParameter("ip");
        Client client = clientDao.findByIp(ip);
        List<Weather> weathers = weatherDAO.recentForLocation( location );

        Map<String,Object> model = new HashMap<String,Object>();
        model.put( "location", location );
        model.put( "weathers", weathers );

        return new ModelAndView("history", model);
    }

    public WeatherDAO getWeatherDAO() {
        return weatherDAO;
    }

    public void setWeatherDAO(WeatherDAO weatherDAO) {
        this.weatherDAO = weatherDAO;
    }

    public LocationDAO getLocationDAO() {
        return locationDAO;
    }

    public void setLocationDAO(LocationDAO locationDAO) {
        this.locationDAO = locationDAO;
    }
}
