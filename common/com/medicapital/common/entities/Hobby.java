package com.medicapital.common.entities;

public class Hobby implements SerializableEntity {

    private int hobbyId;
    private String name;

    @Override
    public int getId() {
        return hobbyId;
    }
    
    public void setId(int hobbyId) {
        this.hobbyId = hobbyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder data = new StringBuilder("[");
        data.append("id: " + hobbyId);
        data.append(", name: " + name);
        data.append("]");
        return data.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return true;
        } else if (!(obj instanceof Hobby)) {
            return false;
        }
        Hobby other = (Hobby) obj;
        return hobbyId == other.hobbyId;
    }

}
