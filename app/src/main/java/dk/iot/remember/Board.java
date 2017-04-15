package dk.iot.remember;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jesper on 14/04/2017.
 */

class Board {

    @SerializedName("name")
    private String name;
    @SerializedName("uuid")
    private String uuid;

    Board(String name, String uuid) {
        this.name = name;
        this.uuid = uuid;
    }

    public Board() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "Board{" +
                "name='" + name + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
