package mr.xuckz.monitoringTool.web.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(name = "Status.byClient",
                query = "from Status s where s.client = :client")
})

public class Status
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(cascade=CascadeType.ALL)
    private Client client;

    @OneToOne(mappedBy = "status", cascade = CascadeType.ALL)
    private Performance performance;

    @OneToOne(mappedBy = "status", cascade = CascadeType.ALL)
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
