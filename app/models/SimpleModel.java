package models;

import javax.persistence.Entity;

/**
 * @author techno
 * @version 1.0
 *          created on 10/21/16
 */
@Entity
public class SimpleModel extends AbstractModel {

    public String name;

    public void asd(){
        long a = this.id;
    }
}
