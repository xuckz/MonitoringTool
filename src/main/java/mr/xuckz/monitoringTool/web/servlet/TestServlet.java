package mr.xuckz.monitoringTool.web.servlet;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.status.Status;
import ch.qos.logback.core.status.StatusManager;
import mr.xuckz.monitoringTool.config.ConfigParameters;
import mr.xuckz.monitoringTool.config.ConfigParametersLoader;
import mr.xuckz.monitoringTool.handler.SnmpHandler;
import mr.xuckz.monitoringTool.handler.SqlHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TestServlet extends HttpServlet
{
    private final ConfigParameters config;

	static final Logger log = LoggerFactory.getLogger(TestServlet.class);

    public TestServlet()
    {
        config = ConfigParametersLoader.loadParameters();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
        if(config == null)
        {
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);

            response.getWriter().println("<h1>ERROR - view logs</h1>");
        }


		else if ("y".equals(request.getParameter("Status")))
		{
			log.debug("Status called");

			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);

			response.getWriter().println("<h1>TEST</h1>");
		}

		else if ("y".equals(request.getParameter("dbtest")))
		{
			log.debug("dbtest called");

			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);

			SqlHandler sqlHandler = new SqlHandler(config);

			if(sqlHandler.connect())
			{
				response.getWriter().println("<h1>DB TEST SUCCESSFULL</h1>");
				sqlHandler.disconnect();
			}

			else
				response.getWriter().println("<h1>DB TEST FAILED</h1>");
		}

		else if ("y".equals(request.getParameter("logtest")))
		{
			log.debug("logtest called");

			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);

			log.trace("Hello World!");
			log.debug("How are you today?");
			log.info("I am fine.");
			log.warn("I love programming.");
			log.error("I am programming.");

			response.getWriter().println("<h1>LOG TEST</h1>");
		}

        else if ("y".equals(request.getParameter("snmptest")))
        {
            log.debug("snmptest called");

            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);

            response.getWriter().println("<h1>SNMP TEST</h1>");

            if(request.getParameter("oid") != null)
                response.getWriter().println("result: " + SnmpHandler.snmpGet("192.168.5.123", "public", request.getParameter("oid")));

            else
                response.getWriter().println("oid not found");
        }

		else if ("y".equals(request.getParameter("logstatus")))
		{
			log.debug("logstatus called");

			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);

			LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
			StatusManager statusManager = lc.getStatusManager();

			List<Status> statusList = statusManager.getCopyOfStatusList();

			response.getWriter().println("<table>" +
					"<thead>" +
					"<tr>" +
					"<th>Date</th>" +
					"<th>Message</th>" +
					"</tr>" +
					"</thead>" +
					"<tbody>");

			for (Status status : statusList)
			{
				response.getWriter().println("<tr>" +
						"<td>" + status.getDate() + "</td>" +
						"<td>" + status.getMessage() + "</td>" +
						"</tr>");
			}

			response.getWriter().println("</tbody>" +
					"</table>");
		}

		else
		{
			log.debug("default called");

			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);

			response.getWriter().println("<h1>SESSION ID</h1>");
			response.getWriter().println("session=" + request.getSession(true).getId());
		}
	}
}
