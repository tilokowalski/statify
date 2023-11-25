package de.tilokowalski.model;

import de.tilokowalski.db.Record;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User extends Record {

  String name;

  public User(String name) {
    this("", name);
  }


  public User(String recordId, String name) {
    super("user", recordId);
    this.name = name;
  }
}
