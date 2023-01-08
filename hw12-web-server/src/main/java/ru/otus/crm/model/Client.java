package ru.otus.crm.model;


import lombok.*;
import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "client")
public class Client implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Phone> phoneList;

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this(id, name, null, Collections.emptyList());
    }

    public Client(Long id, String name, Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.phoneList = phones;
        phoneList.forEach(p -> p.setClient(this));
    }

    @Override
    public Client clone() {
        return new Client(this.id, this.name, this.address, this.phoneList);
    }

    @Override
    public String toString() {
        StringBuilder phones = new StringBuilder();
        for (int i=0; i<phoneList.size(); i++) {
            phones.append(i + 1 == phoneList.size() ? phoneList.get(i).getNumber() : phoneList.get(i).getNumber() + ", ");
        }
        return id + ". " + name + ", " + address.getStreet() + (phones.toString().isEmpty() ? "" : " (" + phones + ")");
    }

}
