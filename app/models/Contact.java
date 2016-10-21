package models;

import play.data.validation.Constraints;

import javax.persistence.Entity;

@Entity
public final class Contact extends AbstractModel {

    @Constraints.Required
    public String name;

    public String title;

    public String email;

}