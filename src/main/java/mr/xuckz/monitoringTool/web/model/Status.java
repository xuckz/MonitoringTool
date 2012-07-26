package mr.xuckz.monitoringTool.web.model;

import java.util.Date;

public class Status
{
    private Integer id;
    private Client client;
    private Performance performance;
    private FileSystem fileSystem;

    private Date date;

    public Status()
    {
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public Performance getPerformance()
    {
        return performance;
    }

    public void setPerformance(Performance performance)
    {
        this.performance = performance;
    }

    public FileSystem getFileSystem()
    {
        return fileSystem;
    }

    public void setFileSystem(FileSystem fileSystem)
    {
        this.fileSystem = fileSystem;
    }

    public Client getClient()
    {
        return client;
    }

    public void setClient(Client client)
    {
        this.client = client;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }
}
