/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Usuario
 */
@Entity
@Table(name = "ranking")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ranking.findAll", query = "SELECT r FROM Ranking r")
    , @NamedQuery(name = "Ranking.findByUsername", query = "SELECT r FROM Ranking r WHERE r.username = :username")
    , @NamedQuery(name = "Ranking.findByIdMap", query = "SELECT r FROM Ranking r WHERE r.idMap = :idMap ORDER BY r.time")
    , @NamedQuery(name = "Ranking.findByTime", query = "SELECT r FROM Ranking r WHERE r.time = :time")
    , @NamedQuery(name = "Ranking.findById", query = "SELECT r FROM Ranking r WHERE r.id = :id")
    , @NamedQuery(name = "Ranking.findPersonalRecordInMap", query = "SELECT r FROM Ranking r WHERE r.idMap = :idMap AND r.username = :username ORDER BY r.time")
})
public class Ranking implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "username")
    private String username;
    @Column(name = "id_map")
    private Integer idMap;
    @Column(name = "time")
    private Integer time;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;

    public Ranking() {
    }

    public Ranking(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getIdMap() {
        return idMap;
    }

    public void setIdMap(Integer idMap) {
        this.idMap = idMap;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ranking)) {
            return false;
        }
        Ranking other = (Ranking) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Ranking[ id=" + id + " ]";
    }
    
}
