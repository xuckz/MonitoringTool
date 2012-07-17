package mr.xuckz.monitoringTool.model;

import javax.persistence.*;

@Entity
public class FileSystem
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String free;
    private String used;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    public FileSystem()
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

    public String getFree()
    {
        return free;
    }

    public void setFree(String free)
    {
        this.free = free;
    }

    public String getUsed()
    {
        return used;
    }

    public void setUsed(String used)
    {
        this.used = used;
    }

    public Status getStatus()
    {
        return status;
    }

    public void setStatus(Status status)
    {
        this.status = status;
    }
}