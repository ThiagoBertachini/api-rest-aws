package com.bertachiniprojetos.unitTests.mappers.mocks;

import java.util.ArrayList;
import java.util.List;

public class MockPerson {


    public com.bertachiniprojetos.model.Person mockEntity() {
        return mockEntity(0);
    }
    
    public com.bertachiniprojetos.data.Vo.V1.PersonVO mockVO() {
        return mockVO(0);
    }
    
    public List<com.bertachiniprojetos.model.Person> mockEntityList() {
        List<com.bertachiniprojetos.model.Person> persons = new ArrayList<com.bertachiniprojetos.model.Person>();
        for (int i = 0; i < 14; i++) {
            persons.add(mockEntity(i));
        }
        return persons;
    }

    public List<com.bertachiniprojetos.data.Vo.V1.PersonVO> mockVOList() {
        List<com.bertachiniprojetos.data.Vo.V1.PersonVO> persons = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            persons.add(mockVO(i));
        }
        return persons;
    }
    
    public com.bertachiniprojetos.model.Person mockEntity(Integer number) {
        com.bertachiniprojetos.model.Person person = new com.bertachiniprojetos.model.Person();
        person.setAddress("Addres Test" + number);
        person.setFirstName("First Name Test" + number);
        person.setGender(((number % 2)==0) ? "Male" : "Female");
        person.setId(number.longValue());
        person.setLastName("Last Name Test" + number);
        return person;
    }

    public com.bertachiniprojetos.data.Vo.V1.PersonVO mockVO(Integer number) {
        com.bertachiniprojetos.data.Vo.V1.PersonVO person = new com.bertachiniprojetos.data.Vo.V1.PersonVO();
        person.setAddress("Addres Test" + number);
        person.setFirstName("First Name Test" + number);
        person.setGender(((number % 2)==0) ? "Male" : "Female");
        person.setKey(number.longValue());
        person.setLastName("Last Name Test" + number);
        return person;
    }

}
