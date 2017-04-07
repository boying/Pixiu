package bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person implements Serializable{
    private String id;
    private int age;
    private boolean married;
    private List<Item> list;
    private Map<String, Item> map;
}
