package model;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Departament implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;

    public Departament(String name) {
        this.name = name;
    }

    public Departament(Integer id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Departament that)) return false;
        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Departament{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
