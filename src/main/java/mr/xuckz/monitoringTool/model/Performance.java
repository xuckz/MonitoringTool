package mr.xuckz.monitoringTool.model;

import javax.persistence.*;

@Entity
public class Performance
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String cpu;
    private String ram;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id", nullable = false)
    private Status status;

    public Performance()
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

    public String getCpu()
    {
        return cpu;
    }

    public void setCpu(String cpu)
    {
        this.cpu = cpu;
    }

    public String getRam()
    {
        return ram;
    }

    public void setRam(String ram)
    {
        this.ram = ram;
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