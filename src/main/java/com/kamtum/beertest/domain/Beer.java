package com.kamtum.beertest.domain;
        import javax.persistence.Entity;
        import javax.persistence.GeneratedValue;
        import javax.persistence.GenerationType;
        import javax.persistence.Id;
        import javax.validation.constraints.NotNull;

@Entity
public class Beer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull
    private int id;
    @NotNull
    private int brewery_id;
    @NotNull
    private String name;

    public Beer() {
    }

    public Beer(@NotNull String name, @NotNull int brewery_id) {
        this.name = name;
        this.brewery_id = brewery_id;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getBrewery_id() {
        return brewery_id;
    }
    public void setBrewery_id(int id) {
        this.brewery_id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
