package com.example.monitorizareangajati2.model;

import java.io.Serializable;

public interface Identifiable<Tid> extends Serializable{
    Tid getID();
    void setID(Tid id);
}
