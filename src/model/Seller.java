package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

public class Seller implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;
    private String email;
    private Date bithDate;
    private Double baseSalary;
    private Departament departament;
    public Seller() {}

    public Seller(Integer id, String name, String email, Date bithDate, Double baseSalary, Departament departament) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.bithDate = bithDate;
        this.baseSalary = baseSalary;
        this.departament = departament;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBithDate() {
        return bithDate;
    }

    public void setBithDate(Date bithDate) {
        this.bithDate = bithDate;
    }

    public Double getBaseSalary() {
        return baseSalary;
    }

    public void setBaseSalary(Double baseSalary) {
        this.baseSalary = baseSalary;
    }

    public Departament getDepartament() {
        return departament;
    }

    public void setDepartament(Departament departament) {
        this.departament = departament;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seller seller)) return false;
        return Objects.equals(getId(), seller.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Seller{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", bithDate=" + bithDate +
                ", baseSalary=" + baseSalary +
                ", departament=" + departament +
                '}';
    }
}
