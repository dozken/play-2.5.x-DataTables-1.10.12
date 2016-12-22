package models;

import javax.persistence.Entity;

/**
 * @author techno
 * @version 1.0
 *          created on 10/21/16
 */
@Entity
public final class SimpleModel extends AbstractModel {

    public String name;

    public String firstname;

    public String toString() {
        return name;
    }
}
