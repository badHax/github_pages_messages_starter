package io.github.badhax.githubpagesmessages.models;

import com.orm.SugarRecord;

/**
 * @author sultanofcardio
 *
 * This class has been written for you
 */

public class Message extends SugarRecord {
    private String name, email, message;
    private Long id;

    public Message(){
    }

    public Message(long id, String name, String email, String message){
        setId(id);
        this.name = name;
        this.email = email;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public Message setName(String name) {
        this.name = name;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Message setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Message setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
        super.setId(id);
    }
}
