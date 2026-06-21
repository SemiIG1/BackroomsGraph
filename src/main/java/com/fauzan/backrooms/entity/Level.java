package com.fauzan.backrooms.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "levels")
public class Level {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Integer difficulty;

    @Column(nullable = false)
    private String url;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.DETACH,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "level_edges",
            joinColumns = @JoinColumn(name = "source_level_id"),
            inverseJoinColumns = @JoinColumn(name = "target_level_id")
    )
    private Set<Level> exits;

    public Level() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void addExit(Level level) {
        if (exits == null) {
            exits = new HashSet<>();
        }
        exits.add(level);
    }

    public Set<Level> getExits() {
        return exits;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Level level = (Level) o;
        return url != null && url.equals(level.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
